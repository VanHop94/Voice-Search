package smarttouch.uet.edu.voicesearch.custom.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import smarttouch.uet.edu.voicesearch.R;
import smarttouch.uet.edu.voicesearch.entities.ResponseFilmDTO;

/**
 * Created by VanHop on 4/10/2016.
 */
public class ResponseFilmAdapter extends BaseAdapter {

    private ArrayList<ResponseFilmDTO> responseFilms;
    private Context context;

    public ResponseFilmAdapter(Context context, ArrayList<ResponseFilmDTO> responseFilms) {
        this.context = context;
        this.responseFilms = responseFilms;
    }

    @Override
    public int getCount() {
        return responseFilms.size();
    }

    @Override
    public Object getItem(int position) {
        return responseFilms.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ResponseFilmDTO responseFilm = responseFilms.get(position);
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            convertView = mInflater.inflate(R.layout.custom_film_result, null);
            holder = new ViewHolder();

            holder.tvDate = (TextView) convertView.findViewById(R.id.tvDate);
            holder.tvFilmName = (TextView) convertView.findViewById(R.id.tvFilm);
            holder.recyclerView = (RecyclerView) convertView.findViewById(R.id.tvTimeScale);
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            holder.recyclerView.setLayoutManager(layoutManager);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvDate.setText(responseFilm.getDate().substring(0,responseFilm.getDate().lastIndexOf("-")));
        holder.tvFilmName.setText(responseFilm.getName());
        holder.recyclerView.setAdapter(new RecycleAdapterViewHour(responseFilm.getTimes(), context));
        return convertView;
    }

    class ViewHolder {
        TextView tvDate;
        TextView tvFilmName;
        RecyclerView recyclerView;
    }
}
