package world;

import game.GamePanel;
import utilities.Global;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Tiles {
    int totalTiles = 3;
    BufferedImage tile[] = new BufferedImage[totalTiles];

    public Tiles(){
        loadTiles();
    }

    private void loadTiles(){
        try {
            tile[0] = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));
            tile[1] = ImageIO.read(getClass().getResourceAsStream("/tiles/flowergrass.png"));
            tile[2] = ImageIO.read(getClass().getResourceAsStream("/tiles/bush.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void drawTile(Graphics g,int id,int x,int y){
        g.drawImage(tile[id],x,y,Global.TILESIZE, Global.TILESIZE, null);
    }
}
