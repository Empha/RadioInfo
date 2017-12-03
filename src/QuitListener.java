/*
 * Applikationsutveckling i Java
 * HT17
 * Obligatorisk uppgift 2
 * RadioInfo

 * File:    QuitListener.java
 * Author:  Emil Lindqvist
 * User:    c13elt
 * Date:    3 December 2017
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class QuitListener implements ActionListener {

	public QuitListener() {}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.exit(0);

	}

}
