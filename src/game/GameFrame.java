package game;

import javax.swing.*;

public class GameFrame extends JFrame {
    public GameFrame()
    {
        setTitle("Game2D");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(new GamePanel());
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
