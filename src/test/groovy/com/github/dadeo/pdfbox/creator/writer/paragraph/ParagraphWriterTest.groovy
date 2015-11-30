package com.github.dadeo.pdfbox.creator.writer.paragraph

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.creator.writer.border.BorderDrawer
import com.github.dadeo.pdfbox.creator.writer.page.ElementDetails
import com.github.dadeo.pdfbox.creator.writer.positioning.CurrentLocationAdjuster
import com.github.dadeo.pdfbox.model.DBounds
import com.github.dadeo.pdfbox.model.DParagraph
import spock.lang.Specification


class ParagraphWriterTest extends Specification {
    private static final float CONTENT_WIDTH = 600
    private static final DBounds PAGE_CONTENT_BOUNDS = new DBounds(1, 2, 3, 4)
    private static final DBounds PARAGRAPH_CONTAINGING_BOUNDS = new DBounds(1, 2, 3, 4)
    private ParagraphWidthCalculator paragraphWidthCalculator = Mock(ParagraphWidthCalculator)
    private ParagraphContextFactory paragraphContextFactory = Mock(ParagraphContextFactory)
    private BoundedTextBlockFactory boundedTextBlockFactory = Mock(BoundedTextBlockFactory)
    private CurrentLocationAdjuster currentLocationAdjuster = Mock(CurrentLocationAdjuster)
    private ParagraphBoundsCalculator paragraphBoundsCalculator = Mock(ParagraphBoundsCalculator)
    private BoundedTextBlockWriter boundedTextBlockWriter = Mock(BoundedTextBlockWriter)
    private BorderDrawer borderDrawer = Mock(BorderDrawer)
    private ParagraphElementDetailsFactory previousElementDetailsFactory = Mock(ParagraphElementDetailsFactory)
    private ParagraphWriter paragraphWriter = new ParagraphWriter(paragraphWidthCalculator: paragraphWidthCalculator,
                                                                  paragraphContextFactory: paragraphContextFactory,
                                                                  boundedTextBlockFactory: boundedTextBlockFactory,
                                                                  currentLocationAdjuster: currentLocationAdjuster,
                                                                  paragraphBoundsCalculator: paragraphBoundsCalculator,
                                                                  boundedTextBlockWriter: boundedTextBlockWriter,
                                                                  borderDrawer: borderDrawer,
                                                                  paragraphElementDetailsFactory: previousElementDetailsFactory)
    private DContext pageContext = new DContext()
    private DContext paragraphContext = new DContext()
    private DParagraph paragraph = new DParagraph()
    private ElementDetails previousElementDetails = Mock(ElementDetails)
    private BoundedTextBlock textBlock = Mock(BoundedTextBlock)

    def "write paragraph orchestration"() {
        given:
        pageContext.contentsBounds = PAGE_CONTENT_BOUNDS
        paragraphContext.containingBounds = PARAGRAPH_CONTAINGING_BOUNDS

        when:
        paragraphWriter.write(pageContext, paragraph, previousElementDetails)

        then:
        1 * paragraphWidthCalculator.calculateFor(paragraph, PAGE_CONTENT_BOUNDS) >> CONTENT_WIDTH
        1 * paragraphContextFactory.createContextFrom(pageContext, paragraph) >> paragraphContext
        1 * boundedTextBlockFactory.createFrom(paragraphContext, paragraph, CONTENT_WIDTH) >> textBlock
        1 * currentLocationAdjuster.adjustFor(paragraphContext, paragraph, previousElementDetails)
        1 * paragraphBoundsCalculator.addCalculationsTo(paragraphContext, paragraph, textBlock)
        1 * boundedTextBlockWriter.write(textBlock, paragraphContext)
        1 * borderDrawer.drawFor(paragraph, paragraphContext)
        1 * previousElementDetailsFactory.createFor(paragraphContext, paragraph, textBlock)
        0 * _
    }

}