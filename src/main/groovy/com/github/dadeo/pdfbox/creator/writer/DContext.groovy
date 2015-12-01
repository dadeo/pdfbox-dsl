package com.github.dadeo.pdfbox.creator.writer

import com.github.dadeo.pdfbox.model.DBounds
import com.github.dadeo.pdfbox.model.DFont
import com.github.dadeo.pdfbox.model.DPoint
import com.github.dadeo.pdfbox.model.Justification
import groovy.transform.AutoClone
import groovy.transform.Canonical
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.PDPageContentStream

@Canonical
@AutoClone
class DContext implements Cloneable {
    DContext parent
    PDDocument pdDocument
    PDPage pdPage
    PDPageContentStream pdContentStream
    DWriter writer
    DBounds containingBounds
    DBounds contentsBounds
    DBounds borderBounds
    DPoint currentLocation
    DFont font
    Justification textJustification
}