package classes;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * The type Panel methods.
 */
public class PanelMethods {
    private JPanel panel;

    /**
     * Instantiates a new Panel methods.
     *
     * @param panel the panel
     */
    public PanelMethods(JPanel panel)
    {
        this.panel = panel;
    }

    /**
     * Sets main menu.
     *
     * @return the main menu
     */
    public JPanel setMainMenu()
    {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(Box.createRigidArea(new Dimension(0, 50)));
        JLabel title = new JLabel("Sudoku By GVK1G8", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(title);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        ImageIcon sudokuImage = new ImageIcon("src/SUDOKU.png");
        JLabel imageLabel = new JLabel(sudokuImage);
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(imageLabel);
        return panel;
    }


    /**
     * Sets new game.
     *
     * @param frame the frame
     * @return the new game
     */
    public JPanel setNewGame(JFrame frame) {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel contentPanel = new JPanel();

        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.add(Box.createRigidArea(new Dimension(0, 100)));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel difficultyLabel = new JLabel("Select Difficulty:");
        difficultyLabel.setFont(new Font("Arial", Font.BOLD, 18));
        difficultyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(difficultyLabel);

        ButtonGroup difficultyGroup = new ButtonGroup();
        JRadioButton easyButton = new JRadioButton("Easy");
        JRadioButton mediumButton = new JRadioButton("Medium");
        JRadioButton hardButton = new JRadioButton("Hard");

        difficultyGroup.add(easyButton);
        difficultyGroup.add(mediumButton);
        difficultyGroup.add(hardButton);

        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        contentPanel.add(easyButton);
        contentPanel.add(mediumButton);
        contentPanel.add(hardButton);


        JLabel sizeLabel = new JLabel("Select Board Size:");
        sizeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        sizeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        contentPanel.add(sizeLabel);

        ButtonGroup sizeGroup = new ButtonGroup();
        JRadioButton smallButton = new JRadioButton("4x4");
        JRadioButton mediumSizeButton = new JRadioButton("9x9");
        JRadioButton largeButton = new JRadioButton("16x16");

        sizeGroup.add(smallButton);
        sizeGroup.add(mediumSizeButton);
        sizeGroup.add(largeButton);

        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        contentPanel.add(smallButton);
        contentPanel.add(mediumSizeButton);
        contentPanel.add(largeButton);


        JButton startButton = new JButton("Start Game");
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.setBackground(new Color(0, 122, 204));
        startButton.setForeground(Color.WHITE);
        startButton.setFont(new Font("Arial", Font.BOLD, 16));
        startButton.setFocusPainted(false);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        contentPanel.add(startButton);

        startButton.addActionListener(e -> {
            Main.Difficulty difficulty = easyButton.isSelected() ? Main.Difficulty.EASY :
                    mediumButton.isSelected() ? Main.Difficulty.MEDIUM :
                            hardButton.isSelected() ? Main.Difficulty.HARD : null;

            String size = smallButton.isSelected() ? "4x4" :
                    mediumSizeButton.isSelected() ? "9x9" :
                            largeButton.isSelected() ? "16x16" : null;

            if (difficulty != null && size != null) {
                JOptionPane.showMessageDialog(panel, "Starting " + difficulty + " game with " + size + " board!");
                int boardSize = Integer.parseInt(size.substring(0, size.indexOf("x")));
                Board newBoard = new Board(boardSize);
                newBoard.setDifficulty(difficulty);
                Sudoku newSudoku = new Sudoku(newBoard,false);
                newSudoku.setFrame();
                frame.dispose();

            } else {
                JOptionPane.showMessageDialog(panel, "Please select difficulty and board size.");
            }
        });


        panel.add(contentPanel, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Sets load game.
     *
     * @param currentWindow the current window
     * @return the load game
     */
    public JPanel setLoadGame(JFrame currentWindow) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();


        JButton loadFile = new JButton("Load File");
        JButton loadButton = new JButton("Load Game");


        loadFile.setPreferredSize(new Dimension(150, 40));
        loadButton.setPreferredSize(new Dimension(150, 40));

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Load Sudoku Game");


        loadFile.addActionListener(e -> {
            int result = fileChooser.showOpenDialog(currentWindow);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                loadFile.setActionCommand(selectedFile.getPath());
            }
        });

        loadButton.addActionListener(e -> {
            String filePath = loadFile.getActionCommand();
            if (filePath != null && !filePath.isEmpty()) {
                File selectedFile = new File(filePath);
                loadGame(selectedFile, currentWindow);
            } else {
                JOptionPane.showMessageDialog(currentWindow, "Please select a file first.");
            }
        });


        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(loadFile, gbc);

        gbc.gridy = 1;
        panel.add(loadButton, gbc);

        return panel;
    }

    /**
     * Load game.
     *
     * @param selectedFile  the selected file
     * @param currentWindow the current window
     */
    public void loadGame(File selectedFile, JFrame currentWindow) {
        Sudoku sudoku;
        Board loadedBoard = Board.loadBoard(selectedFile.getPath());
        if (loadedBoard != null) {
            sudoku = new Sudoku(loadedBoard,true);
            sudoku.setFrame();
            currentWindow.dispose();
        } else {
            JOptionPane.showMessageDialog(currentWindow, "Failed to load the game.");
        }
    }

}
