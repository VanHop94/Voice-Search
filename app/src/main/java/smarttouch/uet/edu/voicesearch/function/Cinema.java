package smarttouch.uet.edu.voicesearch.function;

/**
 * Created by VanHop on 4/17/2016.
 */
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import smarttouch.uet.edu.voicesearch.entities.BasicFilmInfo;


/**
 * Created by zatcsc on 2/26/2016.
 */
public class Cinema {
    private static Context mContext;

    public static void handle(Context context, String command) {
        mContext = context;
        new CurrentFilmListParser().execute(command);
    }

    public static class CurrentFilmListParser extends AsyncTask<String, Void, List<BasicFilmInfo>> {
        public static String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.109 Safari/537.36";

        public static HashMap<String, String> getLocation2IDMap(Document htmlDoc) {
            HashMap<String, String> location2ID = new HashMap<>();
            Elements locationList = htmlDoc
                    .select("div#dropdown-location-header > ul > li");
            for (Element location : locationList) {
                String name = location.attr("data-name").toLowerCase();
                String id = location.attr("data-id").toLowerCase();
                location2ID.put(name, id);
                if (name.equals("hồ chí minh"))
                    location2ID.put("sài gòn", id);
            }
            return location2ID;
        }

        public static List<BasicFilmInfo> getFilmList(Document doc) {
            List<BasicFilmInfo> films = new ArrayList<BasicFilmInfo>();
            String title = null;
            String coverLink;
            String IMDbRate;
            String trailerLink;
            String filmDetailLink;
            Elements filmList = doc.select("div.main > div.content.tag > div.group > div.block-base.movie");
            for (Element film : filmList) {
                coverLink = film.select("a > img").first().attr("src");
                title = film.select("a.film-name").first().text();
                IMDbRate = film.select("div.movie-info.rate > div.imdb > strong").first().text();
                trailerLink = film.select("div.movie-info.rate > a.play-trailer").first().attr("href");
                filmDetailLink = "http://www.123phim.vn" + film.select("a.film-name").first().attr("href");
                BasicFilmInfo basicFilmInfo = new BasicFilmInfo(title, coverLink, IMDbRate, trailerLink, filmDetailLink,"");
                films.add(basicFilmInfo);
            }
            return films;
        }

        public static List<BasicFilmInfo> parseCurrentFilmInfo(String command) {
            try {
                Document doc = Jsoup
                        .connect("http://www.123phim.vn/phim/")
                        .timeout(10000)
                        .userAgent(USER_AGENT)
                        .cookie("location", "2").get();
                HashMap<String, String> location2ID = getLocation2IDMap(doc);
                String locationID = "1";
                String location = "Hà Nội";
                for (String key : location2ID.keySet())
                    if (command.contains(key)) {
                        locationID = location2ID.get(key);
                        location = key;
                    }
                doc = Jsoup
                        .connect("http://www.123phim.vn/phim/")
                        .timeout(10000)
                        .userAgent(USER_AGENT)
                        .cookie("location", locationID).get();
                List<BasicFilmInfo> films = getFilmList(doc);
                for (BasicFilmInfo film : films) Log.d("VAV_Cinema", film.toString());
                return films;
            } catch (Exception e) {
                Log.d("VAV_Cinema", e.toString());
            }
            return null;
        }

        @Override
        protected List<BasicFilmInfo> doInBackground(String... params) {
            String command = params[0];
            return parseCurrentFilmInfo(command);
        }

        @Override
        protected void onPostExecute(List<BasicFilmInfo> basicFilmInfos) {
            super.onPostExecute(basicFilmInfos);
            //FilmsListResponse filmsListResponse = new FilmsListResponse(null, null, basicFilmInfos);
           // VoiceActivity.showFilmsListResponse(mContext, filmsListResponse);
        }
    }

}
