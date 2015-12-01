package com.github.dadeo.pdfbox.creator

import com.github.dadeo.pdfbox.creator.writer.hr.BottomBorderHorizontalRuleAdjustmentRule
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

    static CurrentLocationAdjuster<Bordered> paragraphCurrentLocationAdjuster = new CurrentLocationAdjuster(
        adjustmentRules: [
            new TopLeftRightBorderParagraphAdjustmentRule(),
            new AdjacentBordersParagraphAdjustmentRule()
        ])

    static CurrentLocationAdjuster<DHorizontalRule> horizontalRuleCurrentLocationAdjuster = new CurrentLocationAdjuster(
        adjustmentRules: [
            new BottomBorderHorizontalRuleAdjustmentRule(),
        ])

    static BoundedTextBlockLineWriter leftJustifiedTextBlockLineWriter = createJustifiedTextBlockWriter(new LeftJustifiedTextBlockCurrentLocationPositioner())
    static BoundedTextBlockLineWriter rightJustifiedTextBlockLineWriter = createJustifiedTextBlockWriter(new RightJustifiedTextBlockCurrentLocationPositioner())

    private
    static BoundedTextBlockLineWriter createJustifiedTextBlockWriter(TextBlockCurrentLocationPositioner currentLocationRepositioner) {
        BoundedTextBlockTokenWriter tokenWriter = new BoundedTextBlockTokenWriter(currentLocationPositioner: currentLocationRepositioner)
        new BoundedTextBlockLineWriter(currentLocationPositioner: currentLocationRepositioner, tokenWriter: tokenWriter)
    }

}