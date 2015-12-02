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