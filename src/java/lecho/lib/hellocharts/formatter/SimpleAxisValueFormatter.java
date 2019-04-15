/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 */
package lecho.lib.hellocharts.formatter;

import lecho.lib.hellocharts.formatter.AxisValueFormatter;
import lecho.lib.hellocharts.formatter.ValueFormatterHelper;
import lecho.lib.hellocharts.model.AxisValue;

public class SimpleAxisValueFormatter
implements AxisValueFormatter {
    private ValueFormatterHelper valueFormatterHelper;

    public SimpleAxisValueFormatter() {
        this.valueFormatterHelper = new ValueFormatterHelper();
        this.valueFormatterHelper.determineDecimalSeparator();
    }

    public SimpleAxisValueFormatter(int n) {
        this.valueFormatterHelper.setDecimalDigitsNumber(n);
    }

    @Override
    public int formatValueForAutoGeneratedAxis(char[] arrc, float f, int n) {
        return this.valueFormatterHelper.formatFloatValueWithPrependedAndAppendedText(arrc, f, n);
    }

    @Override
    public int formatValueForManualAxis(char[] arrc, AxisValue axisValue) {
        return this.valueFormatterHelper.formatFloatValueWithPrependedAndAppendedText(arrc, axisValue.getValue(), axisValue.getLabelAsChars());
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

    public SimpleAxisValueFormatter setAppendedText(char[] arrc) {
        this.valueFormatterHelper.setAppendedText(arrc);
        return this;
    }

    public SimpleAxisValueFormatter setDecimalDigitsNumber(int n) {
        this.valueFormatterHelper.setDecimalDigitsNumber(n);
        return this;
    }

    public SimpleAxisValueFormatter setDecimalSeparator(char c) {
        this.valueFormatterHelper.setDecimalSeparator(c);
        return this;
    }

    public SimpleAxisValueFormatter setPrependedText(char[] arrc) {
        this.valueFormatterHelper.setPrependedText(arrc);
        return this;
    }
}

