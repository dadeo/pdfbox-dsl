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

import com.github.dadeo.pdfbox.model.Justification
import com.github.dadeo.pdfbox.model.VerticalAlignment
import spock.lang.Specification


class DContextTest extends Specification {

    def "textJustification defaults to LEFT"() {
        expect:
        new DContext().textJustification == Justification.LEFT
    }

    def "setTextJustifcation overrides previous value when not null"() {
        given:
        DContext context = new DContext()

        when:
        context.textJustification = newValue

        then:
        context.textJustification == expectedValue

        where:
        newValue             | expectedValue
        Justification.LEFT   | Justification.LEFT
        Justification.CENTER | Justification.CENTER
        Justification.RIGHT  | Justification.RIGHT
        null                 | Justification.LEFT
    }

    def "verticalAlignment defaults to TOP"() {
        expect:
        new DContext().verticalAlignment == VerticalAlignment.TOP
    }

    def "setVerticalAlignment overrides previous value when not null"() {
        given:
        DContext context = new DContext()

        when:
        context.verticalAlignment = newValue

        then:
        context.verticalAlignment == expectedValue

        where:
        newValue                 | expectedValue
        VerticalAlignment.TOP    | VerticalAlignment.TOP
        VerticalAlignment.MIDDLE | VerticalAlignment.MIDDLE
        VerticalAlignment.BOTTOM | VerticalAlignment.BOTTOM
        null                     | VerticalAlignment.TOP
    }

}