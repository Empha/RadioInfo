import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class XMLHandler {

    public boolean validate(URL source) {
        try {
            //Source xmlFile = new StreamSource(source.openStream());
            Source xmlFile = new StreamSource(new FileInputStream("channels.xml"));
            SchemaFactory schemaFactory = SchemaFactory.newInstance(
                    XMLConstants.W3C_XML_SCHEMA_NS_URI);
            FileInputStream xsdStream = new FileInputStream("channelSchema.xsd");
            Source offerSchema = new StreamSource(xsdStream);
            Schema schema = schemaFactory.newSchema(offerSchema);
            schema.newValidator().validate(xmlFile);
            xsdStream.close();
        } catch (SAXException |IOException e) {
            System.err.print("Could not validate the given xml file." + e.getClass().getName() + e.getMessage());
            return false;
        }
        return true;
    }

    public ArrayList<Offer> update(URL source) {
        ArrayList<Offer> offers = new ArrayList<Offer>();
        Offer offer;

//		InputStream xmlFile = null;
//		try {
//			xmlFile = source.openStream();
//		} catch (IOException e1) {
//			System.err.println("There was a problem with the update URL.");
//			e1.printStackTrace();
//		}
        InputStream xmlFile = null;
        try {
            xmlFile = new FileInputStream("channels.xml");
        } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        Document doc = null;
        try {
            doc = dbf.newDocumentBuilder().parse(xmlFile);
        } catch (ParserConfigurationException | SAXException
                | IOException e) {
            System.err.println("There was a problem with the xml parser.");
            e.printStackTrace();
        }

        NodeList offerList = doc.getElementsByTagName("Channel");

        for (int i = 0; i < offerList.getLength(); i++){
            NodeList nodes =  offerList.item(i).getChildNodes();
            offer = new Offer();
            System.err.println(nodes.getLength());
            for (int j = 0; j < nodes.getLength(); j++){
                if (nodes.item(j).getNodeType() == Node.ELEMENT_NODE){
                    handleNode(nodes.item(j), offer);

                }
            }
            offers.add(offer);
        }
        return offers;
    }

    private void handleNode(Node n, Offer offer) {

        switch (n.getNodeName()) {
            case "CampaignName":
                offer.setCampaignName(n.getTextContent());
                break;

            case "DepartureName":
                offer.setDepartureName(n.getTextContent());
                break;

            case "OutDate":
                offer.setOutDate(n.getTextContent());
                break;

            case "DestinationName":
                offer.setDestinationName(n.getTextContent());
                break;

            case "CityName":
                offer.setCityName(n.getTextContent());
                break;

            case "RoomDescription":
                offer.setRoomDescription(n.getTextContent());
                break;

            case "JourneyLength":
                offer.setJourneyLength(Integer.parseInt(n.getTextContent()));
                break;

            case "FlightExtern":
                offer.setFlightExtern(n.getTextContent());

            case "CurrentPrice":
                offer.setCurrentPrice(n.getTextContent());
                break;

            case "OriginalPrice":
                break;

            case "PriceCurrency":
                break;

            case "HotelName":
                offer.setHotelName(n.getTextContent());
                break;

            case "HotelImage":
                offer.setHotelImage(n.getTextContent());
                break;

            case "HotelGrade":
                offer.setHotelGrade(Double.parseDouble(n.getTextContent()));
                break;

            case "NoOfSeatsRemaining":
                offer.setNoOfSeatsRemaining(Integer.parseInt(n.getTextContent()));
                break;

            case "HotelGradeString":
                break;

            case "ContentLink":
                offer.setContentLink(n.getTextContent());
                break;

            case "BookLink":
                offer.setBookLink(n.getTextContent());
                break;

            case "JourneyLengthWeeks":
                break;

            default:
                System.err.println("Found something weird in the XML!");
                break;
        }
    }

}
