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
package com.github.dadeo.pdfbox.creator.writer

import com.github.dadeo.pdfbox.model.Bounds
import com.github.dadeo.pdfbox.model.Font
import com.github.dadeo.pdfbox.model.Justification
import com.github.dadeo.pdfbox.model.VerticalAlignment
import groovy.transform.AutoClone
import groovy.transform.Canonical
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.PDPageContentStream

import java.awt.Color

@Canonical
@AutoClone
class DContext implements Cloneable {
    DContext parent
    PDDocument pdDocument
    PDPage pdPage
    PDPageContentStream pdContentStream
    DWriter writer
    Bounds containingBounds
    Bounds contentsBounds
    Bounds borderBounds
    Color backgroundColor
    Font font
    Justification textJustification = Justification.LEFT
    VerticalAlignment verticalAlignment = VerticalAlignment.TOP

    void setTextJustification(Justification value) {
        if (value)
            this.textJustification = value
    }

    void setVerticalAlignment(VerticalAlignment value) {
        if (value)
            this.verticalAlignment = value
    }
}