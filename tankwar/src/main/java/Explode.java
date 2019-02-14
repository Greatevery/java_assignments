import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Explode extends GameObject{
    private List<Image> images;
    private int step;

    public Explode(Location location){
        super.location = location;
        this.step = 0;
        this.images = new ArrayList<>();
        for(int i = 0;i <= 10; ++i){
            images.add(new ImageIcon(this.getClass().getResource("images/" + i + ".gif")).getImage());
        }
    }

    @Override
    public Rectangle getRectangle() {
        return null;
    }

    @Override
    public void draw(Graphics g) {
        if(this.step > images.size() - 1)
            return;
        g.drawImage(images.get(this.step++), this.location.getX(), this.location.getY(), null);
    }
}
