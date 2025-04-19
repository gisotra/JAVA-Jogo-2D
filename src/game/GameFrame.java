package game;

import javax.swing.*; // importa todos os recursos da biblioteca Swing do Java

public class GameFrame extends JFrame { // A classe GameFrame extende JFrame

    /*============ ATRIBUTOS ============*/
    GamePanel gp = new GamePanel();  // Instância do tipo GamePanel

    /*============ CONSTRUTOR ============*/
    public GameFrame()
    {
        setTitle("Game2D");                                 // Título da Janela
        setDefaultCloseOperation(EXIT_ON_CLOSE);            // Fecha o programa quando a janela for fechada
        add(gp);                                            // Adiciona o objeto gp
        pack();                                             // Altera o tamanho da janela para ficar apropriado com seus elementos internos
        gp.createBufferStrategy(3);              // Cria um bufferStrategy com 3 buffers gerenciados
        gp.game.start();                                   // Inicia a Thread da classe GamePanel
        setResizable(false);                               // O usuário não pode alterar a janela
        setIconImage(gp.player.sprites[0]);                // Ícone pra ficar bonitinho
        setLocationRelativeTo(null);                       // Faz com que a janela apareça no meio da tela
        setVisible(true);                                  // Torna a janela gráfica visível
    }

    /*
    A classe GameFrame será usada como o "Quadro" em que o jogo será pintado, e a "pintura" deste quadro será a
    classe GamePanel, responsável por desenhar os elementos.

    - Classes usadas -
    Classe BufferStrategy: É uma classe da biblioteca AWT em Java especializada em gerenciar buffers. Como funciona: Basicamente
    todos os desenhos são pintados em segundo plano, e mostrados todos de uma vez, o que evita tearing dos tiles por exemplo,
    tornando a experiência muito mais consistente pro jogador.
    */
}
