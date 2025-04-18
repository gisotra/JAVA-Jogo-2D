package world;

import game.GamePanel;
import game.Renderable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Tree extends Element implements Renderable {

    public Tree(GamePanel gp, Camera camera, double x, double y){
        super(gp,camera);
        this.x = x;
        this.y = y;
        try {
            texture = ImageIO.read(getClass().getResourceAsStream("/elements/arvore.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public double getY() {
        return y+texture.getHeight()*gp.scale;
    }

    @Override
    public void render(Graphics2D g2d) {
        g2d.drawImage(texture,(int)(x - camera.x),(int)(y - camera.y),texture.getWidth()*(int)gp.scale,texture.getHeight()*(int)gp.scale,null);
    }
}
