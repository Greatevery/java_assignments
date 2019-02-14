public class EnemyTank extends Tank{
    final static int MOVE_EPOCH = 3;
    final static int FIRE_EPOCH = 5;

    private int randStep;
    private int moveEpoch;
    private int fireEpoch;

    public EnemyTank(Location location){
        super(location);
        this.blood = new Blood(20);
        this.speedX = 5;
        this.speedY = 5;
        this.direction = getRandomDirection();
        this.randStep = Tools.nextInt(5);
        this.moveEpoch = MOVE_EPOCH;
        this.fireEpoch = FIRE_EPOCH;
    }


    public Direction getRandomDirection(){
        Direction newDir = this.direction;
        int rand = Tools.nextInt(8);
        for(Direction dir : Direction.values()){
            if(dir.getIndex() == rand)
                newDir = dir;
        }
        return newDir;
    }

    public void randomMove(){
        if(this.moveEpoch <= 0){
            if(this.randStep <= 0){
                this.direction = getRandomDirection();
                this.randStep = Tools.nextInt(8);
            }
            if(!this.outOfBounds()){
                this.move();
                this.randStep--;
            }else {
                this.direction = getRandomDirection();
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
