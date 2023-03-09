
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author radle
 */
public class Boden {

    private class BodenBilder {

        BufferedImage image;
        int x;
    }

    public static int BODENY;

    private BufferedImage image;

    private ArrayList<BodenBilder> BodenBild;

    public Boden(int panelHeight) {
        BODENY = (int) (panelHeight - 0.25 * panelHeight);

        try {
            image = new Bilder().gibBild("Bild/Ground.png");
        } catch (Exception e) {
            e.printStackTrace();
        }

        BodenBild = new ArrayList<BodenBilder>();

        for (int i = 0; i < 3; i++) {
            BodenBilder obj = new BodenBilder();
            obj.image = image;
            obj.x = 0;
            BodenBild.add(obj);
        }
    }

    public void update() {
        Iterator<BodenBilder> looper = BodenBild.iterator();
        BodenBilder first = looper.next();

        first.x -= 10;

        int previousX = first.x;
        while (looper.hasNext()) {
            BodenBilder next = looper.next();
            next.x = previousX + image.getWidth();
            previousX = next.x;
        }

        if (first.x < -image.getWidth()) {
            BodenBild.remove(first);
            first.x = previousX + image.getWidth();
            BodenBild.add(first);
        }

    }

    public void erstelle(Graphics g) {
        for (BodenBilder img : BodenBild) {
            g.drawImage(img.image, (int) img.x, BODENY, null);
        }
    }
}
