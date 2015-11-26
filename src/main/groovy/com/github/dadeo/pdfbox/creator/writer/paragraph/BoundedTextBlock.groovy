package com.github.dadeo.pdfbox.creator.writer.paragraph

import com.github.dadeo.pdfbox.creator.writer.text.AssignedLine
import groovy.transform.Canonical

@Canonical
class BoundedTextBlock {
    List<AssignedLine> assignedLines = []
    float width

    float getHeight() {
        assignedLines.inject(0f) { a, b -> a + b.height }
    }

    float getLastLineDescent() {
        assignedLines ? assignedLines[-1].descent : 0
    }
}