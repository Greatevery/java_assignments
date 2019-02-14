import javax.swing.*;
import java.awt.*;

public class Blood extends GameObject {

    final static int WIDTH = 15, HEIGHT = 15;

    private boolean appear;

    public Blood(Location location){
        super(location);
        appear = true;
    }

    public boolean isAppear() {
        return appear;
    }

    public void setAppear(boolean appear) {
        this.appear = appear;
    }

    @Override
    public Rectangle getRectangle() {
        return new Rectangle(this.location.getX(), this.location.getY(), WIDTH, HEIGHT);
    }

    @Override
    public void draw(Graphics g) {
        //draw blood
        if(this.appear){
            g.setColor(Color.MAGENTA);
            g.fillRect(this.location.getX(), this.location.getY(), WIDTH, HEIGHT);
        }

    }
}
