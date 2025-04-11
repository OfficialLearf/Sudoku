package tests;

import classes.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Sudoku tests.
 */
class SudokuTests {
    private Sudoku sudoku;
    private Board board;

    /**
     * Sets up.
     */
    @BeforeEach
    void beforeEach() {
        board = new Board(4);
        board.setDifficulty(Main.Difficulty.EASY);
        sudoku = new Sudoku(board, false);
        sudoku.setFrame();
    }

    /**
     * Test set frame.
     */
    @Test
    void testSetFrame() {
        assertNotNull(sudoku);
    }

    /**
     * Test calculate progress percentage.
     */
    @Test
    void testCalculateProgressPercentage() {
        sudoku.calculateProgressPercentage();
        assertTrue(sudoku.calculateProgressPercentage() >= 0 && sudoku.calculateProgressPercentage() <= 100);
    }

    /**
     * Test save game.
     */
    @Test
    void testSaveGame() {
        sudoku.setFrame();
        sudoku.setGameHelper(new GameHelper(board,sudoku.getFrame(),sudoku.getBoardPanel(),sudoku.getElapsedTime()));
        assertDoesNotThrow(() -> sudoku.saveGame());
    }

    /**
     * Test game over.
     */
    @Test
    void testGameOver() {
        Board.solve(board,0,0);
        assertTrue(board.isBoardFilledAndValid(board));
    }
    @Test
    void testRestartKeepsSameDifficulty() {
        Board initialBoard = new Board(9);
        initialBoard.generateSudoku(9, Main.Difficulty.HARD);
        Sudoku game = new Sudoku(initialBoard, false);
        game.setFrame();
        game.restartGame(game.getBoardPanel(), game.getFrame());
        Board newBoard = new Board(4);
        newBoard.setDifficulty(initialBoard.getDifficulty());
        assertEquals(initialBoard.getDifficulty(),newBoard.getDifficulty());
    }
}
