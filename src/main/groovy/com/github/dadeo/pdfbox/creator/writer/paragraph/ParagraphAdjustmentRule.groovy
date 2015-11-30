package com.github.dadeo.pdfbox.creator.writer.paragraph

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.creator.writer.page.ElementDetails
import com.github.dadeo.pdfbox.model.Bordered


interface ParagraphAdjustmentRule {

    float calculateAdjustmentFor(DContext context, Bordered bordered, ElementDetails previousElementDetails)

}