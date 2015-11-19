package com.github.dadeo.pdfbox.model

import groovy.transform.AutoClone
import groovy.transform.Immutable


@Immutable
@AutoClone
class DPoint {
    float x, y

    DPoint plusX(float xAdjustment) {
        float adjustedX = (float) (x + xAdjustment)
        new DPoint(adjustedX, y)
    }

    DPoint plusY(float yAdjustment) {
        float adjustedY = (float) (y + yAdjustment)
        new DPoint(x, adjustedY)
    }

    DPoint offset(DPoint offsets) {
        new DPoint((float) (x + offsets.x), (float) (y + offsets.y))
    }
}
