/*
 * Written by : Emil Nilsson
 *
 * Projectwork: Anti Tower Defence
 * Group: 5
 * 
 * Applikationsprogrammering i Java, HT 2014
 */

package antiTD;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import tiles.BackgroundTile;
import tiles.Goal;
import tiles.Intersection;
import tiles.Tile;

/**
 * Class that handles validation and reading of XML file.
 * 
 * @author c13enn
 * 
 */
public class TrackHandler {

	private Class<?> roadClass;
	private Class<?> towerPlacingClass;
	private int tileSize = 50;

	/**
	 * Validates the given file against the levelsSchema.xsd
	 * 
	 * @param fileName
	 *            Name of the file to validate.
	 * @return True if the file is ok, otherwise false.
	 */
	public boolean validateTrackFile(String fileName) {
		try {
			Source xmlFile = new StreamSource(
					this.getClass().getResourceAsStream(fileName));
			SchemaFactory schemaFactory =
					SchemaFactory.newInstance(
							XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Source levelSchema = new StreamSource(
					this.getClass().getResourceAsStream(
							"levelsSchema.xsd"));
			Schema schema = schemaFactory.newSchema(levelSchema);
			Validator validator = schema.newValidator();

			validator.validate(xmlFile);
			return true;
		} catch (SAXException | IOException e) {
			System.err.print("Could not validate " + fileName + ".");
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Reads the given file and saves the levels in an ArrayList.
	 * 
	 * @param fileName
	 *            Name of the XML file to read from.
	 * @return Returns an ArrayList with all levels contained in file.
	 */
	public ArrayList<Track> readTrackFile(String fileName) {
		// assumes the file is validated
		ArrayList<Track> tracks = new ArrayList<Track>();
		Track track;

		try {
			InputStream xmlFile = this.getClass().getResourceAsStream(fileName);
			DocumentBuilderFactory dbFactory =
					DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);

			NodeList levelList = doc.getElementsByTagName("level");

			// loop every level
			for (int i = 0; i < levelList.getLength(); i++) {
				Node levelNode = levelList.item(i);
				NodeList childNodes = levelNode.getChildNodes();
				track = new Track();
				try {
					// loop every child in level
					for (int j = 0; j < childNodes.getLength(); j++) {
						if (childNodes.item(j).getNodeType() != Node.ELEMENT_NODE) {
							continue;
						}

						handleLevelChild(track, childNodes.item(j));

					}

					createDrawable(track.getGrid(), track);
					placeTowerTiles(track);
					tracks.add(tracks.size(), track);

				} catch (NoSuchMethodException | SecurityException
						| InstantiationException | IllegalAccessException
						| IllegalArgumentException
						| InvocationTargetException | ClassNotFoundException
						| DOMException e) {
					System.err.print("Could not load level: "
							+ getAttrubuteByName(levelNode, "name"));
				}
			}

		} catch (SAXException | IOException | ParserConfigurationException e) {
			System.err.print("Could not load any level.");
		}

		return tracks;
	}

	/**
	 * Handles a child of the level element.
	 * 
	 * @param track
	 *            The track to add things to.
	 * @param child
	 *            The child to be handled.
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws ClassNotFoundException
	 * @throws DOMException
	 * @throws IOException
	 */
	private void handleLevelChild(Track track, Node child)
			throws NoSuchMethodException,
			SecurityException,
			InstantiationException,
			IllegalAccessException,
			IllegalArgumentException,
			InvocationTargetException,
			ClassNotFoundException,
			DOMException,
			IOException {
		switch (child.getNodeName()) {
		case "roadClass":
			roadClass = Class.forName(child.getTextContent());
			if (!Tile.class.isAssignableFrom(roadClass)) {
				// doesnt matter what exception just needs to exit
				throw new SecurityException();
			}

			track.setRoadClass(roadClass);
			break;
		case "towerPlacingClass":
			towerPlacingClass = Class.forName(child.getTextContent());
			if (!Tile.class.isAssignableFrom(towerPlacingClass)) {
				// doesnt matter what exception just needs to exit
				throw new SecurityException();
			}

			track.setTowerPlacingClass(towerPlacingClass);
			break;
		case "sizeOfTrack":
			Point p = getPointFromNode(child);
			track.setGrid(initGrid(p));
			track.setSizeOfTrack(p);
			break;
		case "startCurrency":
			track.setStartCurrency(Integer.parseInt(child.getTextContent()));
			break;
		case "numTowers":
			track.setNumTowers(Integer.parseInt(child.getTextContent()));
			break;
		case "numUnitsToWin":
			track.setNumUnitsToWin(Integer.parseInt(child.getTextContent()));
			break;
		case "roads":
			setRoads(track, child);
			break;
		case "start":
			// add start to track
			Point p1 = getPointFromNode(child);

			track.setStart(p1);

			break;
		case "goal":
			// add goal to track
			Point p2 = getPointFromNode(child);
			Goal goal = new Goal(p2);
			Point p3 =
					new Point((int) p2.getX() / tileSize, (int) p2.getY()
							/ tileSize);
			Tile[][] grid1 = track.getGrid();
			grid1[p3.y][p3.x] = goal;
			track.setGoal(p2);

			break;

		}
	}

	/**
	 * Inits the grid with all towerPlacingClass ( TowerTile )
	 * 
	 * @param size
	 *            The size of the grid in pixels, should be divisible
	 *            by 50.
	 * @return The grid.
	 * @throws IOException
	 */
	private Tile[][] initGrid(Point size) throws IOException {
		Tile[][] grid =
				new Tile[(int) size.y / tileSize][(int) size.x / tileSize];

		for (int y = 0; y < (int) size.y / tileSize; y++) {
			for (int x = 0; x < (int) size.x / tileSize; x++) {
				grid[y][x] = new BackgroundTile(new Point(x * 50, y * 50));
			}
		}

		return grid;
	}

	/**
	 * Adds the roads loaded from the xml file to the track.
	 * 
	 * @param track
	 *            The track to add the roads to.
	 * @param node
	 *            The roads node in the xml file.
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	private void
	setRoads(Track track, Node node) throws NoSuchMethodException,
	SecurityException,
	InstantiationException,
	IllegalAccessException,
	IllegalArgumentException,
	InvocationTargetException {
		Tile[][] grid = track.getGrid();
		NodeList childNodes = node.getChildNodes();
		ArrayList<Intersection> intersectionList =
				new ArrayList<Intersection>();

		for (int i = 0; i < childNodes.getLength(); i++) {
			switch (childNodes.item(i).getNodeName()) {
			case "road":
				// check where road starts
				Node start = getChildByName(childNodes.item(i), "start");
				Point startPoint = getPointFromNode(start);
				startPoint.x = (int) startPoint.getX() / tileSize;
				startPoint.y = (int) startPoint.getY() / tileSize;

				// check where road ends
				Node end = getChildByName(childNodes.item(i), "goal");
				Point endPoint = getPointFromNode(end);
				endPoint.x = (int) endPoint.getX() / tileSize;
				endPoint.y = (int) endPoint.getY() / tileSize;

				// find direction
				String attr =
						getAttrubuteByName(childNodes.item(i), "direction");
				Direction d = Direction.getDirectionFromString(attr);

				// add the road
				Tile instance;
				Constructor<?> ctor =
						roadClass.getDeclaredConstructor(Direction.class,
								Point.class);
				while (!startPoint.equals(endPoint)) {
					instance =
							(Tile) ctor.newInstance(d,
									new Point(
											startPoint.x * 50,
											startPoint.y * 50));
					grid[startPoint.y][startPoint.x] = instance;

					switch (d) {
					case NORTH:
						startPoint.y--;
						break;
					case EAST:
						startPoint.x++;
						break;
					case SOUTH:
						startPoint.y++;
						break;
					case WEST:
						startPoint.x--;
						break;
					}

				}

				// add the last tile
				instance =
						(Tile) ctor.newInstance(d, new Point(endPoint.x * 50,
								endPoint.y * 50));
				grid[endPoint.y][endPoint.x] = instance;

				break;
			case "intersection":

				Point p = getPointFromNode(childNodes.item(i));

				p.x = (int) p.getX() / 50;
				p.y = (int) p.getY() / 50;
				Tile[][] gridd = track.getGrid();

				// get directions from xml
				String str =
						getAttrubuteByName(childNodes.item(i), "direction");
				Direction d1 = Direction.getDirectionFromString(str);
				str = getAttrubuteByName(childNodes.item(i), "direction2");
				Direction d2 = Direction.getDirectionFromString(str);

				intersectionList.add(new Intersection(gridd[p.y][p.x], d1, d2));
				break;
			default:
				continue;
			}

		}
		track.setIntersections(intersectionList);

	}

	/**
	 * Gets the x and y coordinates as a Point from an element with
	 * childs x and y.
	 * 
	 * @param node
	 *            Parent element to x and y.
	 * @return The point.
	 */
	private Point getPointFromNode(Node node) {
		NodeList child = node.getChildNodes();
		int x = 0;
		int y = 0;
		for (int i = 0; i < child.getLength(); i++) {
			if (child.item(i).getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}

			if (child.item(i).getNodeName().equals("x")) {
				x = Integer.parseInt(child.item(i).getTextContent());
			} else if (child.item(i).getNodeName().equals("y")) {
				y = Integer.parseInt(child.item(i).getTextContent());
			}
		}

		return new Point(x, y);
	}

	/**
	 * Gets the child with the specified name.
	 * 
	 * @param parent
	 *            The parent node.
	 * @param name
	 *            The name of the child.
	 * @return The child if it is found, otherwise null.
	 */
	private Node getChildByName(Node parent, String name) {
		for (Node child = parent.getFirstChild(); child != null; child =
				child.getNextSibling()) {
			if (name.equals(child.getNodeName())) {
				return child;
			}
		}

		return null;
	}

	/**
	 * Fetches the given attribute corresponding to the name.
	 * 
	 * @param node
	 *            The node who owns the attribute.
	 * @param name
	 *            The name of the attribute.
	 * @return The attribute value if found, otherwise null.
	 */
	private String getAttrubuteByName(Node node, String name) {

		NamedNodeMap attributes = node.getAttributes();

		for (int i = 0; i < attributes.getLength(); i++) {
			if (name.equals(attributes.item(i).getNodeName())) {
				return attributes.item(i).getNodeValue();
			}
		}
		return null;
	}

	/**
	 * Creeates the drawable list for the track.
	 * 
	 * @param grid
	 *            The mao grid.
	 * @param t
	 *            The track to add things to.
	 */
	private void createDrawable(Tile[][] grid, Track t) {
		ArrayList<Drawable> drawable = new ArrayList<Drawable>();
		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[y].length; x++) {
				drawable.add(grid[y][x]);

			}

		}
		t.setDrawable(drawable);
	}

