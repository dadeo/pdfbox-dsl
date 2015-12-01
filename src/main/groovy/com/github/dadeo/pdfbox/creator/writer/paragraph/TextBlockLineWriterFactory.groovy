package com.github.dadeo.pdfbox.creator.writer.paragraph

import com.github.dadeo.pdfbox.creator.BootStrap
import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.model.Justification


class TextBlockLineWriterFactory {

    BoundedTextBlockLineWriter createWriterFor(DContext context) {
        switch (context.textJustification) {
            case Justification.LEFT:
                return BootStrap.leftJustifiedTextBlockLineWriter

            case Justification.RIGHT:
                return BootStrap.rightJustifiedTextBlockLineWriter

            default:
                BootStrap.leftJustifiedTextBlockLineWriter
        }
    }

}