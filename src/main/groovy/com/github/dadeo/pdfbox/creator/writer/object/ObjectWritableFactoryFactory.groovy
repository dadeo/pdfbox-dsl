/**
 * Copyright to the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */
package com.github.dadeo.pdfbox.creator.writer.object

import com.github.dadeo.pdfbox.creator.writer.canvas.CanvasWritableFactory
import com.github.dadeo.pdfbox.creator.writer.hr.HorizontalRuleWritableFactory
import com.github.dadeo.pdfbox.creator.writer.paragraph.ParagraphWritableFactory
import com.github.dadeo.pdfbox.creator.writer.table.TableWritableFactory
import com.github.dadeo.pdfbox.model.HorizontalRule
import com.github.dadeo.pdfbox.model.PdfComponent
import com.github.dadeo.pdfbox.model.Paragraph
import com.github.dadeo.pdfbox.model.Canvas
import com.github.dadeo.pdfbox.model.Table

class ObjectWritableFactoryFactory {
    ParagraphWritableFactory paragraphWritableFactory
    HorizontalRuleWritableFactory horizontalRuleWritableFactory
    TableWritableFactory tableWritableFactory
    CanvasWritableFactory canvasWritableFactory

    ObjectWritableFactory<? extends PdfComponent> createWriter(PdfComponent dObject) {
        switch (dObject) {
            case Paragraph:
                paragraphWritableFactory
                break
            case HorizontalRule:
                horizontalRuleWritableFactory
                break
            case Table:
                tableWritableFactory
                break
            case Canvas:
                canvasWritableFactory
                break
            default:
                throw new RuntimeException("ObjectWritable for ${dObject.class} is not supported.")
        }
    }

}