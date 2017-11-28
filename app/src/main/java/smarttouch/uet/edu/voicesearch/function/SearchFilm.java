package smarttouch.uet.edu.voicesearch.function;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import smarttouch.uet.edu.voicesearch.entities.BasicFilmInfo;
import smarttouch.uet.edu.voicesearch.entities.ResponseFilmDTO;
import smarttouch.uet.edu.voicesearch.entities.ResponseTVDTO;
import smarttouch.uet.edu.voicesearch.util.LoadData;
import smarttouch.uet.edu.voicesearch.util.TimeUtil;
import smarttouch.uet.edu.voicesearch.util.URLConstain;

/**
 * Created by VanHop on 4/4/2016.
 */
public class SearchFilm {

    public final static String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.109 Safari/537.36";

    public static HashMap<String, ArrayList<ResponseFilmDTO>> byFilmName(String sentence,String name, String date) {

        date = date.replaceAll("/","-");
        HashMap<String, ArrayList<ResponseFilmDTO>> stringArrayListHashMap = byTime(sentence, date);
        HashMap<String, ArrayList<ResponseFilmDTO>> results = new HashMap<>();
        for(String key : stringArrayListHashMap.keySet()){
            ArrayList<ResponseFilmDTO> films = stringArrayListHashMap.get(key);
            ArrayList<ResponseFilmDTO> filmsResults = new ArrayList<>();
            for(ResponseFilmDTO responseFilmDTO : films){
                if(responseFilmDTO.getName().toLowerCase().contains(name.toLowerCase().trim()))
                    filmsResults.add(responseFilmDTO);
            }
            if(filmsResults.size() > 0)
                results.put(key,filmsResults);
        }

        return results;
    }

    public static HashMap<String, ArrayList<ResponseFilmDTO>> byFileName(String sentence, String name, String cinema, String date){
        HashMap<String, ArrayList<ResponseFilmDTO>> listHashMap = byFilmName(sentence, name, date);
        HashMap<String, ArrayList<ResponseFilmDTO>> results = new HashMap<>();
        for(String key : listHashMap.keySet()){
            if(key.toLowerCase().contains(cinema)){
                results.put(key,listHashMap.get(key));
            }
        }

        return results;
    }


    public static HashMap<String, ArrayList<ResponseFilmDTO>> byTimeScale(String sentence,String cinema, String date, String timeScale) {

        date = date.replaceAll("/","-");
        HashMap<String, ArrayList<ResponseFilmDTO>> stringArrayListHashMap = !cinema.equals("") ? byCinema(sentence, cinema, date) : byTime(sentence,date);
        HashMap<String, ArrayList<ResponseFilmDTO>> finalResults = new HashMap<>();
        for (String key : stringArrayListHashMap.keySet()) {
            ArrayList<ResponseFilmDTO> results = stringArrayListHashMap.get(key);
            ArrayList<ResponseFilmDTO> films = new ArrayList<>();
            for (ResponseFilmDTO responseFilmDTO : results) {
                for (String time : responseFilmDTO.getTimes()) {
                    if (TimeUtil.isWithinTimeScaleFilm(timeScale, time)) {

                        ResponseFilmDTO responseFilmDTO1 = new ResponseFilmDTO();
                        responseFilmDTO1.setName(responseFilmDTO.getName());
                        ArrayList<String> times = new ArrayList<>();
                        times.add(time);
                        responseFilmDTO1.setTimes(times);
                        responseFilmDTO1.setCinemaName(responseFilmDTO.getCinemaName());
                        responseFilmDTO1.setDate(responseFilmDTO.getDate());

                        films.add(responseFilmDTO1);
                        break;
                    }
                }
            }
            if(films.size() > 0)
                finalResults.put(key, films);
        }

        return finalResults;
    }

    /*
        List of cinema with schedule of film
     */
    public static HashMap<String, ArrayList<ResponseFilmDTO>> byCinema(String sentence, String cinema, String date) {
        date = date.replaceAll("/", "-");
        HashMap<String, ArrayList<ResponseFilmDTO>> filmOfCinema = new HashMap<String, ArrayList<ResponseFilmDTO>>();
        HashMap<String, ArrayList<ResponseFilmDTO>> cinemaWithFilm = byTime(sentence, date);
        for (String nameOfCinema : cinemaWithFilm.keySet()) {
            if (nameOfCinema.toLowerCase().contains(cinema.toLowerCase())) {
                filmOfCinema.put(nameOfCinema, cinemaWithFilm.get(nameOfCinema));
            }
        }
        return filmOfCinema;
    }

