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

import java.awt.*

class ObjectContextFactoryTest extends Specification {
    private static final DBounds PARENT_CONTENTS_BOUNDS = new DBounds(1, 2, 3, 4)
    private static final DBounds CHILD_CONTENTS_BOUNDS = new DBounds(4, 3, 2, 1)
    private ObjectBoundsCalculator objectBoundsCalculator = Mock(ObjectBoundsCalculator)
    private ObjectContextFactory factory = new ObjectContextFactory(objectBoundsCalculator: objectBoundsCalculator)
    private DContext clonedContext = new DContext()
    private DContext parentContext

    def "parent context is cloned and returned"() {
        given:
        parentContext = Mock(DContext) {
            getContentsBounds() >> PARENT_CONTENTS_BOUNDS
            1 * clone() >> clonedContext
        }
        DObject object = new DObject() {}

        expect:
        factory.createContextFrom(parentContext, object).is clonedContext
    }

    def "new context contains parent context as parent"() {
        given:
        parentContext = Mock(DContext) {
            getContentsBounds() >> PARENT_CONTENTS_BOUNDS
            1 * clone() >> clonedContext
        }
        DObject object = new DObject() {}

        expect:
        factory.createContextFrom(parentContext, object).parent.is parentContext
    }

    def "new context's containingBounds is parent's contentsBounds when positioning is relative"() {
        given:
        parentContext = Mock(DContext) {
            getContentsBounds() >> PARENT_CONTENTS_BOUNDS
            1 * clone() >> clonedContext
        }
        DObject object = Mock(DObject) {
            getPositionType() >> PositionType.RELATIVE
            getPosition() >> CHILD_CONTENTS_BOUNDS
        }

        when:
        DContext childContext = factory.createContextFrom(parentContext, object)

        then:
        childContext.containingBounds == PARENT_CONTENTS_BOUNDS
    }

    def "new context's containingBounds is specified by child when positioning is absolute"() {
        given:
        parentContext = Mock(DContext) {
            getContentsBounds() >> PARENT_CONTENTS_BOUNDS
            1 * clone() >> clonedContext
        }
        DObject object = Mock(DObject) {
            getPositionType() >> PositionType.ABSOlUTE
            getPosition() >> CHILD_CONTENTS_BOUNDS
        }

        when:
        DContext childContext = factory.createContextFrom(parentContext, object)

        then:
        childContext.containingBounds == CHILD_CONTENTS_BOUNDS
    }

    def "passes the object and its context to the bounds calculator"() {
        given:
        parentContext = Mock(DContext) {
            getContentsBounds() >> PARENT_CONTENTS_BOUNDS
            1 * clone() >> clonedContext
        }
        DObject object = new DObject() {}

        when:
        factory.createContextFrom(parentContext, object)

        then:
        1 * objectBoundsCalculator.calculateMaxBounds(object, clonedContext)
    }

    def "child context retains parent context's backgroundColor when child does not have a backgroundColor"() {
        given:
        parentContext = new DContext()
        parentContext.backgroundColor = Color.red
        DObject object = new DObject() {}

        when:
        DContext childContext = factory.createContextFrom(parentContext, object)

        then:
        childContext.backgroundColor == Color.red
    }

    def "child context retains parent context's backgroundColor when child does not specify a backgroundColor"() {
        given:
        parentContext = new DContext()
        parentContext.backgroundColor = Color.red
        DObject object = new DObject() {}

        when:
        DContext childContext = factory.createContextFrom(parentContext, object)

        then:
        childContext.backgroundColor == Color.red
    }

    def "child's backgroundColor overrides value in parent context"() {
        given:
        parentContext = new DContext()
        parentContext.backgroundColor = Color.red
        DObject object = new DObject() {}
        object.backgroundColor = Color.blue

        when:
        DContext childContext = factory.createContextFrom(parentContext, object)

        then:
        childContext.backgroundColor == Color.blue
    }

    def "cloned context retains parent context's backgroundColor when no child"() {
        given:
        parentContext = new DContext()
        parentContext.backgroundColor = Color.red

        when:
        DContext childContext = factory.createContextFrom(parentContext, null)

        then:
        childContext.backgroundColor == Color.red
    }

}