import java.awt.*;

public class Tank extends GameObject implements Cloneable{
    public static final int WIDTH = 50, HEIGHT = 60;

    private boolean isAlive;

    protected int hp;
    protected int speedX;
    protected int speedY;
    protected Direction direction;

    public Tank(Location location){
        super(location);
        isAlive = true;
    }

    @Override
    public Tank clone() throws CloneNotSupportedException {
        Tank tank = (Tank)super.clone();
        if(location != null){
            tank.setLocation(location.clone());
        }
        return tank;
    }


    public boolean isAlive(){
        return isAlive;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
        if(this.hp <= 0){
            this.isAlive = false;
            this.hp = 0;
        }
    }

    private boolean hit(){
        if(TankWar.getInstance().tankHitBounds(this)){
            return true;
        }
        if(TankWar.getInstance().tankHitWalls(this)){
            return true;
        }
        return false;
    }

    public boolean canMove() throws CloneNotSupportedException {
        Tank temp = this.clone();
        temp.move();
        return temp.hit() ? false : true;
    }
    public void move(){
        this.location.setX(this.location.getX() + this.speedX * direction.xDir);
        this.location.setY((this.location.getY() + this.speedY * direction.yDir));
    }

    public void fire(){
        Tools.playAudio("shoot.wav");
        TankWar.getInstance().addMissile(new Missile(this, this.direction));
    }

    @Override
    public Rectangle getRectangle(){
        return new Rectangle(this.location.getX(), this.location.getY(), Tank.WIDTH, Tank.HEIGHT);
    }


    @Override
    public void draw(Graphics g) {
        g.drawImage(this.direction.getImage("Tank"), this.location.getX(), this.location.getY(), null);
        //draw HP
        if("PlayerTank".equals(this.getClass().getName())){
            g.setColor(Color.RED);
            g.fillRect(this.location.getX(), this.location.getY() - 10, 35 * this.hp / PlayerTank.FULL_HP, 10);
        }

    }
}
