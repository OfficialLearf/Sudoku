package classes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * The type Menu button.
 */
public class MenuButton extends JButton {

    private CardLayout panelLayout;
    private JPanel panel;

    /**
     * Instantiates a new Menu button.
     *
     * @param name        the name
     * @param dimension   the dimension
     * @param panelLayout the panel layout
     * @param panel       the panel
     * @param frame       the frame
     */
    public MenuButton(String name, Dimension dimension, CardLayout panelLayout, JPanel panel,JFrame frame) {
        super(name);
        this.panelLayout = panelLayout;
        this.panel = panel;
        this.setPreferredSize(dimension);
        this.setMaximumSize(dimension);
        this.addActionListener((ActionEvent actionEvent) -> {
            Main.setPanelContent(name, panel,frame);
            panelLayout.show(panel, name);
        });

    }
}


