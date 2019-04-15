/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 */
package lecho.lib.hellocharts.formatter;

import lecho.lib.hellocharts.formatter.PieChartValueFormatter;
import lecho.lib.hellocharts.formatter.ValueFormatterHelper;
import lecho.lib.hellocharts.model.SliceValue;

public class SimplePieChartValueFormatter
implements PieChartValueFormatter {
    private ValueFormatterHelper valueFormatterHelper;

    public SimplePieChartValueFormatter() {
        this.valueFormatterHelper = new ValueFormatterHelper();
        this.valueFormatterHelper.determineDecimalSeparator();
    }

    public SimplePieChartValueFormatter(int n) {
        this.valueFormatterHelper.setDecimalDigitsNumber(n);
    }

    @Override
    public int formatChartValue(char[] arrc, SliceValue sliceValue) {
        return this.valueFormatterHelper.formatFloatValueWithPrependedAndAppendedText(arrc, sliceValue.getValue(), sliceValue.getLabelAsChars());
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

    public SimplePieChartValueFormatter setAppendedText(char[] arrc) {
        this.valueFormatterHelper.setAppendedText(arrc);
        return this;
    }

    public SimplePieChartValueFormatter setDecimalDigitsNumber(int n) {
        this.valueFormatterHelper.setDecimalDigitsNumber(n);
        return this;
    }

    public SimplePieChartValueFormatter setDecimalSeparator(char c) {
        this.valueFormatterHelper.setDecimalSeparator(c);
        return this;
    }

    public SimplePieChartValueFormatter setPrependedText(char[] arrc) {
        this.valueFormatterHelper.setPrependedText(arrc);
        return this;
    }
}

