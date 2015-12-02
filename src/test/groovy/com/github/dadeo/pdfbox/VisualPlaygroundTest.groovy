package com.github.dadeo.pdfbox

import com.github.dadeo.pdfbox.creator.PdfCreator
import com.github.dadeo.pdfbox.model.*
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.font.PDFont
import org.apache.pdfbox.pdmodel.font.PDType1Font
import org.junit.Test

import java.awt.*

class VisualPlaygroundTest {
    static final int ONE_INCH = 72

    private static final String LOREM_IPSUM = """|Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod
                           |tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,
                           |quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo
                           |consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse
                           |cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non
                           |proident, sunt in culpa qui officia deserunt mollit anim id est laborum g."""
        .stripMargin()
        .replaceAll(/\r/, '')
        .replaceAll(/\n/, '')

    @Test
    void test_use_pdfbox_api_directly() {
        PDDocument document = new PDDocument()
        PDPage page = new PDPage()

        PDFont font = PDType1Font.HELVETICA_BOLD

        PDPageContentStream contents = new PDPageContentStream(document, page)

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
    void test_build_DDocument() {

        Closure timer = { String description, Closure timed ->
            long startTime = System.currentTimeMillis()
            timed()
            println("duration($description): ${System.currentTimeMillis() - startTime}")
        }

        PDType1Font.HELVETICA_BOLD

        1.times {

            timer("create pdf") {
                DDocument document = new DDocument()

                DParagraph paragraph1 = new DParagraph(LOREM_IPSUM)
                paragraph1.font = new DFont(PDType1Font.HELVETICA_OBLIQUE, 6)
                paragraph1.border = 1
                paragraph1.padding = 0
                paragraph1.justification = Justification.LEFT
                DParagraph paragraph3 = new DParagraph(LOREM_IPSUM)
                paragraph3.font = new DFont(PDType1Font.HELVETICA_OBLIQUE, 6)
                paragraph3.border = 1
                paragraph3.padding = 4
                paragraph3.justification = Justification.RIGHT
                DParagraph paragraph4 = new DParagraph(LOREM_IPSUM)
                paragraph4.font = new DFont(PDType1Font.HELVETICA_BOLD, 18, Color.BLUE)
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
                cell2 << paragraph3

                Cell cell3 = new Cell()
                cell3.border = 3
                cell1.borderColor = Color.red
                cell3 << paragraph3

                Table table = new Table([3, 2, 1.5])
                table.border = 5
                table.borderColor = Color.orange
                table << cell1
                table << cell2
                table << cell3
                table << cell3
                table << cell3
                table << cell3

                DPage page = new DPage()
                page.border = 5
                page.borderLeftColor = Color.darkGray
                page.borderRightColor = Color.lightGray
                page.padding = ONE_INCH
                page.addContent(paragraph1)
                page.addContent(paragraph4)
                page.addContent(new DHorizontalRule(thickness: 10, color: Color.red, border: 2, borderColor: Color.blue, marginLeft: 124, marginRight: 124))
//                page.addContent(new DHorizontalRule(thickness: 1, color: Color.BLACK, marginTop: 10, marginBottom: 0, marginLeft: 144, marginRight: 144))
//                page.addContent(new DHorizontalRule(thickness: 10, color: Color.BLUE, marginTop: 0, marginBottom: 0, marginLeft: 144, marginRight: 144))
//                page.addContent(new DHorizontalRule(thickness: 1, color: Color.BLACK, marginTop: 0, marginBottom: 0, marginLeft: 124, marginRight: 124))
//                page.addContent(new DHorizontalRule(thickness: 10, color: Color.RED, marginTop: 0, marginBottom: 0, marginLeft: 124, marginRight: 124))
//                page.addContent(new DHorizontalRule(thickness: 1, color: Color.BLACK, marginTop: 0, marginBottom: 0, marginLeft: 124, marginRight: 124))
//                page.addContent(new DHorizontalRule(thickness: 10, color: Color.BLUE, marginTop: 0, marginBottom: 0, marginLeft: 144, marginRight: 144))
//                page.addContent(new DHorizontalRule(thickness: 1, color: Color.BLACK, marginTop: 0, marginBottom: 10, marginLeft: 144, marginRight: 144))
                page.addContent(paragraph3)
                page.addContent(table)
                page.addContent(paragraph3)

                document.addPage(page)
                document.addPage(createPageTwo())

                new File('target/DDocumentPdf.pdf').bytes = new PdfCreator().createFor(document)
            }
        }
    }

    protected DPage createPageTwo() {

        def data = [
            [first: 'pinky', last: 'jones', ssn: '111'],
            [first: 'dinky', last: 'jones', ssn: '222'],
            [first: 'winky', last: 'jones', ssn: '333'],
        ]

        DHorizontalRule hr = new DHorizontalRule(thickness: 1, color: Color.red, margin: 4, paddingBottom: 2)

        DPage page = new DPage()

        DParagraph firstNameHeaderParagraph = new DParagraph("First Name")
        firstNameHeaderParagraph.justification = Justification.CENTER

        Cell firstNameHeader = new Cell()
        firstNameHeader << firstNameHeaderParagraph
        firstNameHeader << hr

        Cell lastNameHeader = new Cell()
        lastNameHeader << new DParagraph("Last name")

        Cell ssnHeader = new Cell()
        ssnHeader << new DParagraph("SSN")

        Table personTable = new Table([1, 1, 1])
        personTable.margin = 5
        personTable.border = 1
        personTable << firstNameHeader << lastNameHeader << ssnHeader

        data.each {
            personTable << new Cell(new DParagraph(it.first)) << new Cell(new DParagraph(it.last)) << new Cell(new DParagraph(it.ssn))
        }

        Cell emptyCell = new Cell()

        Cell containerCell = new Cell(personTable)

        Cell loremCell = new Cell(new DParagraph(LOREM_IPSUM))

        Table outerTable = new Table([1, 1])
        outerTable << loremCell << emptyCell << containerCell << loremCell << loremCell << loremCell

        page << outerTable

        page
    }

}