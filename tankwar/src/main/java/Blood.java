import java.awt.*;

public class Blood extends GameObject {
    private int HP;

    public Blood(int HP){
        this.HP = HP;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    @Override
    public Rectangle getRectangle() {
        return null;
    }

    @Override
    public void draw(Graphics g) {

    }
}
