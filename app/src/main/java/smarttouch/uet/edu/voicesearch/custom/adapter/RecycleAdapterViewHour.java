package smarttouch.uet.edu.voicesearch.custom.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import smarttouch.uet.edu.voicesearch.R;
import smarttouch.uet.edu.voicesearch.custom.view.HourView;

/**
 * Created by VanHop on 4/10/2016.
 */
public class RecycleAdapterViewHour extends RecyclerView.Adapter<HourView> {

    private ArrayList<String> hours;
    private Context context;

    public RecycleAdapterViewHour(ArrayList<String> hours, Context context) {
        this.hours = hours;
        this.context = context;
    }

    @Override
    public HourView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.hour_arrange, null);
        HourView hourView = new HourView(view);
        return hourView;
    }

    @Override
    public void onBindViewHolder(HourView holder, int position) {
        String hour = hours.get(position);
        holder.hour.setText(hour);
    }

    @Override
    public int getItemCount() {
        return hours.size();
    }
}
