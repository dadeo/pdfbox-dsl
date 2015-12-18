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
import com.github.dadeo.pdfbox.model.PdfComponent
import com.github.dadeo.pdfbox.model.Paragraph

class ParagraphContentsDimensionsCalculator {
    ObjectContentsWidthCalculator objectContentsWidthCalculator
    DescentMultiplier descentMultiplier

    float calculateWidthFor(PdfComponent object, Bounds areaBounds) {
        objectContentsWidthCalculator.calculateFor(object, areaBounds)
    }

    float calculateHeightFor(Paragraph dParagraph, BoundedTextBlock textBlock) {
        textBlock.height - descentMultiplier.apply(textBlock.lastLineDescent, dParagraph.paragraphBottomDescentMultiplier)
    }
}