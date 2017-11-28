package smarttouch.uet.edu.voicesearch.util;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by VanHop on 4/17/2016.
 */
public class ScreenUtils {

    public static int dp2px(Context context, int dp) {
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
        return px;
    }

}
