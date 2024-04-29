/*
 * Copyright (c) 2023 New Vector Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.element.android.features.messages.impl.timeline.model

import io.element.android.libraries.matrix.api.core.EventId
import io.element.android.libraries.matrix.api.core.UserId
import io.element.android.libraries.matrix.api.permalink.PermalinkParser
import io.element.android.libraries.matrix.api.timeline.item.event.EventContent
import io.element.android.libraries.matrix.api.timeline.item.event.InReplyTo
import io.element.android.libraries.matrix.api.timeline.item.event.MessageContent
import io.element.android.libraries.matrix.api.timeline.item.event.ProfileTimelineDetails
import io.element.android.libraries.matrix.api.timeline.item.event.StickerContent
import io.element.android.libraries.matrix.api.timeline.item.event.TextMessageType
import io.element.android.libraries.matrix.ui.messages.toPlainText

sealed class InReplyToDetails(val eventId: EventId) {
    class Ready(
        eventId: EventId,
        val senderId: UserId,
        val senderProfile: ProfileTimelineDetails,
        val eventContent: EventContent?,
        val textContent: String?,
    ) : InReplyToDetails(eventId)

    class Loading(eventId: EventId) : InReplyToDetails(eventId)
    class Error(eventId: EventId, val message: String) : InReplyToDetails(eventId)
}

fun InReplyTo.map(
    permalinkParser: PermalinkParser,
) = when (this) {
    is InReplyTo.Ready -> InReplyToDetails.Ready(
        eventId = eventId,
        senderId = senderId,
        senderProfile = senderProfile,
        eventContent = content,
        textContent = when (content) {
            is MessageContent -> {
                val messageContent = content as MessageContent
                (messageContent.type as? TextMessageType)?.toPlainText(permalinkParser = permalinkParser) ?: messageContent.body
            }
            is StickerContent -> {
                val stickerContent = content as StickerContent
                stickerContent.body
            }
            else -> null
        }
    )
    is InReplyTo.Error -> InReplyToDetails.Error(eventId, message)
    is InReplyTo.NotLoaded -> InReplyToDetails.Loading(eventId)
    is InReplyTo.Pending -> InReplyToDetails.Loading(eventId)
}
