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
package com.github.dadeo.pdfbox

import com.github.dadeo.pdfbox.creator.BootStrap
import com.github.dadeo.pdfbox.creator.PdfCreator
import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.creator.writer.DWriter
import com.github.dadeo.pdfbox.creator.writer.hr.HorizontalRuleContentsDrawer
import com.github.dadeo.pdfbox.creator.writer.object.ObjectContextFactory
import com.github.dadeo.pdfbox.creator.writer.object.PositionType
import com.github.dadeo.pdfbox.dsl.DocumentBuilder
import com.github.dadeo.pdfbox.dsl.PdfMeasurements
import com.github.dadeo.pdfbox.model.*
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.font.PDFont
import org.apache.pdfbox.pdmodel.font.PDType1Font
import org.junit.Test

import java.awt.*

class VisualPlaygroundTest {
    static final float ONE_INCH = 72f

    private static final String LOREM_IPSUM = """|Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod
                                                 |tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,
                                                 |quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo
                                                 |consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse
                                                 |cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non
                                                 |proident, sunt in culpa qui officia deserunt mollit anim id est laborum g."""
        .stripMargin()
        .replaceAll(/\r|\n/, '')

    private Closure<Float> toFloat = { Number number -> number.toFloat() }
    private Closure<Point> point = { Number x, Number y ->
        new Point(toFloat(x), toFloat(y))
    }

