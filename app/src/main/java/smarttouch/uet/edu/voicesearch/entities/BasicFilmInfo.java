package smarttouch.uet.edu.voicesearch.entities;

/**
 * Created by VanHop on 4/17/2016.
 */
public class BasicFilmInfo {
    public String id;
    public String title;
    public String coverLink;
    public String IMDbRate;
    public String trailerLink;
    public String filmDetailLink;
    public String date;
    public BasicFilmInfo(String title, String coverLink,String IMDbRate,String trailerLink,String filmDetailLink, String date){
        this.title = title;
        this.coverLink = coverLink;
        this.IMDbRate = IMDbRate;
        this.trailerLink = trailerLink;
        this.filmDetailLink = filmDetailLink;
        this.date = date;
    }

    @Override
    public String toString() {
        String str = "-----------------------\n"
                + "title: " + title + "\n"
                + "coverLink: " + coverLink + "\n"
                + "IMDbRate: "+IMDbRate +"\n"
                + "trailerLink:" + trailerLink +"\n";
        return str;
    }
}
