/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  java.lang.Class
 *  java.lang.Float
 *  java.lang.Object
 *  java.lang.String
 */
package lecho.lib.hellocharts.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Viewport
implements Parcelable {
    public static final Parcelable.Creator<Viewport> CREATOR = new Parcelable.Creator<Viewport>(){

        public Viewport createFromParcel(Parcel parcel) {
            Viewport viewport = new Viewport();
            viewport.readFromParcel(parcel);
            return viewport;
        }

        public Viewport[] newArray(int n) {
            return new Viewport[n];
        }
    };
    public float bottom;
    public float left;
    public float right;
    public float top;

    public Viewport() {
    }

    public Viewport(float f, float f2, float f3, float f4) {
        this.left = f;
        this.top = f2;
        this.right = f3;
        this.bottom = f4;
    }

    public Viewport(Viewport viewport) {
        if (viewport == null) {
            this.bottom = 0.0f;
            this.right = 0.0f;
            this.top = 0.0f;
            this.left = 0.0f;
            return;
        }
        this.left = viewport.left;
        this.top = viewport.top;
        this.right = viewport.right;
        this.bottom = viewport.bottom;
    }

    public final float centerX() {
        return 0.5f * (this.left + this.right);
    }

    public final float centerY() {
        return 0.5f * (this.top + this.bottom);
    }

    public boolean contains(float f, float f2) {
        return this.left < this.right && this.bottom < this.top && f >= this.left && f < this.right && f2 >= this.bottom && f2 < this.top;
    }

    public boolean contains(float f, float f2, float f3, float f4) {
        return this.left < this.right && this.bottom < this.top && this.left <= f && this.top >= f2 && this.right >= f3 && this.bottom <= f4;
    }

    public boolean contains(Viewport viewport) {
        return this.left < this.right && this.bottom < this.top && this.left <= viewport.left && this.top >= viewport.top && this.right >= viewport.right && this.bottom <= viewport.bottom;
    }

    public int describeContents() {
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        block9 : {
            block8 : {
                if (this == object) break block8;
                if (object == null) {
                    return false;
                }
                if (this.getClass() != object.getClass()) {
                    return false;
                }
                Viewport viewport = (Viewport)object;
                if (Float.floatToIntBits((float)this.bottom) != Float.floatToIntBits((float)viewport.bottom)) {
                    return false;
                }
                if (Float.floatToIntBits((float)this.left) != Float.floatToIntBits((float)viewport.left)) {
                    return false;
                }
                if (Float.floatToIntBits((float)this.right) != Float.floatToIntBits((float)viewport.right)) {
                    return false;
                }
                if (Float.floatToIntBits((float)this.top) != Float.floatToIntBits((float)viewport.top)) break block9;
            }
            return true;
        }
        return false;
    }

    public int hashCode() {
        return 31 * (31 * (31 * (31 + Float.floatToIntBits((float)this.bottom)) + Float.floatToIntBits((float)this.left)) + Float.floatToIntBits((float)this.right)) + Float.floatToIntBits((float)this.top);
    }

    public final float height() {
        return this.top - this.bottom;
    }

    public void inset(float f, float f2) {
        this.left = f + this.left;
        this.top -= f2;
        this.right -= f;
        this.bottom = f2 + this.bottom;
    }

    public boolean intersect(float f, float f2, float f3, float f4) {
        if (this.left < f3 && f < this.right && this.bottom < f2 && f4 < this.top) {
            if (this.left < f) {
                this.left = f;
            }
            if (this.top > f2) {
                this.top = f2;
            }
            if (this.right > f3) {
                this.right = f3;
            }
            if (this.bottom < f4) {
                this.bottom = f4;
            }
            return true;
        }
        return false;
    }

    public boolean intersect(Viewport viewport) {
        return this.intersect(viewport.left, viewport.top, viewport.right, viewport.bottom);
    }

    public final boolean isEmpty() {
        return this.left >= this.right || this.bottom >= this.top;
    }

    public void offset(float f, float f2) {
        this.left = f + this.left;
        this.top = f2 + this.top;
        this.right = f + this.right;
        this.bottom = f2 + this.bottom;
    }

    public void offsetTo(float f, float f2) {
        this.right += f - this.left;
        this.bottom += f2 - this.top;
        this.left = f;
        this.top = f2;
    }

    public void readFromParcel(Parcel parcel) {
        this.left = parcel.readFloat();
        this.top = parcel.readFloat();
        this.right = parcel.readFloat();
        this.bottom = parcel.readFloat();
    }

    public void set(float f, float f2, float f3, float f4) {
        this.left = f;
        this.top = f2;
        this.right = f3;
        this.bottom = f4;
    }

    public void set(Viewport viewport) {
        this.left = viewport.left;
        this.top = viewport.top;
        this.right = viewport.right;
        this.bottom = viewport.bottom;
    }

    public void setEmpty() {
        this.bottom = 0.0f;
        this.top = 0.0f;
        this.right = 0.0f;
        this.left = 0.0f;
    }

    public String toString() {
        return "Viewport [left=" + this.left + ", top=" + this.top + ", right=" + this.right + ", bottom=" + this.bottom + "]";
    }

    public void union(float f, float f2, float f3, float f4) {
        block8 : {
            block7 : {
                if (!(f < f3) || !(f4 < f2)) break block7;
                if (!(this.left < this.right) || !(this.bottom < this.top)) break block8;
                if (this.left > f) {
                    this.left = f;
                }
                if (this.top < f2) {
                    this.top = f2;
                }
                if (this.right < f3) {
                    this.right = f3;
                }
                if (this.bottom > f4) {
                    this.bottom = f4;
                }
            }
            return;
        }
        this.left = f;
        this.top = f2;
        this.right = f3;
        this.bottom = f4;
    }

    public void union(Viewport viewport) {
        this.union(viewport.left, viewport.top, viewport.right, viewport.bottom);
    }

    public final float width() {
        return this.right - this.left;
    }

    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeFloat(this.left);
        parcel.writeFloat(this.top);
        parcel.writeFloat(this.right);
        parcel.writeFloat(this.bottom);
    }

}

