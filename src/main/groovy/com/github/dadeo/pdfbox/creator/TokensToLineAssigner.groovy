package com.github.dadeo.pdfbox.creator

import com.github.dadeo.pdfbox.model.AssignedLine


class TokensToLineAssigner {

    List<AssignedLine> assignToLine(List<StringToken> tokenList, float width, boolean allowLineToStartWithSpaces) {
        List<AssignedLine> result = []
        AssignedLine currentLine = new AssignedLine()
        float currentLineLength = 0

        Closure lineComplete = {
            result << currentLine
            currentLine = new AssignedLine()
            currentLineLength = 0
        }

        for (StringToken token : tokenList) {
            if (allowLineToStartWithSpaces || !(currentLineLength == 0 && token.text == ' ')) {
                currentLineLength += token.size

                if (currentLineLength < width) {
                    currentLine.tokens << token
                } else if (currentLineLength == width) {
                    currentLine.tokens << token
                    lineComplete()
                } else {
                    if (token.size > width)
                        throw new RuntimeException("Words that are wider than the area width are not currently supported.")

                    if (allowLineToStartWithSpaces || token.text != ' ') {
                        lineComplete()
                        currentLine.tokens << token
                        currentLineLength = token.size
                    }
                }
            }

        }

        if (currentLine.tokens)
            result << currentLine

        result
    }

}