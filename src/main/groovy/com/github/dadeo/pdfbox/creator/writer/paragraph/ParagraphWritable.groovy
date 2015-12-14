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
package com.github.dadeo.pdfbox.creator.writer.paragraph

import com.github.dadeo.pdfbox.creator.BootStrap
import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.creator.writer.border.BorderDrawer
import com.github.dadeo.pdfbox.creator.writer.object.BackgroundPainter
import com.github.dadeo.pdfbox.creator.writer.object.ObjectWritable
import com.github.dadeo.pdfbox.model.DParagraph

class ParagraphWritable implements ObjectWritable {
    private BoundedTextBlockWriter boundedTextBlockWriter = BootStrap.boundedTextBlockWriter
    private BackgroundPainter backgroundPainter = BootStrap.backgroundPainter
    private BorderDrawer borderDrawer = BootStrap.borderDrawer
    private DParagraph dParagraph
    private BoundedTextBlock textBlock
    private DContext context

    ParagraphWritable(DParagraph paragraph, BoundedTextBlock textBlock, DContext context) {
        this.dParagraph = paragraph
        this.textBlock = textBlock
        this.context = context
    }

    @Override
    void write() {
        backgroundPainter.paintFor(context)
        boundedTextBlockWriter.write(textBlock, context)
        borderDrawer.drawFor(dParagraph, context)
    }

    @Override
    DContext getContext() {
        context
    }
}