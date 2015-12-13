package com.github.dadeo.pdfbox.model

import groovy.transform.Canonical
import org.apache.pdfbox.pdmodel.font.PDFont

@Canonical
class DParagraph implements DObject, Margined, Bordered, Padded {
    List<DPart> contents = []
    DFont font
    Justification justification
    float paragraphBottomDescentMultiplier = 2

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
        addContent text
    }

    DParagraph addContent(DPart part) {
        contents << part
        this
    }

    DParagraph addContent(String text) {
        addContent new DPart(text: text)
    }

    DParagraph leftShift(String text) {
        addContent(text)
    }

    DParagraph leftShift(DPart part) {
        addContent(part)
    }
}