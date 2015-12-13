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
import com.github.dadeo.pdfbox.creator.writer.object.ObjectWritable
import com.github.dadeo.pdfbox.creator.writer.object.ObjectWritableFactory
import com.github.dadeo.pdfbox.creator.writer.page.ElementDetails
import com.github.dadeo.pdfbox.model.DHorizontalRule

class HorizontalRuleWritableFactory implements ObjectWritableFactory<DHorizontalRule> {
    ObjectContextFactory contextFactory
    ObjectBoundsCalculator objectBoundsCalculator
    HorizontalRuleElementDetailsFactory elementDetailsFactory

    @Override
    ObjectWritable createFor(DContext pageContext, DHorizontalRule horizontalRule, ElementDetails previousElementDetails) {
        DContext horizontalRuleContext = contextFactory.createContextFrom(pageContext, horizontalRule)
        objectBoundsCalculator.resizeBoundsToHeight(horizontalRule.thickness, horizontalRuleContext)
        ElementDetails currentElementDetails = elementDetailsFactory.createFor(horizontalRule, horizontalRuleContext)
        new HorizontalRuleWritable(horizontalRule, horizontalRuleContext, currentElementDetails)
    }
}