package com.github.dadeo.pdfbox.model


trait Margined {

    float marginTop = 0
    float marginRight = 0
    float marginBottom = 0
    float marginLeft = 0

    void setMargin(float value) {
        marginTop = value
        marginRight = value
        marginBottom = value
        marginLeft = value
        this
    }

    DBounds getMarginOffsets() {
        new DBounds(-marginTop, -marginRight, -marginBottom, marginLeft)
    }
}