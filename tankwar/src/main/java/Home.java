import javax.swing.*;
import java.awt.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.List;

public class Home extends GameObject{
    private boolean isAlive;
    private boolean invisible;
    private List<Brick> bricks;

    public Home(Location location) {
        super(location);
        super.image = new ImageIcon(this.getClass().getResource("images/home.png")).getImage();
        width = image.getWidth(null);
        height = image.getHeight(null);
        isAlive = true;
        bricks = new CopyOnWriteArrayList<>();
        invisible = false;


    }

    public boolean isInvisible() {
        return invisible;
    }

    public void changeInvisible(){
        invisible = invisible ? false : true;
    }

    public List<Brick> getBricks() {
        return bricks;
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
        for(int i = 0;i < bricks.size(); ++i)
            bricks.get(i).draw(g);
    }
}
