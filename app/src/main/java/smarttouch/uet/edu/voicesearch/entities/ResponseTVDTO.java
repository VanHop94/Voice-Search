package smarttouch.uet.edu.voicesearch.entities;

/**
 * Created by VanHop on 4/5/2016.
 */
public class ResponseTVDTO {

    private String channelName;
    private String startingTime;
    private String finshingTime;
    private String name;
    private String date;

    public ResponseTVDTO() {
    }

    public ResponseTVDTO(String startingTime, String finshingTime, String name) {
        this.startingTime = startingTime;
        this.finshingTime = finshingTime;
        this.name = name;
    }

    public ResponseTVDTO(String channelName, String startingTime, String finshingTime, String name) {
        this.channelName = channelName;
        this.startingTime = startingTime;
        this.finshingTime = finshingTime;
        this.name = name;
        date = "unknown";
    }

    public ResponseTVDTO(String channelName, String date, String startingTime, String finshingTime, String name) {
        this.channelName = channelName;
        this.startingTime = startingTime;
        this.finshingTime = finshingTime;
        this.name = name;
        this.date = date;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(String startingTime) {
        this.startingTime = startingTime;
    }

    public String getFinshingTime() {
        return finshingTime;
    }

    public void setFinshingTime(String finshingTime) {
        this.finshingTime = finshingTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
