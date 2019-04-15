/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.util.Log
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.text.DecimalFormat
 *  java.text.DecimalFormatSymbols
 *  java.text.NumberFormat
 */
package lecho.lib.hellocharts.formatter;

import android.util.Log;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import lecho.lib.hellocharts.util.FloatUtils;

public class ValueFormatterHelper {
    public static final int DEFAULT_DIGITS_NUMBER = 0;
    private static final String TAG = "ValueFormatterHelper";
    private char[] appendedText = new char[0];
    private int decimalDigitsNumber = Integer.MIN_VALUE;
    private char decimalSeparator = (char)46;
    private char[] prependedText = new char[0];

    public void appendText(char[] arrc) {
        if (this.appendedText.length > 0) {
            System.arraycopy((Object)this.appendedText, (int)0, (Object)arrc, (int)(arrc.length - this.appendedText.length), (int)this.appendedText.length);
        }
    }

    public void determineDecimalSeparator() {
        NumberFormat numberFormat = NumberFormat.getInstance();
        if (numberFormat instanceof DecimalFormat) {
            this.decimalSeparator = ((DecimalFormat)numberFormat).getDecimalFormatSymbols().getDecimalSeparator();
        }
    }

    public int formatFloatValue(char[] arrc, float f, int n) {
        return FloatUtils.formatFloat(arrc, f, arrc.length - this.appendedText.length, n, this.decimalSeparator);
    }

    public int formatFloatValueWithPrependedAndAppendedText(char[] arrc, float f, int n) {
        return this.formatFloatValueWithPrependedAndAppendedText(arrc, f, n, null);
    }

    public int formatFloatValueWithPrependedAndAppendedText(char[] arrc, float f, int n, char[] arrc2) {
        if (arrc2 != null) {
            int n2 = arrc2.length;
            if (n2 > arrc.length) {
                Log.w((String)TAG, (String)"Label length is larger than buffer size(64chars), some chars will be skipped!");
                n2 = arrc.length;
            }
            System.arraycopy((Object)arrc2, (int)0, (Object)arrc, (int)(arrc.length - n2), (int)n2);
            return n2;
        }
        int n3 = this.formatFloatValue(arrc, f, this.getAppliedDecimalDigitsNumber(n));
        this.appendText(arrc);
        this.prependText(arrc, n3);
        return n3 + this.getPrependedText().length + this.getAppendedText().length;
    }

    public int formatFloatValueWithPrependedAndAppendedText(char[] arrc, float f, char[] arrc2) {
        return this.formatFloatValueWithPrependedAndAppendedText(arrc, f, 0, arrc2);
    }

    public char[] getAppendedText() {
        return this.appendedText;
    }

    public int getAppliedDecimalDigitsNumber(int n) {
        if (this.decimalDigitsNumber < 0) {
            return n;
        }
        return this.decimalDigitsNumber;
    }

    public int getDecimalDigitsNumber() {
        return this.decimalDigitsNumber;
    }

    public char getDecimalSeparator() {
        return this.decimalSeparator;
    }

    public char[] getPrependedText() {
        return this.prependedText;
    }

    public void prependText(char[] arrc, int n) {
        if (this.prependedText.length > 0) {
            System.arraycopy((Object)this.prependedText, (int)0, (Object)arrc, (int)(arrc.length - n - this.appendedText.length - this.prependedText.length), (int)this.prependedText.length);
        }
    }

    public ValueFormatterHelper setAppendedText(char[] arrc) {
        if (arrc != null) {
            this.appendedText = arrc;
        }
        return this;
    }

    public ValueFormatterHelper setDecimalDigitsNumber(int n) {
        this.decimalDigitsNumber = n;
        return this;
    }

    public ValueFormatterHelper setDecimalSeparator(char c) {
        if (c != '\u0000') {
            this.decimalSeparator = c;
        }
        return this;
    }

    public ValueFormatterHelper setPrependedText(char[] arrc) {
        if (arrc != null) {
            this.prependedText = arrc;
        }
        return this;
    }
}

