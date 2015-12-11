package com.github.dadeo.pdfbox.creator.writer

import com.github.dadeo.pdfbox.model.DBounds
import com.github.dadeo.pdfbox.model.DFont
import com.github.dadeo.pdfbox.model.Justification
import com.github.dadeo.pdfbox.model.VerticalAlignment
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
    DFont font
    Justification textJustification = Justification.LEFT
    VerticalAlignment verticalAlignment = VerticalAlignment.TOP

    void setTextJustification(Justification value) {
        if (value)
            this.textJustification = value
    }

    void setVerticalAlignment(VerticalAlignment value) {
        if (value)
            this.verticalAlignment = value
    }
}