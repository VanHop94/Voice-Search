package smarttouch.uet.edu.voicesearch.custom.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by VanHop on 4/10/2016.
 */
public class ListViewExpanded extends ListView
{
    public ListViewExpanded(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        setDividerHeight(0);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST));
    }
}
