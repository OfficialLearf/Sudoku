package tests;

import classes.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;


/**
 * The type Game helper test.
 */
class GameHelperTest {

    private Board board;
    private GameHelper gameHelper;

    /**
     * Sets up.
     */
    @BeforeEach
    void beforeEach() {
        board = new Board(9);
        board.setDifficulty(Main.Difficulty.MEDIUM);
        Sudoku sudoku = new Sudoku(board, false);
        sudoku.setFrame();
        Board.solve(board, 0, 0);
        board.setSolvedBoard(board);
        Board.createBoard(board, Main.Difficulty.MEDIUM);
        gameHelper = new GameHelper(board, sudoku.getFrame(), sudoku.getBoardPanel(), 0);
    }

    /**
     * Test start timer.
     */
    @Test
    void testStartTimer() {
        gameHelper.startTimer();
        int initialTime = gameHelper.getTime();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(gameHelper.getTime() > initialTime);
    }

    /**
     * Test stop timer.
     */
    @Test
    void testStopTimer() {
        gameHelper.startTimer();
        int initialTime = gameHelper.getTime();
        gameHelper.stopTimer();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(initialTime, gameHelper.getTime());
    }

    /**
     * Test provide help.
     */
    @Test
    void testProvideHelp() {
        int initialScore = gameHelper.getScore();
        gameHelper.provideHelp(board);
        assertTrue(gameHelper.getScore() < initialScore);
    }

    /**
     * Test update score 1.
     */
    @Test
    void testUpdateScore1() {
        // Test for different difficulties and scoring mechanics
        int initialScore = gameHelper.getScore();
        gameHelper.setHelpCount(1);
        gameHelper.updateScore();
        int updatedScore = gameHelper.getScore();

        // Score should change based on elapsed time and helpCount
        assertNotEquals(initialScore, updatedScore);
        assertEquals(950, updatedScore);
    }

    /**
     * Test update score 2.
     */
    @Test
    void testUpdateScore2() {
        // Test for different difficulties and scoring mechanics
        int initialScore = gameHelper.getScore();
        gameHelper.setElapsedTime(100);
        gameHelper.updateScore();
        int updatedScore = gameHelper.getScore();
        // Score should change based on elapsed time and helpCount
        assertNotEquals(initialScore, updatedScore);
        assertEquals(900, updatedScore);
    }

}
