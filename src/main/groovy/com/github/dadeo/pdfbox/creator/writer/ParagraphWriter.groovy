package com.github.dadeo.pdfbox.creator.writer

import com.github.dadeo.pdfbox.creator.StringToken
import com.github.dadeo.pdfbox.creator.StringTokenizer
import com.github.dadeo.pdfbox.creator.TokensToLineAssigner
import com.github.dadeo.pdfbox.model.*

class ParagraphWriter {
    StringTokenizer stringTokenizer = BootStrap.stringTokenizer
    TokensToLineAssigner tokensToLineAssigner = BootStrap.tokensToLineAssigner

    PreviousElementDetails write(DContext pageContext, DParagraph dParagraph, PreviousElementDetails previousElementDetails) {
        DBounds marginOffsets = dParagraph.marginOffsets
        DBounds borderOffsets = dParagraph.borderTextOffsets

        DPoint topLeft = calculateTopLeftLocation(pageContext, previousElementDetails, borderOffsets)
            .offset(marginOffsets.leftTop())

        DFont font = dParagraph.font ?: dParagraph.font ?: pageContext.font
        DBounds paragraphBounds = pageContext.bounds
                                             .offset(borderOffsets)
                                             .offset(marginOffsets)

        DPoint textBlockStartLocation = topLeft.offset(borderOffsets.leftTop())

        DWriter writer = new DWriter(contentStream: pageContext.pdContentStream)

        WrittenTextResult writtenTextResult = writeTextToBoundedLocation(dParagraph, writer, paragraphBounds, textBlockStartLocation, font)
        DPoint textBlockEndLocation = writtenTextResult.currentPosition

        float borderYOffset = (borderOffsets.bottom == 0) ? 0 : font.descent

        DPoint bottomRight = new DPoint(pageContext.bounds.right, (float) (textBlockEndLocation.y + borderYOffset))
            .offsetX(marginOffsets.right)

        drawBorder(dParagraph, writer, topLeft, bottomRight)

        float currentLocationBorderYOffset = borderOffsets.bottom == 0 ? 0 : borderYOffset + 1
        pageContext.currentLocation = new DPoint(pageContext.bounds.left, (float) (textBlockEndLocation.y + currentLocationBorderYOffset + borderOffsets.bottom))
            .offsetY(marginOffsets.bottom)

        new ParagraphPreviousElementDetails(lastLineDescent: writtenTextResult.lastLineDescent, hasBottomBorder: borderOffsets.bottom)
    }

    /**
     * Calculates the position the paragraph should start relative to the previous element.  This location is before
     * margin and padding are taken in to count.
     */
    protected DPoint calculateTopLeftLocation(DContext pageContext, PreviousElementDetails previousElementDetails, DBounds borderOffsets) {
        if (borderOffsets.top || borderOffsets.left || borderOffsets.right) {
            float additionalOffsetFromPrevious

            switch (previousElementDetails) {
                case ParagraphPreviousElementDetails:
                    ParagraphPreviousElementDetails details = (ParagraphPreviousElementDetails) previousElementDetails
                    additionalOffsetFromPrevious = details.hasBottomBorder ? 0 : details.lastLineDescent
                    break
                default:
                    additionalOffsetFromPrevious = 0
            }

            pageContext.currentLocation.offsetY(additionalOffsetFromPrevious)
        } else {
            pageContext.currentLocation
        }
    }

    protected WrittenTextResult writeTextToBoundedLocation(DParagraph dParagraph, DWriter writer, DBounds paragraphBounds, DPoint currentLocation, DFont font) {
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

        float lastLineDescent = assignedLines[-1].tokens.font.descent.max()

        new WrittenTextResult(currentLocation, lastLineDescent)
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