	/**
	 * Places a tower tile on the coordinate if the coordinate is
	 * adjacent to a road and the current tile on the coordinate is
	 * not a road.
	 * 
	 * @param track
	 *            Track where the grid is located.
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	private void placeTowerTiles(Track track) throws NoSuchMethodException,
	SecurityException,
	InstantiationException,
	IllegalAccessException,
	IllegalArgumentException,
	InvocationTargetException {
		Tile[][] grid = track.getGrid();
		Constructor<?> ctor;

		ctor = towerPlacingClass.getConstructor(Point.class);

		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[y].length; x++) {
				if (isAdjacentToRoad(track, new Point(x, y))) {
					Tile instance =
							(Tile) ctor.newInstance(new Point(x * 50, y * 50));
					grid[y][x] = instance;
				}
			}
		}

	}

	/**
	 * Check if the given point is adjacent to a road in the tracks
	 * grid.
	 * 
	 * @param track
	 *            The Track where the grid is located.
	 * @param point
	 *            What point to check.
	 * @return True if the tile is adjacent to a road, false
	 *         otherwise. False if the point is a road.
	 */
	private boolean isAdjacentToRoad(Track track, Point point) {
		Tile[][] grid = track.getGrid();

		if (grid[point.y][point.x].getClass().equals(roadClass)
				|| grid[point.y][point.x].getClass().equals(Goal.class)) {
			return false;
		}

		int yMin = point.y - 1;
		int yMax = point.y + 1;
		int xMin = point.x - 1;
		int xMax = point.x + 1;

		if (xMin < 0) {
			xMin = 0;
		}

		if (yMin < 0) {
			yMin = 0;
		}

		if (yMax > grid.length - 1) {
			yMax = grid.length - 1;
		}

		if (xMax > grid[0].length - 1) {
			xMax = grid[0].length - 1;
		}

		for (int y = yMin; y <= yMax; y++) {
			for (int x = xMin; x <= xMax; x++) {
				if (y == point.y && x == point.x) {
					continue;
				}
				if (grid[y][x].getClass().equals(roadClass)) {
					return true;
				}
			}
		}
		return false;
	}
}
