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