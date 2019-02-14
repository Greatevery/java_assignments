import javax.swing.*;
import java.awt.*;

public enum Direction {
    Up("U", 0, -1, 0),
    Down("D", 0, 1, 1),
    Left("L", -1, 0, 2),
    Right("R", 1, 0, 3),
    LeftUp("LU", -1, -1, 4),
    LeftDown("LD", -1, 1, 5),
    RightUp("RU", 1, -1, 6),
    RightDown("RD", 1, 1, 7);

    private String dir;
    final int xDir, yDir;
    final int index;

    private Direction(String dir, int xDir, int yDir, int index){
        this.dir = dir;
        this.xDir = xDir;
        this.yDir = yDir;
        this.index = index;
    }

    public int getIndex(){
        return this.index;
    }

    public Image getImage(String objectType){
        return new ImageIcon(this.getClass().getResource("images/" + objectType + dir + ".gif")).getImage();
    }
}
