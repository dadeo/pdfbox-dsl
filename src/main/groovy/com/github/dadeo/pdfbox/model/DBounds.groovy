package com.github.dadeo.pdfbox.model

import groovy.transform.CompileStatic
import groovy.transform.Immutable
import groovy.transform.ToString


@CompileStatic
@Immutable
@ToString
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

    float getHeight() {
        top - bottom
    }

    DBounds offset(DBounds offsets) {
        new DBounds((float) (top + offsets.top), (float) (right + offsets.right), (float) (bottom + offsets.bottom), (float) (left + offsets.left))
    }

    DBounds offset(float offsetX, float offsetY) {
        new DBounds((float) (top + offsetY), (float) (right + offsetX), (float) (bottom + offsetY), (float) (left + offsetX))
    }

    DBounds offset(float topOffset, float rightOffset, float bottomOffset, float leftOffset) {
        new DBounds((float) (top + topOffset), (float) (right + rightOffset), (float) (bottom + bottomOffset), (float) (left + leftOffset))
    }
}