    @Test
    void test_use_pdfbox_api_directly() {
        PDDocument document = new PDDocument()
        PDPage page = new PDPage()

        PDFont font = PDType1Font.HELVETICA_BOLD

        PDPageContentStream contents = new PDPageContentStream(document, page)

        contents.saveGraphicsState()

        Closure<Float> noopAdjust = { y, thicknesse -> y as float }
        Closure<Float> topRightAdjust = { y, thickness -> (float) (y - (thickness / 2) + 0.5) }
        Closure<Float> bottomLeftAdjust = { y, thickness -> (float) (y + (thickness / 2) - 0.5) }

        Closure drawHLine = { Number x1, Number x2, Number y, float thickness, Color color, Closure<Float> adj ->
            float adjustedY = adj(y, thickness)
            contents.moveTo(x1 as float, adjustedY)
            contents.lineTo(x2 as float, adjustedY)
            contents.strokingColor = color
            contents.lineWidth = thickness
            contents.stroke()
        }

        Closure drawVLine = { Number x, Number y1, Number y2, float thickness, Color color, Closure<Float> adj ->
            float adjustedX = adj(x, thickness)
            contents.moveTo(adjustedX, y1 as float)
            contents.lineTo(adjustedX, y2 as float)
            contents.strokingColor = color
            contents.lineWidth = thickness
            contents.stroke()
        }

        Closure<Float> adjust = topRightAdjust
        drawHLine(ONE_INCH, 7.5 * ONE_INCH, 9.25 * ONE_INCH, 1, Color.black, noopAdjust)
        drawHLine(1.5 * ONE_INCH, 2 * ONE_INCH, 9.25 * ONE_INCH, 2, Color.yellow, adjust)
        drawHLine(2.5 * ONE_INCH, 3 * ONE_INCH, 9.25 * ONE_INCH, 3, Color.yellow, adjust)
        drawHLine(3.5 * ONE_INCH, 4 * ONE_INCH, 9.25 * ONE_INCH, 4, Color.yellow, adjust)
        drawHLine(4.5 * ONE_INCH, 5 * ONE_INCH, 9.25 * ONE_INCH, 8, Color.yellow, adjust)
        drawHLine(5.5 * ONE_INCH, 6 * ONE_INCH, 9.25 * ONE_INCH, 9, Color.yellow, adjust)

        adjust = bottomLeftAdjust
        drawHLine(ONE_INCH, 7.5 * ONE_INCH, 9.0 * ONE_INCH, 1, Color.black, noopAdjust)
        drawHLine(1.5 * ONE_INCH, 2 * ONE_INCH, 9.0 * ONE_INCH, 2, Color.yellow, adjust)
        drawHLine(2.5 * ONE_INCH, 3 * ONE_INCH, 9.0 * ONE_INCH, 3, Color.yellow, adjust)
        drawHLine(3.5 * ONE_INCH, 4 * ONE_INCH, 9.0 * ONE_INCH, 4, Color.yellow, adjust)
        drawHLine(4.5 * ONE_INCH, 5 * ONE_INCH, 9.0 * ONE_INCH, 8, Color.yellow, adjust)
        drawHLine(5.5 * ONE_INCH, 6 * ONE_INCH, 9.0 * ONE_INCH, 9, Color.yellow, adjust)

        adjust = bottomLeftAdjust
        drawVLine(ONE_INCH, 9.0 * ONE_INCH, ONE_INCH, 1, Color.black, noopAdjust)
        drawVLine(ONE_INCH, 8.5 * ONE_INCH, 8.0 * ONE_INCH, 2, Color.yellow, adjust)
        drawVLine(ONE_INCH, 7.75 * ONE_INCH, 7.25 * ONE_INCH, 3, Color.yellow, adjust)
        drawVLine(ONE_INCH, 7.0 * ONE_INCH, 6.5 * ONE_INCH, 4, Color.yellow, adjust)
        drawVLine(ONE_INCH, 6.25 * ONE_INCH, 5.75 * ONE_INCH, 8, Color.yellow, adjust)
        drawVLine(ONE_INCH, 5.5 * ONE_INCH, 5.0 * ONE_INCH, 9, Color.yellow, adjust)

        adjust = topRightAdjust
        drawVLine(1.5 * ONE_INCH, 9.0 * ONE_INCH, ONE_INCH, 1, Color.black, noopAdjust)
        drawVLine(1.5 * ONE_INCH, 8.5 * ONE_INCH, 8.0 * ONE_INCH, 2, Color.yellow, adjust)
        drawVLine(1.5 * ONE_INCH, 7.75 * ONE_INCH, 7.25 * ONE_INCH, 3, Color.yellow, adjust)
        drawVLine(1.5 * ONE_INCH, 7.0 * ONE_INCH, 6.5 * ONE_INCH, 4, Color.yellow, adjust)
        drawVLine(1.5 * ONE_INCH, 6.25 * ONE_INCH, 5.75 * ONE_INCH, 8, Color.yellow, adjust)
        drawVLine(1.5 * ONE_INCH, 5.5 * ONE_INCH, 5.0 * ONE_INCH, 9, Color.yellow, adjust)

        Bordered bordered = new Bordered() {}
        bordered.border = 1
        DWriter dWriter = new DWriter(contentStream: contents)
        LineBorder lineBorder = new LineBorder()
        lineBorder.drawBorder(bordered, dWriter, new Bounds(toFloat(9.25f), toFloat(1.75f * ONE_INCH), toFloat(9.0f * ONE_INCH), toFloat(1.70f * ONE_INCH)))

        ObjectContextFactory contextFactory = BootStrap.objectContextFactory

        DContext parentContext = new DContext(writer: dWriter, contentsBounds: Bounds.createFrom(point(ONE_INCH, 8.8 * ONE_INCH), point(1.5 * ONE_INCH, 8.6 * ONE_INCH)))
        HorizontalRule horizontalRule = new HorizontalRule(thickness: 8, color: Color.yellow)
        DContext horizontalRuleContext = contextFactory.createContextFrom(parentContext, horizontalRule)
        HorizontalRuleContentsDrawer horizontalRuleContentsDrawer = new HorizontalRuleContentsDrawer()
        horizontalRuleContentsDrawer.drawFor(horizontalRule, horizontalRuleContext)

        parentContext = new DContext(writer: dWriter, contentsBounds: Bounds.createFrom(point(1.1 * ONE_INCH, 9.25 * ONE_INCH), point(1.4 * ONE_INCH, 9.0 * ONE_INCH)))
        horizontalRule = new HorizontalRule(thickness: toFloat(0.25 * ONE_INCH + 1), color: Color.yellow)
        contextFactory = BootStrap.objectContextFactory
        horizontalRuleContext = contextFactory.createContextFrom(parentContext, horizontalRule)
        horizontalRuleContentsDrawer.drawFor(horizontalRule, horizontalRuleContext)

        contents.restoreGraphicsState()

        int fontSize = 12
        Closure<Float> unitsToDpi = { (float) ((it / 1000) * fontSize) }

        int topBorder = 10 * ONE_INCH
        int rightBorder = 7.5 * ONE_INCH

        String line1 = "Hello PDFBox World 1."

        contents.beginText()
        contents.setFont(font, fontSize)
        contents.newLineAtOffset(ONE_INCH, topBorder)
        contents.showText(line1)
        contents.endText()

        float newX = ONE_INCH + unitsToDpi(font.getStringWidth(line1)) + unitsToDpi(font.spaceWidth)
        contents.beginText()
        contents.setFont(font, fontSize)
        contents.newLineAtOffset(newX, topBorder)
        contents.showText("And there it is!")
        contents.endText()

        contents.beginText()
        contents.setFont(font, fontSize)
        contents.newLineAtOffset(ONE_INCH, topBorder - fontSize)
        contents.showText("Hello PDFBox World 7. And there it is!")
        contents.endText()

//        newX = ONE_INCH + unitsToDpi(font.getStringWidth(line1))
//        contents.beginText()
//        contents.setFont(font, fontSize)
//        contents.newLineAtOffset(newX, topBorder - fontSize)
//        contents.showText("And there it is!")
//        contents.endText()

        int bottomBorderY = topBorder - 2
        contents.moveTo(ONE_INCH, bottomBorderY)
        contents.lineTo(rightBorder, bottomBorderY)
        contents.stroke()

        int topBorderY = bottomBorderY + fontSize
        contents.moveTo(ONE_INCH, topBorderY)
        contents.lineTo(rightBorder, topBorderY)
        contents.stroke()

        int underline = bottomBorderY - fontSize
        float underlineStartX = ONE_INCH + unitsToDpi(font.getStringWidth("Hello "))
        float underlineEndX = underlineStartX + unitsToDpi(font.getStringWidth("PDFBox"))
        contents.moveTo(underlineStartX, underline)
        contents.lineTo(underlineEndX, underline)
        contents.stroke()

        contents.close()

        document.addPage(page)
        document.save(new File('target/VisualPlaygroundTest.pdf'))
        document.close()
    }

