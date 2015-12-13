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
package com.github.dadeo.pdfbox.creator.writer.object

import com.github.dadeo.pdfbox.model.*
import spock.lang.Specification

class ObjectContentsWidthCalculatorTest extends Specification {
    private ObjectContentsWidthCalculator calculator = new ObjectContentsWidthCalculator()

    def "calculate when no margins, border, and spacing"() {
        expect:
        calculator.calculateFor(new DParagraph(), new DBounds(0, 600, 0, 100)) == 500f
    }

    def "calculate when only margin"() {
        expect:
        calculator.calculateFor(new DParagraph(marginLeft: 10, marginRight: 40), new DBounds(0, 600, 0, 100)) == 450f
    }

    def "calculate when only border"() {
        expect:
        calculator.calculateFor(new DParagraph(borderLeft: 10, borderRight: 40), new DBounds(0, 600, 0, 100)) == 450f
    }

    def "calculate when only padding"() {
        expect:
        calculator.calculateFor(new DParagraph(paddingLeft: 10, paddingRight: 40), new DBounds(0, 600, 0, 100)) == 450f
    }

    def "calculate when margin, border, and padding"() {
        given:

        DParagraph paragraph = new DParagraph(marginLeft: 5,
                                              marginRight: 10,
                                              borderLeft: 10,
                                              borderRight: 20,
                                              paddingLeft: 50,
                                              paddingRight: 100)

        expect:

        calculator.calculateFor(paragraph, new DBounds(0, 600, 0, 100)) == 305f
    }


    def "calculate when object is not Bordered or Padded"() {
        given:
        MarginedTestDObject object = new MarginedTestDObject()
        object.margin = 20

        expect:
        calculator.calculateFor(object, new DBounds(0, 600, 0, 100)) == 460f
    }

    def "calculate when object is not Margined or Padded"() {
        given:
        BorderedTestDObject object = new BorderedTestDObject() {}
        object.border = 20

        expect:
        calculator.calculateFor(object, new DBounds(0, 600, 0, 100)) == 460f
    }

    def "calculate when object is not Margined or Bordered"() {
        given:
        PaddedTestDObject object = new PaddedTestDObject() {}
        object.padding = 20

        expect:
        calculator.calculateFor(object, new DBounds(0, 600, 0, 100)) == 460f
    }

    private class MarginedTestDObject implements DObject, Margined {}

    private class BorderedTestDObject implements DObject, Bordered {}

    private class PaddedTestDObject implements DObject, Padded {}

}