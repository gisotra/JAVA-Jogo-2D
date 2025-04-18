package entities;

import game.GamePanel;
import game.Renderable;
import world.World;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Player extends Entity implements Renderable {
    double nextX;
    double nextY;
    double cameraSpeed = 3.0;
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
        x = 0;
        y = 0;
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
        gp.camera.x = (int)(x - screenCenterX);
        gp.camera.y = (int)(y - screenCenterY);

        nextX = x;
        nextY = y;

        realSpeed = speed*deltaTime;
        realCameraSpeed = cameraSpeed;

        if(gp.kh.upKey && (gp.kh.leftKey || gp.kh.rightKey) || gp.kh.downKey && (gp.kh.leftKey || gp.kh.rightKey)) {
            realSpeed = (realSpeed / Math.sqrt(2));
        }
        if (gp.kh.upKey) {
            nextY -= realSpeed; //player
            spriteDir = "up";
        }
        if (gp.kh.downKey) {
            nextY += realSpeed; //player
            spriteDir = "down";
        }
        if (gp.kh.leftKey) {
            nextX -= realSpeed; //player
            spriteDir = "left";
        }
        if (gp.kh.rightKey) {
            nextX += realSpeed; //player
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

    @Override
    public void render(Graphics2D g2d){
        g2d.drawImage(texture,screenCenterX,screenCenterY,gp.tileSize, gp.tileSize, null);
    }

    public double getY(){
        return y + texture.getHeight()*gp.scale;
    }
}
