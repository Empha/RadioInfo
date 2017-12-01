/*
 * Applikationsutveckling i Java
 * HT14
 * Obligatorisk uppgift 1
 * MyUnitTester

 * File:    MyGui.java
 * Author:  Emil Lindqvist
 * User:    c13elt
 * Date:    13 November 2014
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


/** A GUI for a unit testing program. */
public class MyGui {

    private JFrame frame;

    /**
     * Button which runs the tests.
     * <p>
     * When pressed, this button will start the tester for a
     * test class corresponding to the text in the text field.
     */
    private JButton runButton;

    /** Button which empties the text area. */
    private JButton clearButton;

    /**
     * Handles scrolling, in case the test results
     * are too long to fit in the text area.
     */
    private JScrollPane scrollPane;

    /** Writable text field, for specifying the test class to run. */
    private JTextField textField;

    /** Text area where the test results should be written. */
    private JTextArea textArea;

    /** Constructs the GUI. */
    public MyGui() {

        frame = new JFrame();

        /* Build panels */
        JPanel upperPanel = buildUpperPanel();
        JPanel middlePanel = buildMiddlePanel();
        JPanel lowerPanel = buildLowerPanel();

        // Add action listeners to buttons
        runButton.addActionListener(new RunListener(textField, textArea));
        clearButton.addActionListener(new ClearListener(textArea));

        // Add panels to the frame
        frame.add(upperPanel, BorderLayout.NORTH);
        frame.add(middlePanel, BorderLayout.CENTER);
        frame.add(lowerPanel, BorderLayout.SOUTH);

        frame.pack();
    }

    /** Displays the GUI. */
    public void show() {
        frame.setVisible(true);
    }

    /** Builds the upper panel, containing editable text field and run button */
    private JPanel buildUpperPanel() {
        JPanel upperPanel = new JPanel();

        textField = new JTextField("Please input name of a test class.");
        textField.setBackground(Color.pink);
        textField.setPreferredSize(new Dimension(400, 20));
        upperPanel.add(textField);

        runButton = new JButton("Run tests");
        runButton.setBackground(Color.pink);
        upperPanel.add(runButton);

        return upperPanel;
    }

    /** Builds the middle panel, containing the text area */
    private JPanel buildMiddlePanel() {
        JPanel middlePanel = new JPanel();

        textArea = new JTextArea();
        textArea.setBackground(Color.pink);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(600, 350));
        middlePanel.add(scrollPane);

        return middlePanel;
    }

    /** Builds the lower panel, containing the clear button */
    private JPanel buildLowerPanel() {
        JPanel lowerPanel = new JPanel();

        clearButton = new JButton("Clear");
        clearButton.setBackground(Color.pink);
        lowerPanel.add(clearButton);

        return lowerPanel;
    }
}
