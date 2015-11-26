package com.github.dadeo.pdfbox.creator.writer.paragraph

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.model.DParagraph

class ParagraphContextFactory {

    DContext createContextFrom(DContext pageContext, DParagraph paragraph) {
        DContext paragraphContext = pageContext.clone()
        paragraphContext.parent = pageContext
        paragraphContext.font = paragraph.font ?: paragraphContext.font
        paragraphContext
    }

}