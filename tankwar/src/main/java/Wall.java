import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Wall extends GameObject {
    public static final int GAP_TANK = 40;
    public static final int GAP_BRICK = 26;
    private List<Brick> bricks;

    public Wall() {
        super();
        bricks = new CopyOnWriteArrayList<>();
        //initialize bricks
        bricks.add(new Brick(new Location(GAP_TANK, GAP_BRICK + 15)));
        bricks.add(new Brick(new Location(GAP_TANK * 4, GAP_BRICK + 15)));
        bricks.add(new Brick(new Location(GAP_TANK * 10, GAP_BRICK + 15)));
        bricks.add(new Brick(new Location(GAP_TANK * 13, GAP_BRICK + 15)));

        for(int i = 2;i <= 6; ++i) {
            bricks.add(new Brick(new Location(GAP_TANK, GAP_BRICK * i)));
            bricks.add(new Brick(new Location(GAP_TANK * 4, GAP_BRICK * i)));
            bricks.add(new Brick(new Location(GAP_TANK * 10, GAP_BRICK * i)));
            bricks.add(new Brick(new Location(GAP_TANK * 13, GAP_BRICK * i)));
        }
        bricks.add(new Brick(new Location(GAP_TANK * 6, GAP_BRICK + 15)));
        bricks.add(new Brick(new Location(GAP_TANK * 8, GAP_BRICK + 15)));
        for(int i = 2; i <= 4; ++i){
            bricks.add(new Brick(new Location(GAP_TANK * 6, GAP_BRICK * i)));
            bricks.add(new Brick(new Location(GAP_TANK * 8, GAP_BRICK * i)));
        }
        for(int i = 1; i <= 4; ++i){
            bricks.add(new Brick(new Location(GAP_TANK, GAP_BRICK * (14 + i))));
            bricks.add(new Brick(new Location(GAP_TANK * 4, GAP_BRICK * (14 + i))));
            bricks.add(new Brick(new Location(GAP_TANK * 10, GAP_BRICK * (14 + i))));
            bricks.add(new Brick(new Location(GAP_TANK * 13, GAP_BRICK * (14 + i))));

        }
        bricks.add(new Brick(new Location(0, TankWar.GAME_HEIGHT / 2)));
        bricks.add(new Brick(new Location(GAP_BRICK + GAP_TANK, TankWar.GAME_HEIGHT / 2)));
        bricks.add(new Brick(new Location(GAP_BRICK * 2 + GAP_TANK, TankWar.GAME_HEIGHT / 2)));

        bricks.add(new Brick(new Location(TankWar.GAME_WIDTH - GAP_BRICK * 3 -GAP_TANK - 6, TankWar.GAME_HEIGHT / 2)));
        bricks.add(new Brick(new Location(TankWar.GAME_WIDTH - GAP_BRICK * 2 - GAP_TANK - 4, TankWar.GAME_HEIGHT / 2)));
        bricks.add(new Brick(new Location(TankWar.GAME_WIDTH - GAP_BRICK - 4, TankWar.GAME_HEIGHT / 2)));

        bricks.add(new Brick(new Location(GAP_TANK * 6, TankWar.GAME_HEIGHT / 2 - GAP_TANK)));
        bricks.add(new Brick(new Location(GAP_TANK * 8, TankWar.GAME_HEIGHT / 2 - GAP_TANK)));

        bricks.add(new Brick(new Location(GAP_TANK * 7 - GAP_BRICK / 2, TankWar.GAME_HEIGHT / 2 + GAP_BRICK * 2)));
        bricks.add(new Brick(new Location(GAP_TANK * 7 + GAP_BRICK / 2 + 2, TankWar.GAME_HEIGHT / 2 + GAP_BRICK * 2)));
        for (int i = 1; i <= 4; i++) {
            bricks.add(new Brick(new Location(GAP_TANK * 6, TankWar.GAME_HEIGHT / 2 + GAP_BRICK * i)));
            bricks.add(new Brick(new Location(GAP_TANK * 8, TankWar.GAME_HEIGHT / 2 + GAP_BRICK * i)));
        }
        for (int i = 1; i <= 3 ; i++) {
            bricks.add(new Brick(new Location(GAP_TANK * 6 + GAP_BRICK / 4, TankWar.GAME_HEIGHT - GAP_BRICK * i)));
            bricks.add(new Brick(new Location(GAP_TANK * 6 + GAP_BRICK / 4 + 3 * GAP_BRICK, TankWar.GAME_HEIGHT - GAP_BRICK * i)));

        }
        bricks.add(new Brick(new Location(GAP_TANK * 6 + GAP_BRICK / 4 + GAP_BRICK, TankWar.GAME_HEIGHT - GAP_BRICK * 3)));
        bricks.add(new Brick(new Location(GAP_TANK * 6 + GAP_BRICK / 4 + GAP_BRICK * 2, TankWar.GAME_HEIGHT - GAP_BRICK * 3)));
    }

    public List<Brick> getBricks() {
        return bricks;
    }

    @Override
    public void draw(Graphics g) {
        for(Brick brick : bricks){
            brick.draw(g);
        }
    }
}
