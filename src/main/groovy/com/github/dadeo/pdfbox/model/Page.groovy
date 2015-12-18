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
class Page implements Margined, Bordered, Padded {
    static final float ONE_INCH = 72f

    List<PdfComponent> contents = []
    Font font
    Bounds pageBounds = new Bounds((float) (11 * ONE_INCH), ((float) (8.5 * ONE_INCH)), 0, 0)

    Page addContent(PdfComponent part) {
        contents << part
        this
    }

    Page leftShift(PdfComponent part) {
        addContent(part)
    }
}