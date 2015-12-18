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
package com.github.dadeo.pdfbox.creator.writer

import com.github.dadeo.pdfbox.creator.BootStrap
import com.github.dadeo.pdfbox.creator.writer.page.PageWriter
import com.github.dadeo.pdfbox.model.Document
import org.apache.pdfbox.pdmodel.PDDocument

class DocumentWriter {
    PageWriter pageWriter = BootStrap.pageWriter

    byte[] write(Document dDocument) {
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