package entities;

import game.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Player extends Entity{
    double distX;
    double distY;
    double cameraSpeed = 3;
    double realCameraSpeed;
    public Player(GamePanel gp){
        super(gp);
    }
    double screenCenterY = gp.screenHeight/2 - gp.tileSize/2;
    double screenCenterX = gp.screenWidth/2 - gp.tileSize/2;

    @Override
    public void setDefaultValues() {
        x = (double)gp.screenWidth/2 - gp.tileSize/2;
        y = (double)gp.screenHeight/2 - gp.tileSize/2;
        speed = gp.tileSize*4;
        try {
            texture = ImageIO.read(getClass().getResourceAsStream("/players/player_idle_down.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(double deltaTime) {
        distX = x - gp.camera.x - screenCenterX;
        distY = y - gp.camera.y - screenCenterY;;

        System.out.println("distX = " + distX);
        System.out.println("distX = " + distY);

        realSpeed = speed;
        realCameraSpeed = cameraSpeed;
        if(gp.kh.upKey && (gp.kh.leftKey || gp.kh.rightKey) || gp.kh.downKey && (gp.kh.leftKey || gp.kh.rightKey)) {
            realSpeed /= Math.sqrt(2);
        }
        if (gp.kh.upKey) {
            y -= realSpeed * deltaTime; //player
            try {
                texture = ImageIO.read(getClass().getResourceAsStream("/players/player_idle_up.png"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (gp.kh.downKey) {
            y += realSpeed * deltaTime; //player
            try {
                texture = ImageIO.read(getClass().getResourceAsStream("/players/player_idle_down.png"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (gp.kh.leftKey) {
            x -= realSpeed * deltaTime; //player
            try {
                texture = ImageIO.read(getClass().getResourceAsStream("/players/player_idle_left.png"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (gp.kh.rightKey) {
            x += realSpeed * deltaTime; //player
            try {
                texture = ImageIO.read(getClass().getResourceAsStream("/players/player_idle_right.png"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if(gp.camera.x != x - screenCenterX){
            if(gp.camera.x > x - screenCenterX){
                gp.camera.x-= realCameraSpeed;
            }
            if(gp.camera.x < x - screenCenterX){
                gp.camera.x+= realCameraSpeed;
            }
        }
        if(gp.camera.y != y - screenCenterY){
            if(gp.camera.y > y - screenCenterY){
                gp.camera.y-= realCameraSpeed;
            }
            if(gp.camera.y < y - screenCenterY){
                gp.camera.y+= realCameraSpeed;
            }
        }

    }

    @Override
    public void render(Graphics2D g2d){
        g2d.drawImage(texture,(int)x - gp.camera.x,(int)y - gp.camera.y,gp.tileSize, gp.tileSize, null);
    }
}
