package com.github.dadeo.pdfbox.creator.writer.paragraph

import com.github.dadeo.pdfbox.model.DPoint
import groovy.transform.Immutable

@Immutable
class WrittenTextResult {
    DPoint currentPosition
    float lastLineDescent
}