/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.graphics.PathEffect
 *  java.lang.Object
 *  java.util.ArrayList
 *  java.util.Iterator
 *  java.util.List
 */
package lecho.lib.hellocharts.model;

import android.graphics.PathEffect;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import lecho.lib.hellocharts.formatter.LineChartValueFormatter;
import lecho.lib.hellocharts.formatter.SimpleLineChartValueFormatter;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.util.ChartUtils;

public class Line {
    private static final int DEFAULT_AREA_TRANSPARENCY = 64;
    private static final int DEFAULT_LINE_STROKE_WIDTH_DP = 3;
    private static final int DEFAULT_POINT_RADIUS_DP = 6;
    public static final int UNINITIALIZED;
    private int areaTransparency;
    private int color;
    private int darkenColor;
    private LineChartValueFormatter formatter;
    private boolean hasLabels;
    private boolean hasLabelsOnlyForSelected;
    private boolean hasLines;
    private boolean hasPoints;
    private boolean isCubic;
    private boolean isFilled;
    private boolean isSquare;
    private PathEffect pathEffect;
    private int pointColor;
    private int pointRadius;
    private ValueShape shape;
    private int strokeWidth;
    private List<PointValue> values;

    public Line() {
        this.color = ChartUtils.DEFAULT_COLOR;
        this.pointColor = 0;
        this.darkenColor = ChartUtils.DEFAULT_DARKEN_COLOR;
        this.areaTransparency = 64;
        this.strokeWidth = 3;
        this.pointRadius = 6;
        this.hasPoints = true;
        this.hasLines = true;
        this.hasLabels = false;
        this.hasLabelsOnlyForSelected = false;
        this.isCubic = false;
        this.isSquare = false;
        this.isFilled = false;
        this.shape = ValueShape.CIRCLE;
        this.formatter = new SimpleLineChartValueFormatter();
        this.values = new ArrayList();
    }

    public Line(List<PointValue> list) {
        this.color = ChartUtils.DEFAULT_COLOR;
        this.pointColor = 0;
        this.darkenColor = ChartUtils.DEFAULT_DARKEN_COLOR;
        this.areaTransparency = 64;
        this.strokeWidth = 3;
        this.pointRadius = 6;
        this.hasPoints = true;
        this.hasLines = true;
        this.hasLabels = false;
        this.hasLabelsOnlyForSelected = false;
        this.isCubic = false;
        this.isSquare = false;
        this.isFilled = false;
        this.shape = ValueShape.CIRCLE;
        this.formatter = new SimpleLineChartValueFormatter();
        this.values = new ArrayList();
        this.setValues(list);
    }

    public Line(Line line) {
        this.color = ChartUtils.DEFAULT_COLOR;
        this.pointColor = 0;
        this.darkenColor = ChartUtils.DEFAULT_DARKEN_COLOR;
        this.areaTransparency = 64;
        this.strokeWidth = 3;
        this.pointRadius = 6;
        this.hasPoints = true;
        this.hasLines = true;
        this.hasLabels = false;
        this.hasLabelsOnlyForSelected = false;
        this.isCubic = false;
        this.isSquare = false;
        this.isFilled = false;
        this.shape = ValueShape.CIRCLE;
        this.formatter = new SimpleLineChartValueFormatter();
        this.values = new ArrayList();
        this.color = line.color;
        this.pointColor = line.pointColor;
        this.darkenColor = line.darkenColor;
        this.areaTransparency = line.areaTransparency;
        this.strokeWidth = line.strokeWidth;
        this.pointRadius = line.pointRadius;
        this.hasPoints = line.hasPoints;
        this.hasLines = line.hasLines;
        this.hasLabels = line.hasLabels;
        this.hasLabelsOnlyForSelected = line.hasLabelsOnlyForSelected;
        this.isSquare = line.isSquare;
        this.isCubic = line.isCubic;
        this.isFilled = line.isFilled;
        this.shape = line.shape;
        this.pathEffect = line.pathEffect;
        this.formatter = line.formatter;
        for (PointValue pointValue : line.values) {
            this.values.add((Object)new PointValue(pointValue));
        }
    }

