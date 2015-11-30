package com.github.dadeo.pdfbox.creator.writer.hr

import com.github.dadeo.pdfbox.creator.writer.page.ElementDetails
import com.github.dadeo.pdfbox.model.DBounds
import groovy.transform.Immutable

@Immutable
class HorizontalRulerElementDetails implements ElementDetails {
    DBounds containingBounds
}