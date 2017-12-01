import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Timer;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class TimerListener implements ActionListener {
	
	private static int halfHourInMillis = 1800000;
	private JFrame frame;
	private int num;
	private Timer timer;
	private MyGui gui;
	
	public TimerListener(MyGui gui) {
		this.gui = gui;
		this.frame = gui.getFrame();
		num = 0;
		timer = new Timer();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String input = JOptionPane.showInputDialog(frame, "Please input the "
								+ "time between automatic updates, measured in"
								+ " half hours.\n(0 means never update)");
		try {
			num = Integer.valueOf(input);
		} catch (NumberFormatException e1) {
			JOptionPane.showMessageDialog(frame, "Input must be a number.");
		}
		int time = num*halfHourInMillis;
		if (num == 0){
			timer.cancel();
		} else if (num >= 1) {
			timer.cancel();
			timer = new Timer();
			timer.schedule(new MyTimerTask(gui), 
					new Date(System.currentTimeMillis()+time), time);
		} else {
			JOptionPane.showMessageDialog(frame, "Input cannot be negative.");
		}
	}
}
