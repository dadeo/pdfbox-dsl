package com.github.dadeo.pdfbox.creator.writer

import com.github.dadeo.pdfbox.model.DPoint
import groovy.transform.Immutable

@Immutable
class WrittenTextResult {
    DPoint currentPosition
    float lastLineDescent
}