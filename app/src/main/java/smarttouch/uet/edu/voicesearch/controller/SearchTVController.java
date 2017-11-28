package smarttouch.uet.edu.voicesearch.controller;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

import jmdn.base.util.string.StrUtil;
import smarttouch.uet.edu.voicesearch.activity.MainActivity;
import smarttouch.uet.edu.voicesearch.entities.CustomObject;
import smarttouch.uet.edu.voicesearch.entities.Message;
import smarttouch.uet.edu.voicesearch.entities.ResponseTVDTO;
import smarttouch.uet.edu.voicesearch.function.SearchTV;
import smarttouch.uet.edu.voicesearch.util.ActionConstain;
import smarttouch.uet.edu.voicesearch.util.ArgumentUtil;
import smarttouch.uet.edu.voicesearch.util.DatetimeUtil;
import smarttouch.uet.edu.voicesearch.util.ResponseType;
import smarttouch.uet.edu.voicesearch.util.TimeUtil;

/**
 * Created by VanHop on 4/14/2016.
 */
public class SearchTVController {

    public static CustomObject execute(String sentence, List<String> argument, String action) {
        List<String> tokens = StrUtil.tokenizeString(sentence);
        boolean hasChannel = false;
        boolean hasDateTime = false;
        boolean hasTitle = false;
        switch (action) {
            case ActionConstain.TV.SCHEDULE_CHANNEL:
                // channel
                hasChannel = ArgumentUtil.hasChannel(argument);
                hasDateTime = ArgumentUtil.hasDateTime(argument);
                hasTitle = ArgumentUtil.hasTitle(argument);
                MainActivity.isWithinDialog = false;
                if (hasChannel) {

                    if (hasDateTime && !hasTitle) {
                        List<String> times = DatetimeUtil.getTime(ArgumentUtil.getArgument(argument, "-datetime", tokens), DatetimeUtil.isNoon(ArgumentUtil.getArgument(argument, "-datetime", tokens)), DatetimeUtil.isNight(ArgumentUtil.getArgument(argument, "-datetime", tokens)));
                        if (times.size() == 2) {
                            ArrayList<ResponseTVDTO> programByTimeScaleOfChannel = SearchTV.getProgramByTimeScaleOfChannel(ArgumentUtil.getArgument(argument, "-aname", tokens), StringUtils.leftPad(times.get(0), 2, "0") + ":" + StringUtils.leftPad(times.get(1), 2, "0"), DatetimeUtil.convertDate2Str(DatetimeUtil.getDate(ArgumentUtil.getArgument(argument, "-datetime", tokens))));
                            if (programByTimeScaleOfChannel.size() > 0)
                                return new CustomObject(ResponseType.RESPONSE_TV, programByTimeScaleOfChannel);

                            return new CustomObject(ResponseType.MESSAGE, new Message("BOT", "Không tìm được dữ liệu lúc " + (StringUtils.leftPad(times.get(0), 2, "0") + ":" + StringUtils.leftPad(times.get(1), 2, "0")) + " ngày " + DatetimeUtil.convertDate2Str(DatetimeUtil.getDate(ArgumentUtil.getArgument(argument, "-datetime", tokens))) + " trên " + ArgumentUtil.getArgument(argument, "-aname", tokens), false));
                        }
                    }

                    String strTime = ArgumentUtil.getArgument(argument, "-datetime", tokens);
                    String strChannel = ArgumentUtil.getArgument(argument, "-aname", tokens).replace("kênh", "").trim();

                    ArrayList<ResponseTVDTO> scheduleTV = SearchTV.getScheduleTV(sentence, strChannel.toLowerCase(), DatetimeUtil.convertDate2Str(DatetimeUtil.getDate(strTime)));
                    MainActivity.isWithinDialog = false;
                    if (scheduleTV.size() > 0)
                        return new CustomObject(ResponseType.RESPONSE_TV, scheduleTV);
                    if (sentence.contains("phim")) {
                        if (TimeUtil.isRightNow(sentence))
                            return new CustomObject(ResponseType.MESSAGE, new Message("BOT", strChannel + " đang không chiếu phim gì, có thể hiện tại đang chiếu 1 chương trình không phải phim", false));
                        return new CustomObject(ResponseType.MESSAGE, new Message("BOT", "Không tìm được phim nào", false));
                    }
                    return new CustomObject(ResponseType.MESSAGE, new Message("BOT", "Không tìm được dữ liệu phù hợp", false));
                } else {
                    MainActivity.isWithinDialog = true;
                    MainActivity.leakArgument = "aname";
                    return new CustomObject(ResponseType.MESSAGE, new Message("BOT", "Bạn muốn tìm kênh trên kênh nào", false));
                }

            case ActionConstain.TV.SCHEDULE_PROGRAM:

                hasDateTime = ArgumentUtil.hasDateTime(argument);
                hasChannel = ArgumentUtil.hasChannel(argument);
                ArrayList<ResponseTVDTO> scheduleFromProgram = null;
                MainActivity.isWithinDialog = false;
                if (hasChannel) {
                    if (hasDateTime)
                        scheduleFromProgram = SearchTV.getScheduleFromProgram(ArgumentUtil.getArgument(argument, "-title", tokens).replace("phim truyện", "").replace("phim", "").trim(), ArgumentUtil.getArgument(argument, "-aname", tokens), DatetimeUtil.convertDate2Str(DatetimeUtil.getDate(ArgumentUtil.getArgument(argument, "-datetime", tokens))));
                    else
                        scheduleFromProgram = SearchTV.getScheduleFromProgramAndChannel(ArgumentUtil.getArgument(argument, "-title", tokens).replace("phim truyện", "").replace("phim", "").trim(), ArgumentUtil.getArgument(argument, "-aname", tokens));
                } else {
                    if (hasDateTime) {
                        scheduleFromProgram = SearchTV.getScheduleFromProgram(ArgumentUtil.getArgument(argument, "-title", tokens).replace("phim truyện", "").replace("phim", "").trim(), DatetimeUtil.convertDate2Str(DatetimeUtil.getDate(ArgumentUtil.getArgument(argument, "-datetime", tokens))));
                        if (scheduleFromProgram == null) {
                            MainActivity.isWithinDialog = true;
                            MainActivity.leakArgument = "aname";
                            return new CustomObject(ResponseType.MESSAGE, new Message("BOT", "Bạn muốn hỏi ở kênh nào?", false));
                        }
                    } else {
                        scheduleFromProgram = SearchTV.getScheduleFromProgram(ArgumentUtil.getArgument(argument, "-title", tokens));
                        if (scheduleFromProgram == null) {
                            MainActivity.isWithinDialog = true;
                            MainActivity.leakArgument = "aname";
                            return new CustomObject(ResponseType.MESSAGE, new Message("BOT", "Bạn muốn hỏi ở kênh nào?", false));
                        }
                    }
                }

                if (scheduleFromProgram.size() == 0) {
                    return new CustomObject(ResponseType.MESSAGE, new Message("BOT", "Không tìm được dữ liệu", false));
                } else {
                    return new CustomObject(ResponseType.RESPONSE_TV, scheduleFromProgram);
                }

            default:
                return new CustomObject(ResponseType.MESSAGE, new Message("BOT", "Mình không hiểu ý bạn", false));
        }

    }


}
