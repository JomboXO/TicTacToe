package com.example;

import com.example.entities.Board;
import com.example.entities.MyDocumentFilter;
import com.example.logic.Logic;
import com.example.logic.impl.LogicImpl;
import com.example.logic.impl.TicTacToe;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class Main extends JFrame{

    private TicTacToe ticTacToe;
    private Logic logic = new LogicImpl();

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
        textField.addActionListener(e -> {
            if (textField.getText().equals(""))button.setEnabled(false);
            else button.setEnabled(true);
        });

        button.addActionListener(e -> startTheGame(panel, Integer.valueOf(textField.getText())));


    }

    private void startTheGame(JPanel panel, int dimension) {

        panel.revalidate();
        deleteComponents(panel);
        Board board = Board.getInstance();
        board.setDimention(dimension);
        ticTacToe = new TicTacToe(dimension);

        JButton[] buttons = new JButton[dimension*dimension];
        for (int i = 0; i < dimension*dimension; i++){
            buttons[i] = new JButton();
            buttons[i].setPreferredSize(new Dimension(50,45));
            panel.add(buttons[i]);
            JButton button = buttons[i];

            button.addActionListener(new ButtonClickListener(i));
        }

        setBounds(300,300,dimension*62,dimension*45+130);
        JButton button = new JButton("Start again");
        button.addActionListener(e -> startNewGame(panel));
        panel.add(button);


    }

    private class ButtonClickListener implements ActionListener{

        private int index;

        public ButtonClickListener(int index) {
            this.index = index;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            ticTacToe.makeMove(index);
            ((JButton) e.getSource()).setText("X");
            System.out.println(Board.getInstance());
        }
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
