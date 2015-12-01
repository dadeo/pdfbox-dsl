package com.github.dadeo.pdfbox.model

import org.apache.pdfbox.pdmodel.font.PDFont

class DParagraph implements DObject, Margined, Bordered, Padded {
    List<DPart> contents = []
    DFont font
    Justification justification

    DParagraph() {
    }

    DParagraph(String text, PDFont font, float fontSize) {
        this(text, new DFont(font, fontSize))
    }

    DParagraph(String text, DFont font) {
        this.font = font
        contents << new DPart(text: text, font: font)
    }

    DParagraph(String text) {
        addContent new DPart(text: text)
    }

    DParagraph addContent(DPart part) {
        contents << part
        this
    }
}