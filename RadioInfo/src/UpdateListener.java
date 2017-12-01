import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateListener implements ActionListener {

    private MyGui gui;

    public UpdateListener(MyGui gui) {
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        gui.update();
    }
}
