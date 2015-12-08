package com.github.dadeo.pdfbox.creator

import com.github.dadeo.pdfbox.creator.writer.border.BorderDrawer
import com.github.dadeo.pdfbox.creator.writer.hr.BottomBorderHorizontalAdjustmentRule
import com.github.dadeo.pdfbox.creator.writer.hr.HorizontalRuleContentsDrawer
import com.github.dadeo.pdfbox.creator.writer.hr.HorizontalRuleElementDetailsFactory
import com.github.dadeo.pdfbox.creator.writer.hr.HorizontalRuleWritableFactory
import com.github.dadeo.pdfbox.creator.writer.object.ObjectBoundsCalculator
import com.github.dadeo.pdfbox.creator.writer.object.ObjectContentsWidthCalculator
import com.github.dadeo.pdfbox.creator.writer.object.ObjectContextFactory
import com.github.dadeo.pdfbox.creator.writer.object.ObjectWritableFactoryFactory
import com.github.dadeo.pdfbox.creator.writer.page.*
import com.github.dadeo.pdfbox.creator.writer.paragraph.*
import com.github.dadeo.pdfbox.creator.writer.positioning.CurrentLocationAdjuster
import com.github.dadeo.pdfbox.creator.writer.positioning.DescentMultiplier
import com.github.dadeo.pdfbox.creator.writer.table.TableWritableFactory
import com.github.dadeo.pdfbox.creator.writer.text.StringTokenizer
import com.github.dadeo.pdfbox.creator.writer.text.StringWidthCalculator
import com.github.dadeo.pdfbox.creator.writer.text.TokensToLineAssigner
import com.github.dadeo.pdfbox.model.Bordered
import com.github.dadeo.pdfbox.model.DHorizontalRule
import com.github.dadeo.pdfbox.model.LineBorder

class BootStrap {
    static final DescentMultiplier descentMultiplier
    static final StringWidthCalculator stringWidthCalculator
    static final TokensToLineAssigner tokensToLineAssigner
    static final StringTokenizer stringTokenizer
    static final LineBorder lineBorder
    static final BorderDrawer borderDrawer
    static final ObjectBoundsCalculator objectBoundsCalculator
    static final ObjectContentsWidthCalculator objectContentsWidthCalculator
    static final ObjectContextFactory objectContextFactory
    static final ObjectWritableFactoryFactory writerFactoryFactory
    static final TableWritableFactory tableWritableFactory
    static final ParagraphElementDetailsFactory paragraphElementDetailsFactory
    static final BoundedTextBlockWriter boundedTextBlockWriter
    static final TopLeftRightBorderParagraphAdjustmentRule topLeftRightBorderParagraphAdjustmentRule
    static final CurrentLocationAdjuster<Bordered> paragraphCurrentLocationAdjuster
    static final CurrentLocationAdjuster<Bordered> tableCurrentLocationAdjuster
    static final BottomBorderHorizontalAdjustmentRule borderHorizontalAdjustmentRule
    static final CurrentLocationAdjuster<DHorizontalRule> horizontalRuleCurrentLocationAdjuster
    static final BoundedTextBlockLineWriter leftJustifiedTextBlockLineWriter
    static final BoundedTextBlockLineWriter rightJustifiedTextBlockLineWriter
    static final BoundedTextBlockLineWriter centerJustifiedTextBlockLineWriter
    static final TextBlockLineWriterFactory textBlockLineWriterFactory
    static final HorizontalRuleContentsDrawer horizontalRuleContentsDrawer
    static final HorizontalRuleElementDetailsFactory horizontalRuleElementDetailsFactory
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
    static final PageWriter pageWriter

    static {
        descentMultiplier = new DescentMultiplier()
        lineBorder = new LineBorder()
        borderDrawer = new BorderDrawer()
        stringWidthCalculator = new StringWidthCalculator()
        tokensToLineAssigner = new TokensToLineAssigner()
        stringTokenizer = new StringTokenizer(calculator: stringWidthCalculator)
        writerFactoryFactory = new ObjectWritableFactoryFactory()
        paragraphElementDetailsFactory = new ParagraphElementDetailsFactory()
        topLeftRightBorderParagraphAdjustmentRule = new TopLeftRightBorderParagraphAdjustmentRule(descentMultiplier: descentMultiplier)
        borderHorizontalAdjustmentRule = new BottomBorderHorizontalAdjustmentRule(descentMultiplier: descentMultiplier)
        paragraphCurrentLocationAdjuster = new CurrentLocationAdjuster(
            adjustmentRules: [
                topLeftRightBorderParagraphAdjustmentRule,
            ])
        tableCurrentLocationAdjuster = new CurrentLocationAdjuster(
            adjustmentRules: [
                topLeftRightBorderParagraphAdjustmentRule,
            ])
        horizontalRuleCurrentLocationAdjuster = new CurrentLocationAdjuster(
            adjustmentRules: [
                borderHorizontalAdjustmentRule
            ])

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
                                                                currentLocationAdjuster: paragraphCurrentLocationAdjuster,
                                                                objectBoundsCalculator: objectBoundsCalculator,
                                                                elementDetailsFactory: paragraphElementDetailsFactory)

        pageBoundsCalculations = new PageBoundsCalculations()

        pageCurrentLocationAdjuster = new PageContentsCurrentLocationAdjuster()
        pageContextFactory = new PageContextFactory()
        pagePdfBoxHelper = new PagePdfBoxHelper()
        pageBoundsCalculator = new PageBoundsCalculator(pageBoundsCalculations: pageBoundsCalculations)
        pageContentsWriter = new PageContentsWriter(currentLocationAdjuster: pageCurrentLocationAdjuster,
                                                    writableFactoryFactory: writerFactoryFactory)

        horizontalRuleElementDetailsFactory = new HorizontalRuleElementDetailsFactory()
        horizontalRuleWritableFactory = new HorizontalRuleWritableFactory(contextFactory: objectContextFactory,
                                                                          currentLocationAdjuster: horizontalRuleCurrentLocationAdjuster,
                                                                          objectBoundsCalculator: objectBoundsCalculator,
                                                                          elementDetailsFactory: horizontalRuleElementDetailsFactory)

        tableWritableFactory = new TableWritableFactory(objectContextFactory: objectContextFactory,
                                                        objectBoundsCalculator: objectBoundsCalculator,
                                                        writerFactoryFactory: writerFactoryFactory,
                                                        currentLocationAdjuster: tableCurrentLocationAdjuster,
                                                        descentMultiplier: descentMultiplier)

        pageWriter = new PageWriter(pageContextFactory: pageContextFactory,
                                    pagePdfBoxHelper: pagePdfBoxHelper,
                                    pageBoundsCalculator: pageBoundsCalculator,
                                    pageContentsWriter: pageContentsWriter,
                                    pageBorderDrawer: borderDrawer)

        writerFactoryFactory.paragraphWritableFactory = paragraphWritableFactory
        writerFactoryFactory.tableWritableFactory = tableWritableFactory
        writerFactoryFactory.horizontalRuleWritableFactory = horizontalRuleWritableFactory
    }


    private
    static BoundedTextBlockLineWriter createJustifiedTextBlockWriter(TextBlockCurrentLocationPositioner currentLocationRepositioner) {
        BoundedTextBlockTokenWriter tokenWriter = new BoundedTextBlockTokenWriter(currentLocationPositioner: currentLocationRepositioner)
        new BoundedTextBlockLineWriter(currentLocationPositioner: currentLocationRepositioner, tokenWriter: tokenWriter)
    }

}