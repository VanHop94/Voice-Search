package smarttouch.uet.edu.voicesearch.custom.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

import smarttouch.uet.edu.voicesearch.R;

/**
 * Created by VanHop on 4/10/2016.
 */
public class CircularTextView extends TextView
{
    private float strokeWidth;
    private Context context;

    public CircularTextView(Context context) {
        super(context);
        this.context = context;
    }

    public CircularTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public CircularTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }


    @Override
    public void draw(Canvas canvas) {

        Paint circlePaint = new Paint();
        circlePaint.setColor(getColor(R.color.colorAccent));
        circlePaint.setFlags(Paint.ANTI_ALIAS_FLAG);

        Paint strokePaint = new Paint();
        strokePaint.setColor(Color.parseColor("#2196F3"));
        strokePaint.setFlags(Paint.ANTI_ALIAS_FLAG);

        int  h = this.getHeight();
        int  w = this.getWidth();

        int diameter = ((h > w) ? h : w);
        int radius = diameter/2;

        this.setHeight(diameter);
        this.setWidth(diameter);

        canvas.drawCircle(diameter / 2 , diameter / 2, radius, strokePaint);

        canvas.drawCircle(diameter / 2, diameter / 2, radius-strokeWidth, circlePaint);

        super.draw(canvas);
    }


    private int getColor(int colorValue)
    {
        int color;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            color = getContext().getResources().getColor(colorValue, null);
        }
        else
        {
            color =  getContext().getResources().getColor(colorValue);
        }

        return color;
    }

    public void setStrokeWidth(int dp)
    {
        float scale = getContext().getResources().getDisplayMetrics().density;
        strokeWidth = dp*scale;

    }
}
