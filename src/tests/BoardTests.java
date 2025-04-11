package tests;

import classes.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Board tests.
 */
class BoardTests {
    /**
     * Test set cell.
     */
    @Test
    void testSetCell() {
        Board board = new Board(9);
        board.setCell(0, 0, 5);
        assertEquals(5, board.getCell(0, 0));
    }

    /**
     * Test set get cell.
     */
    @Test
    void testSetGetCell() {
        Board board = new Board(9);
        board.setCell(0, 0, 5);
        assertEquals(5, board.getCell(0, 0));
    }

    /**
     * Test get row.
     */
    @Test
    void testGetRow() {
        Board board = new Board(9);
        board.setCell(0, 0, 5);
        int[] row = board.getRow(0);
        assertEquals(5, row[0]);
    }

    /**
     * Test get column.
     */
    @Test
    void testGetColumn() {
        Board board = new Board(9);
        board.setCell(0, 0, 5);
        board.setCell(1, 0, 8);
        int[] column = board.getColumn(0);
        assertEquals(5, column[0]);
        assertEquals(8, column[1]);
    }

    /**
     * Test get subgrid.
     */
    @Test
    void testGetSubgrid() {
        Board board = new Board(9);
        board.setCell(0, 0, 5);
        board.setCell(1, 1, 8);
        int[][] subgrid = board.getSubgrid(board, 0, 0);
        assertEquals(5, subgrid[0][0]);
        assertEquals(8, subgrid[1][1]);
    }

    /**
     * Test is valid.
     */
    @Test
    void testIsValid() {
        Board board = new Board(9);
        board.setCell(0, 0, 5);
        assertTrue(Board.isValid(board, 1, 1, 8));
        assertFalse(Board.isValid(board, 0, 1, 5));
    }

    /**
     * Test solve.
     */
    @Test
    void testSolve() {
        Board board = new Board(9);
        board.generateSudoku(9, Main.Difficulty.MEDIUM);
        assertTrue(Board.solve(board, 0, 0));
    }

    /**
     * Test save and load board.
     */
    @Test
    void testSaveAndLoadBoard() {
        Board board = new Board(9);
        board.setCell(0, 0, 5);
        board.saveBoard("testBoard.ser");

        Board loadedBoard = Board.loadBoard("testBoard.ser");
        assertNotNull(loadedBoard);
        assertEquals(5, loadedBoard.getCell(0, 0));
    }
}
