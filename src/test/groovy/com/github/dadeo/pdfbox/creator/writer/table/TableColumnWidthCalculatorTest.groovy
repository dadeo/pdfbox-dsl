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
package com.github.dadeo.pdfbox.creator.writer.table

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.model.Bounds
import com.github.dadeo.pdfbox.model.Table
import spock.lang.Specification
import spock.lang.Unroll


class TableColumnWidthCalculatorTest extends Specification {
    private static final float LEFT = 100
    private TableColumnWidthCalculator calculator = new TableColumnWidthCalculator()
    private DContext context = new DContext()

    @Unroll
    def "calculateFor"() {
        given:
        context.contentsBounds = new Bounds(0, (float) (LEFT + contentsBoundsWidth), 0, LEFT)

        expect:

        calculator.calculateFor(new Table(columnRatios), context)

        where:

        contentsBoundsWidth | columnRatios | expectedWidths
        100                 | [1]          | [100]
        100                 | [1, 1]       | [50, 50]
        100                 | [3, 1]       | [75, 25]
        100                 | [1, 3]       | [25, 75]
        120                 | [1, 1, 1]    | [40, 40, 40]
        120                 | [3, 1, 2]    | [60, 20, 40]
        200                 | [1, 1, 1, 1] | [50, 50, 50, 50]
        200                 | [1, 3, 3, 1] | [25, 75, 75, 25]
    }

}