package fh.ooe.mcm.inactivitytracker.ui;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;

import fh.ooe.mcm.inactivitytracker.R;

public class CircleButton extends android.support.v7.widget.AppCompatImageView {

    private static final int PRESSED_COLOR_LIGHTUP = 255 / 25;
    private static final int PRESSED_RING_ALPHA = 75;
    private static final int DEFAULT_PRESSED_RING_WIDTH_DIP = 4;
    private static final int ANIMATION_TIME_ID = android.R.integer.config_shortAnimTime;

    private int centerY;
    private int centerX;
    private int outerRadius;
    private int pressedRingRadius;

    private Paint circlePaint;
    private Paint focusPaint;
    private Paint strokePaint;

    private float animationProgress;

    private int pressedRingWidth;
    private int strokeWidth;
    private int defaultColor = Color.BLACK;
    private int pressedColor;
    private ObjectAnimator pressedAnimator;

    public CircleButton(Context context) {
        super(context);
        init(context, null);
    }

    public CircleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CircleButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    @Override
    public void setPressed(boolean pressed) {
        super.setPressed(pressed);

//        if (circlePaint != null) {
//            circlePaint.setColor(defaultColor);
//        }

        if (pressed) {
            showPressedRing();
        } else {
            hidePressedRing();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(centerX, centerY, pressedRingRadius + strokeWidth + animationProgress, focusPaint);
        canvas.drawCircle(centerX, centerY, outerRadius + strokeWidth, strokePaint);
        canvas.drawCircle(centerX, centerY, outerRadius, circlePaint);
        super.onDraw(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = w / 2;
        centerY = h / 2;
        outerRadius = Math.min(w, h) / 2 - strokeWidth*3;
        pressedRingRadius = outerRadius;
    }

    public float getAnimationProgress() {
        return animationProgress;
    }

    public void setAnimationProgress(float animationProgress) {
        this.animationProgress = animationProgress;
        this.invalidate();
    }

    public void setColorStroke(int color) {
        //this.pressedColor = getHighlightColor(color, PRESSED_COLOR_LIGHTUP);

        strokePaint.setColor(color);
        //strokePaint.setAlpha(PRESSED_RING_ALPHA);

        this.invalidate();
    }

    public void setColorFocus(int color) {
        this.pressedColor = getHighlightColor(color, PRESSED_COLOR_LIGHTUP);

        focusPaint.setColor(color);
        focusPaint.setAlpha(PRESSED_RING_ALPHA);

        this.invalidate();
    }

    public void setColorFill(int color) {
        this.defaultColor = color;
        //this.defaultColor = color;

        circlePaint.setColor(color);

        this.invalidate();
    }

    private void hidePressedRing() {
        pressedAnimator.setFloatValues(pressedRingWidth, 0f);
        pressedAnimator.start();
    }

    private void showPressedRing() {
        pressedAnimator.setFloatValues(animationProgress, pressedRingWidth);
        pressedAnimator.start();
    }

    private void init(Context context, AttributeSet attrs) {
        this.setFocusable(true);
        this.setScaleType(ScaleType.CENTER_INSIDE);
        setClickable(true);

        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setStyle(Paint.Style.FILL);

        focusPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        focusPaint.setStyle(Paint.Style.STROKE);

        strokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        strokePaint.setStyle(Paint.Style.STROKE);

        pressedRingWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_PRESSED_RING_WIDTH_DIP, getResources()
                .getDisplayMetrics());

        strokeWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, TypedValue.COMPLEX_UNIT_DIP, getResources()
                .getDisplayMetrics());

        int color = Color.BLACK;
        if (attrs != null) {
            final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleButton);

            color = a.getColor(R.styleable.CircleButton_cb_strokeColor, color);
            strokeWidth = (int) a.getDimension(R.styleable.CircleButton_cb_strokeWidth, strokeWidth);

            setColorStroke(color);
            strokePaint.setStrokeWidth(strokeWidth);

            setColorFocus(color);
            focusPaint.setStrokeWidth(pressedRingWidth);
            final int pressedAnimationTime = getResources().getInteger(ANIMATION_TIME_ID);
            pressedAnimator = ObjectAnimator.ofFloat(this, "animationProgress", 0f, 0f);
            pressedAnimator.setDuration(pressedAnimationTime);

            color = a.getColor(R.styleable.CircleButton_cb_color, color);
            pressedRingWidth = (int) a.getDimension(R.styleable.CircleButton_cb_pressedRingWidth, pressedRingWidth);

            setColorFill(color);
            a.recycle();
        }

    }

    private int getHighlightColor(int color, int amount) {
        return Color.argb(Math.min(255, Color.alpha(color)), Math.min(255, Color.red(color) + amount),
                Math.min(255, Color.green(color) + amount), Math.min(255, Color.blue(color) + amount));
    }
}