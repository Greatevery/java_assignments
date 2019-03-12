import javax.swing.*;
import java.awt.*;

public class Home extends GameObject{
    private boolean isAlive;

    public Home(Location location) {
        super(location);
        super.image = new ImageIcon(this.getClass().getResource("images/home.png")).getImage();
        width = image.getWidth(null);
        height = image.getHeight(null);
        isAlive = true;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(image, location.getX(), location.getY(), null);
    }
}
