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
        return y;
    }

    @Override
    public void render(Graphics2D g2d) {
        double positionX = x + (texture.getWidth()*(int)gp.scale/2);
        double positionY = y + (texture.getHeight()*(int)gp.scale/2);
        g2d.drawImage(texture,(int)positionX-(int)camera.x,(int)positionY- (int)camera.y,texture.getWidth()*(int)gp.scale,texture.getHeight()*(int)gp.scale,null);
    }
}
