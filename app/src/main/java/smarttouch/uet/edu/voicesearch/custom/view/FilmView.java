package smarttouch.uet.edu.voicesearch.custom.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import smarttouch.uet.edu.voicesearch.R;

/**
 * Created by VanHop on 4/17/2016.
 */
public class FilmView extends RecyclerView.ViewHolder {

    public TextView name;
    public ImageView cover;
    public TextView date;

    public FilmView(View itemView) {
        super(itemView);
        cover = (ImageView) itemView.findViewById(R.id.imgCover);
        name = (TextView) itemView.findViewById(R.id.tvName);
        date = (TextView) itemView.findViewById(R.id.tvDate);
    }

}
