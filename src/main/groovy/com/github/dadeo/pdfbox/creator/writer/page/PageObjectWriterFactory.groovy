package com.github.dadeo.pdfbox.creator.writer.page

import com.github.dadeo.pdfbox.creator.writer.paragraph.ParagraphWriter
import com.github.dadeo.pdfbox.model.DObject
import com.github.dadeo.pdfbox.model.DParagraph


class PageObjectWriterFactory {
    ParagraphWriter paragraphWriter = new ParagraphWriter()

    PageObjectWriter<? extends DObject> createWriter(DObject dObject) {
        switch (dObject) {
            case DParagraph:
                paragraphWriter
                break
            default:
                throw new RuntimeException("PageObjectWriter for ${dObject.class} is not supported.")
        }
    }

}