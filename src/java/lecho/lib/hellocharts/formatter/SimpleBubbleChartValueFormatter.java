/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 */
package lecho.lib.hellocharts.formatter;

import lecho.lib.hellocharts.formatter.BubbleChartValueFormatter;
import lecho.lib.hellocharts.formatter.ValueFormatterHelper;
import lecho.lib.hellocharts.model.BubbleValue;

public class SimpleBubbleChartValueFormatter
implements BubbleChartValueFormatter {
    private ValueFormatterHelper valueFormatterHelper;

    public SimpleBubbleChartValueFormatter() {
        this.valueFormatterHelper = new ValueFormatterHelper();
        this.valueFormatterHelper.determineDecimalSeparator();
    }

    public SimpleBubbleChartValueFormatter(int n) {
        this.valueFormatterHelper.setDecimalDigitsNumber(n);
    }

    @Override
    public int formatChartValue(char[] arrc, BubbleValue bubbleValue) {
        return this.valueFormatterHelper.formatFloatValueWithPrependedAndAppendedText(arrc, bubbleValue.getZ(), bubbleValue.getLabelAsChars());
    }

    public char[] getAppendedText() {
        return this.valueFormatterHelper.getAppendedText();
    }

    public int getDecimalDigitsNumber() {
        return this.valueFormatterHelper.getDecimalDigitsNumber();
    }

    public char getDecimalSeparator() {
        return this.valueFormatterHelper.getDecimalSeparator();
    }

    public char[] getPrependedText() {
        return this.valueFormatterHelper.getPrependedText();
    }

    public SimpleBubbleChartValueFormatter setAppendedText(char[] arrc) {
        this.valueFormatterHelper.setAppendedText(arrc);
        return this;
    }

    public SimpleBubbleChartValueFormatter setDecimalDigitsNumber(int n) {
        this.valueFormatterHelper.setDecimalDigitsNumber(n);
        return this;
    }

    public SimpleBubbleChartValueFormatter setDecimalSeparator(char c) {
        this.valueFormatterHelper.setDecimalSeparator(c);
        return this;
    }

    public SimpleBubbleChartValueFormatter setPrependedText(char[] arrc) {
        this.valueFormatterHelper.setPrependedText(arrc);
        return this;
    }
}

