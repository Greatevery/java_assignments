import java.awt.*;

public abstract class GameObject{
    protected Location location;
    protected Image image;

    public  GameObject(){

    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public GameObject(Location location){
        this.location = location;
    }

    public abstract Rectangle getRectangle();

    public abstract void draw(Graphics g);
}
