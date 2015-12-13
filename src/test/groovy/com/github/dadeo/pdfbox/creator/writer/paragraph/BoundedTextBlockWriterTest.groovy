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
import com.github.dadeo.pdfbox.creator.writer.DWriter
import com.github.dadeo.pdfbox.creator.writer.text.AssignedLine
import com.github.dadeo.pdfbox.model.DBounds
import com.github.dadeo.pdfbox.model.DPoint
import spock.lang.Specification


class BoundedTextBlockWriterTest extends Specification {
    private static final DBounds CONTENTS_BOUNDS = new DBounds(800, 0, 0, 100)
    private static final DPoint STARTING_LOCATION = new DPoint(100, 800)
    private static final DPoint END_LOCATION_1 = new DPoint(100, 775)
    private static final DPoint END_LOCATION_2 = new DPoint(100, 750)
    private static final DPoint END_LOCATION_3 = new DPoint(100, 700)
    private static final DPoint END_LOCATION_4 = new DPoint(100, 675)

    private BoundedTextBlockLineWriter lineWriter = Mock(BoundedTextBlockLineWriter)
    private TextBlockLineWriterFactory lineWriterFactory = Mock(TextBlockLineWriterFactory)
    private BoundedTextBlockWriter textBlockWriter = new BoundedTextBlockWriter(textBlockLineWriterFactory: lineWriterFactory)
    private DContext context = new DContext()
    private BoundedTextBlock textBlock = new BoundedTextBlock()
    private DWriter dWriter = Mock(DWriter)
    private List<AssignedLine> assignedLines = (1..4).collect { Mock(AssignedLine) }

    def "current location from last line is passed in to the next line"() {
        given:

        context.contentsBounds = CONTENTS_BOUNDS
        context.writer = dWriter
        textBlock.assignedLines = assignedLines

        when:
        textBlockWriter.write(textBlock, context)

        then:
        1 * lineWriterFactory.createWriterFor(context) >> lineWriter
        1 * lineWriter.write(assignedLines[0], CONTENTS_BOUNDS, STARTING_LOCATION, dWriter) >> END_LOCATION_1
        1 * lineWriter.write(assignedLines[1], CONTENTS_BOUNDS, END_LOCATION_1, dWriter) >> END_LOCATION_2
        1 * lineWriter.write(assignedLines[2], CONTENTS_BOUNDS, END_LOCATION_2, dWriter) >> END_LOCATION_3
        1 * lineWriter.write(assignedLines[3], CONTENTS_BOUNDS, END_LOCATION_3, dWriter) >> END_LOCATION_4
        0 * _
    }

}