package smarttouch.uet.edu.voicesearch.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Created by VanHop on 4/4/2016.
 */
public class LoadData {
    public static String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.109 Safari/537.36";
    public static Document getDocument(String url){

        try {
            return Jsoup.connect(url).userAgent(USER_AGENT).timeout(5000).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;

    }

}
