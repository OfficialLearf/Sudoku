package classes;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;

/**
 * The type Sudoku.
 */
public class Sudoku {
    private JFrame frame;
    private JPanel boardPanel;
    private final Main.Difficulty difficulty;
    private final Board board;
    private final int size;
    private boolean isWon = false;
    private final boolean isLoadedGame;
    private JProgressBar progressBar;
    private int finalScore;
    private GameHelper gameHelper;
    private final int elapsedTime;


    /**
     * Instantiates a new Sudoku.
     *
     * @param board        the board
     * @param isLoadedGame the is loaded game
     */
    public Sudoku(Board board, boolean isLoadedGame) {
        this.board = board;
        this.difficulty = board.getDifficulty();
        this.size = board.board.length;
        this.isLoadedGame = isLoadedGame;
        if(isLoadedGame) elapsedTime = board.getElapsedTime();
        else elapsedTime = 0;

    }

    /**
     * Sets frame.
     */
    public void setFrame()
    {

        frame = new JFrame("Sudoku");

        JMenuBar menuBar = new JMenuBar();
        JMenu options = new JMenu("Options");
        JMenuItem mainMenu = new JMenuItem("Main Menu");
        JMenuItem help = new JMenuItem("Help");
        JMenuItem restartGame = new JMenuItem("Restart");
        JMenuItem save = new JMenuItem("Save");
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(e -> System.exit(0));
        save.addActionListener(e -> saveGame());
        restartGame.addActionListener(e -> restartGame(boardPanel,frame));
        help.addActionListener(e -> {
            gameHelper.provideHelp(board);
            progressBar.setValue(calculateProgressPercentage());
            if (!isWon && board.isBoardFilledAndValid(board)) {
                isWon = true;
                showWinMessage();
            }


        });
        mainMenu.addActionListener(e -> {
            Main.setMainPanel();
            frame.dispose();
        });
        options.add(mainMenu);
        options.add(help);
        options.add(restartGame);
        options.add(save);
        options.add(exit);
        menuBar.add(options);

        progressBar = new JProgressBar(0, 100);

        progressBar.setStringPainted(true);

        frame.add(progressBar, BorderLayout.SOUTH);

        frame.setJMenuBar(menuBar);

        boardPanel = new JPanel(new GridLayout(board.board.length, board.board.length));
        if(!isLoadedGame)this.board.generateSudoku(board.board.length,difficulty);
        initializeBoard(this.board);
        progressBar.setValue(calculateProgressPercentage());
        frame.add(boardPanel, BorderLayout.CENTER);
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
        gameHelper = new GameHelper(board,frame, boardPanel,elapsedTime);
        gameHelper.startTimer();
    }

    /**
     * Calculate progress percentage int.
     *
     * @return the int
     */
    public int calculateProgressPercentage() {
        int filledCells = (int) Arrays.stream(board.board).flatMapToInt(Arrays::stream).filter(cell -> cell != 0).count();
        return (int)(filledCells/Math.pow(size,2)* 100);
    }
    private void initializeBoard(Board board) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                JTextField cell = new JTextField();
                cell.setHorizontalAlignment(JTextField.CENTER);
                ((AbstractDocument)cell.getDocument()).setDocumentFilter(new SudokuDocumentFilter(size));
                cell.setText(String.valueOf(board.getCell(i, j)));
                int finalI = i;
                int finalJ = j;
                cell.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyReleased(KeyEvent e) {
                        String text = cell.getText();
                        if (text.isEmpty()) {
                            cell.setBackground(Color.WHITE);
                            board.setCell(finalI, finalJ, 0);
                            progressBar.setValue(calculateProgressPercentage());
                            return;
                        }
                        if (!text.matches("\\d{1,2}")) {
                            cell.setText("");
                            cell.setBackground(Color.WHITE);
                            board.setCell(finalI, finalJ, 0);
                            progressBar.setValue(calculateProgressPercentage());
                            return;
                        }
                        int num = Integer.parseInt(text);
                        if (num < 1 || num > size) {
                            cell.setText("");
                            cell.setBackground(Color.WHITE);
                            board.setCell(finalI, finalJ, 0);
                            progressBar.setValue(calculateProgressPercentage());
                            return;
                        }
                        if (board.getCell(finalI, finalJ) != num) {
                            if (Board.isValid(board, finalI, finalJ, num)) {
                                cell.setBackground(Color.WHITE);
                                board.setCell(finalI, finalJ, num);
                                progressBar.setValue(calculateProgressPercentage());
                            } else {
                                cell.setBackground(Color.RED);

                            }
                        }
                        if (!isWon && board.isBoardFilledAndValid(board)) {
                            isWon = true;
                            showWinMessage();
                        }
                    }
                });
                if(board.getCell(finalI,finalJ) != 0) {
                    cell.setEditable(false);
                }
                boardPanel.add(cell);

            }
        }


    }

    private void showWinMessage() {
        String[] options = {"Restart", "Main Menu"};
        gameHelper.stopTimer();
        gameHelper.updateScore();
        int choice = JOptionPane.showOptionDialog(
                boardPanel,
                "Congratulations, you won!",
                "Your score: " + gameHelper.getScore(),
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]);
        if (choice == JOptionPane.YES_OPTION) {
            restartGame(boardPanel,frame);
        } else if (choice == JOptionPane.NO_OPTION) {
            Main.setMainPanel();
            frame.dispose();

        }
    }

    public void restartGame(JPanel panel, JFrame frame) {
        JOptionPane.showMessageDialog(panel, "Starting new game!");
        Board newBoard = new Board(size);
        newBoard.setDifficulty(difficulty);
        Sudoku newSudoku = new Sudoku(newBoard,false);
        newSudoku.setFrame();
        frame.dispose();
    }

    /**
     * Sets game helper.
     *
     * @param gameHelper the game helper
     */
    public void setGameHelper(GameHelper gameHelper) {
        this.gameHelper = gameHelper;
    }

    /**
     * Gets frame.
     *
     * @return the frame
     */
    public JFrame getFrame() {
        return frame;
    }

    /**
     * Gets board panel.
     *
     * @return the board panel
     */
    public JPanel getBoardPanel() {
        return boardPanel;
    }

    /**
     * Gets elapsed time.
     *
     * @return the elapsed time
     */
    public int getElapsedTime()
    {
        return elapsedTime;
    }

    /**
     * Save game.
     */
    public void saveGame() {
        board.setElapsedTime(gameHelper.getTime());
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showSaveDialog(frame);
        if (option == JFileChooser.APPROVE_OPTION) {
            String filename = fileChooser.getSelectedFile().getPath();
            board.saveBoard(filename);
            JOptionPane.showMessageDialog(frame, "Game saved successfully!");
        }
    }

    /**
     * Gets is won.
     *
     * @return the is won
     */
    public boolean getIsWon() {
        return isWon;
    }
}
