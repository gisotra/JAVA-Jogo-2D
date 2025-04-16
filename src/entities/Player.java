package entities;

import game.GamePanel;
import world.World;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Player extends Entity{
    double nextX;
    double nextY;
    double distX;
    double distY;
    double cameraSpeed = 3;
    double realCameraSpeed;
    String spriteDir = "down";
    public Player(GamePanel gp, World world){
        super(gp,world);
    }
    int screenCenterY = gp.screenHeight/2 - gp.tileSize/2;
    int screenCenterX = gp.screenWidth/2 - gp.tileSize/2;

    double maxZoom;
    double minZoom;

    @Override
    public void setDefaultValues() {
        x = (double) gp.screenWidth /2 - (double) gp.tileSize /2;
        y = (double) gp.screenHeight /2 - (double) gp.tileSize /2;
        speed = gp.tileSize*5;
        minZoom = gp.scale;
        maxZoom = gp.scale*4;
        try {
            spritesheet = ImageIO.read(getClass().getResourceAsStream("/players/playersheet.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        texture = spritesheet.getSubimage(0,0,gp.originalTileSize,gp.originalTileSize);
    }

    @Override
    public void update(double deltaTime) {
        adjustSpriteDirection();
        zooming();
        gp.camera.x = (int)x - screenCenterX;
        gp.camera.y = (int)y - screenCenterY;

        distX = x - gp.camera.x - screenCenterX;
        distY = y - gp.camera.y - screenCenterY;;

        nextX = x;
        nextY = y;

        realSpeed = speed;
        realCameraSpeed = cameraSpeed;

        if(gp.kh.upKey && (gp.kh.leftKey || gp.kh.rightKey) || gp.kh.downKey && (gp.kh.leftKey || gp.kh.rightKey)) {
            realSpeed /= Math.sqrt(2);
        }
        if (gp.kh.upKey) {
            nextY -= (int) (realSpeed * deltaTime); //player
            spriteDir = "up";
        }
        if (gp.kh.downKey) {
            nextY += (int) (realSpeed * deltaTime); //player
            spriteDir = "down";
        }
        if (gp.kh.leftKey) {
            nextX -= (int) (realSpeed * deltaTime); //player
            spriteDir = "left";
        }
        if (gp.kh.rightKey) {
            nextX += (int) (realSpeed * deltaTime); //player
            spriteDir = "right";
        }

        if(gp.kh.upKey && gp.kh.leftKey){
            spriteDir = "leftUp";
        }
        if(gp.kh.upKey && gp.kh.rightKey){
            spriteDir = "rightUp";
        }
        if(gp.kh.downKey && gp.kh.leftKey){
            spriteDir = "leftDown";
        }
        if(gp.kh.downKey && gp.kh.rightKey){
            spriteDir = "rightDown";
        }

        if(nextX <= world.width && nextX >= 0)
            x = nextX;
        if(nextY <= world.height && nextY >= 0)
            y = nextY;

    }

    public void adjustSpriteDirection(){
        if(spriteDir == "down"){
            texture = spritesheet.getSubimage(0,0,gp.originalTileSize,gp.originalTileSize);
        }
        if(spriteDir == "rightDown"){
            texture = spritesheet.getSubimage(0,gp.originalTileSize*1,gp.originalTileSize,gp.originalTileSize);
        }
        if(spriteDir == "right"){
            texture = spritesheet.getSubimage(0,gp.originalTileSize*2,gp.originalTileSize,gp.originalTileSize);
        }
        if(spriteDir == "rightUp"){
            texture = spritesheet.getSubimage(0,gp.originalTileSize*3,gp.originalTileSize,gp.originalTileSize);
        }
        if(spriteDir == "up"){
            texture = spritesheet.getSubimage(0,gp.originalTileSize*4,gp.originalTileSize,gp.originalTileSize);
        }
        if(spriteDir == "leftUp"){
            texture = spritesheet.getSubimage(0,gp.originalTileSize*5,gp.originalTileSize,gp.originalTileSize);
        }
        if(spriteDir == "left"){
            texture = spritesheet.getSubimage(0,gp.originalTileSize*6,gp.originalTileSize,gp.originalTileSize);
        }
        if(spriteDir == "leftDown"){
            texture = spritesheet.getSubimage(0,gp.originalTileSize*7,gp.originalTileSize,gp.originalTileSize);
        }
    }

    public void zooming(){
        if(gp.kh.zoom){
            if(gp.scale < maxZoom){
                gp.scale += 0.1;
            }
        }else{
            if(gp.scale > minZoom){
                gp.scale -= 0.1;
            }
        }
        gp.tileSize = (int)((double)gp.originalTileSize*gp.scale);
        gp.screenWidth = gp.gameCols*gp.tileSize;
        gp.screenHeight = gp.gameRows*gp.tileSize;
    }

    @Override
    public void render(Graphics2D g2d){
        g2d.drawImage(texture,screenCenterX,screenCenterY,gp.tileSize, gp.tileSize, null);
    }
}
