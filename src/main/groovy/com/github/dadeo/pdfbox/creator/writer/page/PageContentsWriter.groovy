package com.github.dadeo.pdfbox.creator.writer.page

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.creator.writer.object.ObjectWritable
import com.github.dadeo.pdfbox.creator.writer.object.ObjectWritableFactory
import com.github.dadeo.pdfbox.creator.writer.object.ObjectWritableFactoryFactory
import com.github.dadeo.pdfbox.model.DObject
import com.github.dadeo.pdfbox.model.DPage

class PageContentsWriter {
    PageContentsCurrentLocationAdjuster currentLocationAdjuster
    ObjectWritableFactoryFactory writerFactoryFactory

    void writeContents(DPage dPage, DContext pageContext) {
        dPage.contents.inject((ElementDetails) null) { ElementDetails previousElementDetails, DObject dObject ->
            if (previousElementDetails)
                currentLocationAdjuster.adjust(pageContext, previousElementDetails)

            ObjectWritableFactory<? extends DObject> writerFactory = writerFactoryFactory.createWriter(dObject)
            ObjectWritable writable = writerFactory.createFor(pageContext, dObject, previousElementDetails)
            writable.write()

            writable.elementDetails
        }
    }

}