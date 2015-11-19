package com.github.dadeo.pdfbox.creator

import com.github.dadeo.pdfbox.model.DFont
import groovy.transform.Immutable

@Immutable
class StringWidthCalculator {

    float calculateFor(String text, DFont font) {
        float units = font.font.getStringWidth(text)
        (float) ((units / 1000) * font.size)
    }

}