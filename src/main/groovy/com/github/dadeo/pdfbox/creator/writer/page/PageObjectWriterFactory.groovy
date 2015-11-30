package com.github.dadeo.pdfbox.creator.writer.page

import com.github.dadeo.pdfbox.creator.writer.hr.HorizontalRuleWriter
import com.github.dadeo.pdfbox.creator.writer.paragraph.ParagraphWriter
import com.github.dadeo.pdfbox.model.DHorizontalRule
import com.github.dadeo.pdfbox.model.DObject
import com.github.dadeo.pdfbox.model.DParagraph


class PageObjectWriterFactory {
    ParagraphWriter paragraphWriter = new ParagraphWriter()
    HorizontalRuleWriter horizontalRuleWriter = new HorizontalRuleWriter()

    PageObjectWriter<? extends DObject> createWriter(DObject dObject) {
        switch (dObject) {
            case DParagraph:
                paragraphWriter
                break
            case DHorizontalRule:
                horizontalRuleWriter
                break
            default:
                throw new RuntimeException("PageObjectWriter for ${dObject.class} is not supported.")
        }
    }

}