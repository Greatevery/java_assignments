import com.sun.javafx.application.PlatformImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class TankWar extends JComponent {
    private static final int WIDTH = 800, HEIGHT = 600;

    private static final int REPAINT_INTERVAL = 50;

    private int x = WIDTH / 2, y = HEIGHT / 2; //initialized location of playerTank
    private int my = HEIGHT / 2 + 50;

    private PlayerTank playerTank;
    private Blood blood;

    private TankWar() {
        playerTank = new PlayerTank(new Location(x,y));
        this.addKeyListener(this.playerTank);
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
        g.drawString("Missiles: " + Tools.nextInt(10), 10, 50);
        g.drawString("Explodes: " + Tools.nextInt(10), 10, 70);
        g.drawString("Our Tank HP: " + Tools.nextInt(10), 10, 90);
        g.drawString("Enemies Left: " + Tools.nextInt(10), 10, 110);
        g.drawString("Enemies Killed: " + Tools.nextInt(10), 10, 130);
        //draw HP
//        g.setColor(Color.RED);
//        g.fillRect(x, y - 10, 35, 10);
        //g.drawImage(new ImageIcon(this.getClass().getResource("images/tankD.gif")).getImage(),
         //   x, y, null);
        //blood.draw(g);
        playerTank.draw(g);
        //draw enemy tanks
        int dist = (WIDTH - 120) / 9;
        for (int i = 0; i < 10; i++) {
            g.drawImage(new ImageIcon(this.getClass().getResource("images/tankU.gif")).getImage(),
                50 + dist * i, HEIGHT / 2 + 100, null);
        }
        //draw missile
        g.drawImage(new ImageIcon(this.getClass().getResource("images/missileD.gif")).getImage(),
            WIDTH / 2, my, null);
        //draw explosion
        g.drawImage(new ImageIcon(this.getClass().getResource("images/10.gif")).getImage(),
            WIDTH / 2, 100, null);
    }

    private void start() {
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                while (true) {
                    try {
                        repaint();
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

        TankWar tankWar = new TankWar();
        frame.add(tankWar);
        // KeyListeners need to be on the focused component to work
        tankWar.setFocusable(true);
        frame.setVisible(true);
        tankWar.start();
    }
}
