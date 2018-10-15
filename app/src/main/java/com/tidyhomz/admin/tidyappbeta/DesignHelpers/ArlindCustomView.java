package com.tidyhomz.admin.tidyappbeta.DesignHelpers;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Admin on 7/20/2017.
 */

public class ArlindCustomView extends View
{
    private Paint paint = new Paint();

    public ArlindCustomView(Context context) {
        super(context);
    }

    public ArlindCustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ArlindCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onDraw(Canvas canvas)
    {
        paint.setAntiAlias(true);
        paint.setColor(Color.BLUE);
        canvas.drawCircle(70,80,60,paint);


    }

}