package com.github.dadeo.pdfbox

import com.github.dadeo.pdfbox.creator.PdfCreator
import com.github.dadeo.pdfbox.model.DDocument
import com.github.dadeo.pdfbox.model.DFont
import com.github.dadeo.pdfbox.model.DPage
import com.github.dadeo.pdfbox.model.DParagraph
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.font.PDFont
import org.apache.pdfbox.pdmodel.font.PDType1Font
import org.junit.Test

class VisualPlaygroundTest {
    static final int ONE_INCH = 72

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

        String loremIpsum = """|Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod
                               |tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,
                               |quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo
                               |consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse
                               |cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non
                               |proident, sunt in culpa qui officia deserunt mollit anim id est laborum."""
            .stripMargin()
            .replaceAll(/\r/, '')
            .replaceAll(/\n/, '')

        DDocument document = new DDocument()

        DParagraph paragraph3 = new DParagraph(loremIpsum)
        paragraph3.font = new DFont(PDType1Font.HELVETICA_BOLD, 12)
        paragraph3.border = 5

        DParagraph paragraph4 = new DParagraph(loremIpsum)
        paragraph4.font = new DFont(PDType1Font.HELVETICA_BOLD, 16)
        paragraph4.border = 5

        DPage page = new DPage()
        page.addContent(paragraph3)
        page.addContent(paragraph4)
        page.addContent(paragraph3)

        document.addPage(page)

        new File('target/DDocumentPdf.pdf').bytes = new PdfCreator().createFor(document)
    }

}