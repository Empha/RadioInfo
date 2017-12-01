/* Please note that this is NOT a fully working program, and requires a bit more work.*/

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