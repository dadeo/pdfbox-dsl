package com.github.dadeo.pdfbox.creator.writer.page

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.creator.writer.border.BorderDrawer
import com.github.dadeo.pdfbox.model.DPage

class PageWriter {
    PageContextFactory pageContextFactory
    PagePdfBoxHelper pagePdfBoxHelper
    PageBoundsCalculator pageBoundsCalculator
    PageContentsWriter pageContentsWriter
    BorderDrawer pageBorderDrawer

    void write(DContext documentContext, DPage dPage) {
        DContext pageContext = pageContextFactory.createContextFrom(documentContext, dPage)

        pagePdfBoxHelper.initializePageObjects(pageContext)

        pageBoundsCalculator.addCalculationsTo(pageContext, dPage)

        pageContentsWriter.writeContents(dPage, pageContext)

        pageBorderDrawer.drawFor(dPage, pageContext)

        pagePdfBoxHelper.close(pageContext)
    }

}