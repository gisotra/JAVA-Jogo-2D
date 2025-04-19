package game;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


public class MouseInputs implements MouseListener, MouseMotionListener{

    /*=========== ATRIBUTOS ===========*/
    private GamePanel gp;
    boolean mouseClicked = false;
    boolean attackPressed = false;

    /*=========== MÉTODOS DA INTERFACE IMPLEMENTADA ===========*/

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) { // Mouse foi pressionado
        mouseClicked = true;
        attackPressed = true;
        System.out.println("Mouse foi pressionado!!!");
    }

    @Override
    public void mouseReleased(MouseEvent e) { // Mouse foi solto
        mouseClicked = false;
        attackPressed = false;
        System.out.println("Mouse foi solto!!!");
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {}
}
