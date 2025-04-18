package entities;

import game.GamePanel;
import game.Renderable;
import world.World;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Follower extends Entity implements Renderable {
    double nextX;
    double nextY;
    String spriteDir = "down";

    double dx;
    double dy;
    float distance;

    boolean beingWatched = false;

    public Follower(GamePanel gp, World world){
        super(gp,world);
    }

    double maxZoom;
    double minZoom;

    @Override
    public void setDefaultValues() {
        x = 0;
        y = 0;
        speed = gp.tileSize*3.5;
        try {
            spritesheet = ImageIO.read(getClass().getResourceAsStream("/entities/monsters/followersheet.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        texture = spritesheet.getSubimage(0,0,gp.originalTileSize,gp.originalTileSize);
    }

    @Override
    public void update(double deltaTime) {
        adjustSpriteDirection();

        nextX = x;
        nextY = y;

        realSpeed = speed*deltaTime;

        dx = gp.player.x - x;
        dy = gp.player.y - y;
        distance = (float)Math.sqrt(dx * dx + dy * dy);

        double dirX = dx / distance;
        double dirY = dy / distance;

        watchControl();

        if(distance < gp.tileSize*5 && !beingWatched) {

            if (dirX > 0 && dirY > 0) {
                spriteDir = "rightDown";
            } else if (dirX > 0 && dirY < 0) {
                spriteDir = "rightUp";
            } else if (dirX < 0 && dirY > 0) {
                spriteDir = "leftDown";
            } else if (dirX < 0 && dirY < 0) {
                spriteDir = "leftUp";
            }

            nextX += dirX * speed * deltaTime;
            nextY += dirY * speed * deltaTime;

            if (nextX <= world.width && nextX >= 0)
                x = nextX;
            if (nextY <= world.height && nextY >= 0)
                y = nextY;
        }

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

    public void watchControl() {
        if((gp.player.spriteDir == "leftUp" || gp.player.spriteDir == "up" || gp.player.spriteDir == "up") && spriteDir == "rightDown"){
            beingWatched = true;
        }
        else{
            beingWatched = false;
        }
    }

    @Override
    public void render(Graphics2D g2d){
        g2d.drawImage(texture,(int)x- gp.camera.x,(int)y - gp.camera.y,gp.tileSize, gp.tileSize, null);
    }

    public double getY(){
        return y + texture.getHeight()*gp.scale;
    }
}
