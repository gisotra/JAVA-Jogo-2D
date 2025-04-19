package game;

// Importa as outras classes do projeto
import entities.Follower;
import entities.Player;
import utilities.Global;
import world.Camera;
import world.Tree;
import world.World;

import javax.imageio.ImageIO;                   // Importa a classe ImageIO, usada para ler e escrever arquivos de imagem (como PNG, JPG, etc).
import java.awt.*;                              // Importa todo o conteudo da biblioteca AWT
import java.awt.image.BufferStrategy;           // Importa a classe de BufferStrategy, usada na classe GameFrame
import java.awt.image.BufferedImage;            // Importa a classe BufferedImage que representa uma imagem que pode ser manipulada pixel a pixel na memória.
import java.io.IOException;                     // Importa a classe IOException, usada para tratar erros que podem ocorrer durante operações de entrada/saída (como ler imagens de arquivos).
import java.util.ArrayList;                     // Biblioteca de ArrayList
import java.util.List;                          // Biblioteca Lista do Java

public class GamePanel extends Canvas  // Classe GamePanel extende Canvas
{
    /*========== ATRIBUTOS ==========*/

    public int originalTileSize = 32;                                               // Tamanho Original que vai ser usado para calcular os Tiles
    public double scale = 3;                                                        // Escalonamento
    public int tileSize = (int)(originalTileSize*scale);                            // Tamanho que será usado para pintar os Tiles e calcular Altura e Largura
    public int gameCols = 16;                                                       // Número de Colunas
    public int gameRows = 9;                                                        // Número de Linhas
    public int screenWidth = gameCols*tileSize;                                     // Comprimento da Tela
    public int screenHeight = gameRows*tileSize;                                    // Altura da Tela

    List<Renderable> objetos = new ArrayList<>();                                   // Arraylist da interface Renderable, que será usado para pintar os elementos do jogo (ex: Player, Inimigo, Boss...)

    GameLoop gl;                                                                    // Instância do tipo GameLoop

    public final double FPS = 120.0;                                                // Constante de FPS

    public KeyHandler kh = new KeyHandler();                                        // Objeto do tipo KeyHandler, que serve para os inputs do usuário no jogo

    public MouseInputs mI = new MouseInputs();

    public Camera camera = new Camera();                                            // Objeto do tipo Câmera

    public World world = new World(this);                                           // Objeto do tipo World

    public Player player = new Player(this,world);                                  // Objeto do tipo Player


    public Follower follower1 = new Follower(this,world,0,0);
    public Follower follower2 = new Follower(this,world,3*tileSize,5*tileSize);
    public Follower follower3 = new Follower(this,world,8*tileSize,8*tileSize);
    public Follower follower4 = new Follower(this,world,20*tileSize,5*tileSize);
    public Follower follower5 = new Follower(this,world,1*tileSize,15*tileSize);
    public Follower follower6 = new Follower(this,world,12*tileSize,36*tileSize);

    public Tree tree = new Tree(this, camera,world.width/2,world.height/2);

    Cursor c;                           // Atributo do tipo Cursor, que será usado para tratar do Mouse na janela gráfica

    Point cursorPoint;                  // Atributo do tipo Point, que vai tratar o clicker do mouse na janela gráfica

    BufferedImage mouseImg;             // Atributo do tipo BufferedImage, usado para gerenciar as imagens e sprites

    Thread game;                        // A thread que fará o jogo rodar

    /*========== CONSTRUTOR ==========*/

    public GamePanel()
    {
        setPreferredSize(new Dimension(screenWidth,screenHeight));  // Tamanho do Canvas
        setBackground(Color.black); // Fundo preto
        setFocusable(true); // Habilita inputs
        addKeyListener(kh); // Adiciona um leitor
        addMouseListener(mI);
        addMouseMotionListener(mI);
        // Carrega a textura do cursor
        try{
            mouseImg = ImageIO.read(getClass().getResourceAsStream("/cursor/aim.png"));
        } catch (IOException e){}

        // Adiciona os objetos no array do tipo Renderable
        objetos.add(player);
        objetos.add(follower1);
        objetos.add(follower2);
        objetos.add(follower3);
        objetos.add(follower4);
        objetos.add(follower5);
        objetos.add(follower6);
        objetos.add(tree);

        //cria um cursor customizado INVISIVEL
        c = Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB),new Point(0,0),"cursor");
        /*Toolkit é uma classe cuja função é gerenciar recursos do sistema operacional, um deles por exemplo, o mouse.*/

        //seta o cursor invisivel para este componente (Canvas)
        setCursor(c);

        // Instanciado o objeto do GameLoop e iniciada a Thread.
        gl = new GameLoop(this);
        game = new Thread(gl);
    }

    /*========== MÉTODOS ==========*/

    // Render é chamado manualmente, e não pela máquina
    public void render() {
        //obtem o bufferStrategy do componente
        BufferStrategy bufferStrategy = getBufferStrategy();
        Graphics2D g2d = (Graphics2D) bufferStrategy.getDrawGraphics(); // Criação do pincel que será usado para pintar os sprites

        //começa a desenhar na imagem vazia
        g2d.setColor(Color.black);
        g2d.fillRect(0,0,getWidth(),getHeight());

        //renderizando a camada do mundo
        world.renderWorld(g2d); // pintando os tiles do mapa toda vez que o método for chamado

        //renderizando a camada dos objetos (array do tipo Renderable)
        for(Renderable obj : objetos){
            obj.render(g2d); // pintando os elementos (player, inimigo, bosses, etc)
        }

        // Definindo a string desenhada pelo pincel
        g2d.setFont(new Font("Arial",Font.BOLD,30));
        g2d.setColor(Color.WHITE);
        g2d.drawString("FPS: " + String.valueOf(gl.finalFps),10,40);

        //obter as coordenadas do mouse em relação e este componente e salvar em um objeto Point que tem x e y
        cursorPoint = getMousePosition();

        //se o cursor estiver na tela ele desenha nossa textura do mouse na posição do cursor
        if(cursorPoint != null)
            //basicamente desenha um cursor na posição dele menos a metade do tamanho da imagem que ele representa (pro centro da imagem ser o clique)
            g2d.drawImage(mouseImg,(int)(cursorPoint.x-mouseImg.getWidth()* Global.SCALE /2),(int)(cursorPoint.y-mouseImg.getHeight()* Global.SCALE/2),(int)(mouseImg.getWidth()*scale), (int)(mouseImg.getHeight()*scale), null);

        g2d.dispose();                                          // Liberando o desenho do pincel
        bufferStrategy.show();                                  // Liberando a imagem que foi pintada nos buffers
        Toolkit.getDefaultToolkit().sync();                     // Sincronizar com os recursos operacionais
    }
}
