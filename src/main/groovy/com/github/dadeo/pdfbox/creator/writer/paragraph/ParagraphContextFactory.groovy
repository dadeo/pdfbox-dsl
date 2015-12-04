package com.github.dadeo.pdfbox.creator.writer.paragraph

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.creator.writer.object.ObjectContextFactory
import com.github.dadeo.pdfbox.model.DParagraph

class ParagraphContextFactory extends ObjectContextFactory {

    DContext createContextFrom(DContext parentContext, DParagraph paragraph) {
        DContext paragraphContext = super.createContextFrom(parentContext, paragraph)
        paragraphContext.font = paragraph.font ?: paragraphContext.font
        paragraphContext.textJustification = paragraph.justification ?: parentContext.textJustification
        paragraphContext
    }

}