import java.awt.*;

public abstract class GameObject{
    protected Location location;
    protected Image image;
    protected int width;
    protected int height;

    public  GameObject(){

    }

    public GameObject(Location location){
        this.location = location;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Rectangle getRectangle(){
        return new Rectangle(this.location.getX(), this.location.getY(), width, height);
    }

    public abstract void draw(Graphics g);
}
