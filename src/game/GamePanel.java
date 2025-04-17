package game;

import entities.Player;
import world.Camera;
import world.Tree;
import world.World;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel
{
    public int originalTileSize = 32;
    public double scale = 3;
    public int tileSize = (int)((double)originalTileSize*scale);
    public int gameCols = 16;
    public int gameRows = 9;
    public int screenWidth = gameCols*tileSize;
    public int screenHeight = gameRows*tileSize;
    List<Renderable> objetos = new ArrayList<>();
    GameLoop gl;

    public final double FPS = 120.0;
    public KeyHandler kh = new KeyHandler();

    public Camera camera = new Camera();

    public World world = new World(this);

    public Player player = new Player(this,world);

    public Tree tree = new Tree(this, camera,2*tileSize,2*tileSize);

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
        objetos.add(player);
        objetos.add(tree);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        //transformando o pincel em um super pincel
        Graphics2D g2d = (Graphics2D) g;

        //renderizando a camada do mundo
        world.render(g2d);

        //renderizando a camada dos objetos
        for(Renderable obj : objetos){
            obj.render(g2d);
        }
        g2d.setFont(new Font("Arial",Font.BOLD,30));
        g2d.setColor(Color.WHITE);
        g2d.drawString("FPS: " + String.valueOf(gl.finalFps),10,40);
    }
}
