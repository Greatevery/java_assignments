import com.sun.javafx.application.PlatformImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

class TankWar extends JComponent implements MouseListener {
    public static final int WIDTH = 800, HEIGHT = 600;
    public static final int GAME_WIDTH = 600, GAME_HEIGHT = 600;

    private static final int REPAINT_INTERVAL = 50;
    private static final int TIME_GAP = 100;

    private JButton singlePlayerMode;
    private JButton twoPlayerMode;
    private JButton help;
    private JButton moreGames;

    private boolean gameStart;
    private int x =  GAME_WIDTH / 2 - 100, y = GAME_HEIGHT - 80; //initialized location of playerTank

    private int time_gap;
    private PlayerTank playerTank;
    private List<EnemyTank> enemyTanks;
    private Blood blood;
    private Map map;
    private List<Missile> missiles;
    private List<Explode> explodes;

    private TankWar() {
        gameStart = false;
        time_gap = TIME_GAP;

        singlePlayerMode = new JButton();
        twoPlayerMode = new JButton();
        moreGames = new JButton();
        help = new JButton();
        setMenuButton(singlePlayerMode, 300, 235, 220, 70);
        setMenuButton(twoPlayerMode, 300, 315, 220, 70);
        setMenuButton(moreGames, 300, 395, 220, 70);
        setMenuButton(help, 300, 475, 220, 70);
    }

    private void setMenuButton(JButton button, int x, int y, int width, int height){
        button.setContentAreaFilled(false);
        button.setBounds(x, y,width, height);
        button.addMouseListener(this);
        this.add(button);

    }

    private void initWorld(){
        playerTank = new PlayerTank(new Location(x,y));
        enemyTanks = new ArrayList<>();
        missiles = new ArrayList<>();
        explodes = new ArrayList<>();
        blood = new Blood(new Location(GAME_WIDTH / 2, GAME_HEIGHT / 2));
        map = new Map();
        this.addKeyListener(this.playerTank);
    }

    private static TankWar INSTANCE;

    public static TankWar getInstance(){
        if(INSTANCE == null){
            INSTANCE = new TankWar();
        }
        return INSTANCE;
    }

    public void restart(){
        this.initWorld();
        //this.gameStart = false;
    }

    public boolean isGameStart() {
        return gameStart;
    }

    public void setGameStart(boolean gameStart) {
        this.gameStart = gameStart;
    }


    public void addMissile(Missile missile){
        missiles.add(missile);
    }

    private void triggerEvent() throws CloneNotSupportedException {
        if(playerTank.isAlive() && this.gameStart){
            addEnemyTank();
            playerTankEatBlood();
            playerTankIsDying();
            enemyTankRandomMoveAndFire();
            missileOutOfBounds();
            missileHitWalls();
            missileHitTank();
        }
    }

    private void addEnemyTank(){
        if(time_gap-- <= 0){
            while (true){
                int rand = Tools.nextInt(560);
                EnemyTank enemyTank = new EnemyTank(new Location(rand, 2));
                int i;
                for(i = 0;i < enemyTanks.size(); ++i){
                    if(enemyTank.getRectangle().intersects(enemyTanks.get(i).getRectangle())){
                        break;
                    }
                }
                if(i == enemyTanks.size()){
                    enemyTanks.add(enemyTank);
                    time_gap = TIME_GAP;
                    break;
                }
            }
        }
    }

    private void playerTankEatBlood(){
        if(playerTank.getRectangle().intersects(blood.getRectangle())){
            playerTank.setHp(PlayerTank.FULL_HP);
            blood.setAppear(false);
        }
    }



    private void playerTankIsDying(){
        if(playerTank.getHp() < PlayerTank.FULL_HP / 2){
            int rand = Tools.nextInt(3);
            if(rand == 0 || rand == 1)
                blood.setAppear(true);
        }
    }


    private void enemyTankRandomMoveAndFire() throws CloneNotSupportedException {
        for(EnemyTank enemyTank : enemyTanks){
            enemyTank.randomMove();
            enemyTank.randomFire();
        }
    }