    public void finish() {
        Iterator iterator = this.values.iterator();
        while (iterator.hasNext()) {
            ((PointValue)iterator.next()).finish();
        }
    }

    public int getAreaTransparency() {
        return this.areaTransparency;
    }

    public int getColor() {
        return this.color;
    }

    public int getDarkenColor() {
        return this.darkenColor;
    }

    public LineChartValueFormatter getFormatter() {
        return this.formatter;
    }

    public PathEffect getPathEffect() {
        return this.pathEffect;
    }

    public int getPointColor() {
        if (this.pointColor == 0) {
            return this.color;
        }
        return this.pointColor;
    }

    public int getPointRadius() {
        return this.pointRadius;
    }

    public ValueShape getShape() {
        return this.shape;
    }

    public int getStrokeWidth() {
        return this.strokeWidth;
    }

    public List<PointValue> getValues() {
        return this.values;
    }

    public boolean hasLabels() {
        return this.hasLabels;
    }

    public boolean hasLabelsOnlyForSelected() {
        return this.hasLabelsOnlyForSelected;
    }

    public boolean hasLines() {
        return this.hasLines;
    }

    public boolean hasPoints() {
        return this.hasPoints;
    }

    public boolean isCubic() {
        return this.isCubic;
    }

    public boolean isFilled() {
        return this.isFilled;
    }

    public boolean isSquare() {
        return this.isSquare;
    }

    public Line setAreaTransparency(int n) {
        this.areaTransparency = n;
        return this;
    }

    public Line setColor(int n) {
        this.color = n;
        if (this.pointColor == 0) {
            this.darkenColor = ChartUtils.darkenColor(n);
        }
        return this;
    }

    public Line setCubic(boolean bl) {
        this.isCubic = bl;
        if (this.isSquare) {
            this.setSquare(false);
        }
        return this;
    }

    public Line setFilled(boolean bl) {
        this.isFilled = bl;
        return this;
    }

    public Line setFormatter(LineChartValueFormatter lineChartValueFormatter) {
        if (lineChartValueFormatter != null) {
            this.formatter = lineChartValueFormatter;
        }
        return this;
    }

    public Line setHasLabels(boolean bl) {
        this.hasLabels = bl;
        if (bl) {
            this.hasLabelsOnlyForSelected = false;
        }
        return this;
    }

    public Line setHasLabelsOnlyForSelected(boolean bl) {
        this.hasLabelsOnlyForSelected = bl;
        if (bl) {
            this.hasLabels = false;
        }
        return this;
    }

    public Line setHasLines(boolean bl) {
        this.hasLines = bl;
        return this;
    }

    public Line setHasPoints(boolean bl) {
        this.hasPoints = bl;
        return this;
    }

    public void setPathEffect(PathEffect pathEffect) {
        this.pathEffect = pathEffect;
    }

    public Line setPointColor(int n) {
        this.pointColor = n;
        if (n == 0) {
            this.darkenColor = ChartUtils.darkenColor(this.color);
            return this;
        }
        this.darkenColor = ChartUtils.darkenColor(n);
        return this;
    }

    public Line setPointRadius(int n) {
        this.pointRadius = n;
        return this;
    }

    public Line setShape(ValueShape valueShape) {
        this.shape = valueShape;
        return this;
    }

    public Line setSquare(boolean bl) {
        this.isSquare = bl;
        if (this.isCubic) {
            this.setCubic(false);
        }
        return this;
    }

    public Line setStrokeWidth(int n) {
        this.strokeWidth = n;
        return this;
    }

    public void setValues(List<PointValue> list) {
        if (list == null) {
            this.values = new ArrayList();
            return;
        }
        this.values = list;
    }

    public void update(float f) {
        Iterator iterator = this.values.iterator();
        while (iterator.hasNext()) {
            ((PointValue)iterator.next()).update(f);
        }
    }
}

