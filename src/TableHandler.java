import javax.swing.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class TableHandler {

	private final DateTimeFormatter format;
	private XMLHandler xml;
	private String destination;
	private ArrayList<Channel> allChannels;
	private ArrayList<Show> channelShows;
	private URL source;
	private int currentChannel;

	public TableHandler(URL source){
		xml = new XMLHandler();
	    destination = null;
		this.source = source;
		currentChannel = 0;
		format = DateTimeFormatter.ofPattern("HH:mm dd/MM");
	}

	/*public String[][] setDestination(String destination) {
		this.destination = destination;
		updateDestinationOffers();
		return makeTable(destinationChannels);
	}*/

	public void getChannels() {
	    if (xml.validate(source)){
	        allChannels = xml.update(source);
            System.err.println(allChannels.size());
        }
        System.err.println(allChannels.size());
    }

	public String[][] getNewChannelData(int type) {
	    if ((type == -1) && (currentChannel > 0)){
	        currentChannel--;
        } else if ((type == 1) && (currentChannel < allChannels.size())){
	        currentChannel++;
        }
        System.err.println(currentChannel + "   " + allChannels.size());
        String s =allChannels.get(currentChannel).getScheduleurl();
        URL channelSource = null;
        try {
            channelSource = new URL(s);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        if (xml.validate(source)){
            channelShows = xml.updateShows(channelSource);
			String[][] data;
			data = makeTable(channelShows);

			return data;
		}
		return null;
	}

	/*private void updateDestinationOffers() {
		for (Channel channel : allChannels){
			if (channel.getDestinationName().equals(destination) ||
					channel.getCityName().equals(destination)){
				destinationChannels.add(channel);
			}
		}
	}*/

	private String[][] makeTable(ArrayList<Show> shows) {
		String[][] data = new String[shows.size()][5];
		Show s;
		for (int i = 0; i < shows.size(); i++){
			s = shows.get(i);
			data[i][0] = s.getTitle();
			data[i][1] = s.getStarttimeutc().format(format);
			data[i][2] = s.getEndtimeutc().format(format);
            data[i][3] = s.getImageurl();
            data[i][4] = makeInfoText(s);
		}
		return data;
	}

	private String makeInfoText(Show show) {
		String info = show.getTitle();
		if (show.getSubtitle() != null){
		    info = info.concat(show.getSubtitle());
        }
		info = info.concat("\n" + show.getDescription());
		info = info.concat("\n Start: " + show.getStarttimeutc());
		info = info.concat("\nSlut: " + show.getEndtimeutc());
		return info;
	}


    public String getChannelImage() {
	    return allChannels.get(currentChannel).getImage();
    }
}
