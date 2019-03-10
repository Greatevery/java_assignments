import javax.swing.*;
import java.awt.*;

public class Map extends GameObject{
    private Wall wall;
    private Image tree;

    public Map() {
        tree = new ImageIcon(this.getClass().getResource("images/tree.png")).getImage();
        wall = new Wall();
    }

    public Wall getWall() {
        return wall;
    }

    @Override
    public void draw(Graphics g) {
        //g.drawImage(tree, TankWar.WIDTH - tree.getWidth(null) * 2, 0, null);
        //g.drawImage(tree, 0, TankWar.HEIGHT - tree.getHeight(null) * 2, null);
        wall.draw(g);
    }
}
