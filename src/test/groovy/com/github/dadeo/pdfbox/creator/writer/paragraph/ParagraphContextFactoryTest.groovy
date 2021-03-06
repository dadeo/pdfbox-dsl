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
import com.github.dadeo.pdfbox.creator.writer.object.ObjectBoundsCalculator
import com.github.dadeo.pdfbox.model.Bounds
import com.github.dadeo.pdfbox.model.Font
import com.github.dadeo.pdfbox.model.Paragraph
import com.github.dadeo.pdfbox.model.Justification
import spock.lang.Specification

class ParagraphContextFactoryTest extends Specification {
    private static final Bounds PARENT_CONTENTS_BOUNDS = new Bounds(1, 2, 3, 4)
    private static final Font PAGE_FONT = new Font()
    private static final Font PARAGRAPH_FONT = new Font()

    private ObjectBoundsCalculator objectBoundsCalculator = Mock(ObjectBoundsCalculator)
    private ParagraphContextFactory factory = new ParagraphContextFactory(objectBoundsCalculator: objectBoundsCalculator)
    private DContext clonedContext = new DContext(font: PAGE_FONT)
    private DContext parentContext = Mock(DContext) {
        getContentsBounds() >> PARENT_CONTENTS_BOUNDS
        1 * clone() >> clonedContext
    }

    def "page context is cloned and returned"() {
        expect:

        factory.createContextFrom(parentContext, new Paragraph()).is clonedContext
    }

    def "DParagraph font overrides page context font"() {
        expect:

        factory.createContextFrom(parentContext, new Paragraph(font: PARAGRAPH_FONT)).font.is PARAGRAPH_FONT
    }

    def "paragraph context contains parent context font when DParagraph font is null"() {
        expect:

        factory.createContextFrom(parentContext, new Paragraph()).font.is PAGE_FONT
    }

    def "paragraph context contains justification when one is specified"() {
        given:
        Paragraph paragraph = new Paragraph(justification: justification)

        when:
        DContext paragraphContext = factory.createContextFrom(parentContext, paragraph)

        then:
        paragraphContext.textJustification == justification

        where:
        justification << Justification.values()
    }

    def "paragraph context contains parent context justification when DParagraph justification is null"() {
        given:
        parentContext.textJustification >> Justification.CENTER

        expect:
        factory.createContextFrom(parentContext, new Paragraph()).textJustification.is Justification.CENTER
    }

    def "paragraph context contains parent context as parent"() {
        expect:

        factory.createContextFrom(parentContext, new Paragraph()).parent.is parentContext
    }

    def "new context's containingBounds, borderBounds, and contentsBounds are initialized to parent context's contentsBounds"() {
        given:
        Paragraph paragraph = new Paragraph()

        when:
        DContext childContext = factory.createContextFrom(parentContext, paragraph)

        then:
        childContext.containingBounds == PARENT_CONTENTS_BOUNDS
        1 * objectBoundsCalculator.calculateMaxBounds(paragraph, clonedContext)
    }

}