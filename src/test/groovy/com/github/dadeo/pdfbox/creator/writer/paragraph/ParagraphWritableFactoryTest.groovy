package com.github.dadeo.pdfbox.creator.writer.paragraph

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.creator.writer.object.ObjectContentsWidthCalculator
import com.github.dadeo.pdfbox.creator.writer.page.ElementDetails
import com.github.dadeo.pdfbox.creator.writer.positioning.CurrentLocationAdjuster
import com.github.dadeo.pdfbox.model.DBounds
import com.github.dadeo.pdfbox.model.DParagraph
import spock.lang.Specification

class ParagraphWritableFactoryTest extends Specification {
    private static final float CONTENT_WIDTH = 600
    private static final DBounds PAGE_CONTENT_BOUNDS = new DBounds(1, 2, 3, 4)
    private static final DBounds PARAGRAPH_CONTAINING_BOUNDS = new DBounds(1, 2, 3, 4)
    private ObjectContentsWidthCalculator objectContentsWidthCalculator = Mock(ObjectContentsWidthCalculator)
    private ParagraphContextFactory paragraphContextFactory = Mock(ParagraphContextFactory)
    private BoundedTextBlockFactory boundedTextBlockFactory = Mock(BoundedTextBlockFactory)
    private CurrentLocationAdjuster currentLocationAdjuster = Mock(CurrentLocationAdjuster)
    private ParagraphBoundsCalculator paragraphBoundsCalculator = Mock(ParagraphBoundsCalculator)
    private ParagraphElementDetailsFactory elementDetailsFactory = Mock(ParagraphElementDetailsFactory)
    private ParagraphWritableFactory paragraphWritableFactory = new ParagraphWritableFactory(objectContentsWidthCalculator: objectContentsWidthCalculator,
                                                                                             paragraphContextFactory: paragraphContextFactory,
                                                                                             boundedTextBlockFactory: boundedTextBlockFactory,
                                                                                             currentLocationAdjuster: currentLocationAdjuster,
                                                                                             paragraphBoundsCalculator: paragraphBoundsCalculator,
                                                                                             elementDetailsFactory: elementDetailsFactory)
    private DContext pageContext = new DContext()
    private DContext paragraphContext = new DContext()
    private DParagraph paragraph = new DParagraph()
    private ElementDetails previousElementDetails = Mock(ElementDetails)
    private ElementDetails elementDetails = Mock(ElementDetails)
    private BoundedTextBlock textBlock = Mock(BoundedTextBlock)

    def "write paragraph orchestration"() {
        given:
        pageContext.contentsBounds = PAGE_CONTENT_BOUNDS
        paragraphContext.containingBounds = PARAGRAPH_CONTAINING_BOUNDS

        when:
        ParagraphWritable writable = paragraphWritableFactory.createFor(pageContext, paragraph, previousElementDetails)

        then:
        writable.dParagraph.is paragraph
        writable.textBlock.is textBlock
        writable.paragraphContext.is paragraphContext
        writable.elementDetails.is elementDetails

        1 * objectContentsWidthCalculator.calculateFor(paragraph, PAGE_CONTENT_BOUNDS) >> CONTENT_WIDTH
        1 * paragraphContextFactory.createContextFrom(pageContext, paragraph) >> paragraphContext
        1 * boundedTextBlockFactory.createFrom(paragraphContext, paragraph, CONTENT_WIDTH) >> textBlock
        1 * currentLocationAdjuster.adjustFor(paragraphContext, paragraph, previousElementDetails)
        1 * paragraphBoundsCalculator.addCalculationsTo(paragraphContext, paragraph, textBlock)
        1 * elementDetailsFactory.createFor(paragraphContext, paragraph, textBlock) >> elementDetails
        0 * _
    }

}