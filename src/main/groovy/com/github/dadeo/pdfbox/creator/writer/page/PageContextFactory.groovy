package com.github.dadeo.pdfbox.creator.writer.page

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.model.DPage

class PageContextFactory {

    DContext createPageContextFrom(DContext documentContext, DPage dPage) {
        DContext pageContext = documentContext.clone()
        pageContext.font = dPage.font ?: pageContext.font
        pageContext
    }

}