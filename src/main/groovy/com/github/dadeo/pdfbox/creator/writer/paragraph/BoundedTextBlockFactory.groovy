package com.github.dadeo.pdfbox.creator.writer.paragraph

import com.github.dadeo.pdfbox.creator.BootStrap
import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.creator.writer.text.AssignedLine
import com.github.dadeo.pdfbox.creator.writer.text.StringToken
import com.github.dadeo.pdfbox.creator.writer.text.StringTokenizer
import com.github.dadeo.pdfbox.creator.writer.text.TokensToLineAssigner
import com.github.dadeo.pdfbox.model.DParagraph
import com.github.dadeo.pdfbox.model.DPart


class BoundedTextBlockFactory {
    StringTokenizer stringTokenizer = BootStrap.stringTokenizer
    TokensToLineAssigner tokensToLineAssigner = BootStrap.tokensToLineAssigner

    BoundedTextBlock createFrom(DContext paragraphContext, DParagraph dParagraph, float width) {
        List<StringToken> tokens = []

        dParagraph.contents.each { DPart part ->
            tokens.addAll(stringTokenizer.tokenize(part.text, part.font ?: paragraphContext.font))
        }

        List<AssignedLine> assignedLines = tokensToLineAssigner.assignToLine(tokens, width, false)

        new BoundedTextBlock(assignedLines: assignedLines, width: width)
    }
}