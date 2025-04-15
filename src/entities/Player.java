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
    public Player(GamePanel gp, World world){
        super(gp,world);
    }
    int screenCenterY = gp.screenHeight/2 - gp.tileSize/2;
    int screenCenterX = gp.screenWidth/2 - gp.tileSize/2;

    @Override
    public void setDefaultValues() {
        x = (double) gp.screenWidth /2 - (double) gp.tileSize /2;
        y = (double) gp.screenHeight /2 - (double) gp.tileSize /2;
        speed = gp.tileSize*5;
        try {
            texture = ImageIO.read(getClass().getResourceAsStream("/players/player_idle_down.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(double deltaTime) {
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
            try {
                texture = ImageIO.read(getClass().getResourceAsStream("/players/player_idle_up.png"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (gp.kh.downKey) {
            nextY += (int) (realSpeed * deltaTime); //player
            try {
                texture = ImageIO.read(getClass().getResourceAsStream("/players/player_idle_down.png"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (gp.kh.leftKey) {
            nextX -= (int) (realSpeed * deltaTime); //player
            try {
                texture = ImageIO.read(getClass().getResourceAsStream("/players/player_idle_left.png"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (gp.kh.rightKey) {
            nextX += (int) (realSpeed * deltaTime); //player
            try {
                texture = ImageIO.read(getClass().getResourceAsStream("/players/player_idle_right.png"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if(nextX <= world.width && nextX >= 0)
            x = nextX;
        if(nextY <= world.height && nextY >= 0)
            y = nextY;

    }

    @Override
    public void render(Graphics2D g2d){
        g2d.drawImage(texture,screenCenterX,screenCenterY,gp.tileSize, gp.tileSize, null);
    }
}
