/**
 * Copyright to the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */
package com.github.dadeo.pdfbox.creator.writer.object

import com.github.dadeo.pdfbox.model.*

class ObjectContentsWidthCalculator {

    float calculateFor(DObject object, DBounds areaBounds) {
        float right = areaBounds.right
        float left = areaBounds.left

        if (object instanceof Margined) {
            right -= object.marginRight
            left += object.marginLeft
        }

        if (object instanceof Bordered) {
            right -= object.borderRight
            left += object.borderLeft
        }

        if (object instanceof Padded) {
            right -= object.paddingRight
            left += object.paddingLeft
        }

        right - left
    }

}