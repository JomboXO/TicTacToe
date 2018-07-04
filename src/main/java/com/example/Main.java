package com.example;

import com.example.entities.Board;
import com.example.view.MyDocumentFilter;
import com.example.entities.Square;
import com.example.logic.Logic;
import com.example.logic.Observer;
import com.example.logic.impl.LogicImpl;
import com.example.logic.impl.TicTacToe;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class Main extends JFrame {

    private TicTacToe ticTacToe;
    private JPanel panel = new JPanel();
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    public Main() {
        super("TicTacToe");
        executorService.execute(new LogicImpl());
        startNewGame();
    }

    private void startNewGame() {
        panel.revalidate();
        deleteComponents(panel);
        setBounds(300, 300, 200, 200);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JLabel label = new JLabel("Input size of the board:");
        JTextField textField = new JTextField(5);
        ((AbstractDocument) textField.getDocument()).setDocumentFilter(new MyDocumentFilter());
        JButton button = new JButton("Start game");
        button.setEnabled(false);

        panel.add(label);
        panel.add(textField);
        panel.add(button);
        add(panel);
        textField.addActionListener(e -> {
            if (textField.getText().equals("")) button.setEnabled(false);
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

        JButton[] buttons = new JButton[dimension * dimension];
        for (int i = 0; i < dimension * dimension; i++) {
            buttons[i] = new JButton();
            buttons[i].setPreferredSize(new Dimension(50, 45));
            panel.add(buttons[i]);
            JButton button = buttons[i];

            ButtonChangeListener changeListener = new ButtonChangeListener(i, button);
            board.registerObserver(changeListener);
            button.addActionListener(changeListener);

        }

        setBounds(300, 300, dimension * 62, dimension * 45 + 130);
        JButton button = new JButton("Start again");
        button.addActionListener(e -> startNewGame());
        panel.add(button);


    }

    private class ButtonChangeListener implements ActionListener, Observer {

        private int index;
        private JButton button;

        public ButtonChangeListener(int index, JButton button) {
            this.index = index;
            this.button = button;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            ticTacToe.makeMove(index);
            switch (ticTacToe.getWinner()) {
                case DRAW:
                    JOptionPane.showMessageDialog(Main.this, "Game ended with draw");
                    startNewGame();
                    break;
                case PLAYER:
                    JOptionPane.showMessageDialog(Main.this, "Player X won");
                    startNewGame();
                    break;
                case OPPONENT:
                    JOptionPane.showMessageDialog(Main.this, "Player O won");
                    startNewGame();
                    break;
                case CONTINUE:
                default:
                    System.out.println("next step");
            }
            //logic.makeMove();
        }

        @Override
        public void update(Square square) {
            if (square.getIndex() == index) {
                button.setText(square.getName());
            }
        }
    }

    private void deleteComponents(JPanel panel) {
        for (Component component : panel.getComponents()) {
            panel.remove(component);
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.setVisible(true);

    }
}
