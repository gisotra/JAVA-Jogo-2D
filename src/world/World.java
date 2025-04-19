package world;

import game.GamePanel;
import utilities.Global;

import java.awt.*;
import java.util.Random;

public class World {
    int cols,rows;
    public int width, height;
    public int[][] world;
    Tiles tiles;
    Random random = new Random();
    GamePanel gp;

    public World(GamePanel gp){
        this.gp = gp;
        tiles = new Tiles();
        cols = 50;
        rows = 50;
        world = new int[cols][rows];
        width = cols* Global.TILESIZE - Global.TILESIZE;
        height = rows* Global.TILESIZE - Global.TILESIZE;
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

    public void renderWorld(Graphics2D g){
        for(int i = 0; i < cols; i++){
            for(int j = 0; j < rows; j++){
                double tileX = i*Global.TILESIZE - gp.camera.x;
                double tileY = j*Global.TILESIZE - gp.camera.y;

                if(tileX > - Global.TILESIZE && tileX < gp.screenWidth && tileY > - Global.TILESIZE && tileY < gp.screenHeight) {
                    tiles.drawTile(g, world[i][j], (int)tileX, (int)tileY);
                }
            }
        }
    }
}
