package com.github.dadeo.pdfbox.model

import groovy.transform.Canonical
import org.apache.pdfbox.pdmodel.font.PDFont

@Canonical
class DFont {
    PDFont font
    float size
}