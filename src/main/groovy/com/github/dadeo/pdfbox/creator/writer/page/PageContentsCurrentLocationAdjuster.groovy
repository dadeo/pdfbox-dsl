package com.github.dadeo.pdfbox.creator.writer.page

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.model.DBounds


class PageContentsCurrentLocationAdjuster {

    void adjust(DContext pageContext, ElementDetails previousElementDetails) {
        pageContext.currentLocation = previousElementDetails.containingBounds.leftBottom().offset(0, -1)
        pageContext.contentsBounds = new DBounds(pageContext.currentLocation.y, pageContext.contentsBounds.right, pageContext.contentsBounds.bottom, pageContext.contentsBounds.left)
    }

}
