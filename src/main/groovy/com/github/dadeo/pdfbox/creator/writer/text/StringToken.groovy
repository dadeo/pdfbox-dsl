package com.github.dadeo.pdfbox.creator.writer.text

import com.github.dadeo.pdfbox.model.DFont
import groovy.transform.Canonical

@Canonical
class StringToken {
    String text
    float size
    DFont font

    float getDescent() {
        font?.descent ?: 0
    }

}