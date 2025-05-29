package com.example;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Pokemon Database");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 1000);
        frame.setLayout(new FlowLayout());

        JLabel label = new JLabel("input the category (move, ability, type, pokemon) followed by a underscore and the information or q to quit. Move, ability, and type can be mixed, but pokemon can't:");
        JTextField textField = new JTextField(15);
        JButton searchButton = new JButton("Search");

        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String input = textField.getText();
                JOptionPane.showMessageDialog(frame, input);
            }
        });


        frame.add(label);
        frame.add(textField);
        frame.add(searchButton);
        frame.setVisible(true);
    }
}