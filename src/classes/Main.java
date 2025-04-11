package classes;

import javax.swing.*;
import java.awt.*;

/**
 * The type Main.
 */
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    /**
     * The enum Difficulty.
     */
    public enum Difficulty {
        /**
         * Easy difficulty.
         */
        EASY,
        /**
         * Medium difficulty.
         */
        MEDIUM,
        /**
         * Hard difficulty.
         */
        HARD;
    }

    /**
     * Main.
     *
     * @param args the args
     */
    public static void main(String[] args){
        setMainPanel();
    }

    /**
     * Sets main panel.
     */
    public static void setMainPanel()
    {
        JFrame frame = new JFrame("SUDOKU");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,700);
        frame.setResizable(false);
        makePanels(frame);
        frame.setVisible(true);
    }

    /**
     * Buttons.
     *
     * @param boxPanel   the box panel
     * @param cardLayout the card layout
     * @param panel      the panel
     * @param frame      the frame
     */
    public static void buttons(JPanel boxPanel,CardLayout cardLayout,JPanel panel,JFrame frame)
    {
        Dimension buttonSize = new Dimension(200,80);
        JLabel title = new JLabel("SUDOKU");
        title.setPreferredSize(buttonSize);
        title.setMaximumSize(buttonSize);
        title.setVerticalAlignment(JLabel.CENTER);
        title.setHorizontalAlignment(JLabel.CENTER);
        for(String s : buttonLabels)
        {
            MenuButton button = new MenuButton(s,buttonSize,cardLayout,panel,frame);
            boxPanel.add(button);
            boxPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Space between buttons
        }
    }

    /**
     * Make panels.
     *
     * @param frame the frame
     */
    public static void makePanels(JFrame frame)
    {
        JPanel left = new JPanel(new GridBagLayout());
        left.setPreferredSize(new Dimension(400,500));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;

        JPanel boxPanel = new JPanel();
        boxPanel.setLayout(new BoxLayout(boxPanel,BoxLayout.Y_AXIS));
        JPanel right = new JPanel();
        CardLayout cardLayout = new CardLayout();
        right.setBackground(Color.BLUE);
        right.setPreferredSize(new Dimension(400,500));
        right.setLayout(cardLayout);

        for(String s : buttonLabels)
        {
            JPanel panel = new JPanel(new GridBagLayout());
            setPanelContent(s,panel,frame);
            right.add(panel,s);

        }

        buttons(boxPanel,cardLayout,right,frame);
        left.add(boxPanel);
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,left,right);
        split.setDividerLocation(250);
        frame.add(split);
        setPanelContent("Main Menu",right,frame);
        right.revalidate();
        right.repaint();
    }

    /**
     * Set panel content.
     *
     * @param name  the name
     * @param panel the panel
     * @param frame the frame
     */
    static public void setPanelContent(String name,JPanel panel,JFrame frame){
        PanelMethods methods = new PanelMethods(panel);
        panel.removeAll();
        switch(name){
            case "New Game" -> panel.add(methods.setNewGame(frame));
            case "Load Game" -> panel.add(methods.setLoadGame(frame));
            default -> panel.add(methods.setMainMenu());
         }
        panel.revalidate();
        panel.repaint();
    }

    /**
     * The Button labels.
     */
    static public String[] buttonLabels = {
            "Main Menu",
            "New Game",
            "Load Game",
    };

}
