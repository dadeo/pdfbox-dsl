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


@Canonical
class Cell implements PdfComponent, Margined, Bordered, Padded {
    List<PdfComponent> contents = []
    VerticalAlignment verticalAlignment

    Cell() {}

    Cell(PdfComponent object) {
        addContent(object)
    }

    Cell addContent(PdfComponent object) {
        contents << object
        this
    }

    Cell leftShift(PdfComponent object) {
        addContent(object)
    }
}