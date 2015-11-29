package com.github.dadeo.pdfbox.creator.writer.page

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.model.DObject
import com.github.dadeo.pdfbox.model.DPage


class PageContentsWriter {
    PageContentsCurrentLocationAdjuster currentLocationAdjuster = new PageContentsCurrentLocationAdjuster()
    PageObjectWriterFactory writerFactory = new PageObjectWriterFactory()

    void writeContents(DPage dPage, DContext pageContext) {
        dPage.contents.inject((PreviousElementDetails) null) { PreviousElementDetails previousElementDetails, DObject dObject ->
            if (previousElementDetails)
                currentLocationAdjuster.adjust(pageContext, previousElementDetails)
            PageObjectWriter writer = writerFactory.createWriter(dObject)
            writer.write(pageContext, dObject, previousElementDetails)
        }
    }

}