package com.github.dadeo.pdfbox.creator.writer.util

import com.github.dadeo.pdfbox.model.VerticalAlignment

class VerticalAlignmentCalculator {

    float calculateOffsetFor(VerticalAlignment verticalAlignment, float currentCellHeight, float maxCellHeight) {
        switch (verticalAlignment) {
            case VerticalAlignment.MIDDLE:
                (maxCellHeight - currentCellHeight) / 2
                break
            case VerticalAlignment.BOTTOM:
                maxCellHeight - currentCellHeight
                break
            default:
                0
                break
        }
    }

}