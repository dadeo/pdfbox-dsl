package com.github.dadeo.pdfbox.creator.writer.paragraph

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.creator.writer.object.ObjectBoundsCalculator
import com.github.dadeo.pdfbox.creator.writer.page.ElementDetails
import com.github.dadeo.pdfbox.creator.writer.positioning.CurrentLocationAdjuster
import com.github.dadeo.pdfbox.model.DBounds
import com.github.dadeo.pdfbox.model.DParagraph
import spock.lang.Specification

class ParagraphWritableFactoryTest extends Specification {
    private static final float TEXT_BLOCK_HEIGHT = 150
    private static final float CONTENT_WIDTH = 600
    private static final DBounds PAGE_CONTENT_BOUNDS = new DBounds(1, 2, 3, 4)
    private static final DBounds PARAGRAPH_CONTAINING_BOUNDS = new DBounds(1, 2, 3, 4)
    private ParagraphContentsDimensionsCalculator contentsSizeCalculator = Mock(ParagraphContentsDimensionsCalculator)
    private ParagraphContextFactory paragraphContextFactory = Mock(ParagraphContextFactory)
    private BoundedTextBlockFactory boundedTextBlockFactory = Mock(BoundedTextBlockFactory)
    private CurrentLocationAdjuster currentLocationAdjuster = Mock(CurrentLocationAdjuster)
    private ObjectBoundsCalculator objectBoundsCalculator = Mock(ObjectBoundsCalculator)
    private ParagraphElementDetailsFactory elementDetailsFactory = Mock(ParagraphElementDetailsFactory)
    private ParagraphWritableFactory paragraphWritableFactory = new ParagraphWritableFactory(contentsDimensionsCalculator: contentsSizeCalculator,
                                                                                             paragraphContextFactory: paragraphContextFactory,
                                                                                             boundedTextBlockFactory: boundedTextBlockFactory,
                                                                                             currentLocationAdjuster: currentLocationAdjuster,
                                                                                             objectBoundsCalculator: objectBoundsCalculator,
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
        writable.context.is paragraphContext
        writable.elementDetails.is elementDetails

        1 * contentsSizeCalculator.calculateWidthFor(paragraph, PAGE_CONTENT_BOUNDS) >> CONTENT_WIDTH
        1 * paragraphContextFactory.createContextFrom(pageContext, paragraph) >> paragraphContext
        1 * boundedTextBlockFactory.createFrom(paragraphContext, paragraph, CONTENT_WIDTH) >> textBlock
        1 * currentLocationAdjuster.adjustFor(paragraphContext, paragraph, previousElementDetails)
        1 * contentsSizeCalculator.calculateHeightFor(paragraph, textBlock) >> TEXT_BLOCK_HEIGHT
        1 * objectBoundsCalculator.calculateActualBounds(paragraphContext, TEXT_BLOCK_HEIGHT)
        1 * elementDetailsFactory.createFor(paragraphContext, paragraph, textBlock) >> elementDetails
        0 * _
    }

}