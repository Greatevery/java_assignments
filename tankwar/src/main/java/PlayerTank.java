import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PlayerTank extends Tank implements KeyListener {
    public PlayerTank(Location location){
        super(location);
        this.blood = new Blood(5);
        this.speedX = 5;
        this.speedY = 5;
        this.direction = Direction.Down;
        this.imageIcon = new ImageIcon(this.getClass().getResource("images/tankD.gif"));
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key){
            case KeyEvent.VK_CONTROL :
                break;
            case KeyEvent.VK_A :
                break;
            case KeyEvent.VK_LEFT :
                this.location.setX(this.location.getX() - this.speedX);
                break;
            case KeyEvent.VK_UP :
                this.location.setY(this.location.getY() - this.speedY);
                break;
            case KeyEvent.VK_RIGHT :
                this.location.setX(this.location.getX() + this.speedX);
                break;
            case KeyEvent.VK_DOWN :
                this.location.setY(this.location.getY() + this.speedY);
                break;
            default : break;
        }
//        if (key == KeyEvent.VK_CONTROL) {
//            Tools.playAudio("shoot.wav");
//            my += 10;
//        } else if (key == KeyEvent.VK_A) {
//            Tools.playAudio(Tools.nextBoolean() ? "supershoot.wav" : "supershoot.aiff");
//            my += 10;
//        } else if (key == KeyEvent.VK_LEFT) {
//            x -= 5;
//        } else if (key == KeyEvent.VK_UP) {
//            y -= 5;
//        } else if (key == KeyEvent.VK_RIGHT) {
//            x += 5;
//        } else if (key == KeyEvent.VK_DOWN) {
//            y += 5;
//        }
//
//        if (my >= HEIGHT) {
//            my = Tools.nextInt(HEIGHT);
//        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(imageIcon.getImage(), this.location.getX(), this.location.getY(), null);
        //draw HP
        g.setColor(Color.RED);
        g.fillRect(this.location.getX(), this.location.getY() - 10, 35, 10);
    }
}
