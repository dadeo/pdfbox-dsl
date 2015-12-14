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

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.creator.writer.text.AssignedLine
import com.github.dadeo.pdfbox.creator.writer.text.StringToken
import com.github.dadeo.pdfbox.creator.writer.text.StringTokenizer
import com.github.dadeo.pdfbox.creator.writer.text.TokensToLineAssigner
import com.github.dadeo.pdfbox.model.DFont
import com.github.dadeo.pdfbox.model.DParagraph
import com.github.dadeo.pdfbox.model.DPart
import spock.lang.Specification

class BoundedTextBlockFactoryTest extends Specification {
    private static final DFont PARAGRAPH_FONT = new DFont()

    private StringTokenizer stringTokenizer = GroovyMock(StringTokenizer)
    private TokensToLineAssigner tokensToLineAssigner = Mock(TokensToLineAssigner)
    private BoundedTextBlockFactory factory = new BoundedTextBlockFactory(stringTokenizer: stringTokenizer,
                                                                          tokensToLineAssigner: tokensToLineAssigner)
    private DContext paragraphContext = new DContext()
    private DParagraph paragraph = new DParagraph()

    def "part font is used when one is specified"() {
        final String PART_TEXT_1 = "part text 1"
        final String PART_TEXT_2 = "part text 2"
        final DFont PART_FONT_1 = new DFont(size: 8)
        final DFont PART_FONT_2 = new DFont(size: 10)

        given:
        paragraphContext.font = PARAGRAPH_FONT
        paragraph.contents = [
            new DPart(text: PART_TEXT_1, font: PART_FONT_1),
            new DPart(text: PART_TEXT_2, font: PART_FONT_2)
        ]

        when:
        factory.createFrom(paragraphContext, paragraph, 100)

        then:
        1 * stringTokenizer.tokenize(PART_TEXT_1, PART_FONT_1) >> []
        1 * stringTokenizer.tokenize(PART_TEXT_2, PART_FONT_2) >> []
    }

    def "paragraph context font is used when one part does not specify a font"() {
        final String PART_TEXT_1 = "part text 1"
        final String PART_TEXT_2 = "part text 2"
        final DFont PART_FONT_2 = new DFont(size: 10)

        given:
        paragraphContext.font = PARAGRAPH_FONT
        paragraph.contents = [
            new DPart(text: PART_TEXT_1),
            new DPart(text: PART_TEXT_2, font: PART_FONT_2)
        ]

        when:
        factory.createFrom(paragraphContext, paragraph, 100)

        then:
        1 * stringTokenizer.tokenize(PART_TEXT_1, PARAGRAPH_FONT) >> []
        1 * stringTokenizer.tokenize(PART_TEXT_2, PART_FONT_2) >> []
    }

    def "tokens for all parts are passed to line assigner"() {
        StringToken token1 = new StringToken(text: 'a')
        StringToken token2 = new StringToken(text: 'b')
        StringToken token3 = new StringToken(text: 'c')

        given:
        paragraph.contents = [new DPart(), new DPart()]

        1 * stringTokenizer.tokenize(*_) >> [token1, token2]
        1 * stringTokenizer.tokenize(*_) >> [token3]

        when:
        factory.createFrom(paragraphContext, paragraph, 100)

        then:
        1 * tokensToLineAssigner.assignToLine([token1, token2, token3], 100, false)
    }

    def "assigned lines are returned in a BoundedTextBlock"() {
        final List<AssignedLine> assignedLines = []

        given:
        paragraph.contents = [new DPart()]
        stringTokenizer.tokenize(*_) >> []
        tokensToLineAssigner.assignToLine(*_) >> assignedLines

        when:
        BoundedTextBlock result = factory.createFrom(paragraphContext, paragraph, 100)

        then:
        result instanceof BoundedTextBlock
        result.assignedLines.is assignedLines
    }

    def "bounded text block contains width"() {
        final List<AssignedLine> assignedLines = []

        given:
        paragraph.contents = [new DPart()]
        stringTokenizer.tokenize(*_) >> []

        when:
        BoundedTextBlock result = factory.createFrom(paragraphContext, paragraph, 100)

        then:
        result instanceof BoundedTextBlock
        result.width == 100f
    }

    def "bounded text block contains leading"() {
        final List<AssignedLine> assignedLines = []

        given:
        paragraph.contents = [new DPart()]
        paragraph.firstLineLeading = 4
        paragraph.lineLeading = 5
        stringTokenizer.tokenize(*_) >> []

        when:
        BoundedTextBlock result = factory.createFrom(paragraphContext, paragraph, 100)

        then:
        result.firstLineLeading == 4
        result.lineLeading == 5
    }

    def "first line leading defaults to lineLeading when first line leading is not specified"() {
        final List<AssignedLine> assignedLines = []

        given:
        paragraph.contents = [new DPart()]
        paragraph.lineLeading = 5
        stringTokenizer.tokenize(*_) >> []

        when:
        BoundedTextBlock result = factory.createFrom(paragraphContext, paragraph, 100)

        then:
        result.firstLineLeading == 5
        result.lineLeading == 5
    }

}