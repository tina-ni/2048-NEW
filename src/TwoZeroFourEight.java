/**
 * CIS 120 HW09 - TicTacToe Demo
 * (c) University of Pennsylvania
 * Created by Bayley Tuch, Sabrina Green, and Nicolas Corona in Fall 2020.
 */


import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.Deque;
/**
 * This class is a model for TicTacToe.
 *
 * This game adheres to a Model-View-Controller design framework.
 * This framework is very effective for turn-based games. We
 * STRONGLY recommend you review these lecture slides, starting at
 * slide 8, for more details on Model-View-Controller:
 * https://www.seas.upenn.edu/~cis120/current/files/slides/lec36.pdf
 *
 * This model is completely independent of the view and controller.
 * This is in keeping with the concept of modularity! We can play
 * the whole game from start to finish without ever drawing anything
 * on a screen or instantiating a Java Swing object.
 *
 * Run this file to see the main method play a game of TicTacToe,
 * visualized with Strings printed to the console.
 */
public class TwoZeroFourEight {
    private int[][] board;
    private final int SIZE = 4;
    private Deque<int[][]> undoDeque;


    public TwoZeroFourEight() {
        board = new int[SIZE][SIZE];
        undoDeque = new ArrayDeque<>();
        reset();
    }


    // reset the game
    public void reset() {
        board = new int[SIZE][SIZE];
        undoDeque.clear();
        generateTile();
        generateTile();
    }


    // just for testing
    public void setBoard(int[][] newBoard) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = newBoard[i][j];
            }
        }
    }


    // returns the board currently
    public int[][] getBoard() {
        return board;
    }


    // adds the current game board to the undoDeque
    private void saveState() {
        int[][] boardCopy = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                boardCopy[i][j] = board[i][j];
            }
        }
        undoDeque.offerLast(boardCopy);
    }


    // undoes last move and removes it from the deque
    public void undo() {
        if (!undoDeque.isEmpty()) {
            board = undoDeque.pollLast();
        }
    }


    // generate a new tile in an empty cell
    public void generateTile() {
        int emptyCount = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == 0) {
                    emptyCount++;
                }
            }
        }
        if (emptyCount == 0) {
            return;
        } else {
            ArrayList<Integer> rows = new ArrayList<>();
            ArrayList<Integer> columns = new ArrayList<>();
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    if (board[i][j] == 0) {
                        rows.add(i);
                        columns.add(j);
                    }
                }
            }
            int randomTile = (int) (Math.random() * rows.size());


            int row = rows.get(randomTile);
            int column = columns.get(randomTile);


            int twoOrFour = 0;
            if (Math.random() < 0.1) {
                twoOrFour = 4;
            } else {
                twoOrFour = 2;
            }


            board[row][column] = twoOrFour;
        }
    }


    // following few are for saving undo functionality
    // as well as for merging tiles


    // when tiles move up, save board then merge tiles
    public boolean moveUp() {
        saveState();
        return moveAndMergeUp();
    }

    // when tiles move down, save board then merge tiles
    public boolean moveDown() {
        saveState();
        return moveAndMergeDown();
    }

    // when tiles move left, save board then merge tiles
    public boolean moveLeft() {
        saveState();
        return moveAndMergeLeft();
    }

    // when tiles move right, save board then merge tiles
    public boolean moveRight() {
        saveState();
        return moveAndMergeRight();
    }

    // move up; columns are same, rows change
    private boolean moveAndMergeUp() {
        boolean moved = false;
        // store current column
        for (int j = 0; j < SIZE; j++) {
            int[] column = new int[SIZE];
            for (int i = 0; i < SIZE; i++) {
                column[i] = board[i][j];
            }
            int[] mergedColumn = mergeLine(column);
            for (int i = 0; i < SIZE; i++) {
                if (board[i][j] != mergedColumn[i]) {
                    moved = true;
                    board[i][j] = mergedColumn[i];
                }
            }
        }
        return moved;
    }

    // move down; columns are same, rows change
    private boolean moveAndMergeDown() {
        boolean moved = false;
        for (int j = 0; j < SIZE; j++) {
            int[] column = new int[SIZE];
            for (int i = 0; i < SIZE; i++) {
                column[SIZE - 1 - i] = board[i][j];
            }
            int[] mergedColumn = mergeLine(column);
            for (int i = 0; i < SIZE; i++) {
                if (board[SIZE - 1 - i][j] != mergedColumn[i]) {
                    moved = true;
                    board[SIZE - 1 - i][j] = mergedColumn[i];
                }
            }
        }
        return moved;
    }

    // move left; rows are stay, columns change
    private boolean moveAndMergeLeft() {
        boolean moved = false;
        for (int i = 0; i < SIZE; i++) {
            int[] row = new int[SIZE];
            for (int j = 0; j < SIZE; j++) {
                row[j] = board[i][j];
            }
            int[] mergedRow = mergeLine(row);
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] != mergedRow[j]) {
                    moved = true;
                    board[i][j] = mergedRow[j];
                }
            }
        }
        return moved;
    }


    // move right; rows are same, columns change
    private boolean moveAndMergeRight() {
        boolean moved = false;
        for (int i = 0; i < SIZE; i++) {
            int[] row = new int[SIZE];
            for (int j = 0; j < SIZE; j++) {
                row[SIZE - 1 - j] = board[i][j];
            }
            int[] mergedRow = mergeLine(row);
            for (int j = 0; j < SIZE; j++) {
                if (board[i][SIZE - 1 - j] != mergedRow[j]) {
                    moved = true;
                    board[i][SIZE - 1 - j] = mergedRow[j];
                }
            }
        }
        return moved;
    }

    // merge a row/column
    private int[] mergeLine(int[] line) {
        ArrayList<Integer> result = new ArrayList<>();
        int i = 0;
        // merge tiles next to each other
        while (i < line.length) {
            if (line[i] != 0) {
                if (i + 1 < line.length && line[i] == line[i + 1]) {
                    // merge tiles with same number and add to result
                    result.add(line[i] * 2);
                    i += 2;
                } else {
                    // add the tile now as is (tiles don't merge)
                    result.add(line[i]);
                    i++;
                }
            } else {
                i++;
            }
        }
        while (result.size() < SIZE) {
            result.add(0);
        }

        // change into an int[] array
        int[] resultArray = new int[result.size()];
        for (int j = 0; j < result.size(); j++) {
            resultArray[j] = result.get(j);
        }

        return resultArray;
    }


    // player wins if 2048 is achieved
    public boolean checkWin() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == 2048) {
                    return true;
                }
            }
        }
        return false;
    }

    // game is over if player can not move any more
    // (all spaces filed and no more merging possible)
    public boolean checkGameOver() {
        // check if there's still empty space
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == 0) {
                    return false;
                }
            }
        }

        // check if there is still ability to merge
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (i + 1 < SIZE && board[i][j] == board[i + 1][j]) {
                    return false;
                }
                if (j + 1 < SIZE && board[i][j] == board[i][j + 1]) {
                    return false;
                }
            }
        }

        return true;
    }
}