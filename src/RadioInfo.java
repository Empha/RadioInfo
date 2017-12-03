/*
 * Applikationsutveckling i Java
 * HT17
 * Obligatorisk uppgift 2
 * RadioInfo

 * File:    RadioInfo.java
 * Author:  Emil Lindqvist
 * User:    c13elt
 * Date:    3 December 2017
 */
import java.net.MalformedURLException;
import java.net.URL;


public class RadioInfo {

    /**
     * @param args
     */
    public static void main(String[] args) {
        URL source = null;
        try {
            source = new URL(
                    "http://api.sr.se/api/v2/channels/");
        } catch (MalformedURLException e) {
            System.err.println("The update URL is not valid.");
            System.exit(0);
        }
        MyGui gui = new MyGui(source);
        gui.show();

    }

}