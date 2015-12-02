package com.github.dadeo.pdfbox.creator.writer.paragraph

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.model.Justification

class TextBlockLineWriterFactory {
    BoundedTextBlockLineWriter leftJustifiedTextBlockLineWriter
    BoundedTextBlockLineWriter rightJustifiedTextBlockLineWriter
    BoundedTextBlockLineWriter centerJustifiedTextBlockLineWriter

    BoundedTextBlockLineWriter createWriterFor(DContext context) {
        switch (context.textJustification) {
            case Justification.LEFT:
                return leftJustifiedTextBlockLineWriter

            case Justification.CENTER:
                return centerJustifiedTextBlockLineWriter

            case Justification.RIGHT:
                return rightJustifiedTextBlockLineWriter

            default:
                leftJustifiedTextBlockLineWriter
        }
    }

}