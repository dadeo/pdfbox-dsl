package com.github.dadeo.pdfbox.creator.writer.page

import com.github.dadeo.pdfbox.creator.writer.DContext


class PageContentsCurrentLocationAdjuster {

    void adjust(DContext pageContext, PreviousElementDetails previousElementDetails) {
        pageContext.currentLocation = previousElementDetails.containingBounds.leftBottom()
    }

}
