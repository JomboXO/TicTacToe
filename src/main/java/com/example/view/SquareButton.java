package com.example.view;

import com.example.TicTacToeFrame;
import com.example.entities.Board;
import com.example.entities.Square;
import com.example.logic.Observer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SquareButton extends JButton {
    private TicTacToeFrame frame;
    private int index;

    public SquareButton(int index, TicTacToeFrame frame) {
        this.frame = frame;
        this.index = index;
        setPreferredSize(new Dimension(50,45));
        ButtonChangeListener changeListener = new ButtonChangeListener();
        Board.getInstance().registerObserver(changeListener);
        addActionListener(changeListener);
    }

    public class ButtonChangeListener implements ActionListener, Observer<Square> {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (SquareButton.this.getText().isEmpty()) {
                frame.getHumanPlayer().makeMove(index);
                if (frame.getResultHandler().isEndGame.get())
                    frame.startNewGame();
            }
        }

        @Override
        public void update(Square square) {
            if (square.getIndex() == index) {
                SquareButton.this.setText(square.getName());
            }
        }
    }
}
