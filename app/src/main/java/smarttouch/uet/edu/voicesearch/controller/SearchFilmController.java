package smarttouch.uet.edu.voicesearch.controller;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jmdn.base.util.string.StrUtil;
import smarttouch.uet.edu.voicesearch.activity.MainActivity;
import smarttouch.uet.edu.voicesearch.entities.CustomObject;
import smarttouch.uet.edu.voicesearch.entities.Message;
import smarttouch.uet.edu.voicesearch.entities.ResponseFilmDTO;
import smarttouch.uet.edu.voicesearch.function.SearchFilm;
import smarttouch.uet.edu.voicesearch.util.ActionConstain;
import smarttouch.uet.edu.voicesearch.util.ArgumentUtil;
import smarttouch.uet.edu.voicesearch.util.DatetimeUtil;
import smarttouch.uet.edu.voicesearch.util.ResponseType;
import smarttouch.uet.edu.voicesearch.util.TimeUtil;

/**
 * Created by VanHop on 4/7/2016.
 */
public class SearchFilmController {

    public static CustomObject execute(String sentence, List<String> argument, String action) {
        List<String> tokens = StrUtil.tokenizeString(sentence);
        boolean hasCiname = false;
        boolean hasDateTime = false;
        boolean hasTitle = false;
        switch (action) {
            case ActionConstain.FILM.SEARCH_BY_CINNEMA:

                HashMap<String, ArrayList<ResponseFilmDTO>> listHashMap = null;
                List<String> times = DatetimeUtil.getTime(ArgumentUtil.getArgument(argument, "-datetime", tokens), DatetimeUtil.isNoon(ArgumentUtil.getArgument(argument, "-datetime", tokens)), DatetimeUtil.isNight(ArgumentUtil.getArgument(argument, "-datetime", tokens)));
                if (times.size() == 2) {
                    listHashMap = SearchFilm.byTimeScale(sentence, ArgumentUtil.getArgument(argument, "-aname", tokens).replace("rạp", "").replace("ở", ""), DatetimeUtil.convertDate2Str(DatetimeUtil.getDate(ArgumentUtil.getArgument(argument, "-datetime", tokens))), StringUtils.leftPad(times.get(0), 2, "0") + ":" + StringUtils.leftPad(times.get(1), 2, "0"));
                    if (listHashMap.size() == 0)
                        return new CustomObject(ResponseType.MESSAGE, new Message("BOT", "Không có dữ liệu phim của " + ArgumentUtil.getArgument(argument, "-aname", tokens) + " vào " + times.get(0) + ":" + times.get(1) + " ngày " + DatetimeUtil.convertDate2Str(DatetimeUtil.getDate(ArgumentUtil.getArgument(argument, "-datetime", tokens))), false));
                } else {
                    listHashMap = SearchFilm.byCinema(sentence, ArgumentUtil.getArgument(argument, "-aname", tokens).replace("rạp", "").replace("ở", ""), DatetimeUtil.convertDate2Str(DatetimeUtil.getDate(ArgumentUtil.getArgument(argument, "-datetime", tokens))));
                    if (listHashMap.size() == 0) {
                        if (TimeUtil.isRightNow(sentence)) {
                            String[] timeScale = TimeUtil.getTimeScale(sentence);
                            return new CustomObject(ResponseType.MESSAGE, new Message("BOT", "Không có dữ liệu phim của " + ArgumentUtil.getArgument(argument, "-aname", tokens) + " vào " + timeScale[0] + " ngày " + DatetimeUtil.convertDate2Str(DatetimeUtil.getDate(ArgumentUtil.getArgument(argument, "-datetime", tokens))), false));
                        }
                        return new CustomObject(ResponseType.MESSAGE, new Message("BOT", "Không có dữ liệu phim của " + ArgumentUtil.getArgument(argument, "-aname", tokens) + " ngày " + DatetimeUtil.convertDate2Str(DatetimeUtil.getDate(ArgumentUtil.getArgument(argument, "-datetime", tokens))), false));
                    }
                }
                return new CustomObject(ResponseType.RESPONSE_FILM, listHashMap);

            case ActionConstain.FILM.SEARCH_BY_FILM:
                HashMap<String, ArrayList<ResponseFilmDTO>> resultByFilm;
                hasCiname = ArgumentUtil.hasCinema(argument);
                if(!hasCiname)
                    resultByFilm = SearchFilm.byFilmName(sentence, ArgumentUtil.getArgument(argument, "-title", tokens).replace("phim", ""), DatetimeUtil.convertDate2Str(DatetimeUtil.getDate(ArgumentUtil.getArgument(argument, "-datetime", tokens))));
                else resultByFilm = SearchFilm.byFileName(sentence, ArgumentUtil.getArgument(argument,"-title",tokens).replace("phim", ""), ArgumentUtil.getArgument(argument,"-aname",tokens), DatetimeUtil.convertDate2Str(DatetimeUtil.getDate(ArgumentUtil.getArgument(argument, "-datetime", tokens))));
                if (resultByFilm.size() == 0) {
                    return new CustomObject(ResponseType.MESSAGE, new Message("BOT", "Không có dữ liệu của " + ArgumentUtil.getArgument(argument, "-title", tokens).replace("phim", "") + " ngày " + DatetimeUtil.convertDate2Str(DatetimeUtil.getDate(ArgumentUtil.getArgument(argument, "-datetime", tokens))), false));
                }
                return new CustomObject(ResponseType.RESPONSE_FILM, resultByFilm);

            case ActionConstain.FILM.UPCOMING_FILM:

                if(ArgumentUtil.hasTitle(argument)){
                    return new CustomObject(ResponseType.RESPONSE_FILM_DETAIL, SearchFilm.filmUpcoming(ArgumentUtil.getArgument(argument,"-title",tokens)));
                } else
                    return new CustomObject(ResponseType.RESPONSE_FILM_DETAIL, SearchFilm.parseCurrentFilmInfo("http://www.123phim.vn/phim/sap-chieu/"));
            case ActionConstain.FILM.RUNNING_FILM:
                return new CustomObject(ResponseType.RESPONSE_FILM_DETAIL, SearchFilm.parseCurrentFilmInfo("http://www.123phim.vn/phim/"));
            default:
                return new CustomObject(ResponseType.MESSAGE, new Message("BOT", "Mình không hiểu ý bạn", false));
        }
    } //ic_launcher

}
