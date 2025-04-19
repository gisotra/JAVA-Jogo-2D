package entities;

import game.GamePanel;
import game.Renderable;
import utilities.Global;
import utilities.ImageManager;
import world.World;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class Follower extends Entity implements Renderable {
    double nextX;
    double nextY;
    String spriteDir = "down";
    BufferedImage spritesheetinactive;
    BufferedImage[] spritesInactive;
    Random random = new Random();

    double dx;
    double dy;
    float distance;

    boolean beingWatched = false;

    public Follower(GamePanel gp, World world,double x, double y)
    {
        super(gp,world);
        this.x = x;
        this.y = y;
        loadEntityData();
    }

    @Override
    public void loadEntityData() {
        //definindo propriedades
        speed = Global.TILESIZE*3;
        orientation = 0;
        spritesInactive = new BufferedImage[8];

        //carregar spritesheet e array de sprites
        try {
            spritesheet = ImageIO.read(getClass().getResourceAsStream("/entities/monsters/followersheet.png"));
            spritesheetinactive = ImageIO.read(getClass().getResourceAsStream("/entities/monsters/followersheet_inactive.png"));

            for(int i = 0; i < 8; i++) {
                sprites[i] = ImageManager.getCroppedImg(spritesheet, 0, (int) (Global.ORIGINAL_TILESIZE * i), (int) Global.ORIGINAL_TILESIZE, (int) Global.ORIGINAL_TILESIZE);
                spritesInactive[i] = ImageManager.getCroppedImg(spritesheetinactive, 0, (int) (Global.ORIGINAL_TILESIZE * i), (int) Global.ORIGINAL_TILESIZE, (int) Global.ORIGINAL_TILESIZE);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

        if(distance > Global.TILESIZE*0.5 && distance < Global.TILESIZE*15 && !beingWatched) {

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
            orientation = Global.DOWN;
        }
        if(spriteDir == "rightDown"){
            orientation = Global.RIGHT_DOWN;
        }
        if(spriteDir == "right"){
            orientation = Global.RIGHT;
        }
        if(spriteDir == "rightUp"){
            orientation = Global.RIGHT_UP;
        }
        if(spriteDir == "up"){
            orientation = Global.UP;
        }
        if(spriteDir == "leftUp"){
            orientation = Global.LEFT_UP;
        }
        if(spriteDir == "left"){
            orientation = Global.LEFT;
        }
        if(spriteDir == "leftDown"){
            orientation = Global.LEFT_DOWN;
        }
    }

    public void watchControl() {
        if((gp.player.spriteDir == "leftUp" || gp.player.spriteDir == "left" || gp.player.spriteDir == "up") && spriteDir == "rightDown"){
            beingWatched = true;
        }
        else if((gp.player.spriteDir == "rightUp" || gp.player.spriteDir == "right" || gp.player.spriteDir == "up") && spriteDir == "leftDown"){
            beingWatched = true;
        }
        else if((gp.player.spriteDir == "leftDown" || gp.player.spriteDir == "left" || gp.player.spriteDir == "down") && spriteDir == "rightUp"){
            beingWatched = true;
        }
        else if((gp.player.spriteDir == "rightDown" || gp.player.spriteDir == "right" || gp.player.spriteDir == "down") && spriteDir == "leftUp"){
            beingWatched = true;
        }
        else{
            beingWatched = false;
        }
    }

    @Override
    public void render(Graphics2D g2d){
        if(!beingWatched)
            g2d.drawImage(sprites[orientation],(int)x- gp.camera.x,(int)y - gp.camera.y,Global.TILESIZE, Global.TILESIZE, null);
        else
            g2d.drawImage(spritesInactive[orientation],(int)x- gp.camera.x,(int)y - gp.camera.y,Global.TILESIZE, Global.TILESIZE, null);
    }

    public double getY(){
        return y + sprites[orientation].getHeight()*gp.scale;
    }
}
