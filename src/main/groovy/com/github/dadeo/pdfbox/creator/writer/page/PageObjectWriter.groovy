package com.github.dadeo.pdfbox.creator.writer.page

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.model.DObject


interface PageObjectWriter<T extends DObject> {

    PreviousElementDetails write(DContext pageContext, T dObject, PreviousElementDetails previousElementDetails)

}