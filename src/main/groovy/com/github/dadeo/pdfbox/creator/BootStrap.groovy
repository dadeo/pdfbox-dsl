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
package com.github.dadeo.pdfbox.creator

import com.github.dadeo.pdfbox.creator.writer.border.BorderDrawer
import com.github.dadeo.pdfbox.creator.writer.canvas.CanvasWritableFactory
import com.github.dadeo.pdfbox.creator.writer.hr.HorizontalRuleContentsDrawer
import com.github.dadeo.pdfbox.creator.writer.hr.HorizontalRuleWritableFactory
import com.github.dadeo.pdfbox.creator.writer.object.*
import com.github.dadeo.pdfbox.creator.writer.page.*
import com.github.dadeo.pdfbox.creator.writer.paragraph.*
import com.github.dadeo.pdfbox.creator.writer.positioning.DescentMultiplier
import com.github.dadeo.pdfbox.creator.writer.table.TableColumnWidthCalculator
import com.github.dadeo.pdfbox.creator.writer.table.TableWritableFactory
import com.github.dadeo.pdfbox.creator.writer.text.StringTokenizer
import com.github.dadeo.pdfbox.creator.writer.text.StringWidthCalculator
import com.github.dadeo.pdfbox.creator.writer.text.TokensToLineAssigner
import com.github.dadeo.pdfbox.creator.writer.util.VerticalAlignmentCalculator
import com.github.dadeo.pdfbox.model.LineBorder

class BootStrap {
    static final DescentMultiplier descentMultiplier
    static final StringWidthCalculator stringWidthCalculator
    static final TokensToLineAssigner tokensToLineAssigner
    static final StringTokenizer stringTokenizer
    static final VerticalAlignmentCalculator verticalAlignmentCalculator
    static final LineBorder lineBorder
    static final BackgroundPainter backgroundPainter
    static final BorderDrawer borderDrawer
    static final ObjectBoundsCalculator objectBoundsCalculator
    static final ObjectContentsWidthCalculator objectContentsWidthCalculator
    static final ObjectContextFactory objectContextFactory
    static final ObjectWritableFactoryFactory writerFactoryFactory
    static final TableWritableFactory tableWritableFactory
    static final BoundedTextBlockWriter boundedTextBlockWriter
    static final BoundedTextBlockLineWriter leftJustifiedTextBlockLineWriter
    static final BoundedTextBlockLineWriter rightJustifiedTextBlockLineWriter
    static final BoundedTextBlockLineWriter centerJustifiedTextBlockLineWriter
    static final TextBlockLineWriterFactory textBlockLineWriterFactory
    static final HorizontalRuleContentsDrawer horizontalRuleContentsDrawer
    static final PageContentsCurrentLocationAdjuster pageCurrentLocationAdjuster
    static final PageContextFactory pageContextFactory
    static final PagePdfBoxHelper pagePdfBoxHelper
    static final PageBoundsCalculations pageBoundsCalculations
    static final PageBoundsCalculator pageBoundsCalculator
    static final PageContentsWriter pageContentsWriter
    static final ParagraphContentsDimensionsCalculator paragraphContentsDimensionsCalculator
    static final ParagraphContextFactory paragraphContextFactory
    static final BoundedTextBlockFactory boundedTextBlockFactory
    static final ParagraphWritableFactory paragraphWritableFactory
    static final HorizontalRuleWritableFactory horizontalRuleWritableFactory
    static final CanvasWritableFactory canvasWritableFactory
    static final PageWriter pageWriter

