package com.example;

import com.example.entities.MyDocumentFilter;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame{
    public Main(){
        super("TicTacToe");
        JPanel panel = new JPanel();
        //super.getContentPane().add(panel, BorderLayout.CENTER);
        startNewGame(panel);
    }

    private void startNewGame(JPanel panel) {
        panel.revalidate();
        deleteComponents(panel);
        setBounds(300,300,200,200);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JLabel label = new JLabel("Input size of the board:");
        JTextField textField = new JTextField(5);
        ((AbstractDocument)textField.getDocument()).setDocumentFilter(new MyDocumentFilter());
        JButton button = new JButton("Start game");
        button.setEnabled(false);

        panel.add(label);
        panel.add(textField);
        panel.add(button);
        add(panel);
        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (textField.getText().equals(""))button.setEnabled(false);
                else button.setEnabled(true);
            }
        });

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startTheGame(panel, Integer.valueOf(textField.getText()));
            }
        });


    }

    private void startTheGame(JPanel panel, int dimension) {
        panel.revalidate();
        deleteComponents(panel);
        JButton[] buttons = new JButton[dimension*dimension];
        for (int i = 0; i < dimension*dimension; i++){
            buttons[i] = new JButton();
            buttons[i].setPreferredSize(new Dimension(50,45));
            panel.add(buttons[i]);
        }
        setBounds(300,300,dimension*65,dimension*45+130);
        JButton button = new JButton("Start again");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startNewGame(panel);
            }
        });
        panel.add(button);
    }

    private void deleteComponents(JPanel panel) {
        for (Component component: panel.getComponents()){
            panel.remove(component);
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.setVisible(true);
    }
}
