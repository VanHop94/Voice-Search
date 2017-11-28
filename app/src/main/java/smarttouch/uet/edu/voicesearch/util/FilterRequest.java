package smarttouch.uet.edu.voicesearch.util;

import java.util.List;

/**
 * Created by VanHop on 4/7/2016.
 */
public class FilterRequest {

    public static boolean hasTimeScale(String sentence){

        List<String> time = DatetimeUtil.getTime("7 giờ kém 5", DatetimeUtil.isNoon(sentence), DatetimeUtil.isNight(sentence));

        return time.size() > 0 ? true : false;
    }

}
