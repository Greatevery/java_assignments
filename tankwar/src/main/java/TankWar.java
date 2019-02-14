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


    private TankWar() {
        playerTank = new PlayerTank(new Location(x,y));
        enemyTanks = new ArrayList<>();
        missiles = new ArrayList<>();
        explodes = new ArrayList<>();
        blood = playerTank.getBlood();
        this.addKeyListener(this.playerTank);
        this.initWorld();
    }

    private void initWorld(){
        int dist = (WIDTH - 120) / 12;
        for(int i = 0;i < 12; ++i){
            enemyTanks.add(new EnemyTank(new Location(50 + dist * i,HEIGHT / 2 + 100)));
        }
    }

    private static TankWar INSTANCE;

    public static TankWar getInstance(){
        if(INSTANCE == null){
            INSTANCE = new TankWar();
        }
        return INSTANCE;
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
            missileOutOfBounds();
            missileHitTank();
            playerTankHitEnemyTank();
            enemyTankRandomMoveAndFire();
            enemyTankHitEachOther();
        }
    }

    public void enemyTankRandomMoveAndFire(){
        for(EnemyTank enemyTank : enemyTanks){
            enemyTank.randomMove();
            enemyTank.randomFire();
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
                    this.blood.setHP(this.blood.getHP() - 20);
                    playerTank.setBlood(this.blood);
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
        //draw wall
        g.setColor(Color.DARK_GRAY);
        g.fillRect(250, 100, 300, 20);
        g.fillRect(100, 200, 20, 150);
        g.fillRect(680, 200, 20, 150);
        //draw blood
        g.setColor(Color.MAGENTA);
        g.fillRect(360, 270, 15, 15);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Default", Font.BOLD, 14));
        g.drawString("Missiles: " + playerTank.getTotalMissiles(), 10, 50);
        g.drawString("Explodes: " + explodes.size(), 10, 70);
        g.drawString("Our Tank HP: " + playerTank.getBlood().getHP(), 10, 90);
        g.drawString("Enemies Left: " + enemyTanks.size(), 10, 110);
        g.drawString("Enemies Killed: " + (12 - enemyTanks.size()), 10, 130);

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
        PlatformImpl.startup(() -> {});
        Tools.setTheme();
        JFrame frame = new JFrame("Tank War");
        frame.setIconImage(new ImageIcon(TankWar.class.getResource("/icon.png")).getImage());
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocation(400, 100);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);

        TankWar tankWar = TankWar.getInstance();
        frame.add(tankWar);
        // KeyListeners need to be on the focused component to work
        tankWar.setFocusable(true);
        frame.setVisible(true);
        tankWar.start();
    }
}
