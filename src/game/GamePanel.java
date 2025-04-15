package game;

import entities.Player;
import world.Camera;
import world.World;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel
{
    int originalTileSize = 32;
    int scale = 3;
    public int tileSize = originalTileSize*scale;
    int gameCols = 9;
    int gameRows = 9;
    public int screenWidth = gameCols*tileSize;
    public int screenHeight = gameRows*tileSize;
    GameLoop gl;

    public final double FPS = 60.0;
    public KeyHandler kh = new KeyHandler();

    public Camera camera = new Camera();

    public World world = new World(this);

    public Player player = new Player(this,world);

    Thread game;

    public GamePanel()
    {
        setPreferredSize(new Dimension(screenWidth,screenHeight));
        setBackground(Color.black);
        setFocusable(true);
        addKeyListener(kh);
        gl = new GameLoop(this);
        game = new Thread(gl);
        game.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;
        world.render(g2d);
        player.render(g2d);
        g2d.setFont(new Font("Arial",Font.BOLD,30));
        g2d.setColor(Color.WHITE);
        g2d.drawString("FPS: " + String.valueOf(gl.finalFps),10,40);
    }
}
