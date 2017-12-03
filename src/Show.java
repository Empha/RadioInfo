/*
 * Applikationsutveckling i Java
 * HT17
 * Obligatorisk uppgift 2
 * RadioInfo

 * File:    Show.java
 * Author:  Emil Lindqvist
 * User:    c13elt
 * Date:    3 December 2017
 */
import java.time.ZonedDateTime;

public class Show {

    private int episodeid;
    private String title;
    private String subtitle;
    private String description;
    private ZonedDateTime starttimeutc;
    private ZonedDateTime endtimeutc;
    private String imageurl;
    private String imageurltemplate;

    public int getEpisodeid() {
        return episodeid;
    }

    public void setEpisodeid(int episodeid) {
        this.episodeid = episodeid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ZonedDateTime getStarttimeutc() {
        return starttimeutc;
    }

    public void setStarttimeutc(ZonedDateTime starttimeutc) {
        this.starttimeutc = starttimeutc;
    }

    public ZonedDateTime getEndtimeutc() {
        return endtimeutc;
    }

    public void setEndtimeutc(ZonedDateTime endtimeutc) {
        this.endtimeutc = endtimeutc;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getImageurltemplate() {
        return imageurltemplate;
    }

    public void setImageurltemplate(String imageurltemplate) {
        this.imageurltemplate = imageurltemplate;
    }
}
