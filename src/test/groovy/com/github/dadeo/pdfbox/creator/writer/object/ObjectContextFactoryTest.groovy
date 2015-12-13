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

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.model.DBounds
import com.github.dadeo.pdfbox.model.DObject
import spock.lang.Specification

class ObjectContextFactoryTest extends Specification {
    private static final DBounds PARENT_CONTENTS_BOUNDS = new DBounds(1, 2, 3, 4)
    private ObjectBoundsCalculator objectBoundsCalculator = Mock(ObjectBoundsCalculator)
    private ObjectContextFactory factory = new ObjectContextFactory(objectBoundsCalculator: objectBoundsCalculator)
    private DContext clonedContext = new DContext()
    private DContext parentContext = Mock(DContext) {
        getContentsBounds() >> PARENT_CONTENTS_BOUNDS
        1 * clone() >> clonedContext
    }

    def "page context is cloned and returned"() {
        given:
        DObject object = new DObject() {}

        expect:
        factory.createContextFrom(parentContext, object).is clonedContext
    }

    def "new context contains parent context as parent"() {
        given:
        DObject object = new DObject() {}

        expect:
        factory.createContextFrom(parentContext, object).parent.is parentContext
    }

    def "new context's containingBounds, borderBounds, and contentsBounds are initialized to parent context's contentsBounds"() {
        given:
        DObject object = new DObject() {}

        when:
        DContext childContext = factory.createContextFrom(parentContext, object)

        then:
        childContext.containingBounds == PARENT_CONTENTS_BOUNDS
        1 * objectBoundsCalculator.calculateMaxBounds(object, clonedContext)
    }

}