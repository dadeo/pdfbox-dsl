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
package com.github.dadeo.pdfbox.model

import groovy.transform.Canonical
import org.apache.pdfbox.pdmodel.font.PDFont

@Canonical
class Paragraph implements PdfComponent, Margined, Bordered, Padded {
    List<Part> contents = []
    Font font
    Justification justification
    Float firstLineLeading = null
    float lineLeading = 0
    float paragraphBottomDescentMultiplier = 2

    Paragraph() {
    }

    Paragraph(String text, PDFont font, float fontSize) {
        this(text, new Font(font, fontSize))
    }

    Paragraph(String text, Font font) {
        this.font = font
        contents << new Part(text: text, font: font)
    }

    Paragraph(String text) {
        addContent text
    }

    Paragraph addContent(Part part) {
        contents << part
        this
    }

    Paragraph addContent(String text) {
        addContent new Part(text: text)
    }

    Paragraph leftShift(String text) {
        addContent(text)
    }

    Paragraph leftShift(Part part) {
        addContent(part)
    }
}