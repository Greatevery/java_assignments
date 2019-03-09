import javax.swing.*;
import java.awt.*;

public class Blood extends GameObject {
    private boolean appear;

    public Blood(Location location){
        super(location);
        appear = true;
        super.image = new ImageIcon(this.getClass().getResource("images/blood.png")).getImage();
        super.width = image.getWidth(null);
        super.height = image.getHeight(null);
    }

    public boolean isAppear() {
        return appear;
    }

    public void setAppear(boolean appear) {
        this.appear = appear;
    }

    @Override
    public void draw(Graphics g) {
        //draw blood
        if(this.appear){
            g.drawImage(image, this.location.getX(), this.location.getY(),null);
        }

    }
}
