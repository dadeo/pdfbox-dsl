package com.github.dadeo.pdfbox.creator.writer.paragraph

import com.github.dadeo.pdfbox.creator.writer.DContext


class TextBlockLineWriterFactory {

    BoundedTextBlockLineWriter createWriterFor(DContext context) {
        new BoundedTextBlockLineWriter()
    }

}