package com.github.dadeo.pdfbox.creator.writer.paragraph

import com.github.dadeo.pdfbox.creator.writer.page.ElementDetails
import com.github.dadeo.pdfbox.model.DBounds
import groovy.transform.Immutable


@Immutable
class ParagraphElementDetails implements ElementDetails {
    float lastLineDescent
    boolean hasBottomBorder
    DBounds containingBounds
}