/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 */
package lecho.lib.hellocharts.formatter;

import lecho.lib.hellocharts.formatter.ColumnChartValueFormatter;
import lecho.lib.hellocharts.formatter.ValueFormatterHelper;
import lecho.lib.hellocharts.model.SubcolumnValue;

public class SimpleColumnChartValueFormatter
implements ColumnChartValueFormatter {
    private ValueFormatterHelper valueFormatterHelper;

    public SimpleColumnChartValueFormatter() {
        this.valueFormatterHelper = new ValueFormatterHelper();
        this.valueFormatterHelper.determineDecimalSeparator();
    }

    public SimpleColumnChartValueFormatter(int n) {
        this.valueFormatterHelper.setDecimalDigitsNumber(n);
    }

    @Override
    public int formatChartValue(char[] arrc, SubcolumnValue subcolumnValue) {
        return this.valueFormatterHelper.formatFloatValueWithPrependedAndAppendedText(arrc, subcolumnValue.getValue(), subcolumnValue.getLabelAsChars());
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

    public SimpleColumnChartValueFormatter setAppendedText(char[] arrc) {
        this.valueFormatterHelper.setAppendedText(arrc);
        return this;
    }

    public SimpleColumnChartValueFormatter setDecimalDigitsNumber(int n) {
        this.valueFormatterHelper.setDecimalDigitsNumber(n);
        return this;
    }

    public SimpleColumnChartValueFormatter setDecimalSeparator(char c) {
        this.valueFormatterHelper.setDecimalSeparator(c);
        return this;
    }

    public SimpleColumnChartValueFormatter setPrependedText(char[] arrc) {
        this.valueFormatterHelper.setPrependedText(arrc);
        return this;
    }
}

