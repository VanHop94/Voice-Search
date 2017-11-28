package smarttouch.uet.edu.voicesearch.custom.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import smarttouch.uet.edu.voicesearch.R;
import smarttouch.uet.edu.voicesearch.entities.BasicFilmInfo;
import smarttouch.uet.edu.voicesearch.entities.CustomObject;
import smarttouch.uet.edu.voicesearch.entities.Message;
import smarttouch.uet.edu.voicesearch.entities.ResponseFilmDTO;
import smarttouch.uet.edu.voicesearch.entities.ResponseTVDTO;
import smarttouch.uet.edu.voicesearch.util.ResponseType;

/**
 * Created by VanHop on 4/10/2016.
 */
public class MessagesListAdapter extends BaseAdapter {

    private Context context;
    private List<CustomObject> messagesItems;
    private int width;

    public MessagesListAdapter(Context context, List<CustomObject> navDrawerItems) {
        this.context = context;
        this.messagesItems = navDrawerItems;
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        width = metrics.widthPixels;
    }

    @Override
    public int getCount() {
        return messagesItems.size();
    }

    @Override
    public Object getItem(int position) {
        return messagesItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 4;
    }

    @Override
    public int getItemViewType(int position) {
        return messagesItems.get(position).getType();
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CustomObject m = messagesItems.get(position);
        int type = getItemViewType(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);


        if (type == ResponseType.RESPONSE_FILM) {

            HashMap<String, ArrayList<ResponseFilmDTO>> listHashMap = (HashMap<String, ArrayList<ResponseFilmDTO>>) messagesItems.get(position).getData();
            convertView = mInflater.inflate(R.layout.reponse_film, null);
            LinearLayout linearLayout = (LinearLayout) convertView.findViewById(R.id.reponse);
            int totalHeightLayout = 0;
            for (String key : listHashMap.keySet()) {

                View cinemaWithFilm = mInflater.inflate(R.layout.reponse_view, null);
                TextView cinemaName = (TextView) cinemaWithFilm.findViewById(R.id.title);
                cinemaName.setTextColor(Color.parseColor("#2979FF"));
                cinemaName.setText(key);
                ListView listView = (ListView) cinemaWithFilm.findViewById(R.id.listView);
                ResponseFilmAdapter adapter = new ResponseFilmAdapter(context, listHashMap.get(key));
                listView.setAdapter(adapter);
                int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
                int totalHeight = listView.getPaddingTop() + listView.getPaddingBottom();
                for (int i = 0; i < adapter.getCount(); i++) {
                    View listItem = adapter.getView(i, null, listView);
                    listItem.measure(desiredWidth / 3, View.MeasureSpec.UNSPECIFIED);
                    totalHeight += listItem.getMeasuredHeight();
                }

                ViewGroup.LayoutParams params = listView.getLayoutParams();
                params.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
                listView.setLayoutParams(params);
                listView.requestLayout();

                cinemaName.measure(0, 0);
                totalHeightLayout += totalHeight + cinemaName.getMeasuredHeight() - 50;

                linearLayout.addView(cinemaWithFilm);

            }

            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
            layoutParams.height = totalHeightLayout + 150 * listHashMap.size();
            linearLayout.requestLayout();

        } else if (type == ResponseType.RESPONSE_TV) {

            ArrayList<ResponseTVDTO> responseTVDTOs = (ArrayList<ResponseTVDTO>) messagesItems.get(position).getData();
            convertView = mInflater.inflate(R.layout.reponse_view,
                    null);

            ListView listView = (ListView) convertView.findViewById(R.id.listView);

            ResponseTVAdapter responseTVAdapter = new ResponseTVAdapter(context, responseTVDTOs);
            listView.setAdapter(responseTVAdapter);
            int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
            int totalHeight = listView.getPaddingTop() + listView.getPaddingBottom();
            for (int i = 0; i < responseTVAdapter.getCount(); i++) {
                View listItem = responseTVAdapter.getView(i, null, listView);
                listItem.measure(desiredWidth / 3, View.MeasureSpec.UNSPECIFIED);
                totalHeight += listItem.getMeasuredHeight();
            }

            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalHeight + (listView.getDividerHeight() * (responseTVAdapter.getCount() - 1));
            listView.setLayoutParams(params);
            listView.requestLayout();
        } else if (type == ResponseType.RESPONSE_FILM_DETAIL) {
            ArrayList<BasicFilmInfo> listFilm = (ArrayList<BasicFilmInfo>) messagesItems.get(position).getData();

            convertView = mInflater.inflate(R.layout.list_film, null);
            RecyclerView rcListFilm = (RecyclerView) convertView.findViewById(R.id.list_film);
            RecycleViewListFilm adapter = new RecycleViewListFilm(listFilm, context);
            rcListFilm.setLayoutManager(new GridLayoutManager(context, 3));
            rcListFilm.setAdapter(adapter);
            ViewGroup.LayoutParams layoutParams = rcListFilm.getLayoutParams();

            layoutParams.height = (listFilm.size() / 3) * width * 1 / 2;
            rcListFilm.setLayoutParams(layoutParams);
            rcListFilm.requestLayout();
        } else {
            Message message = (Message) messagesItems.get(position).getData();
            if (message.isSelf()) {
                convertView = mInflater.inflate(R.layout.list_item_message_right,
                        null);
            } else {
                convertView = mInflater.inflate(R.layout.list_item_message_left,
                        null);
            }

            TextView lblFrom = (TextView) convertView.findViewById(R.id.lblMsgFrom);
            TextView txtMsg = (TextView) convertView.findViewById(R.id.txtMsg);

            txtMsg.setText(message.getMessage());
            lblFrom.setText(message.getFromName());
        }

        return convertView;
    }
}

