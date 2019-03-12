import javax.swing.*;
import java.awt.*;

public class Map extends GameObject{
    private Wall wall;
    private Image tree;
    private Home home;

    public Map() {
        init();
    }

    public void init(){
        wall = new Wall();
        home = new Home(new Location(Wall.GAP_TANK * 6 + 43, TankWar.GAME_HEIGHT - 40));
    }


    public Wall getWall() {
        return wall;
    }

    public Home getHome() {
        return home;
    }

    @Override
    public void draw(Graphics g) {
        //g.drawImage(tree, TankWar.WIDTH - tree.getWidth(null) * 2, 0, null);
        //g.drawImage(tree, 0, TankWar.HEIGHT - tree.getHeight(null) * 2, null);
        if(wall != null)
            wall.draw(g);
        if(home != null)
            home.draw(g);
    }
}
