import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;


public class RowListener implements MouseListener {

	private JLabel image;
	private JTextArea info;

	public RowListener(JLabel image, JTextArea info) {
		this.image = image;
		this.info = info;
	}

	@Override
	public void mouseClicked(MouseEvent evt) {
	    if (evt.getClickCount() == 1) {
	    	JTable t = (JTable)evt.getSource();
	    	int row = t.rowAtPoint(evt.getPoint());
	    	String url = (String)t.getModel().getValueAt(row, 3);
	    	String infoText = (String)t.getModel().getValueAt(row, 4);
			Image img = null;
			try {
				img = ImageIO.read(new URL(url));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			image.setIcon(new ImageIcon(img.getScaledInstance(200,200,Image.SCALE_SMOOTH)));
	    	info.setText(infoText);
	    	System.err.println(infoText);
	    }
	    System.err.println("nej");
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}
}
