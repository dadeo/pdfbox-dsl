package com.github.dadeo.pdfbox.creator.writer

import com.github.dadeo.pdfbox.creator.StringToken
import com.github.dadeo.pdfbox.model.*

class ParagraphWriter {

    void write(DContext pageContext, DParagraph dParagraph) {
        DContext childContext = (DContext) pageContext.cloneNotNull(font: dParagraph.font,
                                                                    currentLocation: pageContext.currentLocation)

        List<StringToken> tokens = []

        dParagraph.contents.each { DPart part ->
            tokens.addAll(BootStrap.stringTokenizer.tokenize(part.text, part.font ?: childContext.font))
        }

        DWriter writer = new DWriter(contentStream: childContext.pdContentStream)

        DPoint topLeft = childContext.currentLocation

        float width = childContext.bounds.width
        List<AssignedLine> assignedLines = BootStrap.tokensToLineAssigner.assignToLine(tokens, width, false)
        assignedLines.each { AssignedLine line ->
            childContext.currentLocation = new DPoint(x: childContext.bounds.left, y: childContext.currentLocation.y - line.tokens.font['size'].max())

            line.tokens.each { StringToken token ->
                writer.writeText(token.text, childContext.currentLocation, token.font)
                childContext.currentLocation = new DPoint(x: childContext.currentLocation.x + token.size, y: childContext.currentLocation.y)
            }
        }

        float descent = (float) ((childContext.font.font.fontDescriptor.descent / 1000) * childContext.font.size)
        float yOffset = (dParagraph.borderBottom == 0) ? 0 : descent

        drawBorder(dParagraph, writer, topLeft, childContext, yOffset)

        pageContext.currentLocation = new DPoint(pageContext.bounds.left, (float) (childContext.currentLocation.y + yOffset))

    }

    protected void drawBorder(DParagraph dParagraph, DWriter writer, DPoint topLeft, DContext childContext, float descent) {
        float top = topLeft.y
        float right = childContext.bounds.right
        float bottom = (float) (childContext.currentLocation.y + descent)
        float left = topLeft.x

        DBounds borderOffsets = dParagraph.borderOffsets

        float offsetTop = top - borderOffsets.top
        float offsetRight = right - borderOffsets.right
        float offsetBottom = bottom - borderOffsets.bottom
        float offsetLeft = left + borderOffsets.left

        if (dParagraph.borderTop != 0)
            writer.drawLine(new DPoint(left, offsetTop), new DPoint(right, offsetTop), dParagraph.borderTop)

        if (dParagraph.borderRight != 0)
            writer.drawLine(new DPoint(offsetRight, top), new DPoint(offsetRight, offsetBottom), dParagraph.borderRight)

        if (dParagraph.borderBottom != 0)
            writer.drawLine(new DPoint(left, offsetBottom), new DPoint(right, offsetBottom), dParagraph.borderBottom)

        if (dParagraph.borderLeft != 0)
            writer.drawLine(new DPoint(offsetLeft, top), new DPoint(offsetLeft, offsetBottom), dParagraph.borderLeft)
    }

}