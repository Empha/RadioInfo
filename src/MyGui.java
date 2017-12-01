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
	private JMenuItem setTimer;
	private JMenuItem setDestination;
	private JMenuItem quit;

	private JMenu channel;
	private JMenuItem previous;
	private JMenuItem next;

	public MyGui(URL source) {

		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/* Build panels */
		JPanel leftPanel = buildLeftPanel();
		JPanel rightPanel = buildRightPanel();
        handler = new TableHandler(source);
        channelImage = new JLabel();
        frame.add(channelImage, BorderLayout.NORTH);
		frame.add(rightPanel, BorderLayout.EAST);
		frame.add(leftPanel, BorderLayout.WEST);
		buildMenu();
		frame.setResizable(false);
		frame.setSize(900, 700);
	}

	private JPanel buildLeftPanel() {
		JPanel leftPanel = new JPanel();
		infoArea = new JTextArea();
		image = new JLabel();
		leftPanel.add(image, BorderLayout.NORTH);
        leftPanel.add(infoArea, BorderLayout.SOUTH);
        return leftPanel;
	}

	private JPanel buildRightPanel() {
		JPanel rightPanel = new JPanel();
		table = new JTable();
		table.setFont(new Font(table.getFont().getFontName(), Font.PLAIN, 20));
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		rightPanel.add(table);
		return rightPanel;
	}

	private void buildMenu() {
		menuBar = new JMenuBar();
		menu = new JMenu("Menu");
		setTimer = new JMenuItem("Set automatic updates");
		setDestination = new JMenuItem("Choose destination");
		quit = new JMenuItem("Quit");

		setTimer.addActionListener(new TimerListener(this));
		setDestination.addActionListener(new DestinationListener(this.frame,
				handler));
		quit.addActionListener(new QuitListener());

		menu.add(setTimer);
		menu.add(setDestination);
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
        table.getColumnModel().getColumn(0).setPreferredWidth(300);
        table.getColumnModel().getColumn(1).setPreferredWidth(140);
        table.getColumnModel().getColumn(2).setPreferredWidth(140);
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
        channelImage.setIcon(new ImageIcon(img.getScaledInstance(120,120,Image.SCALE_SMOOTH)));
        channelImage.updateUI();
    }

	public JFrame getFrame() {
		return frame;
	}

}
