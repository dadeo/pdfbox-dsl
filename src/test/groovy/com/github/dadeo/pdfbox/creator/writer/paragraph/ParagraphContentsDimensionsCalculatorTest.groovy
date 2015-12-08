package com.github.dadeo.pdfbox.creator.writer.paragraph

import com.github.dadeo.pdfbox.creator.writer.object.ObjectContentsWidthCalculator
import com.github.dadeo.pdfbox.creator.writer.positioning.DescentMultiplier
import com.github.dadeo.pdfbox.model.DBounds
import com.github.dadeo.pdfbox.model.DParagraph
import spock.lang.Specification

class ParagraphContentsDimensionsCalculatorTest extends Specification {
    private static final float TEXT_BLOCK_HEIGHT = 115f
    private static final float CALCULATED_WIDTH = 27f
    private static final float DESCENT_ORIGINAL = -10f
    private static final float DESCENT_MODIFIED = -20f
    private ObjectContentsWidthCalculator objectContentsWidthCalculator = Mock(ObjectContentsWidthCalculator)
    private DescentMultiplier descentMultiplier = Mock(DescentMultiplier)
    private ParagraphContentsDimensionsCalculator paragraphContentsDimensionsCalculator = new ParagraphContentsDimensionsCalculator()

    private void setup() {
        paragraphContentsDimensionsCalculator.objectContentsWidthCalculator = objectContentsWidthCalculator
        paragraphContentsDimensionsCalculator.descentMultiplier = descentMultiplier
    }

    def "calculateWidthFor delegates to ObjectWidthCalculator"() {
        given:
        DParagraph paragraph = new DParagraph() {}
        DBounds bounds = new DBounds(1, 2, 3, 4)
        1 * objectContentsWidthCalculator.calculateFor(paragraph, bounds) >> CALCULATED_WIDTH

        expect:
        paragraphContentsDimensionsCalculator.calculateWidthFor(paragraph, bounds) == CALCULATED_WIDTH
    }

    def "calculateHeight for paragraph with bottom border"() {
        given:
        DParagraph paragraph = Mock(DParagraph) {
            getBorderBottom() >> 1f
        }

        BoundedTextBlock textBlock = Mock(BoundedTextBlock) {
            getHeight() >> TEXT_BLOCK_HEIGHT
            getLastLineDescent() >> DESCENT_ORIGINAL
        }

        1 * descentMultiplier.apply(DESCENT_ORIGINAL) >> DESCENT_MODIFIED

        expect:
        paragraphContentsDimensionsCalculator.calculateHeightFor(paragraph, textBlock) == (float) (TEXT_BLOCK_HEIGHT - DESCENT_MODIFIED)
    }

    def "calculateHeight for paragraph with no bottom border"() {
        given:
        DParagraph paragraph = Mock(DParagraph) {
            getBorderBottom() >> 0f
        }

        BoundedTextBlock textBlock = Mock(BoundedTextBlock) {
            getHeight() >> TEXT_BLOCK_HEIGHT
            getLastLineDescent() >> DESCENT_ORIGINAL
        }

        descentMultiplier.apply(DESCENT_ORIGINAL) >> DESCENT_MODIFIED

        expect:
        paragraphContentsDimensionsCalculator.calculateHeightFor(paragraph, textBlock) == TEXT_BLOCK_HEIGHT
    }
}