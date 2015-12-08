package com.github.dadeo.pdfbox.creator.writer.paragraph

import com.github.dadeo.pdfbox.creator.writer.object.ObjectContentsWidthCalculator
import com.github.dadeo.pdfbox.creator.writer.positioning.DescentMultiplier
import com.github.dadeo.pdfbox.model.DBounds
import com.github.dadeo.pdfbox.model.DObject
import com.github.dadeo.pdfbox.model.DParagraph

class ParagraphContentsDimensionsCalculator {
    ObjectContentsWidthCalculator objectContentsWidthCalculator
    DescentMultiplier descentMultiplier

    float calculateWidthFor(DObject object, DBounds areaBounds) {
        objectContentsWidthCalculator.calculateFor(object, areaBounds)
    }

    float calculateHeightFor(DParagraph dParagraph, BoundedTextBlock textBlock) {
        dParagraph.borderBottom ? textBlock.height - descentMultiplier.apply(textBlock.lastLineDescent) : textBlock.height
    }
}