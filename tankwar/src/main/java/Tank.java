import java.awt.*;

public class Tank extends GameObject implements Cloneable{
    private static int ID = 0;
    private boolean isAlive;

    protected int id;
    protected int hp;
    protected int speed;
    protected Direction direction;

    public Tank(Location location){
        super(location);
        this.id = Tank.ID++;
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

    protected void setImage(){
        //根据坦克的方向更新图片及大小
        super.image = this.direction.getImage("Tank");
        super.width = super.image.getWidth(null);
        super.height = super.image.getHeight(null);
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


    public int getId() {
        return id;
    }

    protected boolean hit(){
        if(TankWar.getInstance().tankHitBounds(this)){
            return true;
        }
        if(TankWar.getInstance().tankHitWalls(this)){
            return true;
        }
        if(TankWar.getInstance().tankHitEachOther(this)){
            return true;
        }
        return false;
    }

    protected boolean canMove() throws CloneNotSupportedException {
        //创建一个临时对象，判断是否碰撞
        Tank temp = this.clone();
        temp.move();
        return temp.hit() ? false : true;
    }

    protected void move(){
        this.location.setX(this.location.getX() + this.speed * direction.xDir);
        this.location.setY((this.location.getY() + this.speed * direction.yDir));
    }

    protected boolean canChangeDirection(Direction dir) throws CloneNotSupportedException {
        Tank temp = this.clone();
        temp.changeDirection(dir);
        return temp.hit() ? false : true;
    }

    protected void changeDirection(Direction dir){
        this.direction = dir;
        setImage();
    }

    public void fire() {
        Tools.playAudio("shoot.wav");
        TankWar.getInstance().addMissile(new Missile(this, this.direction));
    }


    @Override
    public void draw(Graphics g) {
        g.drawImage(this.image, this.location.getX(), this.location.getY(), null);
        //draw HP
        if("PlayerTank".equals(this.getClass().getName())){
            g.setColor(Color.RED);
            g.drawRect(this.location.getX(), this.location.getY() - 10, 35, 10);
            g.fillRect(this.location.getX(), this.location.getY() - 10, 35 * this.hp / PlayerTank.FULL_HP, 10);
        }

    }
}
