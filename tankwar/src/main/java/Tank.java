import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Tank extends GameObject {
    private boolean isAlive;

    protected Blood blood;
    protected int speedX;
    protected int speedY;
    protected Direction direction;
    protected ImageIcon imageIcon;

    public Tank(Location location){
        super(location);

    }

    @Override
    public void draw(Graphics g) {

    }
}
