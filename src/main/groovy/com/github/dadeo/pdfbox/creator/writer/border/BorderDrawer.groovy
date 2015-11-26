package com.github.dadeo.pdfbox.creator.writer.border

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.model.Bordered

class BorderDrawer {
    LineBorder lineBorder = new LineBorder()

    void drawFor(Bordered bordered, DContext currentContext) {
        lineBorder.drawBorder(bordered, currentContext.writer, currentContext.borderBounds)
    }

}