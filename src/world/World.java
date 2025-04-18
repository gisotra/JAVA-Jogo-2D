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
        cols = 50;
        rows = 50;
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
                double tileX = i*gp.tileSize - gp.camera.x;
                double tileY = j*gp.tileSize - gp.camera.y;

                if(tileX > - gp.tileSize && tileX < gp.screenWidth && tileY > - gp.tileSize && tileY < gp.screenHeight) {
                    tiles.drawTile(g, world[i][j], (int)tileX, (int)tileY);
                }
            }
        }
    }
}
