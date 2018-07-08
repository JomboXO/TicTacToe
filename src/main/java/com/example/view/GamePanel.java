package com.example.view;

import com.example.TicTacToeFrame;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;

/**
 * Draw new Panel before beginning new game
 */
public class GamePanel extends JPanel{

    private static final int COLUMN_SIZE = 5;

    public GamePanel(TicTacToeFrame frame) {
        JButton button = new JButton("Start game");
        button.setEnabled(false);

        JLabel label = new JLabel("Input size of the board:");

        JTextField textField = new JTextField(COLUMN_SIZE);
        ((AbstractDocument) textField.getDocument()).setDocumentFilter(new MyDocumentFilter());
        textField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                enableButton();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                enableButton();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                enableButton();
            }

            private void enableButton() {
                button.setEnabled(!"".equals(textField.getText()) && Integer.valueOf(textField.getText()) >= 5);
            }
        });

        button.addActionListener(e -> frame.startTheGame(this, Integer.valueOf(textField.getText())));

        add(label);
        add(textField);
        add(button);
    }


}
