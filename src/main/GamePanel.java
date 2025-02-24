package main;

import entity.Player;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    final int originalTileSize = 16;
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; // 48 * 48
    final int maxScreenCol =  16;
    final int maxScreenRow =  12;
    final int screenWidth = tileSize * maxScreenCol; //768px
    final int screenHeight = tileSize * maxScreenRow; // 576px

    //FPS
    int FPS = 60;

    Thread gameThread;
    KeyHandler keyHandler = new KeyHandler();
    Player player = new Player(this, keyHandler);

    //Set entity.Player position
    int playerX = 100,playerY = 100,playerSpeed = 4;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);

    }
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

//    @Override
//    public void run() {
//        double drawInterval = 1000000000/FPS; // 0.01666 seconds
//        double nextDrawTime = System.nanoTime() + drawInterval;
//        while(gameThread != null) {
//
//            update();
//
//            repaint();
//
//            try {
//                double remainingTime = nextDrawTime - System.nanoTime();
//                remainingTime = remainingTime / 1000000;
//                if (remainingTime <= 0) {
//                    remainingTime = 0;
//                }
//                Thread.sleep((long) remainingTime);
//                nextDrawTime += drawInterval;
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }

    @Override
    public void run(){
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while(gameThread != null){
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;
            if(delta >= 1){
                update();
                repaint();
                delta--;
            }

        }
    }

    public void update() {
        player.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        player.draw(g2);
        g2.dispose();
    }
}
