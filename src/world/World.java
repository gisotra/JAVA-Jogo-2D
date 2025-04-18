package world;

import game.GamePanel;

import java.awt.*;
import java.util.Random;

public class World {
    int cols,rows;
    public int width, height;
    public int[][] world;
    GamePanel gp;
    Tiles tiles;
    Random random = new Random();

    public World(GamePanel gp){
        this.gp = gp;
        tiles = new Tiles(gp);
        cols = 11;
        rows = 8;
        world = new int[cols][rows];
        width = cols*gp.tileSize - gp.tileSize;
        height = rows*gp.tileSize - gp.tileSize;
        convertWorld();
    }

    public void convertWorld() {
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                if (random.nextInt(10) == 0)
                    world[i][j] = 1;
                else
                    world[i][j] = 0;
                if (random.nextInt(60) == 0)
                    world[i][j] = 2;
            }
        }
    }

    public void render(Graphics2D g){
        for(int i = 0; i < cols; i++){
            for(int j = 0; j < rows; j++){
                int tileX = i*gp.tileSize - (int)gp.camera.x;
                int tileY = j*gp.tileSize - (int)gp.camera.y;

                if(tileX > 0 - gp.tileSize && tileX < gp.screenWidth && tileY > 0 - gp.tileSize && tileY < gp.screenHeight) {
                    tiles.drawTile(g, world[i][j], tileX, tileY);
                }
            }
        }
    }
}
