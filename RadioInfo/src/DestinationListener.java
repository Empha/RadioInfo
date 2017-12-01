/*
 * Applikationsutveckling i Java
 * HT14
 * Obligatorisk uppgift 2
 * TravelInfo

 * File:    DestinationListener.java
 * Author:  Emil Lindqvist
 * User:    c13elt
 * Date:    7 Januari 2015
 */

import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class DestinationListener implements ActionListener {

    private JFrame frame;
    private TableHandler handler;

    public DestinationListener(JFrame frame, TableHandler handler) {
        this.frame = frame;
        this.handler = handler;
    }

    @Override
    public void actionPerformed(java.awt.event.ActionEvent evt) {
        String name = JOptionPane.showInputDialog(frame,
                "Select a destination.\nEmpty string for all destinations", null);
        handler.setDestination(name);
    }
}
