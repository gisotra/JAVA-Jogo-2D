package world;

import game.GamePanel;

import java.awt.*;

public class World {
    int cols,rows;
    GamePanel gp;
    Tiles tiles;

    public World(GamePanel gp){
        this.gp = gp;
        tiles = new Tiles(gp);
        cols = 50;
        rows = 50;
    }

    public void render(Graphics2D g){
        for(int i = 0; i < cols; i++){
            for(int j = 0; j < rows; j++){
                tiles.drawTile(g,0,i*gp.tileSize - gp.camera.x,j*gp.tileSize - gp.camera.y);
            }
        }
    }
}
