import java.net.URL;
import java.util.ArrayList;

public class TableHandler {

    private XMLHandler xml;
    private String destination;
    private ArrayList<Offer> allOffers;
    private ArrayList<Offer> destinationOffers;
    private URL source;

    public TableHandler(URL source){
        xml = new XMLHandler();
        destination = null;
        this.source = source;
    }

    public String[][] setDestination(String destination) {
        this.destination = destination;
        updateDestinationOffers();
        return makeTable(destinationOffers);
    }

    public String[][] getNewTableData() {
        if (xml.validate(source)){
            allOffers = xml.update(source);
            String[][] data;
            if (destination == null){
                data = makeTable(allOffers);
            } else {
                updateDestinationOffers();
                data = makeTable(destinationOffers);
            }
            return data;
        }
        return null;
    }

    private void updateDestinationOffers() {
        for (Offer offer : allOffers){
            if (offer.getDestinationName().equals(destination) ||
                    offer.getCityName().equals(destination)){
                destinationOffers.add(offer);
            }
        }
    }

    private String[][] makeTable(ArrayList<Offer> offers) {
        String[][] data = new String[offers.size()][5];
        for (int i = 0; i < offers.size(); i++){
            data[i][0] = offers.get(i).getDestinationName();
            data[i][1] = offers.get(i).getOutDate();
            data[i][2] = offers.get(i).getCurrentPrice();
            data[i][3] = offers.get(i).getHotelImage();
            data[i][4] = makeInfoText(offers.get(i));
        }
        return data;
    }

    private String makeInfoText(Offer offer) {
        String info = new String(offer.getCampaignName() + "\nAvresa: ");
        info = info.concat(offer.getDepartureName() + "\nDestination: ");
        info = info.concat(offer.getCityName() + ", " + offer.getDestinationName());
        info = info.concat("\nAvresedatum: " + offer.getOutDate() + "\nHotell: ");
        info = info.concat(offer.getHotelName() + "(" + offer.getHotelGrade());
        info = info.concat("stj�rnor)\nRum: " + offer.getRoomDescription());
        info = info.concat("\nPris: " + offer.getCurrentPrice());
        return info;
    }



}
