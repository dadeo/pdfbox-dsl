package com.github.dadeo.pdfbox.creator.writer

import com.github.dadeo.pdfbox.model.Bordered
import com.github.dadeo.pdfbox.model.DBounds
import com.github.dadeo.pdfbox.model.DPoint


class LineBorder {

    void drawBorder(Bordered bordered, DWriter writer, DPoint topLeft, DPoint bottomRight) {
        float top = topLeft.y
        float right = bottomRight.x
        float bottom = bottomRight.y
        float left = topLeft.x

        DBounds borderOffsets = bordered.borderLineOffsets

        float offsetTop = top + borderOffsets.top
        float offsetRight = right + borderOffsets.right
        float offsetBottom = bottom + borderOffsets.bottom
        float offsetLeft = left + borderOffsets.left

        if (bordered.borderTop != 0)
            writer.drawLine(new DPoint(left, offsetTop), new DPoint(right, offsetTop), bordered.borderTop)

        if (bordered.borderRight != 0)
            writer.drawLine(new DPoint(offsetRight, top), new DPoint(offsetRight, bottom), bordered.borderRight)

        if (bordered.borderBottom != 0)
            writer.drawLine(new DPoint(left, offsetBottom), new DPoint(right, offsetBottom), bordered.borderBottom)

        if (bordered.borderLeft != 0)
            writer.drawLine(new DPoint(offsetLeft, top), new DPoint(offsetLeft, bottom), bordered.borderLeft)
    }

}