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
        cols = 20;
        rows = 20;
        width = cols*gp.tileSize - gp.tileSize;
        height = rows*gp.tileSize - gp.tileSize;
    }

    public void render(Graphics2D g){
        for(int i = 0; i < cols; i++){
            for(int j = 0; j < rows; j++){
                tiles.drawTile(g,0,i*gp.tileSize - (int)gp.camera.x,j*gp.tileSize - (int)gp.camera.y);
            }
        }
    }
}
