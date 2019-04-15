/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 */
package lecho.lib.hellocharts.formatter;

import lecho.lib.hellocharts.formatter.LineChartValueFormatter;
import lecho.lib.hellocharts.formatter.ValueFormatterHelper;
import lecho.lib.hellocharts.model.PointValue;

public class SimpleLineChartValueFormatter
implements LineChartValueFormatter {
    private ValueFormatterHelper valueFormatterHelper;

    public SimpleLineChartValueFormatter() {
        this.valueFormatterHelper = new ValueFormatterHelper();
        this.valueFormatterHelper.determineDecimalSeparator();
    }

    public SimpleLineChartValueFormatter(int n) {
        this.valueFormatterHelper.setDecimalDigitsNumber(n);
    }

    @Override
    public int formatChartValue(char[] arrc, PointValue pointValue) {
        return this.valueFormatterHelper.formatFloatValueWithPrependedAndAppendedText(arrc, pointValue.getY(), pointValue.getLabelAsChars());
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

    public SimpleLineChartValueFormatter setAppendedText(char[] arrc) {
        this.valueFormatterHelper.setAppendedText(arrc);
        return this;
    }

    public SimpleLineChartValueFormatter setDecimalDigitsNumber(int n) {
        this.valueFormatterHelper.setDecimalDigitsNumber(n);
        return this;
    }

    public SimpleLineChartValueFormatter setDecimalSeparator(char c) {
        this.valueFormatterHelper.setDecimalSeparator(c);
        return this;
    }

    public SimpleLineChartValueFormatter setPrependedText(char[] arrc) {
        this.valueFormatterHelper.setPrependedText(arrc);
        return this;
    }
}

