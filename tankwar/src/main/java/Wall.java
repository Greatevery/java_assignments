import java.awt.*;

public class Wall extends GameObject {
    private int width;
    private int height;

    public Wall(Location location, int width, int height) {
        super(location);
        this.width = width;
        this.height = height;
    }

    @Override
    public Rectangle getRectangle() {
        return new Rectangle(this.location.getX(), this.location.getY(), width, height);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(this.location.getX(), this.location.getY(), this.width, this.height);
    }
}
