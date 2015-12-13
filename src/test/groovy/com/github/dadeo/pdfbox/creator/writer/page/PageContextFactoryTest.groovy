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
import com.github.dadeo.pdfbox.model.DFont
import com.github.dadeo.pdfbox.model.DPage
import spock.lang.Specification


class PageContextFactoryTest extends Specification {
    private static final DFont DOCUMENT_FONT = new DFont()
    private static final DFont PAGE_FONT = new DFont()

    private PageContextFactory factory = new PageContextFactory()
    private DContext clonedContext = new DContext(font: DOCUMENT_FONT)
    private DContext documentContext = Mock(DContext) {
        1 * clone() >> clonedContext
    }

    def "document context is cloned and returned"() {
        expect:

        factory.createContextFrom(documentContext, new DPage()).is clonedContext
    }

    def "DPage font overrides document context font"() {
        expect:

        factory.createContextFrom(documentContext, new DPage(font: PAGE_FONT)).font.is PAGE_FONT
    }

    def "page context contains document context font when DPage font is null"() {
        expect:

        factory.createContextFrom(documentContext, new DPage()).font.is DOCUMENT_FONT
    }

    def "page context contains document context as parent"() {
        expect:

        factory.createContextFrom(documentContext, new DPage()).parent.is documentContext
    }
}