package com.github.dadeo.pdfbox.model

import groovy.transform.AutoClone
import groovy.transform.Immutable


@Immutable
@AutoClone
class DPoint {
    float x, y

    DPoint offsetX(float xAdjustment) {
        new DPoint((float) (x + xAdjustment), y)
    }

    DPoint offsetY(float yAdjustment) {
        new DPoint(x, (float) (y + yAdjustment))
    }

    DPoint offset(DPoint offsets) {
        new DPoint((float) (x + offsets.x), (float) (y + offsets.y))
    }

    DPoint offset(float offsetX, float offsetY) {
        new DPoint((float) (x + offsetX), (float) (y + offsetY))
    }
}
