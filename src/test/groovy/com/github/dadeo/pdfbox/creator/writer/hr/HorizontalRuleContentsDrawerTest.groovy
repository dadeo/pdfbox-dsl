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
package com.github.dadeo.pdfbox.creator.writer.hr

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.creator.writer.DWriter
import com.github.dadeo.pdfbox.model.Bounds
import com.github.dadeo.pdfbox.model.Point
import com.github.dadeo.pdfbox.model.HorizontalRule
import spock.lang.Specification

import java.awt.*

class HorizontalRuleContentsDrawerTest extends Specification {
    private HorizontalRuleContentsDrawer contentsDrawer = new HorizontalRuleContentsDrawer()
    private HorizontalRule horizontalRule = new HorizontalRule()
    private DWriter dWriter = Mock(DWriter)
    private DContext horizontalRuleContext = new DContext(writer: dWriter)

    def "draws line centered between contents bounds"() {
        given:
        horizontalRuleContext.contentsBounds = new Bounds(600f, 700f, 500f, 100f)
        horizontalRule.thickness = 4f
        horizontalRule.color = Color.red

        when:
        contentsDrawer.drawFor(horizontalRule, horizontalRuleContext)

        then:
        1 * dWriter.drawLine(new Point(99.5f, 550f), new Point(700.5f, 550f), 4, Color.red)
    }

}