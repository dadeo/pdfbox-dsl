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
import com.github.dadeo.pdfbox.creator.writer.object.ObjectBoundsCalculator
import com.github.dadeo.pdfbox.creator.writer.object.ObjectContextFactory
import com.github.dadeo.pdfbox.creator.writer.page.ElementDetails
import com.github.dadeo.pdfbox.model.DHorizontalRule
import spock.lang.Specification

class HorizontalRuleWritableFactoryTest extends Specification {
    private static final float THICKNESS = 150
    private ObjectContextFactory contextFactory = Mock(ObjectContextFactory)
    private HorizontalRuleElementDetailsFactory elementDetailsFactory = Mock(HorizontalRuleElementDetailsFactory)
    private ObjectBoundsCalculator objectBoundsCalculator = Mock(ObjectBoundsCalculator)
    private HorizontalRuleWritableFactory horizontalRuleWritableFactory = new HorizontalRuleWritableFactory(contextFactory: contextFactory,
                                                                                                            objectBoundsCalculator: objectBoundsCalculator,
                                                                                                            elementDetailsFactory: elementDetailsFactory)
    private DContext parentContext = Mock(DContext)
    private DHorizontalRule horizontalRule = new DHorizontalRule()
    private ElementDetails previousElementDetails = Mock(ElementDetails)
    private ElementDetails currentElementDetails = Mock(ElementDetails)

    def "createFor returns a configured writable"() {
        given:
        DContext horizontalRuleContext = Mock(DContext)
        horizontalRule.thickness = THICKNESS

        when:
        HorizontalRuleWritable elementDetails = horizontalRuleWritableFactory.createFor(parentContext, horizontalRule, previousElementDetails) as HorizontalRuleWritable

        then:
        elementDetails.horizontalRule.is horizontalRule
        elementDetails.context.is horizontalRuleContext
        elementDetails.elementDetails.is currentElementDetails

        1 * contextFactory.createContextFrom(parentContext, horizontalRule) >> horizontalRuleContext
        1 * objectBoundsCalculator.resizeBoundsToHeight(THICKNESS, horizontalRuleContext)
        1 * elementDetailsFactory.createFor(horizontalRule, horizontalRuleContext) >> currentElementDetails
        0 * _
    }

}