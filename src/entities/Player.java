package entities;

import game.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Player extends Entity{
    public Player(GamePanel gp){
        super(gp);
    }

    @Override
    public void setDefaultValues() {
        x = (double)gp.screenWidth/2;
        y = (double)gp.screenHeight/2;
        speed = gp.tileSize*5;
        try {
            texture = ImageIO.read(getClass().getResourceAsStream("/players/player_idle_down.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(double deltaTime) {
        realSpeed = speed;
        if(gp.kh.upKey && (gp.kh.leftKey || gp.kh.rightKey) || gp.kh.downKey && (gp.kh.leftKey || gp.kh.rightKey))
            realSpeed /= Math.sqrt(2);
        if (gp.kh.upKey) {
            y -= realSpeed * deltaTime;
            try {
                texture = ImageIO.read(getClass().getResourceAsStream("/players/player_idle_up.png"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (gp.kh.downKey) {
            y += realSpeed * deltaTime;
            try {
                texture = ImageIO.read(getClass().getResourceAsStream("/players/player_idle_down.png"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (gp.kh.leftKey) {
            x -= realSpeed * deltaTime;
            try {
                texture = ImageIO.read(getClass().getResourceAsStream("/players/player_idle_left.png"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (gp.kh.rightKey) {
            x += realSpeed * deltaTime;
            try {
                texture = ImageIO.read(getClass().getResourceAsStream("/players/player_idle_right.png"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
