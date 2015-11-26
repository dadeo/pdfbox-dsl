package com.github.dadeo.pdfbox.creator.writer.text

import groovy.transform.Canonical

@Canonical
class AssignedLine {
    List<StringToken> tokens = []

    float getHeight() {
        tokens.font['size'].max() ?: 0
    }

    float getDescent() {
        tokens.inject(0f) { a, b -> float d = b.descent; a < d ? a : d }
    }

}