    @Test
    void test_dsl_document() {
        use(PdfMeasurements) {
            Document document = new DocumentBuilder().document {
                page margin: 1.inch, {
                    paragraph text: LOREM_IPSUM
                    paragraph text: LOREM_IPSUM
                    paragraph text: LOREM_IPSUM
                    paragraph text: LOREM_IPSUM, border: 1
                    paragraph text: LOREM_IPSUM, border: 1
                    paragraph text: LOREM_IPSUM, border: 1
                }
                page margin: 1.inch, {
                    table(columnRatios: [1, 1]) {
                        cell('2 adjacent tables, both with borders')
                        cell {
                            2.times { index ->
                                int tableNumber = index + 1
                                table columnRatios: [1], border: 3, borderColor: Color.red, backgroundColor: new Color(210, 210, 210), {
                                    cell border: 2, borderColor: Color.blue, {
                                        paragraph "this is table $tableNumber, cell 1, paragraph 1", border: 1
                                        paragraph "this is table $tableNumber, cell 1, paragraph 2", border: 1
                                    }
                                    cell border: 2, borderColor: Color.blue, {
                                        paragraph "this is table $tableNumber, cell 2, paragraph 1", border: 1
                                        paragraph "this is table $tableNumber, cell 2, paragraph 2", border: 1
                                    }
                                }
                            }
                        }
                        cell '2 adjacent tables, both without borders', marginTop: 1.cm
                        cell marginTop: 1.cm, {
                            2.times { index ->
                                int tableNumber = index + 1
                                table columnRatios: [1], {
                                    cell {
                                        paragraph "this 1s table $tableNumber,\ncell 1 ggggg,\nparagraph 1", lineLeading: 2
                                        paragraph "this 1s table $tableNumber,\ncell 1 ggggg,\nparagraph 2", lineLeading: 2
                                    }
                                    cell {
                                        paragraph "this 1s table $tableNumber,\ncell 2 ggggg,\nparagraph 1", lineLeading: 2
                                        paragraph "this 1s table $tableNumber,\ncell 2 ggggg,\nparagraph 2", border: 1, backgroundColor: Color.pink, firstLineLeading: -2, lineLeading: 5
                                    }
                                }
                            }
                        }
                    }

                    table columnRatios: [1],
                          positionType: PositionType.ABSOlUTE,
                          position: Bounds.createFrom(new Point(0.5.inch, 2.75.inch), new Point(1.5.inch, 1.75.inch)),
                          {
                              cell border: 2, {
                                  paragraph text: 'Confirmation\nCode:', font: new Font(PDType1Font.HELVETICA_BOLD_OBLIQUE, 10), justification: Justification.CENTER
                                  paragraph text: '4321', font: new Font(PDType1Font.HELVETICA_BOLD, 14), justification: Justification.CENTER
                              }
                          }

                    paragraph LOREM_IPSUM, border: 1

                    canvas height: 10, { Bounds bounds, DWriter writer ->
                        writer.drawLine(bounds.leftTop(), bounds.rightBottom())
                        writer.drawLine(bounds.leftBottom(), bounds.rightTop())
                    }
                }
            }

            new File('target/DslDocumentPdf.pdf').bytes = new PdfCreator().createFor(document)
        }
    }

