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
class DParagraph implements DObject, Margined, Bordered, Padded {
    List<DPart> contents = []
    DFont font
    Justification justification
    float paragraphBottomDescentMultiplier = 2

    DParagraph() {
    }

    DParagraph(String text, PDFont font, float fontSize) {
        this(text, new DFont(font, fontSize))
    }

    DParagraph(String text, DFont font) {
        this.font = font
        contents << new DPart(text: text, font: font)
    }

    DParagraph(String text) {
        addContent text
    }

    DParagraph addContent(DPart part) {
        contents << part
        this
    }

    DParagraph addContent(String text) {
        addContent new DPart(text: text)
    }

    DParagraph leftShift(String text) {
        addContent(text)
    }

    DParagraph leftShift(DPart part) {
        addContent(part)
    }
}