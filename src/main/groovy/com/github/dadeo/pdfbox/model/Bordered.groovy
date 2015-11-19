package com.github.dadeo.pdfbox.model

import java.awt.*

trait Bordered {

    float borderTop = 0
    float borderRight = 0
    float borderBottom = 0
    float borderLeft = 0

    Color borderTopColor = Color.black
    Color borderRightColor = Color.black
    Color borderBottomColor = Color.black
    Color borderLeftColor = Color.black

    void setBorder(float newValue) {
        borderTop = newValue
        borderRight = newValue
        borderBottom = newValue
        borderLeft = newValue
        this
    }

    void setBorderColor(Color newColor) {
        borderTopColor = newColor
        borderRightColor = newColor
        borderBottomColor = newColor
        borderLeftColor = newColor
        this
    }
    
    DBounds getBorderOffsets() {
        float topOffset = borderTop / 2
        float rightOffset = borderRight / 2
        float bottomOffset = borderBottom / 2
        float leftOffset = borderLeft / 2
        new DBounds(topOffset, rightOffset, bottomOffset, leftOffset)
    }
}