package com.github.dadeo.pdfbox.creator.writer.paragraph

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.model.DParagraph

class ParagraphContextFactory {

    DContext createContextFrom(DContext parentContext, DParagraph paragraph) {
        DContext paragraphContext = parentContext.clone()
        paragraphContext.parent = parentContext
        paragraphContext.font = paragraph.font ?: paragraphContext.font
        paragraphContext.textJustification = paragraph.justification ?: parentContext.textJustification
        paragraphContext
    }

}