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
package com.github.dadeo.pdfbox.dsl

import com.github.dadeo.pdfbox.model.*
import org.apache.pdfbox.pdmodel.font.PDType1Font
import spock.lang.Specification

class DocumentBuilderTest extends Specification {
    private Font FONT_1 = new Font(PDType1Font.HELVETICA_BOLD, 12)
    private Font FONT_2 = new Font(PDType1Font.HELVETICA, 10)
    private DocumentBuilder builder = new DocumentBuilder()

    def "creates empty document"() {
        when:
        Document document = builder.document()

        then:
        document instanceof Document
        document.pages == []
    }

    def "creates document with page"() {
        when:
        Document document = builder.document {
            page()
        }

        then:
        document.pages == [new Page()]
    }

    def "creates document with multiple pages"() {
        when:
        Document document = builder.document {
            page()
            page()
        }

        then:
        document.pages == [new Page(), new Page()]
    }

    def "creates page with font"() {
        when:
        Page page = builder.page(font: FONT_1)

        then:
        page instanceof Page
        page.contents == []
        page.font == FONT_1
    }

    def "creates page with contents"() {
        when:
        Page page = builder.page {
            paragraph(font: FONT_1)
            hr()
        }

        then:
        page instanceof Page
        page.contents == [new Paragraph(font: FONT_1), new HorizontalRule()]
    }

    def "creates empty paragraph"() {
        when:
        Paragraph paragraph = builder.paragraph()

        then:
        paragraph == new Paragraph()
    }

    def "creates paragraph with a font and a part"() {
        when:
        Paragraph paragraph = builder.paragraph("yo dog", justification: Justification.CENTER, font: FONT_1)

        then:
        paragraph == new Paragraph(justification: Justification.CENTER, font: FONT_1, contents: [new Part("yo dog")])
    }

    def "creates paragraph text specified with text, justification, and font attributes"() {
        when:
        Paragraph paragraph = builder.paragraph(text: "yo dog", justification: Justification.CENTER, font: FONT_1)

        then:
        paragraph == new Paragraph(justification: Justification.CENTER, font: FONT_1, contents: [new Part("yo dog")])
    }

    def "creates paragraph where text attribute is an Object"() {
        given:
        Object textObject = new Object() {
            @Override
            String toString() {
                "yo dog"
            }
        }

        when:
        Paragraph paragraph = builder.paragraph(text: textObject, justification: Justification.CENTER, font: FONT_1)

        then:
        paragraph == new Paragraph(justification: Justification.CENTER, font: FONT_1, contents: [new Part("yo dog")])
    }

    def "creates paragraph where text parameter is an Object"() {
        given:
        Object textObject = new Object() {
            @Override
            String toString() {
                "yo dog"
            }
        }

        when:
        Paragraph paragraph = builder.paragraph(textObject, justification: Justification.CENTER, font: FONT_1)

        then:
        paragraph == new Paragraph(justification: Justification.CENTER, font: FONT_1, contents: [new Part("yo dog")])
    }

    def "creates paragraph with multiple parts"() {
        when:
        Paragraph paragraph = builder.paragraph {
            part(text: "yo ", font: FONT_1)
            part(text: "dog", font: FONT_2)
        }

        then:
        paragraph == new Paragraph(contents: [new Part(text: "yo ", font: FONT_1), new Part(text: "dog", font: FONT_2)])
    }

    def "creates empty part"() {
        when:
        Part part = builder.part()

        then:
        part == new Part()
    }

    def "creates part with text parameter (String)"() {
        when:
        Part part = builder.part("yo dog")

        then:
        part == new Part(text: "yo dog")
    }

    def "creates part with text parameter (Object)"() {
        given:
        Object textObject = new Object() {
            @Override
            String toString() {
                "yo dog"
            }
        }

        when:
        Part part = builder.part(textObject)

        then:
        part == new Part(text: "yo dog")
    }

    def "creates part with text specified with text attribute (String)"() {
        when:
        Part part = builder.part(text: "yo dog")

        then:
        part == new Part(text: "yo dog")
    }

