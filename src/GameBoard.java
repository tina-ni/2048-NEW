/*
 * CIS 120 HW09 - TicTacToe Demo
 * (c) University of Pennsylvania
 * Created by Bayley Tuch, Sabrina Green, and Nicolas Corona in Fall 2020.
 */


import javax.swing.*;
import java.awt.*;


/**
 * This class instantiates a TicTacToe object, which is the model for the game.
 * As the user clicks the game board, the model is updated. Whenever the model
 * is updated, the game board repaints itself and updates its status JLabel to
 * reflect the current state of the model.
 *
 * This game adheres to a Model-View-Controller design framework. This
 * framework is very effective for turn-based games. We STRONGLY
 * recommend you review these lecture slides, starting at slide 8,
 * for more details on Model-View-Controller:
 * https://www.seas.upenn.edu/~cis120/current/files/slides/lec37.pdf
 *
 * In a Model-View-Controller framework, GameBoard stores the model as a field
 * and acts as both the controller (with a MouseListener) and the view (with
 * its paintComponent method and the status JLabel).
 */
@SuppressWarnings("serial")
public class GameBoard extends JPanel {

    private final TwoZeroFourEight game;

    private static final int TILE_SIZE = 100;
    private static final int SPACE = 10;

    public GameBoard() {
        this.game = new TwoZeroFourEight();
        setPreferredSize(new Dimension(4 * TILE_SIZE + 5 * SPACE, 4 * TILE_SIZE + 5 * SPACE));
        setBackground(Color.LIGHT_GRAY);

        showInstructions();
    }


    public void reset() {
        game.reset();
        repaint();
    }


    public void showInstructions() {
        String instructions = "Welcome to 2048!\n\n"
                + "1. Use the arrow keys to move the tiles.\n"
                + "2. Tiles with the same number will merge when they hit.\n"
                + "3. The goal is to combine tiles until you reach tile 2048.\n"
                + "4. Keep playing until you get to 2048 or until you can \n" +
                "no longer move.\n\n"
                + "thanks for grading :))";


        JOptionPane.showMessageDialog(this, instructions,
                "Game Instructions", JOptionPane.INFORMATION_MESSAGE);
    }


    // for all of the following functions movement is prevented when
    // the game is over
    public boolean moveUp() {
        if (game.checkWin() || game.checkGameOver()) {
            return false;
        }
        return game.moveUp();
    }


    public boolean moveDown() {
        if (game.checkWin() || game.checkGameOver()) {
            return false;
        }
        return game.moveDown();
    }


    public boolean moveLeft() {
        if (game.checkWin() || game.checkGameOver()) {
            return false;
        }
        return game.moveLeft();
    }


    public boolean moveRight() {
        if (game.checkWin() || game.checkGameOver()) {
            return false;
        }
        return game.moveRight();
    }


    public void generateTile() {
        game.generateTile();
    }


    public void undo() {
        game.undo();
        repaint();
        requestFocusInWindow();
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int[][] board = game.getBoard();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                drawTile(g, i, j, board[i][j]);
            }
        }


        if (game.checkWin()) {
            showEndGameMessage(g, "You Win!");
        } else if (game.checkGameOver()) {
            showEndGameMessage(g, "Game Over!");
        }
    }


    private void showEndGameMessage(Graphics g, String message) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.drawString(message, 150,150);
    }


    private void drawTile(Graphics g, int row, int col, int value) {
        int x = SPACE + col * (TILE_SIZE + SPACE);
        int y = SPACE + row * (TILE_SIZE + SPACE);


        Color tileColor;
        if (value == 0) {
            tileColor = Color.WHITE;
        } else if (value == 2) {
            tileColor = new Color(250, 200, 100);
        } else if (value == 4) {
            tileColor = new Color(250, 160, 100);
        } else if (value == 8) {
            tileColor = new Color(250, 100, 100);
        } else if (value == 16) {
            tileColor = new Color(250, 200, 80);
        } else if (value == 32) {
            tileColor = new Color(250, 160, 80);
        } else if (value == 64) {
            tileColor = new Color(250, 100, 80);
        } else if (value == 128) {
            tileColor = new Color(250, 200, 50);
        } else if (value == 256) {
            tileColor = new Color(250, 160, 50);
        } else if (value == 512) {
            tileColor = new Color(250, 100, 50);
        } else if (value == 1024) {
            tileColor = new Color(250, 200, 20);
        } else if (value == 2048) {
            tileColor = new Color(250, 160, 20);
        } else {
            tileColor = new Color(150, 200, 100);
        }
        g.setColor(tileColor);
        g.fillRect(x, y, TILE_SIZE, TILE_SIZE);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, TILE_SIZE, TILE_SIZE);


        if (value != 0) {
            String text = String.valueOf(value);
            g.setColor(Color.BLACK);
            g.drawString(text, x + TILE_SIZE / 4, y + TILE_SIZE / 2);
        }
    }
}