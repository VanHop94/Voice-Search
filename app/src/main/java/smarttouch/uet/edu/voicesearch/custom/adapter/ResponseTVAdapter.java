package smarttouch.uet.edu.voicesearch.custom.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import smarttouch.uet.edu.voicesearch.R;
import smarttouch.uet.edu.voicesearch.entities.ResponseTVDTO;

/**
 * Created by VanHop on 4/10/2016.
 */
public class ResponseTVAdapter extends BaseAdapter {

    private ArrayList<ResponseTVDTO> responseTVDTOs;
    private Context context;

    public ResponseTVAdapter(Context context, ArrayList<ResponseTVDTO> responseTVDTOs){
        this.context = context;
        this.responseTVDTOs = responseTVDTOs;
    }

    @Override
    public int getCount() {
        return responseTVDTOs.size();
    }

    @Override
    public Object getItem(int position) {
        return responseTVDTOs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        ResponseTVDTO responseTVDTO = responseTVDTOs.get(position);
        if(convertView == null){
            LayoutInflater mInflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.custom_tv_resutl, null);
            holder = new ViewHolder();
            holder.tvChannelName = (TextView) convertView.findViewById(R.id.tvChannel);
            holder.tvHour = (TextView) convertView.findViewById(R.id.tvHour);
            holder.tvDate = (TextView) convertView.findViewById(R.id.tvDate);
            holder.tvProgram = (TextView) convertView.findViewById(R.id.tvProgram);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvChannelName.setText(responseTVDTO.getChannelName());
        holder.tvHour.setText(responseTVDTO.getStartingTime() + " - " + responseTVDTO.getFinshingTime());
        holder.tvDate.setText(responseTVDTO.getDate());
        holder.tvProgram.setText(responseTVDTO.getName());

        return convertView;
    }

    class ViewHolder{
        TextView tvChannelName;
        TextView tvHour;
        TextView tvDate;
        TextView tvProgram;
    }
}
