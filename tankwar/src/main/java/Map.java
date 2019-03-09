import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Map extends GameObject{
    private List<Wall> walls;
    private Image tree;

    public Map() {
        tree = new ImageIcon(this.getClass().getResource("images/tree.png")).getImage();
        walls = new ArrayList<>();
        //initialize walls
        walls.add(new Wall(new Location(250, 100), 6, 1, 6));
        walls.add(new Wall(new Location(100, 200), 6, 6, 1));
        walls.add(new Wall(new Location(680, 200), 6, 6, 1));
    }

    public List<Wall> getWalls() {
        return walls;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(tree, TankWar.WIDTH - tree.getWidth(null) * 2, 0, null);
        g.drawImage(tree, 0, TankWar.HEIGHT - tree.getHeight(null) * 2, null);
        for(Wall wall : walls)
            wall.draw(g);
    }
}
