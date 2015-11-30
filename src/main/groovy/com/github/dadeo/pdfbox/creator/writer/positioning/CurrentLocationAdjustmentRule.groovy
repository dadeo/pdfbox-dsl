package com.github.dadeo.pdfbox.creator.writer.positioning

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.creator.writer.page.ElementDetails

interface CurrentLocationAdjustmentRule<T> {

    float calculateAdjustmentFor(DContext context, T currentObject, ElementDetails previousElementDetails)

}