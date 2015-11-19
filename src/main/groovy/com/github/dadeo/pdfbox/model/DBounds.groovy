package com.github.dadeo.pdfbox.model

import groovy.transform.CompileStatic
import groovy.transform.Immutable


@Immutable
@CompileStatic
class DBounds {
    float top, right, bottom, left

    DPoint leftTop() {
        new DPoint(left, top)
    }

    DPoint rightTop() {
        new DPoint(right, top)
    }

    DPoint leftBottom() {
        new DPoint(left, bottom)
    }

    DPoint rightBottom() {
        new DPoint(right, bottom)
    }

    float getWidth() {
        right - left
    }

    DBounds offset(float topOffset, float rightOffset, float bottomOffset, float leftOffset) {
        new DBounds((float) (top + topOffset), (float) (right + rightOffset), (float) (bottom + bottomOffset), (float) (left + leftOffset))
    }
}
