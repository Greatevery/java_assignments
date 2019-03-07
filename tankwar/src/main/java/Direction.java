import javax.swing.*;
import java.awt.*;

public enum Direction {
    Up("U", 0, -1),
    Down("D", 0, 1),
    Left("L", -1, 0),
    Right("R", 1, 0),
    LeftUp("LU", -1, -1),
    LeftDown("LD", -1, 1),
    RightUp("RU", 1, -1),
    RightDown("RD", 1, 1);

    private String dir;
    final int xDir, yDir;

    Direction(String dir, int xDir, int yDir){
        this.dir = dir;
        this.xDir = xDir;
        this.yDir = yDir;
    }


    public Image getImage(String objectType){
        return new ImageIcon(this.getClass().getResource("images/" + objectType + dir + ".gif")).getImage();
    }
}
