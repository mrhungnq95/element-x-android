/*
 * Copyright (c) 2024 New Vector Ltd
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

package io.element.android.features.login.impl.di

import com.squareup.anvil.annotations.ContributesTo
import com.squareup.anvil.annotations.MergeSubcomponent
import io.element.android.libraries.architecture.NodeFactoriesBindings
import io.element.android.libraries.di.AppScope
import io.element.android.libraries.di.SingleIn

@SingleIn(QrCodeLoginScope::class)
@MergeSubcomponent(QrCodeLoginScope::class)
interface QrCodeLoginComponent : NodeFactoriesBindings {
    @MergeSubcomponent.Builder
    interface Builder {
        fun build(): QrCodeLoginComponent
    }

    @ContributesTo(AppScope::class)
    interface ParentBindings {
        fun qrCodeLoginComponentBuilder(): Builder
    }
}
