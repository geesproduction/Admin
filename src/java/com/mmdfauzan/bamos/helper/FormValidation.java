/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.IBinder
 *  android.text.TextUtils
 *  android.util.Patterns
 *  android.view.View
 *  android.view.inputmethod.InputMethodManager
 *  java.lang.CharSequence
 *  java.lang.Object
 *  java.lang.String
 *  java.util.regex.Matcher
 *  java.util.regex.Pattern
 */
package com.mmdfauzan.bamos.helper;

import android.content.Context;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormValidation {
    Context context;

    public FormValidation() {
    }

    public FormValidation(Context context) {
        this.context = context;
    }

    public static final boolean isSamePassword(CharSequence charSequence, CharSequence charSequence2) {
        return charSequence.equals((Object)charSequence2);
    }

    public static final boolean isValidEmail(CharSequence charSequence) {
        if (TextUtils.isEmpty((CharSequence)charSequence)) {
            return false;
        }
        return Patterns.EMAIL_ADDRESS.matcher(charSequence).matches();
    }

    /*
     * Enabled aggressive block sorting
     */
    public static final boolean isValidId(CharSequence charSequence) {
        if (charSequence.length() <= 0) {
            return false;
        }
        char c = charSequence.charAt(0);
        if (c >= '0' && c <= '9') {
            return false;
        }
        boolean bl = false;
        if (bl) {
            return false;
        }
        if (charSequence.length() > 15) return false;
        return true;
    }

    public static final boolean isValidName(CharSequence charSequence) {
        return charSequence.length() > 0;
    }

    public static final boolean isValidNumber(CharSequence charSequence) {
        return !charSequence.equals((Object)"0");
    }

    public static final boolean isValidPassword(CharSequence charSequence) {
        return charSequence.length() >= 5;
    }

    public static final boolean isValidPhoneNumber(CharSequence charSequence) {
        return charSequence.length() > 9;
    }

    public void hideKeyboard(View view) {
        ((InputMethodManager)this.context.getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void showKeyboard() {
        ((InputMethodManager)this.context.getSystemService("input_method")).toggleSoftInput(2, 1);
    }
}

