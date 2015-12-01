package com.github.dadeo.pdfbox.creator.writer.object

import com.github.dadeo.pdfbox.creator.writer.page.ElementDetails

interface ObjectWritable {

    void write()

    ElementDetails getElementDetails()

}