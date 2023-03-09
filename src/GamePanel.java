
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.*;
import java.awt.event.*;
import static java.awt.event.KeyEvent.VK_P;
import static java.awt.event.KeyEvent.VK_SPACE;
import static java.awt.event.KeyEvent.VK_UP;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Random;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author radle
 */
public class GamePanel extends javax.swing.JPanel {

    /**
     * Creates new form GamePanel
     */
    public static int WIDTH;
    public static int HEIGHT;

    private boolean running = false;
    private boolean gameOver = false;
    Boden boden;
    Spieler spieler;
    Hindernis hindernis;
    private int Punkte;
    private int Highscore;
    private double delay = 50;
    Random random = new Random();
    Timer Timr;

    private int Zeit = 500;

    private long time;
    BufferedImage Bg = new Bilder().gibBild("Bild/Background.png");
    Clip Cantina;

    boolean Musik=false;
    
  
    public GamePanel() {
        initComponents();
        WIDTH = GameFrame.WIDTH;
        HEIGHT = GameFrame.HEIGHT;

        boden = new Boden(HEIGHT);
        spieler = new Spieler();
        hindernis = new Hindernis((int) (WIDTH * 1.5 + random.nextInt(50)));
        Cantina = new Musik().SpieleMusik("src\\Musik\\BG.wav");
        Cantina.start();
        Cantina.loop(Clip.LOOP_CONTINUOUSLY);
       
        Punkte = 0;

        setSize(WIDTH, HEIGHT);
        Timr = new Timer((int) delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (running == true) {
                    updateSpiel();
                    repaint();
                    if (delay > 30) {
                        delay = delay * 0.99994;        //spiel wird langsam schneller
                    }

                    time = System.currentTimeMillis();
                } else if (System.currentTimeMillis() - time > 1300) {
                    Timr.stop();                                              // nach gif ende (1,3 Sek)timer stoppen
                }
                repaint();

            }

        });
        Timr.start();

    }

    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(Bg, 0, 0, this);
        g.setFont(new Font("Courier New", Font.BOLD, 20));
        g.drawString(Integer.toString(Punkte), getWidth() - 75, 20);      //Score     zeichnen
        g.drawString(Integer.toString(Highscore), getWidth() - 150, 20);   //Highscore zeichnen
        if (gameOver) {
            if(Highscore > Punkte){
                g.drawString("Verloren! Highscore:" + Integer.toString(Highscore), (getWidth() / 2) - 100, (getHeight() / 2) - 50);
                g.drawString("Score:" + Integer.toString(Punkte), (getWidth() / 2 + 168) - 100, (getHeight() / 2 + 20) - 50);
            }else{
                g.drawString("Neuer Highscore! Highscore:" + Integer.toString(Highscore), (getWidth() / 2) - 150, (getHeight() / 2) - 50);
               
            }
            g.drawString("P - Play/Pause Music", 0, 20);
            g.drawString("Space/Up - Jump, Start Game", 0, 40);
        }
        boden.erstelle(g);
        spieler.erstelle(g);
        hindernis.erstelle(g);
    }

    public void updateSpiel() {
        Punkte += 1;
        if (Highscore < Punkte) {
            Highscore = Punkte;
        }
        boden.update();
        hindernis.update();

        if (hindernis.hasCollided()) {
            spieler.Sterbe();
            repaint();
            running = false;
            gameOver = true;
            Clip Boom = new Musik().SpieleMusik("src\\Musik\\Dirt_Explosion.wav");
            Boom.start();
        }
    }

    public void reset() {
        Punkte = 0;
        hindernis.weiter();
        delay = 50;
        gameOver = false;
        Clip BB = new Musik().SpieleMusik("src\\Musik\\BB8\\"+random.nextInt(31)+".wav");
        BB.start();
    }

    public void neuzeichnen() {
        repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                formKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        if (evt.getKeyCode() == VK_UP || evt.getKeyCode() == VK_SPACE) {
            if (gameOver == true) {
                reset();

            }
            if (!running) {
                System.out.println("Game starts");
                running = true;
                Timr.start();
                spieler.startRunning();
            } else {
                spieler.jump();
            }
        }
        if (evt.getKeyCode() == VK_P) {
            if(Musik){
                Cantina.start();
                Musik = !Musik;
            }else{
                Cantina.stop();
                Musik = !Musik;
            }
          }
    }//GEN-LAST:event_formKeyPressed

    private void formKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyTyped

    }//GEN-LAST:event_formKeyTyped


    


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
