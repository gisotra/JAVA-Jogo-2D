package game;

import entities.Follower;
import entities.Player;
import world.Camera;
import world.Tree;
import world.World;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends Canvas
{
    public int originalTileSize = 32;
    public double scale = 3;
    public int tileSize = (int)(originalTileSize*scale);
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
    public Follower follower1 = new Follower(this,world,0,0);
    public Follower follower2 = new Follower(this,world,3*tileSize,5*tileSize);
    public Follower follower3 = new Follower(this,world,8*tileSize,8*tileSize);
    public Follower follower4 = new Follower(this,world,20*tileSize,5*tileSize);
    public Follower follower5 = new Follower(this,world,1*tileSize,15*tileSize);
    public Follower follower6 = new Follower(this,world,12*tileSize,36*tileSize);

    public Tree tree = new Tree(this, camera,world.width/2,world.height/2);

    Cursor c;
    Point cursorPoint;

    BufferedImage mouseImg;

    Thread game;

    public GamePanel()
    {
        setPreferredSize(new Dimension(screenWidth,screenHeight));
        setBackground(Color.black);
        setFocusable(true);
        addKeyListener(kh);

        //carrega a textura do cursor
        try{
            mouseImg = ImageIO.read(getClass().getResourceAsStream("/cursor/cursor.png"));
        } catch (IOException e){}

        objetos.add(player);
        objetos.add(follower1);
        objetos.add(follower2);
        objetos.add(follower3);
        objetos.add(follower4);
        objetos.add(follower5);
        objetos.add(follower6);
        objetos.add(tree);

        //cria um cursor customizado invisivel
        c = Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB),new Point(0,0),"cursor");
        //seta o cursor invisivel para este componente (Canvas)
        setCursor(c);

        gl = new GameLoop(this);
        game = new Thread(gl);
    }

    public void render() {
        //obtem o bufferStrategy do componente
        BufferStrategy bufferStrategy = getBufferStrategy();
        Graphics2D g2d = (Graphics2D) bufferStrategy.getDrawGraphics();

        //começa a desenhar na imagem vazia
        g2d.setColor(Color.black);
        g2d.fillRect(0,0,getWidth(),getHeight());

        //renderizando a camada do mundo
        world.render(g2d);

        //renderizando a camada dos objetos
        for(Renderable obj : objetos){
            obj.render(g2d);
        }
        g2d.setFont(new Font("Arial",Font.BOLD,30));
        g2d.setColor(Color.WHITE);
        g2d.drawString("FPS: " + String.valueOf(gl.finalFps),10,40);

        //obter as coordenadas do mouse em relação e este componente e salvar em um objeto Point que tem x e y
        cursorPoint = getMousePosition();

        //se o cursor estiver na tela ele desenha nossa textura do mouse na posição do cursor
        if(cursorPoint != null)
            g2d.drawImage(mouseImg,cursorPoint.x,cursorPoint.y,(int)(mouseImg.getWidth()*scale), (int)(mouseImg.getHeight()*scale), null);

        g2d.dispose();
        bufferStrategy.show();
        Toolkit.getDefaultToolkit().sync();
    }
}
