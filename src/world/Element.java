package world;

import game.GamePanel;
import game.Renderable;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Element implements Renderable {
    double x;
    double y;
    BufferedImage texture;
    GamePanel gp;
    Camera camera;

    public Element(GamePanel gp, Camera camera){
        this.gp = gp;
        this.camera = camera;
    }

    public void render(Graphics2D g2d){
        g2d.drawImage(texture,(int)x,(int)y,gp.tileSize,gp.tileSize,null);
    }
    public double getY(){
        return y;
    }
}
