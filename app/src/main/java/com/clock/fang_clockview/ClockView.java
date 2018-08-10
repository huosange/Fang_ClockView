package com.clock.fang_clockview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.Calendar;

public class ClockView extends View {

    private Paint bigCirclePaint;
    private Paint smallCirclePaint;
    private Paint numPaint;

    private Paint hourPaint;
    private Paint minutePaint;
    private Paint secondPaint;

    private Paint calibrationPaint;

    private Calendar calendar;

    private int cx;
    private int cy;

    private float bigRadius;

    public ClockView(Context context) {
        this(context, null);
    }

    public ClockView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        calibrationPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        calibrationPaint.setColor(Color.parseColor("#FF928A87"));
        calibrationPaint.setStyle(Paint.Style.FILL);
        calibrationPaint.setStrokeWidth(30);

        bigCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bigCirclePaint.setColor(Color.parseColor("#FF404447"));
        bigCirclePaint.setStyle(Paint.Style.FILL);
        bigCirclePaint.setStrokeWidth(34);

        smallCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        smallCirclePaint.setColor(Color.parseColor("#FF928A87"));
        smallCirclePaint.setStyle(Paint.Style.FILL);

        numPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        numPaint.setColor(Color.parseColor("#FF928A87"));
        numPaint.setAntiAlias(true);
        numPaint.setTextAlign(Paint.Align.CENTER);
        numPaint.setTextSize(80);

        hourPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        hourPaint.setColor(Color.parseColor("#FF928A87"));
        hourPaint.setStrokeWidth(25);

        minutePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        minutePaint.setColor(Color.parseColor("#FF928A87"));
        minutePaint.setStrokeWidth(18);

        secondPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        secondPaint.setColor(Color.parseColor("#FF928A87"));
        secondPaint.setStrokeWidth(11);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int clockWidth = getWidth();
        cx = clockWidth / 2;
        cy = clockWidth / 2;

        bigRadius = clockWidth / 2 - 75;

        canvas.drawCircle(cx, cy, bigRadius, bigCirclePaint);
        canvas.drawCircle(cx, cy, 30, smallCirclePaint);

        for (int i = 1; i < 13; i++) {
            canvas.save();
            canvas.rotate(i*30,cx,cy);
            canvas.drawLine(cx,cy-bigRadius,cx,cy-bigRadius+30,calibrationPaint);
            canvas.restore();

            float x = (float) (cx + Math.sin(Math.PI / 6 * i) * (bigRadius - 80));
            float y = (float) (cy - Math.cos(Math.PI / 6 * i) * (bigRadius - 80));
            Paint.FontMetricsInt fontMetricsInt = numPaint.getFontMetricsInt();
            y = y - fontMetricsInt.top / 2 - fontMetricsInt.bottom / 2;
            canvas.drawText(i + "", x, y, numPaint);
        }

        calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        float hourDegree = (minute / 60 + hour) * 30;
        canvas.save();
        canvas.rotate(hourDegree, cx, cy);
        canvas.drawLine(cx, cy - 200, cx, cy, hourPaint);
        canvas.restore();

        float minuteDegree = (float) (minute / 60.0 * 360);
        canvas.save();
        canvas.rotate(minuteDegree, cx, cy);
        canvas.drawLine(cx, cy - 250, cx, cy, minutePaint);
        canvas.restore();

        float secondDegree = (float) (second / 60.0 * 360);
        canvas.save();
        canvas.rotate(secondDegree, cx, cy);
        canvas.drawLine(cx, cy - 300, cx, cy, secondPaint);
        canvas.restore();

        postInvalidateDelayed(1000);
    }

}