    @Test
    void test_build_DDocument() {

        Closure timer = { String description, Closure timed ->
            long startTime = System.currentTimeMillis()
            timed()
            println("duration($description): ${System.currentTimeMillis() - startTime}")
        }

        PDType1Font.HELVETICA_BOLD

        1.times {

            timer("create pdf") {
                Document document = new Document()

                DocumentBuilder builder = new DocumentBuilder()

                Table confirmationCodeTable = builder.table columnRatios: [1],
                                                            positionType: PositionType.ABSOlUTE,
                                                            position: new Bounds((float) (1.5 * ONE_INCH), (float) (1.5 * ONE_INCH), (float) (0.5 * ONE_INCH), (float) (0.5 * ONE_INCH)),
                                                            {
                                                                cell border: 2, {
                                                                    paragraph font: new Font(PDType1Font.HELVETICA_BOLD_OBLIQUE, 10), justification: Justification.CENTER, {
                                                                        part text: 'Confirmation'
                                                                    }
                                                                    paragraph font: new Font(PDType1Font.HELVETICA_BOLD_OBLIQUE, 10), justification: Justification.CENTER, {
                                                                        part text: "Code:"
                                                                    }
                                                                    paragraph font: new Font(PDType1Font.HELVETICA_BOLD, 14), justification: Justification.CENTER, {
                                                                        part text: '4321'
                                                                    }
                                                                }
                                                            }

                Paragraph paragraph1 = new Paragraph(LOREM_IPSUM)
                paragraph1.font = new Font(PDType1Font.HELVETICA_OBLIQUE, 6)
                paragraph1.border = 1
                paragraph1.padding = 0
                paragraph1.justification = Justification.LEFT
                Paragraph paragraph3 = new Paragraph(LOREM_IPSUM)
                paragraph3.font = new Font(PDType1Font.HELVETICA_OBLIQUE, 6)
                paragraph3.border = 1
//                paragraph3.borderPainter = new DashedBorder()
                paragraph3.padding = 4
                paragraph3.justification = Justification.RIGHT
                paragraph3.backgroundColor = Color.lightGray
                Paragraph paragraph4 = new Paragraph(LOREM_IPSUM)
                paragraph4.font = new Font(PDType1Font.HELVETICA_BOLD, 18, Color.BLUE)
                paragraph4.border = 1
                paragraph4.padding = 0
                paragraph4.justification = Justification.CENTER
//                paragraph4.borderTopColor = Color.CYAN
//                paragraph4.borderBottomColor = Color.pink
//                paragraph4.borderPainter = new DashedBorder(verticalFragments: 5, verticalFragmentLength: 20, horizontalFragments: 3, horizontalSpacingLength: 20)
//                paragraph4.borderLeft = 10
//                paragraph4.borderRight = 10
//                paragraph4.paddingLeft = 10
//                paragraph4.paddingRight = 10
//                paragraph4.margin = 20

                Cell cell1 = new Cell()
                cell1.border = 3
                cell1.borderColor = Color.red
                cell1 << paragraph3
                cell1 << paragraph3
                cell1 << paragraph3

                Cell cell2 = new Cell()
                cell2.border = 3
                cell2.borderColor = Color.red
                cell2.backgroundColor = Color.pink
                cell2 << paragraph3

                Cell cell3 = new Cell()
                cell3.border = 3
                cell3.borderColor = Color.red
                cell3 << paragraph3

                Cell cell4 = new Cell()
                cell4.border = 3
                cell4.borderColor = Color.red
                cell4 << paragraph3

                Table table = new Table([3, 2, 1.5])
                table.border = 5
                table.borderColor = Color.orange
                table.backgroundColor = Color.cyan
                table.verticalAlignment = VerticalAlignment.MIDDLE
                table << cell1
                table << cell2
                table << cell3
                table << cell4
                table << cell3
                table << cell3

                Canvas xCanvas = new Canvas(height: 20, border: 3, borderColor: Color.darkGray, backgroundColor: Color.orange, content: {
                    Bounds bounds, DWriter writer ->
                        writer.drawLine(bounds.leftTop(), bounds.rightBottom())
                        writer.drawLine(bounds.leftBottom(), bounds.rightTop())
                })

                Page page = new Page()
                page.border = 5
                page.borderLeftColor = Color.darkGray
                page.borderRightColor = Color.lightGray
                page.padding = ONE_INCH
                page.addContent(paragraph1)
                page.addContent(paragraph4)
                page.addContent(new HorizontalRule(thickness: 10, color: Color.red, border: 2, borderColor: Color.blue, marginLeft: 124, marginRight: 124, padding: 3, backgroundColor: Color.green))
//                page.addContent(new DHorizontalRule(thickness: 1, color: Color.BLACK, marginTop: 10, marginBottom: 0, marginLeft: 144, marginRight: 144))
//                page.addContent(new DHorizontalRule(thickness: 10, color: Color.BLUE, marginTop: 0, marginBottom: 0, marginLeft: 144, marginRight: 144))
//                page.addContent(new DHorizontalRule(thickness: 1, color: Color.BLACK, marginTop: 0, marginBottom: 0, marginLeft: 124, marginRight: 124))
//                page.addContent(new DHorizontalRule(thickness: 10, color: Color.RED, marginTop: 0, marginBottom: 0, marginLeft: 124, marginRight: 124))
//                page.addContent(new DHorizontalRule(thickness: 1, color: Color.BLACK, marginTop: 0, marginBottom: 0, marginLeft: 124, marginRight: 124))
//                page.addContent(new DHorizontalRule(thickness: 10, color: Color.BLUE, marginTop: 0, marginBottom: 0, marginLeft: 144, marginRight: 144))
//                page.addContent(new DHorizontalRule(thickness: 1, color: Color.BLACK, marginTop: 0, marginBottom: 10, marginLeft: 144, marginRight: 144))
                page.addContent(paragraph3)
                page.addContent(xCanvas)
                page.addContent(paragraph3)
                page.addContent(table)
                page.addContent(paragraph3)
                page << confirmationCodeTable

                document.addPage(page)
                document.addPage(createPageTwo())

                new File('target/DDocumentPdf.pdf').bytes = new PdfCreator().createFor(document)
            }
        }
    }

