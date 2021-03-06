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


trait Margined {

    float marginTop = 0
    float marginRight = 0
    float marginBottom = 0
    float marginLeft = 0

    void setMargin(float value) {
        marginTop = value
        marginRight = value
        marginBottom = value
        marginLeft = value
        this
    }

    Bounds getMarginOffsets() {
        new Bounds(-marginTop, -marginRight, marginBottom, marginLeft)
    }
}