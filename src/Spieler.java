
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author radle
 */
public class Spieler {

    private static int spielerBaseY, spielerTopY, spielerStartX, spielerEndX;
    private static int spielerTop, spielerBottom, topPoint;
    private static boolean obersterPktErreicht;
    private static int sprungFactor = 15;
    public static final int STEHEN = 1,RENNEN = 2,SPRINGEN = 3,STERBEN = 4, CLEAR = 5;       

    private static int state;
    private static int Spielergroesse = 50;
    static Image Spielersteht ;
    Image SpielerLauf;
    Image SpielerTod;
    

    public Spieler() {
        Spielersteht = new Bilder().gibGIF("Bild/bbacht.gif");
        SpielerLauf = new Bilder().gibGIF("Bild/bbacht.gif");
        SpielerTod = new Bilder().gibGIF("Bild/dead.gif");

        spielerBaseY = Boden.BODENY + 5;
        spielerTopY = Boden.BODENY - Spielergroesse + 5;
        spielerStartX = 100;
        spielerEndX = spielerStartX + Spielergroesse;
        topPoint = spielerTopY - 120;

        state = 1;
    }

    public void erstelle(Graphics g) {
        spielerBottom = spielerTop + Spielergroesse;
        switch (state) {

            case STEHEN:
                g.drawImage(Spielersteht, spielerStartX, spielerTopY, null);
                break;

            case RENNEN:
                    g.drawImage(SpielerLauf, spielerStartX, spielerTopY,Spielergroesse,Spielergroesse, null);                
                break;
            case SPRINGEN:
                if (spielerTop > topPoint && !obersterPktErreicht) {
                    g.drawImage(Spielersteht, spielerStartX, spielerTop -= sprungFactor,Spielergroesse,Spielergroesse, null);
                    break;
                }
                if (spielerTop >= topPoint && !obersterPktErreicht) {
                    obersterPktErreicht = true;
                    g.drawImage(Spielersteht, spielerStartX, spielerTop += sprungFactor,Spielergroesse,Spielergroesse, null);
                    break;
                }
                if (spielerTop > topPoint && obersterPktErreicht) {
                    if (spielerTopY == spielerTop && obersterPktErreicht) {
                        state = RENNEN;
                        obersterPktErreicht = false;
                        break;
                    }
                    g.drawImage(Spielersteht, spielerStartX, spielerTop += sprungFactor,Spielergroesse,Spielergroesse, null);
                    break;
                }
            case STERBEN:
                g.drawImage(SpielerTod, spielerStartX, spielerTop,Spielergroesse,Spielergroesse, null);
                
                
                break;
            case CLEAR:
                g.clearRect(spielerStartX, spielerTop, Spielergroesse, Spielergroesse);
        }
    }

    public void Sterbe() {
        state = STERBEN;
    }

    public static Rectangle getSpieler() {      //benötigt für Kollision
        Rectangle spieler = new Rectangle();
        spieler.x = spielerStartX;

        if (state == SPRINGEN && !obersterPktErreicht) {
            spieler.y = spielerTop - sprungFactor;
        } else if (state == SPRINGEN && obersterPktErreicht) {
            spieler.y = spielerTop + sprungFactor;
        } else if (state != SPRINGEN) {
            spieler.y = spielerTop;
        }

        spieler.width = Spielergroesse;
        spieler.height = Spielergroesse;

        return spieler;
    }

    public void startRunning() {
        spielerTop = spielerTopY;
        state = RENNEN;    
    }

    public void jump() {
        spielerTop = spielerTopY;
        obersterPktErreicht = false;
        state = SPRINGEN;
    }

    



}
