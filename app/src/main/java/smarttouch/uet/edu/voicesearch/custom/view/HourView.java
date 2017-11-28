package smarttouch.uet.edu.voicesearch.custom.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import smarttouch.uet.edu.voicesearch.R;

/**
 * Created by VanHop on 4/10/2016.
 */
public class HourView extends RecyclerView.ViewHolder {

    public TextView hour;

    public HourView(View itemView) {
        super(itemView);
        hour = (TextView) itemView.findViewById(R.id.tvHour);
    }
}
