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

package io.element.android.features.messages.impl.voicemessages.composer

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import io.element.android.libraries.designsystem.components.dialogs.ConfirmationDialog
import io.element.android.libraries.ui.strings.CommonStrings

@Composable
internal fun VoiceMessagePermissionRationaleDialog(
    onContinue: () -> Unit,
    onDismiss: () -> Unit,
    appName: String,
) {
    ConfirmationDialog(
        content = stringResource(CommonStrings.error_missing_microphone_voice_rationale_android, appName),
        onSubmitClicked = onContinue,
        onDismiss = onDismiss,
        submitText = stringResource(CommonStrings.action_continue),
        cancelText = stringResource(CommonStrings.action_cancel),
    )
}
