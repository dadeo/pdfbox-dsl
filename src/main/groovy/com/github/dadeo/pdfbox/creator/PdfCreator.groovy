package com.github.dadeo.pdfbox.creator

import com.github.dadeo.pdfbox.creator.writer.DocumentWriter
import com.github.dadeo.pdfbox.model.DDocument

class PdfCreator {
    byte[] createFor(DDocument document) {
        new DocumentWriter().write(document)
    }
}