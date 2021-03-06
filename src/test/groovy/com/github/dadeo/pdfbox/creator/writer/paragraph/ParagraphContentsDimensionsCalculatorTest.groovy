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

import com.github.dadeo.pdfbox.creator.writer.object.ObjectContentsWidthCalculator
import com.github.dadeo.pdfbox.creator.writer.positioning.DescentMultiplier
import com.github.dadeo.pdfbox.model.Bounds
import com.github.dadeo.pdfbox.model.Paragraph
import spock.lang.Specification

class ParagraphContentsDimensionsCalculatorTest extends Specification {
    private static final float TEXT_BLOCK_HEIGHT = 115f
    private static final float CALCULATED_WIDTH = 27f
    private static final float DESCENT_ORIGINAL = -10f
    private static final float DESCENT_MODIFIED = -20f
    private static final float DESCENT_MODIFIER = 2f
    private ObjectContentsWidthCalculator objectContentsWidthCalculator = Mock(ObjectContentsWidthCalculator)
    private DescentMultiplier descentMultiplier = Mock(DescentMultiplier)
    private ParagraphContentsDimensionsCalculator paragraphContentsDimensionsCalculator = new ParagraphContentsDimensionsCalculator()

    private void setup() {
        paragraphContentsDimensionsCalculator.objectContentsWidthCalculator = objectContentsWidthCalculator
        paragraphContentsDimensionsCalculator.descentMultiplier = descentMultiplier
    }

    def "calculateWidthFor delegates to ObjectWidthCalculator"() {
        given:
        Paragraph paragraph = new Paragraph() {}
        Bounds bounds = new Bounds(1, 2, 3, 4)
        1 * objectContentsWidthCalculator.calculateFor(paragraph, bounds) >> CALCULATED_WIDTH

        expect:
        paragraphContentsDimensionsCalculator.calculateWidthFor(paragraph, bounds) == CALCULATED_WIDTH
    }

    def "calculateHeight for paragraph"() {
        given:
        Paragraph paragraph = Mock(Paragraph) {
            getBorderBottom() >> 1f
            getParagraphBottomDescentMultiplier() >> DESCENT_MODIFIER
        }

        BoundedTextBlock textBlock = Mock(BoundedTextBlock) {
            getHeight() >> TEXT_BLOCK_HEIGHT
            getLastLineDescent() >> DESCENT_ORIGINAL
        }

        1 * descentMultiplier.apply(DESCENT_ORIGINAL, DESCENT_MODIFIER) >> DESCENT_MODIFIED

        expect:
        paragraphContentsDimensionsCalculator.calculateHeightFor(paragraph, textBlock) == (float) (TEXT_BLOCK_HEIGHT - DESCENT_MODIFIED)
    }

}