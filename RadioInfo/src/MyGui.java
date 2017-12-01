import java.awt.BorderLayout;
import java.awt.Font;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

public class MyGui {

    private TableHandler handler;
    private JFrame frame;
    private JMenuBar menuBar;
    private JTextArea infoArea;
    private JLabel image;
    private JTable table;

    private JMenu menu;
    private JMenuItem update;
    private JMenuItem setTimer;
    private JMenuItem setDestination;
    private JMenuItem quit;

    public MyGui(URL source) {

        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/* Build panels */
        JPanel leftPanel = buildLeftPanel();
        JPanel rightPanel = buildRightPanel();
        frame.add(rightPanel, BorderLayout.EAST);
        frame.add(leftPanel, BorderLayout.WEST);
        this.handler = new TableHandler(source);
        buildMenu();
        frame.setResizable(false);
        frame.setSize(900, 700);
    }

    private JPanel buildLeftPanel() {
        JPanel leftPanel = new JPanel();
        infoArea = new JTextArea("hejsan svejsan");
        image = new JLabel();
        leftPanel.add(infoArea, BorderLayout.SOUTH);
        leftPanel.add(image, BorderLayout.NORTH);
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
        update = new JMenuItem("Update");
        setTimer = new JMenuItem("Set automatic updates");
        setDestination = new JMenuItem("Choose destination");
        quit = new JMenuItem("Quit");

        update.addActionListener(new UpdateListener(this));
        setTimer.addActionListener(new TimerListener(this));
        setDestination.addActionListener(new DestinationListener(this.frame,
                handler));
        quit.addActionListener(new QuitListener());

        menu.add(update);
        menu.add(setTimer);
        menu.add(setDestination);
        menu.add(quit);

        menuBar.add(menu);
        frame.setJMenuBar(menuBar);
    }

    public void show() {
        frame.setVisible(true);
        update();
    }

    public void update() {
        String[][] tableData = handler.getNewTableData();
        if (tableData == null){
            System.exit(0);
        }
        table.setModel(new DefaultTableModel(tableData,
                new String[]{"destination", "date", "price",
                        "image", "description"}));
        table.getColumnModel().getColumn(0).setPreferredWidth(150);
        table.getColumnModel().getColumn(1).setPreferredWidth(120);
        table.getColumnModel().removeColumn(table.getColumnModel().getColumn(3));
        table.getColumnModel().removeColumn(table.getColumnModel().getColumn(3));
        table.setRowHeight(25);
        table.addMouseListener(new RowListener(image, infoArea));
    }

    public JFrame getFrame() {
        return frame;
    }

}
