package com.github.dadeo.pdfbox.creator.writer.page

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.model.DPage

class PageContextFactory {

    DContext createContextFrom(DContext documentContext, DPage dPage) {
        DContext pageContext = documentContext.clone()
        pageContext.parent = documentContext
        pageContext.font = dPage.font ?: pageContext.font
        pageContext
    }

}