    protected Page createPageTwo() {

        def data = [
            [first: 'pinky', last: 'jones', ssn: '111'],
            [first: 'dinky', last: 'jones', ssn: '222'],
            [first: 'winky', last: 'jones', ssn: '333'],
        ]

        HorizontalRule hr = new HorizontalRule(thickness: 1, color: Color.red, margin: 4, paddingBottom: 2)

        Page page = new Page()

        Paragraph firstNameHeaderParagraph = new Paragraph("First Name")
        firstNameHeaderParagraph.justification = Justification.CENTER

        Cell firstNameHeader = new Cell()
        firstNameHeader << firstNameHeaderParagraph
        firstNameHeader << hr

        Cell lastNameHeader = new Cell()
        lastNameHeader << new Paragraph("Last name")

        Cell ssnHeader = new Cell()
        ssnHeader << new Paragraph("SSN")

        Table personTable = new Table([1, 1, 1])
        personTable.margin = 5
        personTable.border = 1
        personTable << firstNameHeader << lastNameHeader << ssnHeader

        data.each {
            personTable << new Cell(new Paragraph(it.first)) << new Cell(new Paragraph(it.last)) << new Cell(new Paragraph(it.ssn))
        }

        Cell emptyCell = new Cell()

        Cell containerCell = new Cell(personTable)
        containerCell.verticalAlignment = VerticalAlignment.MIDDLE

        Cell loremCell = new Cell(new Paragraph(LOREM_IPSUM))

        Table outerTable = new Table([1, 1])
        outerTable << loremCell << emptyCell << containerCell << loremCell << loremCell << loremCell

        page << outerTable

        page
    }

}