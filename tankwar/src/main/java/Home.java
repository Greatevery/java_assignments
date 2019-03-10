import javax.swing.*;
import java.awt.*;

public class Home extends GameObject{
    public Home(Location location) {
        super(location);
        super.image = new ImageIcon(this.getClass().getResource("images/home.png")).getImage();
        width = image.getWidth(null);
        height = image.getHeight(null);
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(image, location.getX(), location.getY(), null);
    }
}
