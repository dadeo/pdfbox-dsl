package com.github.dadeo.pdfbox.creator.writer

import com.github.dadeo.pdfbox.model.DBounds
import com.github.dadeo.pdfbox.model.DFont
import com.github.dadeo.pdfbox.model.DPoint
import groovy.transform.Canonical
import org.apache.pdfbox.pdmodel.PDPageContentStream

import java.awt.*

@Canonical
class DWriter {
    PDPageContentStream contentStream

    void writeText(String text, DPoint location, DFont font) {
        contentStream.beginText()
        contentStream.nonStrokingColor = font.color
        contentStream.setFont(font.font, font.size)
        contentStream.newLineAtOffset(location.x, location.y)
        contentStream.showText(text)
        contentStream.endText()
    }

    void drawLine(DPoint start, DPoint end, float lineWidth = 1, Color color = Color.black) {
        contentStream.lineWidth = lineWidth
        contentStream.strokingColor = color
        contentStream.moveTo(start.x, start.y)
        contentStream.lineTo(end.x, end.y)
        contentStream.stroke()
    }

    void drawRectangle(DBounds dBounds, float lineWidth = 1) {
        drawLine(dBounds.leftTop(), dBounds.rightTop(), lineWidth)
        drawLine(dBounds.leftTop(), dBounds.leftBottom(), lineWidth)
        drawLine(dBounds.leftBottom(), dBounds.rightBottom(), lineWidth)
        drawLine(dBounds.rightTop(), dBounds.rightBottom(), lineWidth)
    }
}