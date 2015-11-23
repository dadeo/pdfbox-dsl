package com.github.dadeo.pdfbox.creator.writer.page

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.model.DPage

class PageWriter {
    PageContextFactory pageContextFactory = new PageContextFactory()
    PagePdfBoxHelper pagePdfBoxHelper = new PagePdfBoxHelper()
    PageBoundsCalculator pageBoundsCalculator = new PageBoundsCalculator()
    PageContentsWriter pageContentsWriter = new PageContentsWriter()
    PageBorderDrawer pageBorderDrawer = new PageBorderDrawer()

    void write(DContext documentContext, DPage dPage) {
        DContext pageContext = pageContextFactory.createPageContextFrom(documentContext, dPage)

        pagePdfBoxHelper.initializePageObjects(pageContext)

        pageBoundsCalculator.addCalculationsTo(pageContext, dPage)

        pageContentsWriter.writeContents(dPage, pageContext)

        pageBorderDrawer.drawFor(dPage, pageContext)

        pagePdfBoxHelper.close(pageContext)
    }

}