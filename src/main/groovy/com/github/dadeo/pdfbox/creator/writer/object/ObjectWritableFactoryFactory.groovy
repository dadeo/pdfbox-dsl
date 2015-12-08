package com.github.dadeo.pdfbox.creator.writer.object

import com.github.dadeo.pdfbox.creator.writer.hr.HorizontalRuleWritableFactory
import com.github.dadeo.pdfbox.creator.writer.paragraph.ParagraphWritableFactory
import com.github.dadeo.pdfbox.creator.writer.table.TableWritableFactory
import com.github.dadeo.pdfbox.model.DHorizontalRule
import com.github.dadeo.pdfbox.model.DObject
import com.github.dadeo.pdfbox.model.DParagraph
import com.github.dadeo.pdfbox.model.Table

class ObjectWritableFactoryFactory {
    ParagraphWritableFactory paragraphWritableFactory
    HorizontalRuleWritableFactory horizontalRuleWritableFactory
    TableWritableFactory tableWritableFactory

    ObjectWritableFactory<? extends DObject> createWriter(DObject dObject) {
        switch (dObject) {
            case DParagraph:
                paragraphWritableFactory
                break
            case DHorizontalRule:
                horizontalRuleWritableFactory
                break
            case Table:
                tableWritableFactory
                break
            default:
                throw new RuntimeException("ObjectWritable for ${dObject.class} is not supported.")
        }
    }

}