package com.github.dadeo.pdfbox.creator.writer.page

import com.github.dadeo.pdfbox.creator.writer.DContext

interface PageObjectWriter<DObject> {

    ElementDetails write(DContext pageContext, DObject dObject, ElementDetails previousElementDetails)

}