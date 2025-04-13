package game;

import entities.Player;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel
{
    int originalTileSize = 32;
    int scale = 3;
    public int tileSize = originalTileSize*scale;
    int gameCols = 16;
    int gameRows = 9;
    public int screenWidth = gameCols*tileSize;
    public int screenHeight = gameRows*tileSize;

    public final double FPS = 240.0;
    public KeyHandler kh = new KeyHandler();

    public Player player = new Player(this);

    Thread game;

    public GamePanel()
    {
        setPreferredSize(new Dimension(screenWidth,screenHeight));
        setBackground(Color.black);
        setFocusable(true);
        addKeyListener(kh);
        game = new Thread(new GameLoop(this));
        game.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;
        player.render(g2d);
        g2d.setFont(new Font("Arial",Font.BOLD,50));
    }
}
