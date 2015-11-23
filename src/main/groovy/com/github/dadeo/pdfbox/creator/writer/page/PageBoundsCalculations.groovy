package com.github.dadeo.pdfbox.creator.writer.page

import com.github.dadeo.pdfbox.model.DBounds
import com.github.dadeo.pdfbox.model.DPage

class PageBoundsCalculations {

    DBounds calculatePageBorderBounds(DPage dPage) {
        dPage.pageBounds
             .offset(dPage.marginOffsets)
    }

    DBounds calculatePageContentBounds(DPage dPage) {
        dPage.pageBounds
             .offset(dPage.marginOffsets)
             .offset(dPage.borderTextOffsets)
             .offset(dPage.paddingOffsets)
    }

}