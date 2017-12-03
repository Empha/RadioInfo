/*
 * Applikationsutveckling i Java
 * HT17
 * Obligatorisk uppgift 2
 * RadioInfo

 * File:    Channel.java
 * Author:  Emil Lindqvist
 * User:    c13elt
 * Date:    3 December 2017
 */
public class Channel {

    private String name;
	private String image;
	private String imagetemplate;
	private String color;
	private String siteurl;
	private String url;
	private String statkey;
	private String scheduleurl;
	private String channeltype;
	private String xmltvid;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImagetemplate() {
        return imagetemplate;
    }

    public void setImagetemplate(String imagetemplate) {
        this.imagetemplate = imagetemplate;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSiteurl() {
        return siteurl;
    }

    public void setSiteurl(String siteurl) {
        this.siteurl = siteurl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStatkey() {
        return statkey;
    }

    public void setStatkey(String statkey) {
        this.statkey = statkey;
    }

    public String getScheduleurl() {
        return scheduleurl;
    }

    public void setScheduleurl(String scheduleurl) {
        this.scheduleurl = scheduleurl;
    }

    public String getChanneltype() {
        return channeltype;
    }

    public void setChanneltype(String channeltype) {
        this.channeltype = channeltype;
    }

    public String getXmltvid() {
        return xmltvid;
    }

    public void setXmltvid(String xmltvid) {
        this.xmltvid = xmltvid;
    }
}
