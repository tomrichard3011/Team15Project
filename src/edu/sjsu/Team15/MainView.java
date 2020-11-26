package edu.sjsu.Team15;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.LinkedBlockingQueue;

public class MainView extends JFrame {
    DomainInfoListView domainInfoListView;
    DomainInfoView domainInfoView;
    JButton addDomainButton = new JButton("Add Domain");
    JButton settingsButton = new JButton("Settings");
    ButtonPress buttonPressListener = new ButtonPress();
    LinkedBlockingQueue<Message> queue;

    public MainView(DomainInfoListView domainInfoListView, DomainInfoView domainInfoView, LinkedBlockingQueue<Message> queue) {
        this.domainInfoListView = domainInfoListView;
        this.domainInfoView = domainInfoView;
        this.queue = queue;

        // create buttons
        addDomainButton.addActionListener(buttonPressListener);
        settingsButton.addActionListener(buttonPressListener);

        SpringLayout layout = new SpringLayout();
        this.setLayout(layout);
        this.add(this.domainInfoListView);
        this.add(this.domainInfoView);
        this.add(addDomainButton);
        this.add(settingsButton);
        this.SpringLayoutSetup(layout);

        this.setPreferredSize(new Dimension(500, 450));
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }

    private void SpringLayoutSetup(SpringLayout layout) {
        // Domain Info List
        layout.putConstraint(SpringLayout.NORTH, domainInfoListView, 0,
                SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, domainInfoListView, 10,
                SpringLayout.WEST, this);

        // Domain info view
        layout.putConstraint(SpringLayout.NORTH, domainInfoView, 0,
                SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.EAST, domainInfoView, 0,
                SpringLayout.EAST, this);
        
        // Add Domain Button
        layout.putConstraint(SpringLayout.NORTH, addDomainButton, 10,
                SpringLayout.SOUTH, domainInfoListView);
        layout.putConstraint(SpringLayout.WEST, addDomainButton, 10,
                SpringLayout.WEST, this);

        // Settings button
        layout.putConstraint(SpringLayout.NORTH, settingsButton, 10,
                SpringLayout.SOUTH, addDomainButton);
        layout.putConstraint(SpringLayout.WEST, settingsButton, 10,
                SpringLayout.WEST, this);
    }

    private class ButtonPress implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            Object source = actionEvent.getSource();
            Message message = new Message();

            if (source == addDomainButton) {
                System.out.println("Add domain");
                message.action = Message.Action.CREATE_DOMAININFO_MENU;
                queue.add(message);
            }
            if (source == settingsButton) {
                System.out.println("Settings");
                message.action = Message.Action.SETTINGS_MENU;
                queue.add(message);
            }
        }
    }
}
