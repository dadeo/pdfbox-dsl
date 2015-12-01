package com.github.dadeo.pdfbox.creator

import com.github.dadeo.pdfbox.creator.writer.border.BorderDrawer
import com.github.dadeo.pdfbox.creator.writer.hr.BottomBorderHorizontalRuleAdjustmentRule
import com.github.dadeo.pdfbox.creator.writer.hr.HorizontalRuleContentsDrawer
import com.github.dadeo.pdfbox.creator.writer.paragraph.*
import com.github.dadeo.pdfbox.creator.writer.positioning.CurrentLocationAdjuster
import com.github.dadeo.pdfbox.creator.writer.text.StringTokenizer
import com.github.dadeo.pdfbox.creator.writer.text.StringWidthCalculator
import com.github.dadeo.pdfbox.creator.writer.text.TokensToLineAssigner
import com.github.dadeo.pdfbox.model.Bordered
import com.github.dadeo.pdfbox.model.DHorizontalRule
import com.github.dadeo.pdfbox.model.LineBorder

class BootStrap {
    static final StringWidthCalculator stringWidthCalculator
    static final TokensToLineAssigner tokensToLineAssigner
    static final StringTokenizer stringTokenizer
    static final LineBorder lineBorder

    static {
        lineBorder = new LineBorder()
        stringWidthCalculator = new StringWidthCalculator()
        tokensToLineAssigner = new TokensToLineAssigner()
        stringTokenizer = new StringTokenizer(calculator: stringWidthCalculator)
    }

    static final BorderDrawer borderDrawer = new BorderDrawer()

    static final ParagraphElementDetailsFactory paragraphElementDetailsFactory = new ParagraphElementDetailsFactory()

    static final BoundedTextBlockWriter boundedTextBlockWriter = new BoundedTextBlockWriter()

    static final CurrentLocationAdjuster<Bordered> paragraphCurrentLocationAdjuster = new CurrentLocationAdjuster(
        adjustmentRules: [
            new TopLeftRightBorderParagraphAdjustmentRule(),
            new AdjacentBordersParagraphAdjustmentRule()
        ])

    static
    final CurrentLocationAdjuster<DHorizontalRule> horizontalRuleCurrentLocationAdjuster = new CurrentLocationAdjuster(
        adjustmentRules: [
            new BottomBorderHorizontalRuleAdjustmentRule(),
        ])

    static
    final BoundedTextBlockLineWriter leftJustifiedTextBlockLineWriter = createJustifiedTextBlockWriter(new LeftJustifiedTextBlockCurrentLocationPositioner())
    static
    final BoundedTextBlockLineWriter rightJustifiedTextBlockLineWriter = createJustifiedTextBlockWriter(new RightJustifiedTextBlockCurrentLocationPositioner())
    static
    final BoundedTextBlockLineWriter centerJustifiedTextBlockLineWriter = createJustifiedTextBlockWriter(new CenterJustifiedTextBlockCurrentLocationPositioner())

    static final HorizontalRuleContentsDrawer horizontalRuleContentsDrawer = new HorizontalRuleContentsDrawer()

    private
    static BoundedTextBlockLineWriter createJustifiedTextBlockWriter(TextBlockCurrentLocationPositioner currentLocationRepositioner) {
        BoundedTextBlockTokenWriter tokenWriter = new BoundedTextBlockTokenWriter(currentLocationPositioner: currentLocationRepositioner)
        new BoundedTextBlockLineWriter(currentLocationPositioner: currentLocationRepositioner, tokenWriter: tokenWriter)
    }

}