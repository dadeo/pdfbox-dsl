package com.github.dadeo.pdfbox.creator.writer.page

import com.github.dadeo.pdfbox.creator.writer.DContext

interface PageObjectWriter<DObject> {

    PreviousElementDetails write(DContext pageContext, DObject dObject, PreviousElementDetails previousElementDetails)

}