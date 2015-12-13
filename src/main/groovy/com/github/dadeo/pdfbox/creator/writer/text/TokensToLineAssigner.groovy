package com.github.dadeo.pdfbox.creator.writer.text

class TokensToLineAssigner {

    List<AssignedLine> assignToLine(List<StringToken> tokenList, float width, boolean allowLineToStartWithSpaces) {
        List<AssignedLine> result = []
        AssignedLine currentLine = new AssignedLine()
        float currentLineLength = 0

        List<StringToken> spacesBuffer = []

        Closure lineComplete = {
            result << currentLine
            currentLine = new AssignedLine()
            currentLineLength = 0
            spacesBuffer.clear()
        }

        for (StringToken token : tokenList) {
            if (allowLineToStartWithSpaces || !(currentLineLength == 0 && token.text == ' ')) {
                currentLineLength += token.size

                if (token.text == ' ') {
                    spacesBuffer << token
                } else if (token.text == '\n') {
                    spacesBuffer.clear()
                    lineComplete()
                } else if (currentLineLength < width) {
                    if (spacesBuffer) {
                        currentLine.tokens.addAll spacesBuffer
                        spacesBuffer.clear()
                    }
                    currentLine.tokens << token
                } else if (currentLineLength == width) {
                    if (spacesBuffer) {
                        currentLine.tokens.addAll spacesBuffer
                        spacesBuffer.clear()
                    }
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