import javax.swing.*;
import java.awt.*;

public class Wall extends GameObject {
    private int brickCounts;
    private int row;
    private int col;


    public Wall(Location location, int brickCounts, int row, int col) {
        super(location);
        super.image = new ImageIcon(this.getClass().getResource("images/brick.png")).getImage();
        this.brickCounts = brickCounts;
        this.row = row;
        this.col = col;
        super.width = col * image.getWidth(null);
        super.height = row * image.getHeight(null);
    }

    @Override
    public Rectangle getRectangle() {
        return new Rectangle(this.location.getX(), this.location.getY(), width, height);
    }

    @Override
    public void draw(Graphics g) {
        for(int i = 0;i < row; ++i){
            for(int j = 0;j < col; ++j){
                g.drawImage(image, this.location.getX() + j * image.getWidth(null), this.location.getY() + i * image.getHeight(null), null);
            }
        }
    }
}
