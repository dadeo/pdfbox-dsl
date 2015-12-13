package com.github.dadeo.pdfbox.model

import groovy.transform.Canonical

@Canonical
class DPage implements Margined, Bordered, Padded {
    static final float ONE_INCH = 72f

    List<DObject> contents = []
    DFont font
    DBounds pageBounds = new DBounds((float) (11 * ONE_INCH), ((float) (8.5 * ONE_INCH)), 0, 0)

    DPage addContent(DObject part) {
        contents << part
        this
    }

    DPage leftShift(DObject part) {
        addContent(part)
    }
}