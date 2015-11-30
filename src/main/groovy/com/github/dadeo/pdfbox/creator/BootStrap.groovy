package com.github.dadeo.pdfbox.creator

import com.github.dadeo.pdfbox.creator.writer.hr.BottomBorderHorizontalRuleAdjustmentRule
import com.github.dadeo.pdfbox.creator.writer.paragraph.AdjacentBordersParagraphAdjustmentRule
import com.github.dadeo.pdfbox.creator.writer.paragraph.TopLeftRightBorderParagraphAdjustmentRule
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

}