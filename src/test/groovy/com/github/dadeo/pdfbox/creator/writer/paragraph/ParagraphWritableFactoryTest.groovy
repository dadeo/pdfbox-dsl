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
import com.github.dadeo.pdfbox.model.Paragraph
import spock.lang.Specification

class ParagraphWritableFactoryTest extends Specification {
    private static final float TEXT_BLOCK_HEIGHT = 150
    private static final float CONTENT_WIDTH = 600
    private static final Bounds PAGE_CONTENT_BOUNDS = new Bounds(1, 2, 3, 4)
    private static final Bounds PARAGRAPH_CONTAINING_BOUNDS = new Bounds(1, 2, 3, 4)
    private ParagraphContentsDimensionsCalculator contentsSizeCalculator = Mock(ParagraphContentsDimensionsCalculator)
    private ParagraphContextFactory paragraphContextFactory = Mock(ParagraphContextFactory)
    private BoundedTextBlockFactory boundedTextBlockFactory = Mock(BoundedTextBlockFactory)
    private ObjectBoundsCalculator objectBoundsCalculator = Mock(ObjectBoundsCalculator)
    private ParagraphWritableFactory paragraphWritableFactory = new ParagraphWritableFactory(contentsDimensionsCalculator: contentsSizeCalculator,
                                                                                             paragraphContextFactory: paragraphContextFactory,
                                                                                             boundedTextBlockFactory: boundedTextBlockFactory,
                                                                                             objectBoundsCalculator: objectBoundsCalculator)
    private DContext pageContext = new DContext()
    private DContext paragraphContext = new DContext()
    private Paragraph paragraph = new Paragraph()
    private BoundedTextBlock textBlock = Mock(BoundedTextBlock)

    def "write paragraph orchestration"() {
        given:
        pageContext.contentsBounds = PAGE_CONTENT_BOUNDS
        paragraphContext.containingBounds = PARAGRAPH_CONTAINING_BOUNDS

        when:
        ParagraphWritable writable = paragraphWritableFactory.createFor(pageContext, paragraph)

        then:
        writable.dParagraph.is paragraph
        writable.textBlock.is textBlock
        writable.context.is paragraphContext

        1 * contentsSizeCalculator.calculateWidthFor(paragraph, PAGE_CONTENT_BOUNDS) >> CONTENT_WIDTH
        1 * paragraphContextFactory.createContextFrom(pageContext, paragraph) >> paragraphContext
        1 * boundedTextBlockFactory.createFrom(paragraphContext, paragraph, CONTENT_WIDTH) >> textBlock
        1 * contentsSizeCalculator.calculateHeightFor(paragraph, textBlock) >> TEXT_BLOCK_HEIGHT
        1 * objectBoundsCalculator.resizeBoundsToHeight(TEXT_BLOCK_HEIGHT, paragraphContext)
        0 * _
    }

}