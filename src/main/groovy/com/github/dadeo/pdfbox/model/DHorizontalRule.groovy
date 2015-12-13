package com.github.dadeo.pdfbox.model

import groovy.transform.Canonical

import java.awt.*

@Canonical
class DHorizontalRule implements DObject, Margined, Bordered, Padded {

    float thickness = 1
    Color color = Color.black

}