    public static HashMap<String, ArrayList<ResponseFilmDTO>> byTime(String sentence,String date) {
        date = date.replaceAll("/", "-");
        Document document = LoadData.getDocument(URLConstain.SEARCH_FIML + date);
        HashMap<String, ArrayList<ResponseFilmDTO>> cinemaWithFilm = new HashMap<String, ArrayList<ResponseFilmDTO>>();
        Elements listCinemas = document.select(".title-pre");
        Elements listFilms = document.select(".tbl-list");
        for (int i = 0; i < listCinemas.size(); i++) {
            String nameOfCinema = listCinemas.get(i).text().trim();
            Element listFilmElement = listFilms.get(i);
            Elements eleFilmWithTimes = listFilmElement.select("tr");
            ArrayList<ResponseFilmDTO> fimlWithTimes = new ArrayList<ResponseFilmDTO>();
            for (int j = 1; j < eleFilmWithTimes.size(); j++) {
                String name = eleFilmWithTimes.get(j).child(0).text().trim();
                String[] times = eleFilmWithTimes.get(j).child(1).text().trim().split(",");
                fimlWithTimes.add(new ResponseFilmDTO(nameOfCinema , name, new ArrayList<String>(Arrays.asList(times)), date));
            }
            cinemaWithFilm.put(nameOfCinema, fimlWithTimes);
        }

        String[] timeScale = TimeUtil.getTimeScale(sentence);

        if (timeScale[0].equals("00:00") && timeScale[1].equals("24:00"))
            return cinemaWithFilm;

        HashMap<String, ArrayList<ResponseFilmDTO>> result = new HashMap<>();
        for (String key : cinemaWithFilm.keySet()) {
            ArrayList<ResponseFilmDTO> responseFilmDTOs = cinemaWithFilm.get(key);
            ArrayList<ResponseFilmDTO> subResults = new ArrayList<>();
            for(ResponseFilmDTO responseFilmDTO : responseFilmDTOs){
                ResponseFilmDTO filmDTO = new ResponseFilmDTO();
                filmDTO.setDate(responseFilmDTO.getDate());
                filmDTO.setCinemaName(responseFilmDTO.getCinemaName());
                filmDTO.setName(responseFilmDTO.getName());
                if(TimeUtil.isRightNow(sentence)){
                    for (String time : responseFilmDTO.getTimes()){
                        if(TimeUtil.isWithinTimeScale(time,timeScale[0], 30)){
                            filmDTO.addTime(time);
                        }
                    }
                } else {
                    for (String time : responseFilmDTO.getTimes()){
                        if(TimeUtil.isWithinTimeScale(time.trim(), timeScale[0], timeScale[1])){
                            filmDTO.addTime(time);
                        }
                    }
                }
                if(filmDTO.getTimes().size() > 0)
                    subResults.add(filmDTO);
            }
            if(subResults.size() > 0){
                result.put(key, subResults);
            }
        }

        return result;
    }

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
        String date;
        Elements filmList = doc.select("div.main > div.content.tag > div.group > div.block-base.movie");
        for (Element film : filmList) {
            coverLink = film.select("a > img").first().attr("src");
            title = film.select("a.film-name").first().text();
            IMDbRate = film.select("div.movie-info.rate > div.imdb > strong").first().text();
            trailerLink = film.select("div.movie-info.rate > a.play-trailer").first().attr("href");
            filmDetailLink = "http://www.123phim.vn" + film.select("a.film-name").first().attr("href");
            date = film.select("span.publish-date-new > span.date").text() + film.select("span.publish-date-new > span.month").text();
            BasicFilmInfo basicFilmInfo = new BasicFilmInfo(title, coverLink, IMDbRate, trailerLink, filmDetailLink, date);
            films.add(basicFilmInfo);
        }
        return films;
    }

    public static List<BasicFilmInfo> parseCurrentFilmInfo(String url) {
        try {
            /*Document doc = Jsoup
                    .connect("http://www.123phim.vn/phim/sap-chieu/")
                    .timeout(10000)
                    .userAgent(USER_AGENT)
                    .cookie("location", "2").get();
            HashMap<String, String> location2ID = getLocation2IDMap(doc);*/
            String locationID = "1";
            String location = "Hà Nội";
            /*for (String key : location2ID.keySet())
                if (command.contains(key)) {
                    locationID = location2ID.get(key);
                    location = key;
                }*/
            Document doc = Jsoup
                    .connect(url)
                    .timeout(10000)
                    .userAgent(USER_AGENT)
                    .cookie("location", locationID).get();
            List<BasicFilmInfo> films = getFilmList(doc);
            for (BasicFilmInfo film : films) Log.d("VAV_Cinema", film.toString());
            return films;
        } catch (Exception e) {
            Log.d("VAV_Cinema", e.toString());
        }
        return new ArrayList<>();
    }

    public static List<BasicFilmInfo> filmUpcoming(String filmName){

        List<BasicFilmInfo> basicFilmInfos = SearchFilm.parseCurrentFilmInfo("http://www.123phim.vn/phim/sap-chieu/");
        List<BasicFilmInfo> result = new ArrayList<>();
        for(BasicFilmInfo basicFilmInfo : basicFilmInfos){
            if(basicFilmInfo.title.toLowerCase().contains(filmName.toLowerCase())){
                result.add(basicFilmInfo);
                result.add(basicFilmInfo);
                result.add(basicFilmInfo);
                result.add(basicFilmInfo);
            }
        }

        return result;
    }

}
