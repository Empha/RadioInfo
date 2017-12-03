/*
 * Applikationsutveckling i Java
 * HT17
 * Obligatorisk uppgift 2
 * RadioInfo

 * File:    XMLHandler.java
 * Author:  Emil Lindqvist
 * User:    c13elt
 * Date:    3 December 2017
 */
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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
			Source xmlFile = new StreamSource(source.openStream());
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

	public ArrayList<Channel> update(URL source) {
        ArrayList<Channel> channels = new ArrayList<Channel>();
        Channel channel;

        InputStream xmlFile = null;
        try {
            xmlFile = source.openStream();
        } catch (IOException e) {
            e.printStackTrace();
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
        NodeList pagination = doc.getElementsByTagName("pagination");
        //int totalpages = Integer.parseInt(pagination.item(3).getTextContent());

        NodeList channelList = doc.getElementsByTagName("channel");

        for (int i = 0; i < channelList.getLength(); i++){
            NodeList nodes =  channelList.item(i).getChildNodes();
            channel = new Channel();
            System.err.println(nodes.getLength());
            for (int j = 0; j < nodes.getLength(); j++){
                if (nodes.item(j).getNodeType() == Node.ELEMENT_NODE){
                    handleCNode(nodes.item(j), channel);

                }
            }
            channels.add(channel);
        }
        return channels;

    }

    public ArrayList<Show> updateShows(URL source) {
        ArrayList<Show> shows = new ArrayList<Show>();
        Show show;

		InputStream xmlFile = null;
		try {
			xmlFile = source.openStream();
		} catch (IOException e) {
			e.printStackTrace();
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

		NodeList showList = doc.getElementsByTagName("scheduledepisode");

		for (int i = 0; i < showList.getLength(); i++){
			NodeList nodes =  showList.item(i).getChildNodes();
			show = new Show();
			System.err.println(nodes.getLength());
			for (int j = 0; j < nodes.getLength(); j++){
				if (nodes.item(j).getNodeType() == Node.ELEMENT_NODE){
					handleSNode(nodes.item(j), show);

				}
			}
			shows.add(show);
		}
		return shows;
	}

    private void handleSNode(Node n, Show show) {

        switch (n.getNodeName()) {
            case "episodeid":
                show.setEpisodeid(Integer.parseInt(n.getTextContent()));
                break;

            case "title":
                show.setTitle(n.getTextContent());
                break;

            case "subtitle":
                show.setSubtitle(n.getTextContent());
                break;

            case "description":
                show.setDescription(n.getTextContent());
                break;

            case "starttimeutc":
                show.setStarttimeutc(ZonedDateTime.parse(n.getTextContent()));
                break;

            case "endtimeutc":
                show.setEndtimeutc(ZonedDateTime.parse(n.getTextContent()));
                break;

            case "imageurl":
                show.setImageurl(n.getTextContent());
                break;

            case "imageurltemplate":
                show.setImageurltemplate(n.getTextContent());
                break;

            default:
                System.err.println("Found something weird in the XML!");
                break;
        }
    }

    private void handleCNode(Node n, Channel channel) {

		switch (n.getNodeName()) {
		case "channel":
			channel.setName(n.getTextContent());
			break;

		case "image":
			channel.setImage(n.getTextContent());
			break;

		case "color":
			channel.setColor(n.getTextContent());
			break;

		case "siteurl":
			channel.setSiteurl(n.getTextContent());
			break;

		case "url":
			channel.setUrl(n.getTextContent());
			break;

		case "statkey":
			channel.setStatkey(n.getTextContent());
			break;

		case "scheduleurl":
			channel.setScheduleurl(n.getTextContent());
			break;

		case "channeltype":
			channel.setChanneltype(n.getTextContent());

		case "xmltvid":
			channel.setXmltvid(n.getTextContent());
			break;

		default:
			System.err.println("Found something weird in the XML!");
			break;
		}
	}
}