    def "creates part with text specified with text attribute (Object)"() {
        given:
        Object textObject = new Object() {
            @Override
            String toString() {
                "yo dog"
            }
        }

        when:
        Part part = builder.part(text: textObject)

        then:
        part == new Part(text: "yo dog")
    }

    def "creates part with text and font"() {
        when:
        Part part = builder.part("yo dog", font: FONT_2)

        then:
        part == new Part(text: "yo dog", font: FONT_2)
    }

    def "creates empty horizontal rule"() {
        when:
        HorizontalRule horizontalRule = builder.hr()

        then:
        horizontalRule == new HorizontalRule()
    }

    def "creates horizontal rule with thickness"() {
        when:
        HorizontalRule horizontalRule = builder.hr(thickness: 4f)

        then:
        horizontalRule == new HorizontalRule(thickness: 4f)
    }

    def "creates empty table"() {
        when:
        Table table = builder.table()

        then:
        table instanceof Table
    }

    def "creates table with column ratios and vertical alignment"() {
        when:
        Table table = builder.table(columnRatios: [1, 2, 3], verticalAlignment: VerticalAlignment.BOTTOM)

        then:
        table.columnRatios == [1f, 2f, 3f]
        table.verticalAlignment == VerticalAlignment.BOTTOM
    }

    def "creates table with cells"() {
        when:
        Table table = builder.table(columnRatios: [1, 2, 3], verticalAlignment: VerticalAlignment.BOTTOM) {
            cell()
            cell()
        }

        then:
        table.columnRatios == [1f, 2f, 3f]
        table.verticalAlignment == VerticalAlignment.BOTTOM
        table.contents == [new Cell(), new Cell()]
    }

    def "creates empty cell"() {
        when:
        Cell cell = builder.cell()

        then:
        cell == new Cell()
    }

    def "creates cell with vertical alignment and content"() {
        when:
        Cell cell = builder.cell(verticalAlignment: VerticalAlignment.MIDDLE) {
            paragraph()
            hr()
            paragraph()
        }

        then:
        cell.verticalAlignment == VerticalAlignment.MIDDLE
        cell.contents == [new Paragraph(), new HorizontalRule(), new Paragraph()]
    }

    def "creates cell with paragraph with text parameter (String)"() {
        when:
        Cell cell = builder.cell("yo dog", verticalAlignment: VerticalAlignment.MIDDLE)

        then:
        cell.verticalAlignment == VerticalAlignment.MIDDLE
        cell.contents == [new Paragraph("yo dog")]
    }

    def "creates cell with paragraph with text parameter (Object)"() {
        given:
        Object textObject = new Object() {
            @Override
            String toString() {
                "yo dog"
            }
        }

        when:
        Cell cell = builder.cell(textObject, verticalAlignment: VerticalAlignment.MIDDLE)

        then:
        cell.verticalAlignment == VerticalAlignment.MIDDLE
        cell.contents == [new Paragraph("yo dog")]
    }

    def "creates cell with paragraph when text (String) passed in with text attribute"() {
        when:
        Cell cell = builder.cell(text: "yo dog", verticalAlignment: VerticalAlignment.MIDDLE)

        then:
        cell.verticalAlignment == VerticalAlignment.MIDDLE
        cell.contents == [new Paragraph("yo dog")]
    }

    def "creates cell with paragraph when text (Object) passed in with text attribute"() {
        given:
        Object textObject = new Object() {
            @Override
            String toString() {
                "yo dog"
            }
        }

        when:
        Cell cell = builder.cell(text: textObject, verticalAlignment: VerticalAlignment.MIDDLE)

        then:
        cell.verticalAlignment == VerticalAlignment.MIDDLE
        cell.contents == [new Paragraph("yo dog")]
    }

    def "creates a canvas with height and content"() {
        given:
        Closure content = { -> }

        when:
        Canvas canvas = builder.canvas height: 18, content

        then:
        canvas.height == 18
        canvas.content.is content
    }

}