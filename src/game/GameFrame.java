package game;

import javax.swing.*;

public class GameFrame extends JFrame {
    GamePanel gp = new GamePanel();
    public GameFrame()
    {
        setTitle("Game2D");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(gp);
        pack();
        gp.createBufferStrategy(3);
        gp.game.start();
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
