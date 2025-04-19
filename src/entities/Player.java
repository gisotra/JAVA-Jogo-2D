package entities;

import game.GamePanel;
import game.Renderable;
import utilities.Global;
import utilities.ImageManager;
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
    int screenCenterY = gp.screenHeight/2 - Global.TILESIZE/2;
    int screenCenterX = gp.screenWidth/2 - Global.TILESIZE/2;

    @Override
    public void loadEntityData() {
        //definindo propriedades
        x = 0;
        y = 0;
        speed = Global.TILESIZE*5;
        orientation = 0;

        //carregar spritesheet e array de sprites
        try {
            spritesheet = ImageIO.read(getClass().getResourceAsStream("/entities/players/playersheet.png"));

            for(int i = 0; i < 8; i++){
                sprites[i] = ImageManager.getCroppedImg(spritesheet,0,(int)(Global.ORIGINAL_TILESIZE*i), (int)Global.ORIGINAL_TILESIZE, (int)Global.ORIGINAL_TILESIZE);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    @Override
    public void render(Graphics2D g2d){
        g2d.drawImage(sprites[orientation],screenCenterX,screenCenterY,Global.TILESIZE, Global.TILESIZE, null);
    }

    public double getY(){
        return y + sprites[orientation].getHeight()*gp.scale;
    }
}
