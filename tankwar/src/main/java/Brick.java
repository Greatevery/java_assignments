import javax.swing.*;
import java.awt.*;

public class Brick extends GameObject{
    private int hitCounts;
    public Brick(Location location) {
        super(location);
        super.image = new ImageIcon(this.getClass().getResource("images/brick.png")).getImage();
        super.width = image.getWidth(null);
        super.height = image.getHeight(null);
        hitCounts = 0;
    }

    public void hit(){
        hitCounts++;
    }

    public int getHitCounts() {
        return hitCounts;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(image, location.getX(), location.getY(), null);
    }
}
