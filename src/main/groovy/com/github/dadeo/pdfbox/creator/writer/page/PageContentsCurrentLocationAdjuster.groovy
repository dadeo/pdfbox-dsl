package com.github.dadeo.pdfbox.creator.writer.page

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.model.DBounds


class PageContentsCurrentLocationAdjuster {

    void adjust(DContext pageContext, ElementDetails previousElementDetails) {
        float newTop = previousElementDetails.containingBounds.bottom - 1
        DBounds contentsBounds = pageContext.contentsBounds
        pageContext.contentsBounds = new DBounds(newTop, contentsBounds.right, contentsBounds.bottom, contentsBounds.left)
    }

}
