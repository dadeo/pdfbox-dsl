package com.github.dadeo.pdfbox.creator.writer.paragraph

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.model.DParagraph


class ParagraphElementDetailsFactory {

    ParagraphElementDetails createFor(DContext paragraphContext, DParagraph paragraph, BoundedTextBlock textBlock) {
        new ParagraphElementDetails(lastLineDescent: textBlock.lastLineDescent,
                                    hasBottomBorder: paragraph.borderBottom,
                                    containingBounds: paragraphContext.containingBounds)
    }

}