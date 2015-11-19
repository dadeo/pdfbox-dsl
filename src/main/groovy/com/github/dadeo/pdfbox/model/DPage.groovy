package com.github.dadeo.pdfbox.model

class DPage implements Bordered {
    List<DObject> contents = []
    DFont font

    DPage addContent(DObject part) {
        contents << part
        this
    }
}