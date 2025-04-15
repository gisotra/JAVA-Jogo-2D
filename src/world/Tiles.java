package world;

import game.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Tiles {
    GamePanel gp;
    int totalTiles = 1;
    BufferedImage tile[] = new BufferedImage[totalTiles];

    public Tiles(GamePanel gp){
        this.gp = gp;
        loadTiles();
    }

    private void loadTiles(){
        try {
            tile[0] = ImageIO.read(getClass().getResourceAsStream("/tiles/grasstile.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void drawTile(Graphics g,int id,int x,int y){
        g.drawImage(tile[id],x,y,gp.tileSize, gp.tileSize, null);
    }
}
