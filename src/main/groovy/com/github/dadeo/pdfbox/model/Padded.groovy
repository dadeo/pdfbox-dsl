package com.github.dadeo.pdfbox.model


trait Padded {

    float paddingTop = 0
    float paddingRight = 0
    float paddingBottom = 0
    float paddingLeft = 0

    void setPadding(float value) {
        paddingTop = value
        paddingRight = value
        paddingBottom = value
        paddingLeft = value
        this
    }

    DBounds getPaddingOffsets() {
        new DBounds(-paddingTop, -paddingRight, -paddingBottom, paddingLeft)
    }
}