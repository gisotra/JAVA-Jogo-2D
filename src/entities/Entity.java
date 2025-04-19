package entities;

import game.GamePanel;
import game.Renderable;
import utilities.Global;
import world.World;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Entity implements Renderable {
    public double x;
    public double y;
    public double speed;
    public double realSpeed;
    boolean diagonal = false;
    protected int orientation = 0;
    public BufferedImage[] sprites = new BufferedImage[8];
    BufferedImage spritesheet;
    World world;

    GamePanel gp;

    public Entity(GamePanel gp,World world){
        this.world = world;
        this.gp = gp;
        loadEntityData();
    }

    @Override
    public void render(Graphics2D g2d){
        g2d.drawImage(sprites[orientation],(int)x,(int)y, Global.TILESIZE, Global.TILESIZE, null);
    }

    public abstract void update(double deltaTime);

    public abstract void loadEntityData();
}
