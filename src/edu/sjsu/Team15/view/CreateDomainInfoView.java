package edu.sjsu.Team15.view;

import edu.sjsu.Team15.model.DomainInfo;
import edu.sjsu.Team15.utility.Message;
import edu.sjsu.Team15.utility.PasswordGenerator;
import io.github.novacrypto.SecureCharBuffer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Create window for adding in domains
 */
public class CreateDomainInfoView extends JFrame{
	/** The window frame */
    private final JFrame frame = this;
    /** Subdomain Label */
    private final JLabel domainLabel = new JLabel("Enter domain: www.");
    /** Top Level Domain Label */
    private final JLabel domainExtLabel = new JLabel(".com");
    /** User Label */
    private final JLabel usernameLabel = new JLabel("Enter username:");
    /** Password Label */
    private final JLabel passLabel = new JLabel("Enter password:");
    /** Random Check Label */
    private final JLabel randomPassLabel = new JLabel("Randomize Password");

    /** Domain Text Input */
    private final JTextField domainInput = new JTextField();
    /** User Text Input */
    private final JTextField usernameInput = new JTextField();
    /** Pass Text Input */
    private final JPasswordField passField = new JPasswordField();
    /** Submit Button */
    private final JButton addCreds = new JButton("add Credentials");
    /** Checkbox */
    private final JCheckBox randomPass = new JCheckBox();
    /** Layout Object */
    private final SpringLayout layout = new SpringLayout();
    /** Message Queue for the whole application */
    private final LinkedBlockingQueue<Message> queue;
    /** Determine if we use random generation */
    private boolean random;

    /**
     * constructor
     * @param queue thread safe queue to send commands to
     */
    public CreateDomainInfoView(LinkedBlockingQueue<Message> queue) {
        this.queue = queue;

        domainInput.setColumns(10);
        usernameInput.setColumns(10);
        passField.setColumns(10);

        // add action listeners
        addCreds.addActionListener(new ButtonPress());
        randomPass.addActionListener(new ButtonPress());

        this.setLayout(layout);

        // add components
        this.add(domainLabel);
        this.add(domainExtLabel);
        this.add(usernameLabel);
        this.add(passLabel);
        this.add(randomPassLabel);
        this.add(domainInput);
        this.add(usernameInput);
        this.add(passField);
        this.add(addCreds);
        this.add(randomPass);

        SpringLayoutSetup(layout);

        // frame setups
        this.setPreferredSize(new Dimension(350, 250));
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
    }

    /**
     * setup for spring layout
     * @param layout layout used for frame
     */
    private void SpringLayoutSetup(SpringLayout layout) {
        // input fields
        // domain input
        layout.putConstraint(SpringLayout.NORTH, domainInput, 10,
                SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, domainInput, -10,
                SpringLayout.HORIZONTAL_CENTER, this);

        // username input
        layout.putConstraint(SpringLayout.NORTH, usernameInput, 10,
                SpringLayout.SOUTH, domainInput);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, usernameInput, 0,
                SpringLayout.HORIZONTAL_CENTER, domainInput);

        // password input
        layout.putConstraint(SpringLayout.NORTH, passField, 10,
                SpringLayout.SOUTH, usernameInput);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, passField, 0,
                SpringLayout.HORIZONTAL_CENTER, domainInput);

        // labels
        // domain label
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, domainLabel, 0,
                SpringLayout.VERTICAL_CENTER, domainInput);
        layout.putConstraint(SpringLayout.EAST, domainLabel, 0,
                SpringLayout.WEST, domainInput);

        // domain extension label
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, domainExtLabel, 0,
                SpringLayout.VERTICAL_CENTER, domainInput);
        layout.putConstraint(SpringLayout.WEST, domainExtLabel, 0,
                SpringLayout.EAST, domainInput);

        // username label
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, usernameLabel, 0,
                SpringLayout.VERTICAL_CENTER, usernameInput);
        layout.putConstraint(SpringLayout.EAST, usernameLabel, -10,
                SpringLayout.WEST, usernameInput);

        // password label
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, passLabel, 0,
                SpringLayout.VERTICAL_CENTER, passField);
        layout.putConstraint(SpringLayout.EAST, passLabel, -10,
                SpringLayout.WEST, passField);

        // random pass label
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, randomPassLabel, 0,
                SpringLayout.VERTICAL_CENTER, randomPass);
        layout.putConstraint(SpringLayout.EAST, randomPassLabel, -10,
                SpringLayout.WEST, randomPass);

        // random pass checkbox
        layout.putConstraint(SpringLayout.NORTH, randomPass, 4,
                SpringLayout.SOUTH, passField);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, randomPass, 0,
                SpringLayout.HORIZONTAL_CENTER, passField);

        // Add credential button
        layout.putConstraint(SpringLayout.NORTH, addCreds, 10,
                SpringLayout.SOUTH, randomPass);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, addCreds, 0,
                SpringLayout.HORIZONTAL_CENTER, this);
    }

    /**
     * Action listener for when a button is pressed
     */
    private class ButtonPress implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            Object source = actionEvent.getSource();
            Message message = new Message();
            // create domain info based off of input credentials
            if (source == addCreds) {
                String domain = domainInput.getText();
                String username = usernameInput.getText();
                char[] password = passField.getPassword();
                SecureCharBuffer charBuffer = new SecureCharBuffer();

                // transfer data to new character buffer
                if (random) {
                    charBuffer = PasswordGenerator.generatePassword();
                }
                else { // use the input password
                    for (int i = 0; i < password.length; i++) {
                        charBuffer.append(password[i]);
                        password[i] = '\0'; // null the password after
                    }
                }

                message.action = Message.Action.ADD_DOMAININFO;
                message.setDomainInfo(new DomainInfo(domain, username, charBuffer));

                queue.add(message);
                frame.dispose();
            }
            else { // random checkbox is marked
                random = !random;
            }
        }
    }
}
