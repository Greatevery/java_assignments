import javax.swing.*;
import java.awt.*;

public class Brick extends GameObject{
    private int hitCounts;
    private boolean invisible;
    private int type;


    public Brick(Location location) {
        super(location);
        super.image = new ImageIcon(this.getClass().getResource("images/brick.png")).getImage();
        super.width = image.getWidth(null);
        super.height = image.getHeight(null);
        invisible = false;
        type = 0;
    }

    public Brick(Location location, int type) {
        super(location);
        super.image = new ImageIcon(this.getClass().getResource("images/brick.png")).getImage();
        super.width = image.getWidth(null);
        super.height = image.getHeight(null);
        this.invisible = invisible;
        hitCounts = 0;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public boolean isInvisible() {
        return invisible;
    }

    public void changeInvisible(){
        if(type == 1)
            invisible = !invisible;
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
