package smarttouch.uet.edu.voicesearch.entities;

import java.util.ArrayList;

/**
 * Created by VanHop on 4/4/2016.
 */
public class ResponseFilmDTO {

    private String date;
    private String cinemaName;
    private String name;
    private ArrayList<String> times;

    public ResponseFilmDTO() {
        name = "";
        times = new ArrayList<String>();
    }

    public ResponseFilmDTO(String name, ArrayList<String> times) {
        this.name = name;
        this.times = times;
    }

    public ResponseFilmDTO(String cinemaName, String name, ArrayList<String> times) {
        this.cinemaName = cinemaName;
        this.name = name;
        this.times = times;
    }

    public ResponseFilmDTO(String cinemaName, String name, ArrayList<String> times, String date) {
        this.cinemaName = cinemaName;
        this.name = name;
        this.times = times;
        this.date = date;
    }

    public void addTime(String time){
        times.add(time);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getTimes() {
        return times;
    }

    public void setTimes(ArrayList<String> times) {
        this.times = times;
    }
}
