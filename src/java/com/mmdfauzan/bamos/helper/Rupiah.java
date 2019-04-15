/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Double
 *  java.lang.Object
 *  java.lang.String
 *  java.text.DecimalFormat
 *  java.text.DecimalFormatSymbols
 *  java.util.Locale
 */
package com.mmdfauzan.bamos.helper;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class Rupiah {
    public String toRibuan(String string2) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0");
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols(new Locale("sv", "SE"));
        decimalFormatSymbols.setDecimalSeparator(',');
        decimalFormatSymbols.setGroupingSeparator('.');
        decimalFormat.setDecimalFormatSymbols(decimalFormatSymbols);
        return decimalFormat.format(Double.parseDouble((String)string2));
    }

    public String toRupiah(String string2) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0");
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols(new Locale("sv", "SE"));
        decimalFormatSymbols.setDecimalSeparator(',');
        decimalFormatSymbols.setGroupingSeparator('.');
        decimalFormat.setDecimalFormatSymbols(decimalFormatSymbols);
        return "Rp " + decimalFormat.format(Double.parseDouble((String)string2));
    }
}

