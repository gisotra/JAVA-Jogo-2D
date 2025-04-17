package entities;

import game.GameLoop;
import game.GamePanel;
import game.Renderable;
import world.World;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Entity implements Renderable {
    public double x;
    public double y;
    public double speed;
    public double realSpeed;
    boolean diagonal = false;
    BufferedImage texture;
    BufferedImage spritesheet;
    World world;

    GamePanel gp;

    public Entity(GamePanel gp,World world){
        this.gp = gp;
        this.world = world;
        setDefaultValues();
    }

    @Override
    public void render(Graphics2D g2d){
        g2d.drawImage(texture,(int)x,(int)y,gp.tileSize, gp.tileSize, null);
    }

    public abstract void update(double deltaTime);

    public abstract void setDefaultValues();
}
