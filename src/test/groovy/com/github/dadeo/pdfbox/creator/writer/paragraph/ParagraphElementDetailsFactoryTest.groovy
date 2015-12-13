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
import com.github.dadeo.pdfbox.model.DBounds
import com.github.dadeo.pdfbox.model.DParagraph
import spock.lang.Specification

class ParagraphElementDetailsFactoryTest extends Specification {
    private static final DBounds CONTAINING_BOUNDS = new DBounds(1, 2, 3, 4)
    private ParagraphElementDetailsFactory factory = new ParagraphElementDetailsFactory()
    private DParagraph paragraph = new DParagraph()
    private BoundedTextBlock textBlock = Mock(BoundedTextBlock)
    private DContext context = new DContext()

    def "returns a populated ParagraphPreviousElementDetails"() {
        given:
        paragraph.borderBottom = borderBottom
        textBlock.lastLineDescent >> lastLineDescent
        context.containingBounds = CONTAINING_BOUNDS

        expect:
        factory.createFor(context, paragraph, textBlock) == new ParagraphElementDetails(expectedLastLineDescent, expectedHasBorder, CONTAINING_BOUNDS)

        where:
        borderBottom | lastLineDescent | expectedHasBorder | expectedLastLineDescent
        0            | -27f            | false             | -27f
        1            | -27f            | true              | -27f
        1            | -18f            | true              | -18f
        0            | -18f            | false             | -18f
    }

}