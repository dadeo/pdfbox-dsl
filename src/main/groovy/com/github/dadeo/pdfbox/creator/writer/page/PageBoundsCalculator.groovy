package com.github.dadeo.pdfbox.creator.writer.page

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.model.DBounds
import com.github.dadeo.pdfbox.model.DPage

class PageBoundsCalculator {
    PageBoundsCalculations pageBoundsCalculations = new PageBoundsCalculations()

    void addCalculationsTo(DContext pageContext, DPage dPage) {
        DBounds borderBounds = pageBoundsCalculations.calculatePageBorderBounds(dPage)
        DBounds pageContentBounds = pageBoundsCalculations.calculatePageContentBounds(dPage)

        pageContext.bounds = pageContentBounds
        pageContext.borderBounds = borderBounds
        pageContext.currentLocation = pageContentBounds.leftTop()
    }

}