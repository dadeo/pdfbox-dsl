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