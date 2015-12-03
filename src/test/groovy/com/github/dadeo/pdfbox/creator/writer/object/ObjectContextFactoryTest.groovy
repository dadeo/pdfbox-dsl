package com.github.dadeo.pdfbox.creator.writer.object

import com.github.dadeo.pdfbox.creator.writer.DContext
import com.github.dadeo.pdfbox.model.DBounds
import spock.lang.Specification

class ObjectContextFactoryTest extends Specification {
    private static final DBounds PARENT_CONTENTS_BOUNDS = new DBounds(1, 2, 3, 4)
    private ObjectContextFactory factory = new ObjectContextFactory()
    private DContext clonedContext = new DContext()
    private DContext parentContext = Mock(DContext) {
        getContentsBounds() >> PARENT_CONTENTS_BOUNDS
        1 * clone() >> clonedContext
    }

    def "page context is cloned and returned"() {
        expect:

        factory.createContextFrom(parentContext).is clonedContext
    }

    def "new context contains parent context as parent"() {
        expect:

        factory.createContextFrom(parentContext).parent.is parentContext
    }

    def "new context's containingBounds, borderBounds, and contentsBounds are initialized to parent context's contentsBounds"() {
        when:
        DContext childContext = factory.createContextFrom(parentContext)

        then:
        childContext.containingBounds == PARENT_CONTENTS_BOUNDS
        childContext.borderBounds == PARENT_CONTENTS_BOUNDS
        childContext.contentsBounds == PARENT_CONTENTS_BOUNDS
    }

}