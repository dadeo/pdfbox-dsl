package com.github.dadeo.pdfbox.creator.writer.paragraph

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.creator.writer.page.PreviousElementDetails
import com.github.dadeo.pdfbox.model.Bordered


interface ParagraphAdjustmentRule {

    float calculateAdjustmentFor(DContext paragraphContext, Bordered bordered, PreviousElementDetails previousElementDetails)

}