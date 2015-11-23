package com.github.dadeo.pdfbox.creator.writer

import com.github.dadeo.pdfbox.model.DBounds
import com.github.dadeo.pdfbox.model.DFont
import com.github.dadeo.pdfbox.model.DPoint
import groovy.transform.AutoClone
import groovy.transform.Canonical
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.PDPageContentStream

@Canonical
@AutoClone
class DContext implements Cloneable {
    PDDocument pdDocument
    PDPage pdPage
    PDPageContentStream pdContentStream
    DBounds borderBounds
    DBounds pageContentBounds
    DWriter writer
    DBounds bounds
    DPoint currentLocation
    DFont font

    DContext cloneNotNull(Map props) {
        DContext clone = clone()
        props.each { k, v ->
            if (v != null)
                clone[k] = v
        }
        clone
    }

}