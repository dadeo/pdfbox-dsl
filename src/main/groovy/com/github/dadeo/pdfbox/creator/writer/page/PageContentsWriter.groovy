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
package com.github.dadeo.pdfbox.creator.writer.page

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.creator.writer.object.ObjectWritable
import com.github.dadeo.pdfbox.creator.writer.object.ObjectWritableFactory
import com.github.dadeo.pdfbox.creator.writer.object.ObjectWritableFactoryFactory
import com.github.dadeo.pdfbox.creator.writer.object.PositionType
import com.github.dadeo.pdfbox.model.PdfComponent
import com.github.dadeo.pdfbox.model.Page

class PageContentsWriter {
    PageContentsCurrentLocationAdjuster currentLocationAdjuster
    ObjectWritableFactoryFactory writableFactoryFactory

    void writeContents(Page dPage, DContext pageContext) {
        dPage.contents.inject((ObjectWritable) null) { ObjectWritable objectWritable, PdfComponent dObject ->
            if (objectWritable && objectWritable.positionType == PositionType.RELATIVE)
                currentLocationAdjuster.adjust(pageContext, objectWritable.context)

            ObjectWritableFactory<? extends PdfComponent> writableFactory = writableFactoryFactory.createWriter(dObject)
            ObjectWritable writable = writableFactory.createFor(pageContext, dObject)
            writable.write()

            writable
        }
    }

}