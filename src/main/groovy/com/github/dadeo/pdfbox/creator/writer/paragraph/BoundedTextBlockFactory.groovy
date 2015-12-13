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