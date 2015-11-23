package com.github.dadeo.pdfbox.creator.writer.page

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.creator.writer.border.LineBorder
import com.github.dadeo.pdfbox.model.DPage


class PageBorderDrawer {
    LineBorder lineBorder = new LineBorder()

    void drawFor(DPage dPage, DContext pageContext) {
        lineBorder.drawBorder(dPage, pageContext.writer, pageContext.borderBounds)
    }

}