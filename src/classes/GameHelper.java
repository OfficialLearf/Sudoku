package classes;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * The type Game helper.
 */
public class GameHelper {
    private final Board board;
    private final JFrame frame;
    private final JPanel boardPanel;
    private int elapsedTime;
    private int score = 1000;
    private int helpCount = 0;
    private Timer timer;

    /**
     * Instantiates a new Game helper.
     *
     * @param board       the board
     * @param frame       the frame
     * @param boardPanel  the board panel
     * @param elapsedTime the elapsed time
     */
    public GameHelper(Board board, JFrame frame, JPanel boardPanel,int elapsedTime) {
        this.board = board;
        this.frame = frame;
        this.boardPanel = boardPanel;
        this.elapsedTime = elapsedTime;
    }

    /**
     * Start timer.
     */
    public void startTimer() {
        timer = new Timer(1000, e -> {
            elapsedTime++;
            updateTimerLabel();
        });
        timer.start();
    }


    /**
     * Stop timer.
     */
    public void stopTimer() {
        if (timer != null) {
            timer.stop();
        }
    }


    private void updateTimerLabel() {
        int minutes = elapsedTime / 60;
        int seconds = elapsedTime % 60;
        frame.setTitle("Sudoku - Idő: " + minutes + " perc " + seconds + " mp");
    }

    /**
     * Sets help count.
     *
     * @param count the count
     */
    public void setHelpCount(int count) {
        helpCount = count;
    }

    /**
     * Sets elapsed time.
     *
     * @param time the time
     */
    public void setElapsedTime(int time) {
        elapsedTime = time;
    }

    /**
     * Provide help.
     *
     * @param board the board
     */
    public void provideHelp(Board board) {
        int randomRow = 0;
        int randomCol = 0;
        Random random = new Random();
        while(board.getCell(randomRow, randomCol) != 0) {
            randomRow = random.nextInt(board.board.length);
            randomCol = random.nextInt(board.board.length);
        }
        board.setCell(randomRow, randomCol, board.getSolvedBoardCell(randomRow, randomCol));
        updateCellUI(randomRow,randomCol,board.getSolvedBoardCell(randomRow, randomCol));
        helpCount++;
        updateScore();
    }

    /**
     * Update cell ui.
     *
     * @param row   the row
     * @param col   the col
     * @param value the value
     */
    public void updateCellUI(int row, int col, int value) {
        int componentIndex = row * board.board.length + col;
        Component cell = boardPanel.getComponent(componentIndex);
        if (cell instanceof JTextField textField) {
            textField.setText(String.valueOf(value));
            textField.setBackground(Color.GREEN);
            textField.repaint();
            boardPanel.repaint();
        } else {
            System.err.println("Expected a JTextField at row: " + row + ", col: " + col);
        }
    }

    /**
     * Update score.
     */
    public void updateScore() {
        int difficultyMultiplier = switch (board.getDifficulty()) {
            case EASY -> 2;
            case MEDIUM -> 1;
            case HARD -> 1/2;
        };
        score = Math.max(0, 1000 - (helpCount * 50) - elapsedTime * difficultyMultiplier);
    }

    /**
     * Gets score.
     *
     * @return the score
     */
    public int getScore() {
        return score;
    }

    /**
     * Gets time.
     *
     * @return the time
     */
    public int getTime() {
        return elapsedTime;
    }
}