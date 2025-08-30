/*
 * CIS 120 HW09 - TicTacToe Demo
 * (c) University of Pennsylvania
 * Created by Bayley Tuch, Sabrina Green, and Nicolas Corona in Fall 2020.
 */


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


/**
 * This class sets up the top-level frame and widgets for the GUI.
 *
 * This game adheres to a Model-View-Controller design framework. This
 * framework is very effective for turn-based games. We STRONGLY
 * recommend you review these lecture slides, starting at slide 8,
 * for more details on Model-View-Controller:
 * https://www.seas.upenn.edu/~cis120/current/files/slides/lec37.pdf
 *
 * In a Model-View-Controller framework, Game initializes the view,
 * implements a bit of controller functionality through the reset
 * button, and then instantiates a GameBoard. The GameBoard will
 * handle the rest of the game's view and controller functionality, and
 * it will instantiate a TicTacToe object to serve as the game's model.
 */


public class RunTwoZeroFourEight implements Runnable {
    public void run() {
        final JFrame frame = new JFrame("2048");
        frame.setLocation(300, 300);

        // new gameBoard
        final GameBoard board = new GameBoard();
        frame.add(board, BorderLayout.CENTER);

        // make undo button
        JButton undoButton = new JButton("Undo");
        undoButton.addActionListener(e -> {
            board.undo();
            board.repaint();
        });

        // undo button added to bottom (south)
        JPanel controlPanel = new JPanel();
        controlPanel.add(undoButton);
        frame.add(controlPanel, BorderLayout.SOUTH);

        // use key listener
        board.setFocusable(true);
        board.requestFocusInWindow();
        board.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                boolean moved = false;


                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    moved = board.moveUp();
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    moved = board.moveDown();
                } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    moved = board.moveLeft();
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    moved = board.moveRight();
                }


                if (moved) {
                    board.generateTile();
                    board.repaint();
                }
            }

            // extra functions unneeded but code doesn't compile without them
            @Override
            public void keyReleased(KeyEvent e) {}

            @Override
            public void keyTyped(KeyEvent e) {}
        });

        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        board.reset();
    }
}