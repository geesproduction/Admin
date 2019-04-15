/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.Paint$Style
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.view.View
 *  java.lang.Math
 */
package yuku.ambilwarna.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

public class AmbilWarnaPrefWidgetView
extends View {
    Paint paint;
    float rectSize;
    float strokeWidth;

    public AmbilWarnaPrefWidgetView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        float f = context.getResources().getDisplayMetrics().density;
        this.rectSize = (float)Math.floor((double)(0.5f + 24.0f * f));
        this.strokeWidth = (float)Math.floor((double)(0.5f + 1.0f * f));
        this.paint = new Paint();
        this.paint.setColor(-1);
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setStrokeWidth(this.strokeWidth);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(this.strokeWidth, this.strokeWidth, this.rectSize - this.strokeWidth, this.rectSize - this.strokeWidth, this.paint);
    }
}

