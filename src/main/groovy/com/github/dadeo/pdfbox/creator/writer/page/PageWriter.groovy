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
import com.github.dadeo.pdfbox.creator.writer.border.BorderDrawer
import com.github.dadeo.pdfbox.model.Page

class PageWriter {
    PageContextFactory pageContextFactory
    PagePdfBoxHelper pagePdfBoxHelper
    PageBoundsCalculator pageBoundsCalculator
    PageContentsWriter pageContentsWriter
    BorderDrawer pageBorderDrawer

    void write(DContext documentContext, Page dPage) {
        DContext pageContext = pageContextFactory.createContextFrom(documentContext, dPage)

        pagePdfBoxHelper.initializePageObjects(pageContext)

        pageBoundsCalculator.addCalculationsTo(pageContext, dPage)

        pageContentsWriter.writeContents(dPage, pageContext)

        pageBorderDrawer.drawFor(dPage, pageContext)

        pagePdfBoxHelper.close(pageContext)
    }

}