    private void missileHitWalls(){
        for (Brick brick : map.getWall().getBricks()) {
              missiles.removeIf(missile -> missile.getRectangle().intersects(brick.getRectangle()));
        }
    }

    private void missileOutOfBounds(){
        missiles.removeIf(missile -> missile.outOfBounds());
        for (int i = 0; i < missiles.size(); i++) {
            missiles.get(i).move();
        }
    }

    private void missileHitTank(){
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

    public boolean tankHitEachOther(Tank tank){
        for(int i = 0;i < enemyTanks.size(); ++i) {
            EnemyTank enemy = enemyTanks.get(i);
            if (enemy.getId() != tank.getId()) {
                if (tank.getRectangle().intersects(enemyTanks.get(i).getRectangle())) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean tankHitBounds(Tank tank){
        int x = tank.getLocation().getX();
        int y = tank.getLocation().getY();
        if(x <= 0 || x >= TankWar.GAME_WIDTH  - tank.getWidth()|| y <= 0 || y >= TankWar.GAME_HEIGHT - tank.getHeight() * 1.8)
            return true;
        return false;
    }

    public boolean tankHitWalls(Tank tank){
        for(Brick brick : map.getWall().getBricks()){
            if(brick.getRectangle().intersects(tank.getRectangle())){
                return true;
            }
        }
        return false;
    }

    @Override
    protected void paintComponent(Graphics g) {

        //draw background
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        g.setColor(Color.BLACK);
        g.fillRect(0,0,GAME_WIDTH, GAME_HEIGHT);
        //start UI
        if(!this.gameStart){
            g.drawImage(new ImageIcon(this.getClass().getResource("images/background.png")).getImage(), 0, 0, null);
        }else{
            //draw game data
            g.setColor(Color.WHITE);
            g.setFont(new Font("Default", Font.BOLD, 14));
            g.drawString("Missiles: " + missiles.size(), GAME_WIDTH + 10, 50);
            g.drawString("Explodes: " + explodes.size(), GAME_WIDTH + 10, 70);
            g.drawString("Our Tank HP: " + playerTank.getHp(), GAME_WIDTH + 10, 90);
            g.drawString("Enemies Left: " + enemyTanks.size(), GAME_WIDTH + 10, 110);
            g.drawString("Enemies Killed: " + (12 - enemyTanks.size()), GAME_WIDTH + 10, 130);
            map.draw(g);
            blood.draw(g);

            //if player tank is alive, then draw the player tank and enemy tanks
            if(playerTank.isAlive()){
                playerTank.draw(g);
                for(int i = 0; i < missiles.size(); ++i)
                    missiles.get(i).draw(g);
                for(int i = 0;i < enemyTanks.size(); ++i)
                    enemyTanks.get(i).draw(g);
                for(int i = 0;i < explodes.size(); ++i)
                    explodes.get(i).draw(g);

            }else{
                //draw the UI of game over
                Tools.playAudio("death.mp3");
                g.setColor(Color.RED);
                g.setFont(new Font("Default", Font.BOLD, 30));
                g.drawString("GAME OVER ", GAME_WIDTH / 2, GAME_HEIGHT / 2 - 100);
                g.drawString("PRESS F2 TO RESTART", GAME_WIDTH / 2 - 160, GAME_HEIGHT / 2 - 60);
            }
        }
    }

    public void start() {
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
        frame.setResizable(true);

        TankWar tankWar = TankWar.getInstance();
        tankWar.initWorld();
        frame.add(tankWar);
        // KeyListeners need to be on the focused component to work
        tankWar.setFocusable(true);


        frame.setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getSource() == singlePlayerMode){
            if(!TankWar.getInstance().isGameStart()){
                removeAll();
                TankWar.getInstance().setGameStart(true);
                TankWar.getInstance().start();
            }
        }else if(e.getSource() == twoPlayerMode){
            remove(twoPlayerMode);
        }else if(e.getSource() == help){
            remove(help);
        }else if(e.getSource() == moreGames){
            remove(moreGames);
        }
        setCursor(Cursor.getDefaultCursor());
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if(!gameStart){
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if(!gameStart){
            setCursor(Cursor.getDefaultCursor());
        }
    }
}
