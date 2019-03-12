public class EnemyTank extends Tank{
    public final static int FULL_HP = 20;
    final static int MOVE_EPOCH = 3;
    final static int FIRE_EPOCH = 5;


    private int randStep;
    private int moveEpoch;
    private int fireEpoch;

    public EnemyTank(Location location){
        super(location);
        this.hp = FULL_HP;
        this.speed = 5;
        this.direction = Direction.Down;
        this.randStep = Tools.nextInt(5);
        this.moveEpoch = MOVE_EPOCH;
        this.fireEpoch = FIRE_EPOCH;
        setImage();
    }

    public void setRandomDirection() throws CloneNotSupportedException {
        int rand = Tools.nextInt(8);
        for(Direction dir : Direction.values()){
            if(dir.ordinal() == rand){
                if(super.canChangeDirection(dir)){
                    super.changeDirection(dir);
                    break;
                }
            }
        }
    }

    public void randomMove() throws CloneNotSupportedException {
        if(this.moveEpoch <= 0){
            if(this.randStep <= 0){
                setRandomDirection();
                this.randStep = Tools.nextInt(8);
            }
            if(this.canMove()){
                this.move();
                this.randStep--;
            }else {
                setRandomDirection();
                this.randStep = Tools.nextInt(8);
            }
            this.moveEpoch = MOVE_EPOCH;
        }
        this.moveEpoch--;
    }

    public void randomFire(){
        if(this.fireEpoch <= 0){
            int rand = Tools.nextInt(2);
            if(rand == 0)
                this.fire();
            this.fireEpoch = FIRE_EPOCH;
        }
        this.fireEpoch--;
    }

}
