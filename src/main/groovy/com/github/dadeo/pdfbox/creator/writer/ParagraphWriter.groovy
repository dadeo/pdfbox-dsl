package com.github.dadeo.pdfbox.creator.writer

import com.github.dadeo.pdfbox.creator.StringToken
import com.github.dadeo.pdfbox.creator.StringTokenizer
import com.github.dadeo.pdfbox.creator.TokensToLineAssigner
import com.github.dadeo.pdfbox.model.*

class ParagraphWriter {
    StringTokenizer stringTokenizer = BootStrap.stringTokenizer
    TokensToLineAssigner tokensToLineAssigner = BootStrap.tokensToLineAssigner

    void write(DContext pageContext, DParagraph dParagraph) {
        DPoint topLeft = pageContext.currentLocation

        DBounds borderOffsets = dParagraph.borderTextOffsets

        DFont font = dParagraph.font ?: dParagraph.font ?: pageContext.font
        DBounds paragraphBounds = pageContext.bounds.offset(borderOffsets)
        DPoint textBlockStartLocation = topLeft.offset(borderOffsets.leftTop())

        DWriter writer = new DWriter(contentStream: pageContext.pdContentStream)

        DPoint textBlockEndLocation = writeTextToBoundedLocation(dParagraph, writer, paragraphBounds, textBlockStartLocation, font)

        float yOffset = (dParagraph.borderBottom == 0) ? 0 : font.descent

        DPoint bottomRight = new DPoint(pageContext.bounds.right, (float) (textBlockEndLocation.y + yOffset))
        drawBorder(dParagraph, writer, topLeft, bottomRight)

        pageContext.currentLocation = new DPoint(pageContext.bounds.left, (float) (textBlockEndLocation.y + yOffset + borderOffsets.bottom))
    }

    protected DPoint writeTextToBoundedLocation(DParagraph dParagraph, DWriter writer, DBounds paragraphBounds, DPoint currentLocation, DFont font) {
        List<StringToken> tokens = []

        dParagraph.contents.each { DPart part ->
            tokens.addAll(stringTokenizer.tokenize(part.text, part.font ?: font))
        }

        float width = paragraphBounds.width
        List<AssignedLine> assignedLines = tokensToLineAssigner.assignToLine(tokens, width, false)
        assignedLines.each { AssignedLine line ->
            currentLocation = new DPoint(x: paragraphBounds.left, y: currentLocation.y - line.tokens.font['size'].max())

            line.tokens.each { StringToken token ->
                writer.writeText(token.text, currentLocation, token.font)
                currentLocation = new DPoint(x: currentLocation.x + token.size, y: currentLocation.y)
            }
        }

        currentLocation
    }

    protected void drawBorder(DParagraph dParagraph, DWriter writer, DPoint topLeft, DPoint bottomRight) {
        float top = topLeft.y
        float right = bottomRight.x
        float bottom = bottomRight.y
        float left = topLeft.x

        DBounds borderOffsets = dParagraph.borderLineOffsets

        float offsetTop = top + borderOffsets.top
        float offsetRight = right + borderOffsets.right
        float offsetBottom = bottom + borderOffsets.bottom
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