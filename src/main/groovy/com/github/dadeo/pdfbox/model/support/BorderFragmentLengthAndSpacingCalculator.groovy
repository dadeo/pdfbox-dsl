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
package com.github.dadeo.pdfbox.model.support


class BorderFragmentLengthAndSpacingCalculator {

    BorderFragmentLengthAndSpacing calculate(float totalLength, int fragmentCount, float fragmentLength, float spacingBetweenFragments) {
        if (fragmentLength != 0 && spacingBetweenFragments != 0) {
            throw new RuntimeException("Either fragment length of spacing may be specified, not both.")
        } else if (fragmentLength != 0f) {
            float totalLengthOfSpacing = totalLength - (fragmentCount * fragmentLength)
            float calculatedSpacingLength = totalLengthOfSpacing / (fragmentCount - 1)
            new BorderFragmentLengthAndSpacing(fragmentLength, calculatedSpacingLength)
        } else if (spacingBetweenFragments != 0f) {
            float totalLengthOfFragments = totalLength - ((fragmentCount - 1) * spacingBetweenFragments)
            float calculatedFragmentLength = totalLengthOfFragments / fragmentCount
            new BorderFragmentLengthAndSpacing(calculatedFragmentLength, spacingBetweenFragments)
        } else {
            float calculatedFragmentLength = totalLength / (fragmentCount * 2 - 1)
            new BorderFragmentLengthAndSpacing(calculatedFragmentLength, calculatedFragmentLength)
        }
    }

}