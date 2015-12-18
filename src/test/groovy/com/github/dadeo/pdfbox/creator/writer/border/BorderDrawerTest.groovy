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
package com.github.dadeo.pdfbox.creator.writer.border

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.creator.writer.DWriter
import com.github.dadeo.pdfbox.model.Bordered
import com.github.dadeo.pdfbox.model.Bounds
import com.github.dadeo.pdfbox.model.LineBorder
import spock.lang.Specification

import static org.hamcrest.CoreMatchers.sameInstance

class BorderDrawerTest extends Specification {
    private static final Bounds BORDER_BOUNDS = new Bounds(0, 0, 0, 0)
    private BorderDrawer drawer = new BorderDrawer()
    private LineBorder border = Mock(LineBorder)
    private Bordered bordered = Mock(Bordered)
    private DWriter writer = new DWriter()
    private DContext pageContext = new DContext()

    def "requests border to draw itself"() {
        given:

        bordered.borderPainter >> border
        pageContext.writer = writer
        pageContext.borderBounds = BORDER_BOUNDS

        when:

        drawer.drawFor(bordered, pageContext)

        then:

        1 * border.drawBorder(sameInstance(bordered), sameInstance(writer), sameInstance(BORDER_BOUNDS))
    }

    def "nothing drawn if border does not exist"() {
        given:

        bordered.borderPainter >> null
        pageContext.writer = writer
        pageContext.borderBounds = BORDER_BOUNDS

        when:

        drawer.drawFor(bordered, pageContext)

        then:

        0 * border.drawBorder(sameInstance(bordered), sameInstance(writer), sameInstance(BORDER_BOUNDS))
    }

}