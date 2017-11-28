package smarttouch.uet.edu.voicesearch.custom.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import smarttouch.uet.edu.voicesearch.R;
import smarttouch.uet.edu.voicesearch.custom.view.FilmView;
import smarttouch.uet.edu.voicesearch.entities.BasicFilmInfo;

/**
 * Created by VanHop on 4/17/2016.
 */
public class RecycleViewListFilm extends RecyclerView.Adapter<FilmView> {

    private ArrayList<BasicFilmInfo> basicFilmInfos;
    private Context context;

    public RecycleViewListFilm(ArrayList<BasicFilmInfo> basicFilmInfos, Context context) {
        this.basicFilmInfos = basicFilmInfos;
        this.context = context;
    }

    @Override
    public FilmView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_film_detail, null);
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, width * 1 / 2));
        FilmView filmView = new FilmView(view);
        filmView.cover.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, width * 3 / 8));
        filmView.name.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, width * 1 / 8));
        return filmView;
    }

    @Override
    public void onBindViewHolder(FilmView holder, int position) {
        final BasicFilmInfo basicFilmInfo = basicFilmInfos.get(position);
        holder.name.setText(basicFilmInfo.title);
        holder.date.setText(basicFilmInfo.date);
        Picasso.with(context).load(basicFilmInfo.coverLink).into(holder.cover);

        holder.cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(basicFilmInfo.filmDetailLink));
                context.startActivity(browserIntent);
            }
        });
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    @Override
    public int getItemCount() {
        return basicFilmInfos.size();
    }
}
