package smarttouch.uet.edu.voicesearch.function;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Set;

import smarttouch.uet.edu.voicesearch.entities.ResponseTVDTO;
import smarttouch.uet.edu.voicesearch.util.DatetimeUtil;
import smarttouch.uet.edu.voicesearch.util.LoadData;
import smarttouch.uet.edu.voicesearch.util.TimeUtil;

/**
 * Created by VanHop on 4/5/2016.
 */
public class SearchTV {

    private static final String URL = "http://cdn.xemtiviso.com/lps/lichphatsong.php?dateSchedule=module%2Fajax%2Fajax_get_schedule.php%3FchannelId%3D(#1)%26dateSchedule%3D(#2)%2F(#3)%2F(#4)";
    private static HashMap<String, Integer> channelName2ChannelId = new HashMap<String, Integer>();
    private static final String DEFAULT_MESSAGE_ERROR = "Không tìm được dữ liệu";

    static {
        channelName2ChannelId.put("vtv1", 1);
        channelName2ChannelId.put("vtv2", 59);
        channelName2ChannelId.put("vtv3", 2); //vtv3
        channelName2ChannelId.put("vtv4", 60);
        channelName2ChannelId.put("vtv5", 53);
        channelName2ChannelId.put("vtv6", 52);
        channelName2ChannelId.put("vtv1 hd", 182);
        channelName2ChannelId.put("vtv3 hd", 183);
        channelName2ChannelId.put("vtv6 hd", 184);
        channelName2ChannelId.put("hà nội 1", 3);
        channelName2ChannelId.put("truyền hình hà nội", 3);
        channelName2ChannelId.put("truyền hình hà nội một", 3);
        channelName2ChannelId.put("truyền hình hà nội 1", 3);
        channelName2ChannelId.put("hà nội 2", 82);
        channelName2ChannelId.put("hà tây", 82);
        channelName2ChannelId.put("truyền hình hà nội hai", 3);
        channelName2ChannelId.put("truyền hình hà nội 2", 3);
        channelName2ChannelId.put("vĩnh phúc", 81);
        channelName2ChannelId.put("ftv", 8);
        channelName2ChannelId.put("hbo", 9);
        channelName2ChannelId.put("star movie", 11);
        channelName2ChannelId.put("starmovie", 11);
        channelName2ChannelId.put("starmovies", 11);
        channelName2ChannelId.put("vtc1", 18);
        channelName2ChannelId.put("vtc2", 107);
        channelName2ChannelId.put("vtc3", 58);
        channelName2ChannelId.put("vtc6", 106);
        channelName2ChannelId.put("vtc9", 140);
        channelName2ChannelId.put("net việt", 140);
        channelName2ChannelId.put("let việt", 140);
        channelName2ChannelId.put("vtc12", 103);
        channelName2ChannelId.put("vtc14", 141);
        channelName2ChannelId.put("vtc16", 148);
        channelName2ChannelId.put("vtc hd1", 168);
        channelName2ChannelId.put("vtc hd2", 169);
        channelName2ChannelId.put("vtc hd3", 170);
        channelName2ChannelId.put("vtc3 hd", 172);
        channelName2ChannelId.put("htv1", 54);
        channelName2ChannelId.put("htv2", 55);
        channelName2ChannelId.put("htv3", 56);
        channelName2ChannelId.put("htv4", 142);
        channelName2ChannelId.put("htv7", 19);
        channelName2ChannelId.put("htv9", 20);
        channelName2ChannelId.put("htv2 hd", 165);
        channelName2ChannelId.put("htv7 hd", 163);
        channelName2ChannelId.put("htv9 hd", 164);
        channelName2ChannelId.put("htvc phụ nữ", 32);
        channelName2ChannelId.put("htvc thể thao", 143);
        channelName2ChannelId.put("htvc thuần việt", 144);
        channelName2ChannelId.put("htvc gia đình", 133);
        channelName2ChannelId.put("htvc phim", 5);
        channelName2ChannelId.put("htvc ca nhạc", 6);
        channelName2ChannelId.put("phú yên", 100);
        channelName2ChannelId.put("discovery", 45);
        channelName2ChannelId.put("vtv đà nẵng", 101);
        channelName2ChannelId.put("mtv", 108);
        channelName2ChannelId.put("mtv hd", 123);
        channelName2ChannelId.put("start world hd", 120);
        channelName2ChannelId.put("start movies hd", 121);
        channelName2ChannelId.put("start movie hd", 121);
        channelName2ChannelId.put("antv", 138);
        channelName2ChannelId.put("công an nhân dân", 138);
        channelName2ChannelId.put("today", 139);
        channelName2ChannelId.put("today tv", 139);
        channelName2ChannelId.put("vtc7", 139);
        channelName2ChannelId.put("bắc ninh", 145);
        channelName2ChannelId.put("itv", 147);
        channelName2ChannelId.put("k cộng 1", 173);
        channelName2ChannelId.put("k+1", 173);
        channelName2ChannelId.put("k cộng một", 173);
        channelName2ChannelId.put("k cộng nhịp sống", 174);
        channelName2ChannelId.put("k+ nhịp sống", 174);
        channelName2ChannelId.put("k cộng phái mạnh", 175);
        channelName2ChannelId.put("k+ phái mạnh", 175);
    }

