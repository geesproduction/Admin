/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.preference.Preference
 *  android.preference.Preference$BaseSavedState
 *  android.util.AttributeSet
 *  android.view.View
 *  java.lang.Class
 *  java.lang.Integer
 *  java.lang.Object
 */
package yuku.ambilwarna.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;
import yuku.ambilwarna.AmbilWarnaDialog;
import yuku.ambilwarna.R;

public class AmbilWarnaPreference
extends Preference {
    private final boolean supportsAlpha;
    int value;

    public AmbilWarnaPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.supportsAlpha = context.obtainStyledAttributes(attributeSet, R.styleable.AmbilWarnaPreference).getBoolean(R.styleable.AmbilWarnaPreference_supportsAlpha, false);
        this.setWidgetLayoutResource(R.layout.ambilwarna_pref_widget);
    }

    public void forceSetValue(int n) {
        this.value = n;
        this.persistInt(n);
        this.notifyChanged();
    }

    protected void onBindView(View view) {
        super.onBindView(view);
        View view2 = view.findViewById(R.id.ambilwarna_pref_widget_box);
        if (view2 != null) {
            view2.setBackgroundColor(this.value);
        }
    }

    protected void onClick() {
        new AmbilWarnaDialog(this.getContext(), this.value, this.supportsAlpha, new AmbilWarnaDialog.OnAmbilWarnaListener(){

            @Override
            public void onCancel(AmbilWarnaDialog ambilWarnaDialog) {
            }

            @Override
            public void onOk(AmbilWarnaDialog ambilWarnaDialog, int n) {
                if (!AmbilWarnaPreference.this.callChangeListener(n)) {
                    return;
                }
                AmbilWarnaPreference.this.value = n;
                AmbilWarnaPreference.this.persistInt(AmbilWarnaPreference.this.value);
                AmbilWarnaPreference.this.notifyChanged();
            }
        }).show();
    }

    protected Object onGetDefaultValue(TypedArray typedArray, int n) {
        return typedArray.getInteger(n, 0);
    }

    protected void onRestoreInstanceState(Parcelable parcelable) {
        if (!parcelable.getClass().equals(SavedState.class)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState)parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.value = savedState.value;
        this.notifyChanged();
    }

    protected Parcelable onSaveInstanceState() {
        Parcelable parcelable = super.onSaveInstanceState();
        if (this.isPersistent()) {
            return parcelable;
        }
        SavedState savedState = new SavedState(parcelable);
        savedState.value = this.value;
        return savedState;
    }

    protected void onSetInitialValue(boolean bl, Object object) {
        int n;
        if (bl) {
            this.value = this.getPersistedInt(this.value);
            return;
        }
        this.value = n = ((Integer)object).intValue();
        this.persistInt(n);
    }

    private static class SavedState
    extends Preference.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>(){

            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] newArray(int n) {
                return new SavedState[n];
            }
        };
        int value;

        public SavedState(Parcel parcel) {
            super(parcel);
            this.value = parcel.readInt();
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            parcel.writeInt(this.value);
        }

    }

}

