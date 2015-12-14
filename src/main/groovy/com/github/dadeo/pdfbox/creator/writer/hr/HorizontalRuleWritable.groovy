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

import com.github.dadeo.pdfbox.creator.BootStrap
import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.creator.writer.border.BorderDrawer
import com.github.dadeo.pdfbox.creator.writer.object.BackgroundPainter
import com.github.dadeo.pdfbox.creator.writer.object.ObjectWritable
import com.github.dadeo.pdfbox.model.DHorizontalRule

class HorizontalRuleWritable implements ObjectWritable {
    private HorizontalRuleContentsDrawer contentsDrawer = BootStrap.horizontalRuleContentsDrawer
    private BackgroundPainter backgroundPainter = BootStrap.backgroundPainter
    private BorderDrawer borderDrawer = BootStrap.borderDrawer
    private DHorizontalRule horizontalRule
    private DContext context

    HorizontalRuleWritable(DHorizontalRule horizontalRule, DContext context) {
        this.horizontalRule = horizontalRule
        this.context = context
    }

    @Override
    void write() {
        backgroundPainter.paintFor(context)
        contentsDrawer.drawFor(horizontalRule, context)
        borderDrawer.drawFor(horizontalRule, context)
    }

    @Override
    DContext getContext() {
        context
    }
}