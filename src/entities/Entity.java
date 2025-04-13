package entities;

import game.GameLoop;
import game.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Entity {
    public double x;
    public double y;
    public double speed;
    public double realSpeed;
    boolean diagonal = false;
    BufferedImage texture;

    GamePanel gp;

    public Entity(GamePanel gp){
        this.gp = gp;
        setDefaultValues();
    }

    public void render(Graphics2D g2d){
        g2d.drawImage(texture,(int)x,(int)y,gp.tileSize, gp.tileSize, null);
    }

    public abstract void update(double deltaTime);

    public abstract void setDefaultValues();
}
