package com.github.dadeo.pdfbox.model


class Cell implements DObject, Margined, Bordered, Padded {
    List<DObject> contents = []
    VerticalAlignment verticalAlignment

    Cell() {}

    Cell(DObject object) {
        addContent(object)
    }

    Cell addContent(DObject object) {
        contents << object
        this
    }

    Cell leftShift(DObject object) {
        addContent(object)
    }
}