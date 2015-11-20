package com.github.dadeo.pdfbox.creator.writer

import com.github.dadeo.pdfbox.creator.StringToken
import com.github.dadeo.pdfbox.creator.StringTokenizer
import com.github.dadeo.pdfbox.creator.TokensToLineAssigner
import com.github.dadeo.pdfbox.model.*

class ParagraphWriter {
    StringTokenizer stringTokenizer = BootStrap.stringTokenizer
    TokensToLineAssigner tokensToLineAssigner = BootStrap.tokensToLineAssigner
    LineBorder lineBorder = new LineBorder()

    PreviousElementDetails write(DContext pageContext, DParagraph dParagraph, PreviousElementDetails previousElementDetails) {
        DFont font = dParagraph.font ?: dParagraph.font ?: pageContext.font
        DWriter writer = pageContext.writer

        DBounds marginOffsets = dParagraph.marginOffsets
        DBounds borderOffsets = dParagraph.borderTextOffsets
        DBounds paddingOffsets = dParagraph.paddingOffsets

        DPoint borderTopLeft = pageContext.currentLocation
                                          .offsetY(calculatePreviousElementYOffset(previousElementDetails, borderOffsets))
                                          .offset(marginOffsets.leftTop())

        DBounds paragraphBounds = pageContext.bounds
                                             .offset(marginOffsets)
                                             .offset(borderOffsets)
                                             .offset(paddingOffsets)

        DPoint textBlockStartLocation = borderTopLeft.offset(borderOffsets.leftTop())
                                                     .offset(paddingOffsets.leftTop())

        WrittenTextResult writtenTextResult = writeTextToBoundedLocation(dParagraph, writer, paragraphBounds, textBlockStartLocation, font)
        DPoint textBlockEndLocation = writtenTextResult.currentPosition

        float fontDescentOffsetWhenBorder = (borderOffsets.bottom == 0) ? 0 : font.descent

        DPoint borderBottomRight = new DPoint(pageContext.bounds.right, textBlockEndLocation.y)
            .offsetY(paddingOffsets.bottom)
            .offsetY(borderOffsets.bottom)
            .offsetY(fontDescentOffsetWhenBorder)
            .offsetX(marginOffsets.right)

        lineBorder.drawBorder(dParagraph, writer, borderTopLeft, borderBottomRight)

        float currentLocationBorderYOffset = borderOffsets.bottom == 0 ? 0 : fontDescentOffsetWhenBorder + 1

        pageContext.currentLocation = new DPoint(pageContext.bounds.left, textBlockEndLocation.y)
            .offsetY(currentLocationBorderYOffset)
            .offsetY(paddingOffsets.bottom)
            .offsetY(borderOffsets.bottom)
            .offsetY(marginOffsets.bottom)

        new ParagraphPreviousElementDetails(lastLineDescent: writtenTextResult.lastLineDescent, hasBottomBorder: borderOffsets.bottom)
    }

    /**
     * Calculates the offset the paragraph should start relative to the previous element.
     */
    protected float calculatePreviousElementYOffset(PreviousElementDetails previousElementDetails, DBounds borderOffsets) {
        float additionalOffsetFromPrevious = 0

        if (borderOffsets.top || borderOffsets.left || borderOffsets.right) {
            switch (previousElementDetails) {
                case ParagraphPreviousElementDetails:
                    ParagraphPreviousElementDetails details = (ParagraphPreviousElementDetails) previousElementDetails
                    additionalOffsetFromPrevious = details.hasBottomBorder ? 0 : details.lastLineDescent
                    break
            }
        }

        additionalOffsetFromPrevious
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

}