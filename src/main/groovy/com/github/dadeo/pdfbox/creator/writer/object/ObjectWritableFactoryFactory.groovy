package com.github.dadeo.pdfbox.creator.writer.object

import com.github.dadeo.pdfbox.creator.writer.hr.HorizontalRuleWritableFactory
import com.github.dadeo.pdfbox.creator.writer.paragraph.ParagraphWritableFactory
import com.github.dadeo.pdfbox.model.DHorizontalRule
import com.github.dadeo.pdfbox.model.DObject
import com.github.dadeo.pdfbox.model.DParagraph


class ObjectWritableFactoryFactory {
    ParagraphWritableFactory paragraphWriter = new ParagraphWritableFactory()
    HorizontalRuleWritableFactory horizontalRuleWriter = new HorizontalRuleWritableFactory()

    ObjectWritableFactory<? extends DObject> createWriter(DObject dObject) {
        switch (dObject) {
            case DParagraph:
                paragraphWriter
                break
            case DHorizontalRule:
                horizontalRuleWriter
                break
            default:
                throw new RuntimeException("ObjectWritable for ${dObject.class} is not supported.")
        }
    }

}