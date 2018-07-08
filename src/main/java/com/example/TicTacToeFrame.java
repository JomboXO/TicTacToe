package com.example;

import com.example.entities.Board;
import com.example.entities.GameResult;
import com.example.logic.Observer;
import com.example.logic.impl.AIPlayer;
import com.example.logic.impl.HumanPlayer;
import com.example.logic.impl.ResultHandler;
import com.example.view.GamePanel;
import com.example.view.SquareButton;

import javax.swing.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TicTacToeFrame extends JFrame implements Observer<GameResult>{

    private static final int DEF_X_COORD = 200;
    private static final int DEF_Y_COORD = 200;
    private static final int WIDTH = 200;
    private static final int HEIGHT = 200;

    private ResultHandler resultHandler = new ResultHandler();
    private HumanPlayer humanPlayer;

    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private JPanel panel;

    private TicTacToeFrame() {
        super("TicTacToe");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        resultHandler.registerObserver(this);
        humanPlayer = new HumanPlayer(resultHandler);

        startNewGame();
    }

    public void startNewGame() {
        setBounds(TicTacToeFrame.DEF_X_COORD, TicTacToeFrame.DEF_Y_COORD, WIDTH, HEIGHT);
        if(panel != null){
            remove(panel);
        }
        resultHandler.reset();

        panel = new GamePanel(this);

        add(panel);
        revalidate();
        executorService.execute( new AIPlayer(resultHandler, this));
    }

    public void startTheGame(JPanel panel, int dimension) {

        panel.removeAll();
        panel.revalidate();

        Board.getInstance().setDimention(dimension);

        JButton[] buttons = new JButton[dimension * dimension];
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new SquareButton(i,this);
            panel.add(buttons[i]);
        }

        setBounds(DEF_X_COORD, DEF_Y_COORD, dimension * 62, dimension * 45 + 130);
        JButton button = new JButton("Start again");
        button.addActionListener(e -> startNewGame());
        panel.add(button);
    }

    @Override
    public void update(GameResult gameResult) {
        switch (gameResult) {
            case DRAW:
                JOptionPane.showMessageDialog(TicTacToeFrame.this, "Game ended with draw");
                break;
            case PLAYER:
                JOptionPane.showMessageDialog(TicTacToeFrame.this, "Player X won");
                break;
            case OPPONENT:
                JOptionPane.showMessageDialog(TicTacToeFrame.this, "Player O won");
                break;
            case CONTINUE:
            default:
                System.out.println("next step");
        }
    }

    public ResultHandler getResultHandler() {
        return resultHandler;
    }

    public HumanPlayer getHumanPlayer() {
        return humanPlayer;
    }

    public static void main(String[] args) {
        TicTacToeFrame main = new TicTacToeFrame();
        main.setVisible(true);
    }
}
