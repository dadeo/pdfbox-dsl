/**
 * Copyright to the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */
package com.github.dadeo.pdfbox.creator.writer

import com.github.dadeo.pdfbox.model.Bounds
import com.github.dadeo.pdfbox.model.Font
import com.github.dadeo.pdfbox.model.Point
import groovy.transform.Canonical
import org.apache.pdfbox.pdmodel.PDPageContentStream

import java.awt.*

@Canonical
class DWriter {
    PDPageContentStream contentStream

    void writeText(String text, Point location, Font font) {
        contentStream.beginText()
        contentStream.setNonStrokingColor font.color
        contentStream.setFont(font.font, font.size)
        contentStream.newLineAtOffset(location.x, location.y)
        contentStream.showText(text)
        contentStream.endText()
    }

    void drawLine(Point start, Point end, float lineWidth = 1, Color color = Color.black) {
        contentStream.lineWidth = lineWidth
        contentStream.setStrokingColor color
        contentStream.moveTo(start.x, start.y)
        contentStream.lineTo(end.x, end.y)
        contentStream.stroke()
    }

    void fillRectangle(Bounds bounds, Color color) {
        float width = bounds.width
        float height = bounds.height
        contentStream.setNonStrokingColor color
        contentStream.addRect(bounds.left, bounds.bottom, width, height)
        contentStream.fill()
    }
}