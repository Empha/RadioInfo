import javax.swing.*;
import java.awt.event.ActionListener;

public class ChannelListener implements ActionListener {

    private MyGui gui;
    private int type;

    public ChannelListener(MyGui gui, int type) {
        this.gui = gui;
        this.type = type;
    }

    @Override
    public void actionPerformed(java.awt.event.ActionEvent evt) {
        gui.channel(type);
    }
}
