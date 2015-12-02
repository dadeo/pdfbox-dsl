package com.github.dadeo.pdfbox.creator.writer

import com.github.dadeo.pdfbox.creator.BootStrap
import com.github.dadeo.pdfbox.creator.writer.page.PageWriter
import com.github.dadeo.pdfbox.model.DDocument
import org.apache.pdfbox.pdmodel.PDDocument

class DocumentWriter {
    PageWriter pageWriter = BootStrap.pageWriter

    byte[] write(DDocument dDocument) {
        PDDocument pDDocument = new PDDocument()

        DContext context = new DContext(pdDocument: pDDocument, font: dDocument.font)
        dDocument.pages.each {
            pageWriter.write(context, it)
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream()
        pDDocument.save(baos)
        pDDocument.close()
        baos.toByteArray()
    }
}