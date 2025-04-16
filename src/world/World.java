package world;

import game.GamePanel;

import java.awt.*;

public class World {
    int cols,rows;
    public int width, height;
    GamePanel gp;
    Tiles tiles;

    public World(GamePanel gp){
        this.gp = gp;
        tiles = new Tiles(gp);
        cols = 50;
        rows = 50;
        width = cols*gp.tileSize - gp.tileSize;
        height = rows*gp.tileSize - gp.tileSize;
    }

    public void render(Graphics2D g){
        for(int i = 0; i < cols; i++){
            for(int j = 0; j < rows; j++){
                int tileX = i*gp.tileSize - (int)gp.camera.x;
                int tileY = j*gp.tileSize - (int)gp.camera.y;

                if(tileX > 0 - gp.tileSize && tileX < gp.screenWidth && tileY > 0 - gp.tileSize && tileY < gp.screenHeight)
                    tiles.drawTile(g,0,tileX,tileY);
            }
        }
    }
}
