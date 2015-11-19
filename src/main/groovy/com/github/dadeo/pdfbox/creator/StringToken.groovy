package com.github.dadeo.pdfbox.creator

import com.github.dadeo.pdfbox.model.DFont
import groovy.transform.Canonical

@Canonical
class StringToken {
    String text
    float size
    DFont font
}