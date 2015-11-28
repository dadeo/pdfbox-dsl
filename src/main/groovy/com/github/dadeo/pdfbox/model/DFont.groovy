package com.github.dadeo.pdfbox.model

import groovy.transform.Canonical
import org.apache.pdfbox.pdmodel.font.PDFont

import java.awt.*

@Canonical
class DFont {
    PDFont font
    float size
    Color color = Color.black

    float getDescent() {
        (float) ((font.fontDescriptor.descent / 1000) * size)
    }
}