    public static ArrayList<ResponseTVDTO> getScheduleTV(String channelName, String date) {
        ArrayList<ResponseTVDTO> scheduleTVs = new ArrayList<>();
        try {
            Integer channelId = channelName2ChannelId.get(channelName);
            if (channelId == null)
                return scheduleTVs;

            String url = URL.replace("(#1)", String.valueOf(channelId)).replace("(#2)", date.split("/")[0]).replace("(#3)", date.split("/")[1]).replace("(#4)", String.valueOf(Integer.parseInt(date.split("/")[2])));

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(5000);
            con.setRequestProperty("User-Agent", LoadData.USER_AGENT);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            Document document = Jsoup.parse(StringEscapeUtils.unescapeJava(response.toString()));
            Elements elements = document.select("p");

            for (Element scheduleTV : elements) {
                String time = scheduleTV.select("strong").text();
                String name = scheduleTV.text().replace(time, "");
                scheduleTVs.add(new ResponseTVDTO(channelName.toUpperCase(), date, time, "", name));
            }

            for (int i = 0; i < scheduleTVs.size() - 1; i++)
                scheduleTVs.get(i).setFinshingTime(scheduleTVs.get(i + 1).getStartingTime());
            scheduleTVs.get(scheduleTVs.size() - 1).setFinshingTime("24:00");
            return scheduleTVs;

        } catch (Exception e) {
            return scheduleTVs;
        }

    }

    public static ArrayList<ResponseTVDTO> getScheduleByProgramNameOfChannel(String sentence, String channelName, String programName, String date) {
        ArrayList<ResponseTVDTO> scheduleTV = getScheduleTV(channelName, date);
        ArrayList<ResponseTVDTO> results = new ArrayList<>();
        if (scheduleTV == null)
            return results;
        for (ResponseTVDTO responseTVDTO : scheduleTV) {
            if (responseTVDTO.getName().toLowerCase().contains(programName.toLowerCase()))
                results.add(responseTVDTO);
        }
        if (results.size() == 0)
            return results;

        String[] timseScale = TimeUtil.getTimeScale(sentence);

        ArrayList<ResponseTVDTO> subResults = new ArrayList<>();
        for (ResponseTVDTO responseTVDTO : results) {
            if (TimeUtil.isWithinTimeScale(responseTVDTO.getStartingTime(), timseScale[0], timseScale[1]))
                subResults.add(responseTVDTO);
        }

        return subResults;
    }

    public static ArrayList<ResponseTVDTO> getScheduleTV(String sentence, String channelName, String date) {

        ArrayList<ResponseTVDTO> scheduleTV = getScheduleTV(channelName, date);

        String[] timseScale = TimeUtil.getTimeScale(sentence);
        ArrayList<ResponseTVDTO> subResults = new ArrayList<>();
        for (ResponseTVDTO responseTVDTO : scheduleTV) {
            if (timseScale[0].equals(timseScale[1])) {
                if (TimeUtil.isWithinTimeScaleTV(timseScale[0], responseTVDTO.getStartingTime(), responseTVDTO.getFinshingTime())) {
                    subResults.add(responseTVDTO);
                } else {
                    continue;
                }
            } else if (TimeUtil.isWithinTimeScale(responseTVDTO.getStartingTime(), timseScale[0], timseScale[1]))
                subResults.add(responseTVDTO);
        }

        if (sentence.contains("phim")) {
            ArrayList<ResponseTVDTO> filmResults = new ArrayList<>();
            for (ResponseTVDTO responseTVDTO : subResults) {
                if (responseTVDTO.getName().toLowerCase().contains("phim"))
                    filmResults.add(responseTVDTO);
            }
            return filmResults;
        }

        return subResults;
    }

    //kenh nao chieu chuong trinh ai la trieu phu
    public String getChannelByProgram(String program) {
        return getChannelNameFromProgram(program);
    }

    //thời gian phát sóng chương trình lục lạc vàng
    public static ArrayList<ResponseTVDTO> getScheduleFromProgram(String program, String date) {

        String channelName = getChannelNameFromProgram(program);
        if (channelName.equals("unknown"))
            return null;


        return getScheduleFromProgram(program, channelName, date);
    }

