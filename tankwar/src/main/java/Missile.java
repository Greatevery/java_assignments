import javax.swing.*;
import java.awt.*;

public class Missile extends GameObject{
    public static final int WIDTH = 10, HEIGHT = 10;

    private Direction direction;
    private Tank tank;
    private int speedX;
    private int speedY;
    private boolean exist;

    public Missile(Tank tank, Direction direction){
        this.tank = tank;
        this.direction = direction;
        super.location = initLocation();
        super.image = this.direction.getImage("Missile");
        this.speedX = 10;
        this.speedY = 10;
        this.exist = true;
    }

    public String getTankType(){
        return this.tank.getClass().getName();
    }

    public Location initLocation(){
        int x = this.tank.getLocation().getX();
        int y = this.tank.getLocation().getY();
        switch (this.direction){
            case Up:
                x += 11;
                y -= 4;
                break;
            case Down:
                x += 13;
                y += 25;
                break;
            case Left:
                y += 15;
                break;
            case Right:
                x += 35;
                y += 15;
                break;
            case LeftUp:
                break;
            case LeftDown:
                y += 28;
                break;
            case RightUp:
                x += 35;
                break;
            case RightDown:
                x += 40;
                y += 40;
                break;
        }
        return new Location(x, y);
    }

    public boolean isExist(){
        return exist;
    }

    public boolean outOfBounds(){
        int x = this.getLocation().getX();
        int y = this.getLocation().getY();
        if(x < 0 || x > TankWar.WIDTH || y < 0 || y > TankWar.HEIGHT)
            return true;
        return false;
    }

    public boolean hitEnemyTank(){
        return false;
    }


    public void move(){
        int x = this.location.getX() + speedX * this.direction.xDir;
        int y = this.location.getY() + speedY * this.direction.yDir;
        this.location.setX(x);
        this.location.setY(y);
    }

    @Override
    public Rectangle getRectangle() {
        return new Rectangle(this.location.getX(), this.location.getY(), WIDTH, HEIGHT);
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(this.image, this.location.getX(), this.location.getY(),null);
    }
}
