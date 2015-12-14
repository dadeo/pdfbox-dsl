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
import com.github.dadeo.pdfbox.creator.writer.border.BorderDrawer
import com.github.dadeo.pdfbox.creator.writer.object.BackgroundPainter
import com.github.dadeo.pdfbox.model.DHorizontalRule
import spock.lang.Specification

class HorizontalRuleWritableTest extends Specification {
    private HorizontalRuleContentsDrawer contentsDrawer = Mock(HorizontalRuleContentsDrawer)
    private BackgroundPainter backgroundPainter = Mock(BackgroundPainter)
    private BorderDrawer borderDrawer = Mock(BorderDrawer)
    private DContext context = new DContext()
    private DHorizontalRule horizontalRule = new DHorizontalRule()

    def "write paragraph orchestration"() {
        given:
        HorizontalRuleWritable horizontalRuleWritable = new HorizontalRuleWritable(horizontalRule, context)
        horizontalRuleWritable.backgroundPainter = backgroundPainter
        horizontalRuleWritable.contentsDrawer = contentsDrawer
        horizontalRuleWritable.borderDrawer = borderDrawer

        when:
        horizontalRuleWritable.write()

        then:
        1 * backgroundPainter.paintFor(context)
        1 * contentsDrawer.drawFor(horizontalRule, context)
        1 * borderDrawer.drawFor(horizontalRule, context)
    }

}