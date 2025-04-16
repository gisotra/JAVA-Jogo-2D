package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean upKey = false, downKey = false, leftKey = false, rightKey = false;
    public boolean zoom = false;

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == 87)
            upKey = true;
        if(e.getKeyCode() == 83)
            downKey = true;
        if(e.getKeyCode() == 65)
            leftKey = true;
        if(e.getKeyCode() == 68)
            rightKey = true;

        if(e.getKeyCode() == 90)
            zoom = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == 87)
            upKey = false;
        if(e.getKeyCode() == 83)
            downKey = false;
        if(e.getKeyCode() == 65)
            leftKey = false;
        if(e.getKeyCode() == 68)
            rightKey = false;

        if(e.getKeyCode() == 90)
            zoom = false;
    }
}
