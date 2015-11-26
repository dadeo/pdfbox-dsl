package com.github.dadeo.pdfbox.creator.writer.paragraph

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.model.DBounds
import com.github.dadeo.pdfbox.model.DParagraph
import spock.lang.Specification

class ParagraphPreviousElementDetailsFactoryTest extends Specification {
    private static final DBounds CONTAINING_BOUNDS = new DBounds(1, 2, 3, 4)
    private ParagraphPreviousElementDetailsFactory factory = new ParagraphPreviousElementDetailsFactory()
    private DParagraph paragraph = new DParagraph()
    private BoundedTextBlock textBlock = Mock(BoundedTextBlock)
    private DContext context = new DContext()

    def "returns a populated ParagraphPreviousElementDetails"() {
        given:
        paragraph.borderBottom = borderBottom
        textBlock.lastLineDescent >> lastLineDescent
        context.containingBounds = CONTAINING_BOUNDS

        expect:
        factory.createFor(context, paragraph, textBlock) == new ParagraphPreviousElementDetails(expectedLastLineDescent, expectedHasBorder, CONTAINING_BOUNDS)

        where:
        borderBottom | lastLineDescent | expectedHasBorder | expectedLastLineDescent
        0            | -27f            | false             | -27f
        1            | -27f            | true              | -27f
        1            | -18f            | true              | -18f
        0            | -18f            | false             | -18f
    }

}