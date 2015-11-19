package com.github.dadeo.pdfbox.model

import org.apache.pdfbox.pdmodel.font.PDType1Font

class DDocument {
    List<DPage> pages = []
    DFont font = new DFont(PDType1Font.HELVETICA_BOLD, 12)

    DDocument addPage(DPage page) {
        pages << page
        this
    }
}