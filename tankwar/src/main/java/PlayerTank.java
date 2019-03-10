
import java.awt.event.*;

public class PlayerTank extends Tank implements KeyListener {
    public static final int FULL_HP = 1000;

    public PlayerTank(Location location){
        super(location);
        super.hp = FULL_HP;
        super.speed = 5;
        super.direction = Direction.Up;
        setImage();
    }



    private void updateLocation() throws CloneNotSupportedException {
        //update the location of tank
        Direction newDir = updateDirection();
        if(this.direction == newDir) {
            if(this.canMove())
                this.move();
        }else{
            if(super.canChangeDirection(newDir)){
                super.changeDirection(newDir);
            }
        }
    }

    private boolean bu, bd, bl, br;

    private Direction updateDirection(){
        Direction newDir = this.direction;
        if(bu && !bd && !bl && !br)
            newDir = Direction.Up;
        else if(!bu && bd && !bl && !br)
            newDir = Direction.Down;
        else if(!bu && !bd && bl && !br)
            newDir = Direction.Left;
        else if(!bu && !bd && !bl && br)
            newDir = Direction.Right;
        else if(bu && !bd && bl && !br)
            newDir = Direction.LeftUp;
        else if(bu && !bd && !bl && br)
            newDir = Direction.RightUp;
        else if(!bu && bd && bl && !br)
            newDir = Direction.LeftDown;
        else if(!bu && bd && !bl && br)
            newDir = Direction.RightDown;
         return newDir;
    }


    public void superFire(){
        Tools.playAudio(Tools.nextBoolean() ? "supershoot.wav" : "supershoot.aiff");
        for(Direction direction : Direction.values())
            TankWar.getInstance().addMissile(new Missile(this, direction));
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key){
            case KeyEvent.VK_SPACE:
                if(!TankWar.getInstance().isGameStart()){
                    TankWar.getInstance().setGameStart(true);
                    TankWar.getInstance().start();
                }
                return;
            case KeyEvent.VK_F2:
                if(!this.isAlive()){
                    TankWar.getInstance().restart();
                }
                return;
            case KeyEvent.VK_CONTROL :
                this.fire();
                return;
            case KeyEvent.VK_A :
                this.superFire();
                return;
            case KeyEvent.VK_LEFT :
                bl = true;
                break;
            case KeyEvent.VK_UP :
                bu = true;
                break;
            case KeyEvent.VK_RIGHT :
                br = true;
                break;
            case KeyEvent.VK_DOWN :
                bd = true;
                break;
            default : break;
        }
        try {
            updateLocation();
        } catch (CloneNotSupportedException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key){
            case KeyEvent.VK_CONTROL :
                break;
            case KeyEvent.VK_A :
                break;
            case KeyEvent.VK_LEFT :
                bl = false;
                break;
            case KeyEvent.VK_UP :
                bu = false;
                break;
            case KeyEvent.VK_RIGHT :
                br = false;
                break;
            case KeyEvent.VK_DOWN :
                bd = false;
                break;
            default : break;
        }
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

}
