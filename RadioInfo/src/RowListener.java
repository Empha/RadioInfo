import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextArea;


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
            try {
                image.setIcon(new ImageIcon(new URL(url)));
            } catch (MalformedURLException e) {
                System.err.println("Malformed image url!");
            }
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
