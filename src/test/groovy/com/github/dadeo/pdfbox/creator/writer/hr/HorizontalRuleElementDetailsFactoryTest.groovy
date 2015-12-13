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
import com.github.dadeo.pdfbox.model.DBounds
import com.github.dadeo.pdfbox.model.DHorizontalRule
import spock.lang.Specification


class HorizontalRuleElementDetailsFactoryTest extends Specification {
    private HorizontalRuleElementDetailsFactory factory = new HorizontalRuleElementDetailsFactory()
    private DContext context = new DContext()
    private DHorizontalRule horizontalRule = new DHorizontalRule()
    private DBounds containingBounds = new DBounds(1, 2, 3, 4)

    def "creates details with containing bounds"() {
        given:
        context.containingBounds = containingBounds

        when:
        HorizontalRuleElementDetails details = factory.createFor(horizontalRule, context)

        then:
        details.containingBounds == containingBounds
    }

}