package com.github.dadeo.pdfbox.creator.writer.paragraph

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.model.DBounds
import com.github.dadeo.pdfbox.model.DParagraph
import com.github.dadeo.pdfbox.model.DPoint
import spock.lang.Specification
import spock.lang.Unroll


class ParagraphBoundsCalculationsTest extends Specification {
    private ParagraphBoundsCalculations calculations = new ParagraphBoundsCalculations()
    private DContext parentContext = new DContext()
    private DContext paragraphContext = new DContext(parent: parentContext)
    private DParagraph paragraph = new DParagraph()
    private BoundedTextBlock textBlock = Mock(BoundedTextBlock)

    def "containing bounds when no margin, no border, and no padding"() {
        given:
        parentContext.contentsBounds = new DBounds(800, 600, 100, 72)
        paragraphContext.currentLocation = new DPoint(72, 500)
        textBlock.height >> 150

        expect:
        calculations.calculateContainingBounds(paragraphContext, paragraph, textBlock) == new DBounds(500, 600, 350, 72)
    }

    def "containing bounds affected by margin"() {
        given:
        paragraph.marginTop = 5
        paragraph.marginRight = 10
        paragraph.marginBottom = 15
        paragraph.marginLeft = 20

        parentContext.contentsBounds = new DBounds(800, 600, 100, 72)
        paragraphContext.currentLocation = new DPoint(72, 500)
        textBlock.height >> 150

        expect:
        calculations.calculateContainingBounds(paragraphContext, paragraph, textBlock) == new DBounds(500, 600, 330, 72)
    }

    def "containing bounds affected by border"() {
        given:
        paragraph.borderTop = 5
        paragraph.borderRight = 10
        paragraph.borderBottom = 15
        paragraph.borderLeft = 20

        parentContext.contentsBounds = new DBounds(800, 600, 100, 72)
        paragraphContext.currentLocation = new DPoint(72, 500)
        textBlock.height >> 150

        expect:
        calculations.calculateContainingBounds(paragraphContext, paragraph, textBlock) == new DBounds(500, 600, 330, 72)
    }

    @Unroll
    def "containing bounds increased by last line descent when bottom border"() {
        given:
        paragraph.borderTop = borderTop
        paragraph.borderRight = borderRight
        paragraph.borderBottom = borderBottom
        paragraph.borderLeft = borderLeft

        parentContext.contentsBounds = new DBounds(800, 600, 100, 72)
        paragraphContext.currentLocation = new DPoint(72, 500)
        textBlock.height >> 150
        textBlock.lastLineDescent >> -10

        expect:
        calculations.calculateContainingBounds(paragraphContext, paragraph, textBlock).bottom == expectedBottom

        where:
        borderTop | borderRight | borderBottom | borderLeft | expectedBottom
        0         | 0           | 0            | 0          | 350
        10        | 0           | 0            | 0          | 340
        0         | 10          | 0            | 0          | 350
        0         | 0           | 10           | 0          | 330
        0         | 0           | 0            | 10         | 350
        10        | 10          | 10           | 10         | 320
        0         | 10          | 10           | 0          | 330
        10        | 10          | 0            | 10         | 340
    }

    def "containing bounds affected by padding"() {
        given:
        paragraph.paddingTop = 5
        paragraph.paddingRight = 10
        paragraph.paddingBottom = 15
        paragraph.paddingLeft = 20

        parentContext.contentsBounds = new DBounds(800, 600, 100, 72)
        paragraphContext.currentLocation = new DPoint(72, 500)
        textBlock.height >> 150

        expect:
        calculations.calculateContainingBounds(paragraphContext, paragraph, textBlock) == new DBounds(500, 600, 330, 72)
    }

    def "containing bounds affected by margin, border, and padding"() {
        given:
        paragraph.margin = 20
        paragraph.border = 20
        paragraph.padding = 20

        parentContext.contentsBounds = new DBounds(800, 600, 100, 72)
        paragraphContext.currentLocation = new DPoint(72, 500)
        textBlock.height >> 150

        expect:
        calculations.calculateContainingBounds(paragraphContext, paragraph, textBlock) == new DBounds(500, 600, 230, 72)
    }

    def "border bounds when no margin, no border, and no padding"() {
        given:
        paragraphContext.containingBounds = new DBounds(800, 600, 100, 72)

        expect:
        calculations.calculateBorderBounds(paragraphContext, paragraph) == new DBounds(800, 600, 100, 72)
    }

    def "border bounds affected by margin"() {
        given:
        paragraph.marginTop = 5
        paragraph.marginRight = 10
        paragraph.marginBottom = 15
        paragraph.marginLeft = 20

        paragraphContext.containingBounds = new DBounds(800, 600, 100, 72)

        expect:
        calculations.calculateBorderBounds(paragraphContext, paragraph) == new DBounds(795, 590, 115, 92)
    }

    def "border bounds not affected by border or padding"() {
        given:
        paragraph.border = 20
        paragraph.padding = 20

        paragraphContext.containingBounds = new DBounds(800, 600, 100, 72)

        expect:
        calculations.calculateBorderBounds(paragraphContext, paragraph) == new DBounds(800, 600, 100, 72)
    }

    def "contents bounds when no margin, no border, and no padding"() {
        given:
        paragraphContext.borderBounds = new DBounds(800, 600, 100, 72)

        expect:
        calculations.calculateContentsBounds(paragraphContext, paragraph) == new DBounds(800, 600, 100, 72)
    }

    def "contents bounds not affected by margin (border bounds already includes margin offsets)"() {
        given:
        paragraph.margin = 20

        paragraphContext.borderBounds = new DBounds(800, 600, 100, 72)

        expect:
        calculations.calculateContentsBounds(paragraphContext, paragraph) == new DBounds(800, 600, 100, 72)
    }

    def "contents bounds affected by border"() {
        given:
        paragraph.borderTop = 5
        paragraph.borderRight = 10
        paragraph.borderBottom = 15
        paragraph.borderLeft = 20

        paragraphContext.borderBounds = new DBounds(800, 600, 100, 72)

        expect:
        calculations.calculateContentsBounds(paragraphContext, paragraph) == new DBounds(795, 590, 115, 92)
    }

    def "contents bounds affected by padding"() {
        given:
        paragraph.paddingTop = 5
        paragraph.paddingRight = 10
        paragraph.paddingBottom = 15
        paragraph.paddingLeft = 20

        paragraphContext.borderBounds = new DBounds(800, 600, 100, 72)

        expect:
        calculations.calculateContentsBounds(paragraphContext, paragraph) == new DBounds(795, 590, 115, 92)
    }

    def "contents bounds affected by border and padding"() {
        given:
        paragraph.margin = 20   // no affect
        paragraph.border = 20
        paragraph.padding = 20

        paragraphContext.borderBounds = new DBounds(800, 600, 100, 72)

        expect:
        calculations.calculateContentsBounds(paragraphContext, paragraph) == new DBounds(760, 560, 140, 112)
    }

}