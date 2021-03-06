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

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.creator.writer.border.BorderDrawer
import com.github.dadeo.pdfbox.creator.writer.object.BackgroundPainter
import com.github.dadeo.pdfbox.model.Paragraph
import spock.lang.Specification

class ParagraphWritableTest extends Specification {
    private BoundedTextBlockWriter boundedTextBlockWriter = Mock(BoundedTextBlockWriter)
    private BackgroundPainter backgroundPainter = Mock(BackgroundPainter)
    private BorderDrawer borderDrawer = Mock(BorderDrawer)
    private DContext paragraphContext = new DContext()
    private Paragraph paragraph = new Paragraph()
    private BoundedTextBlock textBlock = Mock(BoundedTextBlock)

    def "write paragraph orchestration"() {
        given:
        ParagraphWritable paragraphWritable = new ParagraphWritable(paragraph, textBlock, paragraphContext)
        paragraphWritable.boundedTextBlockWriter = boundedTextBlockWriter
        paragraphWritable.backgroundPainter = backgroundPainter
        paragraphWritable.borderDrawer = borderDrawer

        when:
        paragraphWritable.write()

        then:
        1 * backgroundPainter.paintFor(paragraphContext)
        1 * boundedTextBlockWriter.write(textBlock, paragraphContext)
        1 * borderDrawer.drawFor(paragraph, paragraphContext)
    }

}