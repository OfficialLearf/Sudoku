package classes;

import java.io.*;
import java.util.*;

/**
 * The type Board.
 */
public class Board implements Serializable {
    /**
     * The Board.
     */
    int[][] board;
    private boolean isWon;
    /**
     * The Difficulty.
     */
    Main.Difficulty difficulty;
    private static final long serialVersionUID = 1L;
    /**
     * The Solved board.
     */
    int[][] solvedBoard;
    private int elapsedTime;


    /**
     * Instantiates a new Board.
     *
     * @param size the size
     */
    public Board(int size)
    {
        board = new int[size][size];
    }

    /**
     * Sets cell.
     *
     * @param row   the row
     * @param col   the col
     * @param value the value
     */
    public void setCell(int row, int col, int value)
    {
        board[row][col] = value;
    }

    /**
     * Sets elapsed time.
     *
     * @param time the time
     */
    public void setElapsedTime(int time)
    {
        this.elapsedTime = time;
    }

    /**
     * Gets elapsed time.
     *
     * @return the elapsed time
     */
    public int getElapsedTime()
    {
        return this.elapsedTime;
    }

    /**
     * Gets solved board cell.
     *
     * @param row the row
     * @param col the col
     * @return the solved board cell
     */
    public int getSolvedBoardCell(int row, int col)
    {
        return solvedBoard[row][col];
    }

    /**
     * Gets cell.
     *
     * @param row the row
     * @param col the col
     * @return the cell
     */
    public int getCell(int row, int col)
    {
        return board[row][col];
    }

    /**
     * Get row int [ ].
     *
     * @param row the row
     * @return the int [ ]
     */
    public int[] getRow(int row)
    {
        return board[row];
    }

    /**
     * Gets difficulty.
     *
     * @return the difficulty
     */
    public Main.Difficulty getDifficulty() {
        return this.difficulty;
    }

    /**
     * Sets difficulty.
     *
     * @param difficulty the difficulty
     */
    public void setDifficulty(Main.Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * Get column int [ ].
     *
     * @param col the col
     * @return the int [ ]
     */
    public int[] getColumn(int col)
    {
        int[] column = new int[board.length];
        for(int i = 0; i < board.length;i++)
        {
            column[i] = board[i][col];
        }
        return column;
    }

    /**
     * Get subgrid int [ ] [ ].
     *
     * @param board the board
     * @param row   the row
     * @param col   the col
     * @return the int [ ] [ ]
     */
    public int[][] getSubgrid(Board board,int row, int col)
    {
        int subgridSize = (int) Math.sqrt(board.board.length);
        int[][] subgrid = new int[subgridSize][subgridSize];
        int startRow = (row / subgridSize) * subgridSize;
        int startCol = (col / subgridSize) * subgridSize;
        for (int i = 0; i < subgridSize; i++)
            System.arraycopy(board.board[startRow + i], startCol, subgrid[i], 0, subgridSize);
        return subgrid;
    }

    /**
     * Is valid boolean.
     *
     * @param board the board
     * @param row   the row
     * @param col   the col
     * @param value the value
     * @return the boolean
     */
    public static boolean isValid(Board board, int row, int col, int value)
    {
        List<Integer> currentRow = new ArrayList<>();
        List<Integer> currentColumn = new ArrayList<>();
        List<Integer> currentSubgrid = new ArrayList<>();
        for(int c : board.getColumn(col))
        {
            currentColumn.add(c);
        }
        for(int r : board.getRow(row))
        {
            currentRow.add(r);
        }
        int[][] subgridArray = board.getSubgrid(board,row,col);
        for (int[] ints : subgridArray) for (int anInt : ints) currentSubgrid.add(anInt);
        boolean not_in_subgrid = !currentSubgrid.contains(value);
        boolean not_in_row = !currentRow.contains(value);
        boolean not_in_column = !currentColumn.contains(value);

        return not_in_subgrid && not_in_row && not_in_column;

    }

    /**
     * Solve boolean.
     *
     * @param board the board
     * @param row   the row
     * @param col   the col
     * @return the boolean
     */
    public static boolean solve(Board board, int row, int col) {
        if (row == board.board.length) {
            return true;
        } else if (col == board.board.length) {
            return solve(board, row + 1, 0);
        } else if (board.getCell(row, col) != 0) {
            return solve(board, row, col + 1);
        } else {
            List<Integer> numbers = generateRandomNumbers(board.board.length);
            for (int i : numbers) {
                if (isValid(board, row, col, i)) {
                    board.setCell(row, col, i);
                    if (solve(board, row, col + 1)) {
                        return true;
                    } else {
                        board.setCell(row, col, 0);
                    }
                }
            }
        }
        return false;
    }
    private static List<Integer> generateRandomNumbers(int size) {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            numbers.add(i);
        }
        Collections.shuffle(numbers);
        return numbers;
    }

    /**
     * Calculate cells to remove int.
     *
     * @param boardSize  the board size
     * @param difficulty the difficulty
     * @return the int
     */
    public static int calculateCellsToRemove(int boardSize, Main.Difficulty difficulty)
    {
        return switch (difficulty) {
            case EASY -> boardSize * boardSize / 5;
            case MEDIUM -> (boardSize * boardSize / 4);
            case HARD -> (boardSize * boardSize / 3);
        };
    }

    /**
     * Generate sudoku.
     *
     * @param size       the size
     * @param difficulty the difficulty
     */
    public  void generateSudoku(int size, Main.Difficulty difficulty)
    {
        Board sudoku = new Board(size);
        solve(sudoku,0,0);
        sudoku.solvedBoard = Arrays.stream(sudoku.board).map(int[]::clone).toArray(int[][]::new);
        createBoard(sudoku,difficulty);
        this.board = sudoku.board;
        this.solvedBoard = sudoku.solvedBoard;
        this.difficulty = difficulty;
    }

    /**
     * Create board.
     *
     * @param board      the board
     * @param difficulty the difficulty
     */
    public static void createBoard(Board board, Main.Difficulty difficulty) {
        int cellsToRemove = calculateCellsToRemove(board.board.length, difficulty);

        while (cellsToRemove > 0) {
            int row = (int) (Math.random() * board.board.length);
            int col = (int) (Math.random() * board.board.length);

            if(!(board.getCell(row,col) == 0))
            {
                board.setCell(row,col,0);
            }
            else {
                cellsToRemove--;
            }

        }

    }

    /**
     * Is board filled and valid boolean.
     *
     * @param board the board
     * @return the boolean
     */
    public boolean isBoardFilledAndValid(Board board) {
        for (int[] row : board.board) {
            for (int cell : row) {
                if (cell == 0) {
                    return false;
                }
            }
        }
        for (int i = 0; i < board.board.length; i++) {
            for (int j = 0; j < board.board[i].length; j++) {
                int num = board.getCell(i, j);
                int originalValue = board.getCell(i, j);
                board.setCell(i, j, 0);
                if (num != 0 && !isValid(board, i, j, num)) {
                    board.setCell(i, j, originalValue);
                    return false;
                }
                board.setCell(i, j, originalValue);
            }
        }
        isWon = true;
        return true;
    }

    /**
     * Save board.
     *
     * @param filename the filename
     */
    public void saveBoard(String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load board board.
     *
     * @param filename the filename
     * @return the board
     */
    public static Board loadBoard(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            return (Board) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Sets solved board.
     *
     * @param board the board
     */
    public void setSolvedBoard(Board board) {
        this.solvedBoard  = Arrays.stream(board.board).map(int[]::clone).toArray(int[][]::new);
    }
}
