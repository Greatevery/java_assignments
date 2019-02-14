import com.sun.javafx.application.PlatformImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

class TankWar extends JComponent {
    public static final int WIDTH = 800, HEIGHT = 600;

    private static final int REPAINT_INTERVAL = 50;

    private int x = WIDTH / 2, y = HEIGHT / 2; //initialized location of playerTank
    private int my = HEIGHT / 2 + 50;

    private PlayerTank playerTank;
    private List<EnemyTank> enemyTanks;
    private Blood blood;
    private List<Missile> missiles;
    private List<Explode> explodes;
    private List<Wall> walls;

    private TankWar() {
        playerTank = new PlayerTank(new Location(x,y));
        enemyTanks = new ArrayList<>();
        missiles = new ArrayList<>();
        explodes = new ArrayList<>();
        walls = new ArrayList<>();
        blood = new Blood(new Location(360, 270));
        this.addKeyListener(this.playerTank);
        //initialize enemy tanks
        int dist = (WIDTH - 120) / 12;
        for(int i = 0;i < 12; ++i){
            enemyTanks.add(new EnemyTank(new Location(50 + dist * i,HEIGHT / 2 + 100)));
        }
        //initialize walls
        walls.add(new Wall(new Location(250, 100), 300, 20));
        walls.add(new Wall(new Location(100, 200), 20, 150));
        walls.add(new Wall(new Location(680, 200), 20, 150));
    }

    private void initWorld(){
        PlatformImpl.startup(() -> {});
        Tools.setTheme();
        JFrame frame = new JFrame("Tank War");
        frame.setIconImage(new ImageIcon(TankWar.class.getResource("/icon.png")).getImage());
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocation(400, 100);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);

        frame.add(this);
        // KeyListeners need to be on the focused component to work
        this.setFocusable(true);
        frame.setVisible(true);
    }

    private static TankWar INSTANCE;

    public static TankWar getInstance(){
        if(INSTANCE == null){
            INSTANCE = new TankWar();
        }
        return INSTANCE;
    }

    public void restart(){
        TankWar tankWar = TankWar.getInstance();
        tankWar.initWorld();
        tankWar.start();
    }

    public void gameOver(Graphics g){
        Tools.playAudio("death.mp3");
        g.setColor(Color.RED);
        g.setFont(new Font("Default", Font.BOLD, 30));
        g.drawString("GAME OVER ", x - 100, y - 100);
        g.drawString("PRESS F2 TO RESTART", x - 160, y - 60);
    }

    public void addMissile(Missile missile){
        missiles.add(missile);
    }

    public void triggerEvent(){
        if(playerTank.isAlive()){
            playerTankEatBlood();
            playerTankIsDying();
            missileOutOfBounds();
            missileHitTank();
            missileHitWalls();
            playerTankHitEnemyTank();
            enemyTankRandomMoveAndFire();
            enemyTankHitEachOther();
            enemyTankHitWalls();
        }
    }

    public void playerTankEatBlood(){
        if(playerTank.getRectangle().intersects(blood.getRectangle())){
            playerTank.setHp(PlayerTank.FULL_HP);
            blood.setAppear(false);
        }
    }

    public void enemyTankHitWalls(){
        for(Wall wall : walls){
            for(EnemyTank enemyTank : enemyTanks){
                if(wall.getRectangle().intersects(enemyTank.getRectangle())){
                    enemyTank.setRandomDirection();
                }
            }
        }
    }

    public void playerTankIsDying(){
        if(playerTank.getHp() < PlayerTank.FULL_HP / 2){
            int rand = Tools.nextInt(3);
            if(rand == 0 || rand == 1)
                blood.setAppear(true);
        }
    }


    public void enemyTankRandomMoveAndFire(){
        for(EnemyTank enemyTank : enemyTanks){
            enemyTank.randomMove();
            enemyTank.randomFire();
        }
    }

    public void missileHitWalls(){
        for(Wall wall : walls){
            missiles.removeIf(missile -> missile.getRectangle().intersects(wall.getRectangle()));
        }
    }

    public void missileOutOfBounds(){
        missiles.removeIf(missile -> missile.outOfBounds());
        for(Missile missile : missiles)
            missile.move();
    }

    public void missileHitTank(){
        ListIterator<Missile> iterMissile = missiles.listIterator();
        while(iterMissile.hasNext()){
            Missile missile = iterMissile.next();
            //shoot enemy tank
            if(missile.getTankType() == "PlayerTank"){
                ListIterator<EnemyTank> iterEnemyTank = enemyTanks.listIterator();
                while(iterEnemyTank.hasNext()){
                    EnemyTank enemyTank = iterEnemyTank.next();
                    if(enemyTank.getRectangle().intersects(missile.getRectangle())){
                        explodes.add(new Explode(enemyTank.getLocation()));
                        iterEnemyTank.remove();
                        iterMissile.remove();
                        break;
                    }
                }
                //enemyTanks.removeIf(enemyTank -> enemyTank.getRectangle().intersects(missile.getRectangle()));
            }else{
                if(playerTank.getRectangle().intersects(missile.getRectangle())){
                    playerTank.setHp(this.playerTank.getHp() - 20);
                }
            }
        }
    }

    public void playerTankHitEnemyTank(){
        //enemyTanks.removeIf(enemyTank -> enemyTank.getRectangle().intersects(playerTank.getRectangle()));
        for(EnemyTank enemyTank : enemyTanks){
            if(enemyTank.getRectangle().intersects(playerTank.getRectangle()))
                enemyTank.randomMove();
        }
    }

    public void enemyTankHitEachOther(){
        for(int i = 0;i < enemyTanks.size(); ++i)
            for(int j = i + 1; j < enemyTanks.size(); ++j){
                if(enemyTanks.get(i).getRectangle().intersects(enemyTanks.get(j).getRectangle())){
                    enemyTanks.get(i).randomMove();
                    enemyTanks.get(j).randomMove();
                }
            }
    }


    @Override
    protected void paintComponent(Graphics g) {
        //draw background
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        //draw game data
        g.setColor(Color.WHITE);
        g.setFont(new Font("Default", Font.BOLD, 14));
        g.drawString("Missiles: " + playerTank.getTotalMissiles(), 10, 50);
        g.drawString("Explodes: " + explodes.size(), 10, 70);
        g.drawString("Our Tank HP: " + playerTank.getHp(), 10, 90);
        g.drawString("Enemies Left: " + enemyTanks.size(), 10, 110);
        g.drawString("Enemies Killed: " + (12 - enemyTanks.size()), 10, 130);
        //draw game objects
        for(Wall wall : walls)
            wall.draw(g);
        blood.draw(g);
        if(playerTank.isAlive()){
            playerTank.draw(g);
            for(Missile missile : missiles){
                missile.draw(g);
            }
            for(EnemyTank enemyTank : enemyTanks)
                enemyTank.draw(g);
            for(Explode explode : explodes)
                explode.draw(g);
        }else{
            gameOver(g);
        }

    }

    private void start() {
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                while (true) {
                    try {
                        repaint();
                        triggerEvent();
                        Tools.sleepSilently(REPAINT_INTERVAL);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }.execute();
    }

    public static void main(String[] args) {

        TankWar tankWar = TankWar.getInstance();
        tankWar.initWorld();
        tankWar.start();
    }
}
