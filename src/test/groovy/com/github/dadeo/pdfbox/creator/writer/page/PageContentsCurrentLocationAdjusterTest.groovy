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
import com.github.dadeo.pdfbox.model.DBounds
import com.github.dadeo.pdfbox.model.DPoint
import spock.lang.Specification


class PageContentsCurrentLocationAdjusterTest extends Specification {
    private static final DBounds CONTENTS_BOUNDS = new DBounds(500, 600, 150, 120)
    private static final DBounds UPDATED_CONTENTS_BOUNDS = new DBounds(349, 600, 150, 120)
    private PageContentsCurrentLocationAdjuster adjuster = new PageContentsCurrentLocationAdjuster()

    def "adjusts the current location to the bottom bounds of the previous element"() {
        given:
        DContext pageContext = new DContext(contentsBounds: CONTENTS_BOUNDS)
        ElementDetails previousElementDetails = Mock(ElementDetails)
        1 * previousElementDetails.containingBounds >> new DBounds(500, 550, 350, 120)

        when:
        adjuster.adjust(pageContext, previousElementDetails)

        then:
        pageContext.contentsBounds == UPDATED_CONTENTS_BOUNDS
    }

}