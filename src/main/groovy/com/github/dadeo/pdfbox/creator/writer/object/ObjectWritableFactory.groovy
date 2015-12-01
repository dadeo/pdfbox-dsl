package com.github.dadeo.pdfbox.creator.writer.object

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.creator.writer.page.ElementDetails
import com.github.dadeo.pdfbox.model.DObject


interface ObjectWritableFactory<T extends DObject> {

    ObjectWritable createFor(DContext pageContext, T dObject, ElementDetails previousElementDetails)

}