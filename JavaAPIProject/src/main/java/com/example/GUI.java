package com.example;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.util.ArrayList;

public class GUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Pokemon database");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 200);
        frame.setLayout(new FlowLayout());

        JLabel label = new JLabel("input the category (move, ability, type, pokemon) followed by a underscore and the information.");
        JLabel label2 = new JLabel("Move, ability, and type can be mixed, but pokemon can't. Seperate filters with a space");
        JTextField textField = new JTextField(40);
        JButton inputButton = new JButton("Input");

        inputButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String input = textField.getText();

                ArrayList<String> inputs = new ArrayList<>();

                while (input.indexOf(" ") > -1) {
                    String aInput = input.substring(0, input.indexOf(" ")); //get it
                    input = input.substring(input.indexOf(" ") + 1); //cut it off
                    inputs.add(aInput); //add it
                }

                inputs.add(input); //adds the last filter (when there are no more spaces) and also adds the solo element if there is only one filter

                ArrayList<String> output = new ArrayList<>();
                try { //idk why this is here, but i need it
                    output = ListUtils.processFilter(inputs);
                } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                String finalOutput = "";
                for (int i = 0; i < output.size(); i++) { //turn the arraylist into a string
                    finalOutput += output.get(i) + "\n";
                }
                JOptionPane.showMessageDialog(frame, finalOutput);
            }
        });

        frame.add(label);
        frame.add(label2);
        frame.add(textField);
        frame.add(inputButton);
        frame.setVisible(true);
    }
}