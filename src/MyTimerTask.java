import java.util.TimerTask;


public class MyTimerTask extends TimerTask {

	private MyGui gui;

	public MyTimerTask(MyGui gui) {
		this.gui = gui;
	}

	@Override
	public void run() {
		gui.channel(0);
	}

}
