/*
 * Applikationsutveckling i Java
 * HT17
 * Obligatorisk uppgift 2
 * RadioInfo

 * File:    MyGui.java
 * Author:  Emil Lindqvist
 * User:    c13elt
 * Date:    3 December 2017
 */
import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class MyGui {

	private TableHandler handler;
	private JFrame frame;
	private JMenuBar menuBar;
	private JTextArea infoArea;
	private JLabel image;
	private JTable table;

	private JLabel channelImage;

	private JMenu menu;
	private JMenuItem update;
	private JMenuItem quit;

	private JMenu channel;
	private JMenuItem previous;
	private JMenuItem next;

	public MyGui(URL source) {

		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/* Build panels */
		JPanel panel = buildPanel();
        handler = new TableHandler(source);
        frame.add(panel);
		buildMenu();
		frame.setResizable(false);
		frame.setSize(600, 800);
	}

	private JPanel buildPanel() {
        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JButton b = new JButton("previous");
        b.setSize(200, 20);
        b.addActionListener(new ChannelListener(this, -1));
        addComponent(p, b, 0, 0, c);

        channelImage = new JLabel();
        addComponent(p, channelImage, 1, 0, c);

        b = new JButton("next");
        b.addActionListener(new ChannelListener(this, 1));
        addComponent(p, b, 2, 0, c);

        table = new JTable();
        table.setFont(new Font(table.getFont().getFontName(), Font.PLAIN, 18));
        //JScrollPane pane = new JScrollPane(table);
        //pane.setPreferredSize(new Dimension(600, 200));
        //table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        //pane.add(table);

        addComponent(p, table, 0, 1, 3, 1, c);


        image = new JLabel();
        addComponent(p, image, 0, 2, c);

        infoArea = new JTextArea();
        infoArea.setSize(400, 200);
        addComponent(p, infoArea, 1, 2, 2, 1, c);

		return p;
	}

	private void addComponent(JPanel p, JComponent comp, int x, int y, GridBagConstraints cons){
	    addComponent(p, comp, x, y, 1, 1, cons);
    }

    private void addComponent(JPanel p, JComponent comp, int x, int y, int width, int height, GridBagConstraints cons) {
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.gridx = x;
        cons.gridy = y;
        cons.gridwidth = width;
        p.add(comp, cons);
    }


	private void buildMenu() {
		menuBar = new JMenuBar();
		menu = new JMenu("Menu");

		quit = new JMenuItem("Quit");
		update = new JMenuItem("Update");

        update.addActionListener(new ChannelListener(this,0));
		quit.addActionListener(new QuitListener());

        menu.add(update);
		menu.add(quit);

		channel = new JMenu("Channel");
		previous = new JMenuItem("Previous");
		next = new JMenuItem("Next");

		previous.addActionListener(new ChannelListener(this, -1));
		next.addActionListener(new ChannelListener(this, 1));

		channel.add(previous);
		channel.add(next);

		menuBar.add(menu);
		menuBar.add(channel);
		frame.setJMenuBar(menuBar);
	}

	public void show() {
		handler.getChannels();
		channel(0);
        setChannelImage(handler.getChannelImage());
        frame.setVisible(true);
    }

    public void channel(int type) {
        String[][] tableData = handler.getNewChannelData(type);
        if (tableData == null){
            System.exit(-1);
        }

        table.setModel(new DefaultTableModel(tableData,
                new String[]{"title", "start", "end",
                        "image", "description"}));
        table.getColumnModel().getColumn(0).setPreferredWidth(340);
        table.getColumnModel().getColumn(1).setPreferredWidth(130);
        table.getColumnModel().getColumn(2).setPreferredWidth(130);
        table.getColumnModel().removeColumn(table.getColumnModel().getColumn(3));
        table.getColumnModel().removeColumn(table.getColumnModel().getColumn(3));
        table.setRowHeight(25);
        table.addMouseListener(new RowListener(image, infoArea));
        setChannelImage(handler.getChannelImage());
	}

	public void setChannelImage(String url){
        System.err.println(url);
        Image img = null;
        try {
            img = ImageIO.read(new URL(url));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        channelImage.setIcon(new ImageIcon(img.getScaledInstance(200,200,Image.SCALE_SMOOTH)));
        channelImage.updateUI();
    }

	public JFrame getFrame() {
		return frame;
	}

}