    public static ArrayList<ResponseTVDTO> getScheduleFromProgramAndChannel(String program, String channelName) {

        GregorianCalendar date = DatetimeUtil.getDate("");
        ArrayList<ResponseTVDTO> results = new ArrayList<>();
        String today = DatetimeUtil.convertDate2Str(date);
        for (int i = 0; i < 2; i++) {
            ArrayList<ResponseTVDTO> scheduleFromProgram = getScheduleFromProgram(program, channelName, today);
            if (scheduleFromProgram.size() > 0) {
                for (ResponseTVDTO rp : scheduleFromProgram)
                    results.add(rp);
            }
            today = TimeUtil.nextDateTV(today);
            if (results.size() > 0)
                return results;
        }

        if (results.size() > 0)
            return results;

        String today2 = TimeUtil.lateDateTV(DatetimeUtil.convertDate2Str(date));
        for (int i = 0; i < 5; i++) {
            ArrayList<ResponseTVDTO> scheduleFromProgram = getScheduleFromProgram(program, channelName, today2);
            if (scheduleFromProgram.size() > 0) {
                for (ResponseTVDTO rp : scheduleFromProgram)
                    results.add(rp);
            }
            today2 = TimeUtil.lateDateTV(today2);
        }
        return results;

    }

    //lich phat song chuong trinh luc lac vang
    public static ArrayList<ResponseTVDTO> getScheduleFromProgram(String program) {
        String channelName = getChannelNameFromProgram(program);
        if (channelName.equals("unknown"))
            return null;
        return getScheduleFromProgramAndChannel(program, channelName);
    }

    //mấy giờ hôm nay ai là triệu phú được chiếu
    public static ArrayList<ResponseTVDTO> getScheduleFromProgram(String program, String channelName, String date) {
        ArrayList<ResponseTVDTO> scheduleTV = getScheduleTV(channelName, date);
        ArrayList<ResponseTVDTO> responseTVDTOs = new ArrayList<>();
        for (ResponseTVDTO responseTVDTO : scheduleTV) {
            if (responseTVDTO.getName().toLowerCase().contains(program))
                responseTVDTOs.add(responseTVDTO);
        }

        return responseTVDTOs;
    }

    public static ArrayList<ResponseTVDTO> getProgramByTimeScaleByProgram(String program, String time, String date) {

        String channelName = getChannelNameFromProgram(program);
        ArrayList<ResponseTVDTO> scheduleTV = getScheduleTV(channelName, date);
        if (scheduleTV == null)
            return null;

        for (int i = 0; i < scheduleTV.size(); i++) {
            if (TimeUtil.isWithinTimeScale(time, scheduleTV.get(i).getStartingTime(), scheduleTV.get(i).getFinshingTime())) {
                return new ArrayList<>(Arrays.asList(scheduleTV.get(i)));
            }
        }
        return new ArrayList<>();
    }

    public static ArrayList<ResponseTVDTO> getProgramByTimeScaleByProgram(String program, String channelName, String time, String date) {

        ArrayList<ResponseTVDTO> scheduleTV = getScheduleTV(channelName, date);
        if (scheduleTV == null)
            return new ArrayList<>();

        for (int i = 0; i < scheduleTV.size(); i++) {
            if (TimeUtil.isWithinTimeScale(time, scheduleTV.get(i).getStartingTime(), scheduleTV.get(i).getFinshingTime())) {
                return new ArrayList<>(Arrays.asList(scheduleTV.get(i)));
            }
        }
        return new ArrayList<>();
    }

    public static ArrayList<ResponseTVDTO> getProgramByTimeScaleOfChannel(String channelName, String time, String date) {

        ArrayList<ResponseTVDTO> scheduleTV = getScheduleTV(channelName, date);
        if (scheduleTV == null)
            return new ArrayList<>();

        for (int i = 0; i < scheduleTV.size(); i++) {
            if (TimeUtil.isWithinTimeScale(time, scheduleTV.get(i).getStartingTime(), scheduleTV.get(i).getFinshingTime())) {
                return new ArrayList<>(Arrays.asList(scheduleTV.get(i)));
            }
        }
        return new ArrayList<>();
    }

    public static String getChannelNameFromProgram(String program) {
        String url = "http://www.google.com/search?q=kênh+phát+sóng+" + program;
        String channelName = "unknown";
        Set<String> strings = channelName2ChannelId.keySet();

        try {
            Document doc = Jsoup.connect(url).timeout(5000).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.110 Safari/537.36").get();
            Element element = doc.select("#ires").first();
            Elements results = element.select(".g");

            for (int i = 0; i < 3; i++) {
                try {
                    String header = results.get(i).select(".r").first().text().toLowerCase();
                    String body = results.get(i).select(".s").first().text().toLowerCase();
                    header = header + " " + body;

                    for (String key : strings) {
                        if (header.contains(key))
                            return key;
                    }
                } catch (Exception e) {

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return channelName;
    }

}
