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


class Table implements DObject, Margined, Bordered, Padded {
    List<Float> columnRatios
    List<Cell> contents = []
    VerticalAlignment verticalAlignment

    Table() {
    }

    Table(List<Float> columnRatios) {
        this.columnRatios = columnRatios
    }

    Table addCell(Cell cell) {
        contents << cell
        this
    }

    Table leftShift(Cell cell) {
        addCell(cell)
    }
}