    static {
        descentMultiplier = new DescentMultiplier()
        lineBorder = new LineBorder()
        backgroundPainter = new BackgroundPainter()
        borderDrawer = new BorderDrawer()
        stringWidthCalculator = new StringWidthCalculator()
        tokensToLineAssigner = new TokensToLineAssigner()
        stringTokenizer = new StringTokenizer(calculator: stringWidthCalculator)
        verticalAlignmentCalculator = new VerticalAlignmentCalculator()
        writerFactoryFactory = new ObjectWritableFactoryFactory()
        horizontalRuleContentsDrawer = new HorizontalRuleContentsDrawer()

        leftJustifiedTextBlockLineWriter = createJustifiedTextBlockWriter(new LeftJustifiedTextBlockCurrentLocationPositioner())
        rightJustifiedTextBlockLineWriter = createJustifiedTextBlockWriter(new RightJustifiedTextBlockCurrentLocationPositioner())
        centerJustifiedTextBlockLineWriter = createJustifiedTextBlockWriter(new CenterJustifiedTextBlockCurrentLocationPositioner())

        textBlockLineWriterFactory = new TextBlockLineWriterFactory(leftJustifiedTextBlockLineWriter: leftJustifiedTextBlockLineWriter,
                                                                    rightJustifiedTextBlockLineWriter: rightJustifiedTextBlockLineWriter,
                                                                    centerJustifiedTextBlockLineWriter: centerJustifiedTextBlockLineWriter)

        boundedTextBlockWriter = new BoundedTextBlockWriter(textBlockLineWriterFactory: textBlockLineWriterFactory)

        objectBoundsCalculator = new ObjectBoundsCalculator()
        objectContextFactory = new ObjectContextFactory(objectBoundsCalculator: objectBoundsCalculator)
        objectContentsWidthCalculator = new ObjectContentsWidthCalculator()

        paragraphContentsDimensionsCalculator = new ParagraphContentsDimensionsCalculator(objectContentsWidthCalculator: objectContentsWidthCalculator,
                                                                                          descentMultiplier: descentMultiplier)

        paragraphContextFactory = new ParagraphContextFactory(objectBoundsCalculator: objectBoundsCalculator)
        boundedTextBlockFactory = new BoundedTextBlockFactory()

        paragraphWritableFactory = new ParagraphWritableFactory(contentsDimensionsCalculator: paragraphContentsDimensionsCalculator,
                                                                paragraphContextFactory: paragraphContextFactory,
                                                                boundedTextBlockFactory: boundedTextBlockFactory,
                                                                objectBoundsCalculator: objectBoundsCalculator)

        pageBoundsCalculations = new PageBoundsCalculations()

        pageCurrentLocationAdjuster = new PageContentsCurrentLocationAdjuster()
        pageContextFactory = new PageContextFactory()
        pagePdfBoxHelper = new PagePdfBoxHelper()
        pageBoundsCalculator = new PageBoundsCalculator(pageBoundsCalculations: pageBoundsCalculations)
        pageContentsWriter = new PageContentsWriter(currentLocationAdjuster: pageCurrentLocationAdjuster,
                                                    writableFactoryFactory: writerFactoryFactory)

        horizontalRuleWritableFactory = new HorizontalRuleWritableFactory(contextFactory: objectContextFactory,
                                                                          objectBoundsCalculator: objectBoundsCalculator)

        TableColumnWidthCalculator tableColumnWidthCalculator = new TableColumnWidthCalculator()

        tableWritableFactory = new TableWritableFactory(objectContextFactory: objectContextFactory,
                                                        objectBoundsCalculator: objectBoundsCalculator,
                                                        columnWidthCalculator: tableColumnWidthCalculator,
                                                        writerFactoryFactory: writerFactoryFactory,
                                                        verticalAlignmentCalculator: verticalAlignmentCalculator,
                                                        descentMultiplier: descentMultiplier)

        pageWriter = new PageWriter(pageContextFactory: pageContextFactory,
                                    pagePdfBoxHelper: pagePdfBoxHelper,
                                    pageBoundsCalculator: pageBoundsCalculator,
                                    pageContentsWriter: pageContentsWriter,
                                    pageBorderDrawer: borderDrawer)

        canvasWritableFactory = new CanvasWritableFactory(objectContextFactory: objectContextFactory,
                                                          objectBoundsCalculator: objectBoundsCalculator)

        writerFactoryFactory.paragraphWritableFactory = paragraphWritableFactory
        writerFactoryFactory.tableWritableFactory = tableWritableFactory
        writerFactoryFactory.horizontalRuleWritableFactory = horizontalRuleWritableFactory
        writerFactoryFactory.canvasWritableFactory = canvasWritableFactory
    }


    private
    static BoundedTextBlockLineWriter createJustifiedTextBlockWriter(TextBlockCurrentLocationPositioner currentLocationRepositioner) {
        BoundedTextBlockTokenWriter tokenWriter = new BoundedTextBlockTokenWriter(currentLocationPositioner: currentLocationRepositioner)
        new BoundedTextBlockLineWriter(currentLocationPositioner: currentLocationRepositioner, tokenWriter: tokenWriter)
    }

}