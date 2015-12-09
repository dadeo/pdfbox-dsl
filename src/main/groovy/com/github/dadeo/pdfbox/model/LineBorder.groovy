package com.github.dadeo.pdfbox.model

import com.github.dadeo.pdfbox.creator.writer.DWriter

class LineBorder implements DBorder {

    void drawBorder(Bordered bordered, DWriter writer, DBounds bounds) {
        // the 0.5f adjustment is to make up for line centering
        float top = bounds.top + 0.5f
        float right = bounds.right + 0.5f
        float bottom = bounds.bottom - 0.5f
        float left = bounds.left - 0.5f

        DBounds borderOffsets = bordered.borderLineOffsets

        float offsetTop = top + borderOffsets.top
        float offsetRight = right + borderOffsets.right
        float offsetBottom = bottom + borderOffsets.bottom
        float offsetLeft = left + borderOffsets.left


        if (bordered.borderLeft != 0)
            writer.drawLine(new DPoint(offsetLeft, top), new DPoint(offsetLeft, bottom), bordered.borderLeft, bordered.borderLeftColor)

        if (bordered.borderRight != 0)
            writer.drawLine(new DPoint(offsetRight, top), new DPoint(offsetRight, bottom), bordered.borderRight, bordered.borderRightColor)

        if (bordered.borderTop != 0)
            writer.drawLine(new DPoint(left, offsetTop), new DPoint(right, offsetTop), bordered.borderTop, bordered.borderTopColor)

        if (bordered.borderBottom != 0)
            writer.drawLine(new DPoint(left, offsetBottom), new DPoint(right, offsetBottom), bordered.borderBottom, bordered.borderBottomColor)
    }

}