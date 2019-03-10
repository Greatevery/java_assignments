import java.awt.*;

public class Missile extends GameObject{
    private Direction direction;
    private Tank tank;
    private int speed;

    public Missile(Tank tank, Direction direction){
        this.tank = tank;
        this.direction = direction;
        super.location = initLocation();
        super.image = this.direction.getImage("Missile");
        super.width = image.getWidth(null);
        super.height = image.getHeight(null);
        this.speed = 10;
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

    public boolean outOfBounds(){
        int x = this.getLocation().getX();
        int y = this.getLocation().getY();
        if(x < 0 || x >= TankWar.GAME_WIDTH - width|| y < 0 || y >= TankWar.GAME_HEIGHT - height)
            return true;
        return false;
    }

    public void move(){
        int x = this.location.getX() + speed * this.direction.xDir;
        int y = this.location.getY() + speed * this.direction.yDir;
        this.location.setX(x);
        this.location.setY(y);
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(this.image, this.location.getX(), this.location.getY(),null);
    }
}
