package com.github.dadeo.pdfbox.creator.writer.paragraph

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.model.DParagraph


class ParagraphPreviousElementDetailsFactory {

    ParagraphPreviousElementDetails createFor(DContext paragraphContext, DParagraph paragraph, BoundedTextBlock textBlock) {
        new ParagraphPreviousElementDetails(lastLineDescent: textBlock.lastLineDescent,
                                            hasBottomBorder: paragraph.borderBottom,
                                            containingBounds: paragraphContext.containingBounds)
    }

}