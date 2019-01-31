import java.awt.*;

public abstract class GameObject{
    protected Location location;
    //protected Image image;

    public  GameObject(){

    }
    public GameObject(Location location){
        this.location = location;
    }

    public abstract void draw(Graphics g);
}
