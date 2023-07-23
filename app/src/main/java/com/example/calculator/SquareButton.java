package com.example.calculator;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
public class SquareButton extends androidx.appcompat.widget.AppCompatButton {

    public SquareButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(width, width);
    }
}