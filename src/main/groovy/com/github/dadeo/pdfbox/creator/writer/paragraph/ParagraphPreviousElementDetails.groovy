package com.github.dadeo.pdfbox.creator.writer.paragraph

import com.github.dadeo.pdfbox.creator.writer.page.PreviousElementDetails
import com.github.dadeo.pdfbox.model.DBounds
import groovy.transform.Immutable


@Immutable
class ParagraphPreviousElementDetails implements PreviousElementDetails {
    float lastLineDescent
    boolean hasBottomBorder
    DBounds containingBounds
}