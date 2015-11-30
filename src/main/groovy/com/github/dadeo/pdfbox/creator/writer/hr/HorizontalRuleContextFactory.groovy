package com.github.dadeo.pdfbox.creator.writer.hr

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.model.DHorizontalRule

class HorizontalRuleContextFactory {

    DContext createContextFrom(DContext pageContext, DHorizontalRule horizontalRule) {
        DContext paragraphContext = pageContext.clone()
        paragraphContext.parent = pageContext
        paragraphContext
    }

}