
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author radle
 */
public class Hindernis {
    private class Hindernisse {
    BufferedImage image;
    int x;
    int y;
    
    Rectangle getHindernis() {
      Rectangle obstacle = new Rectangle();
      obstacle.x = x;
      obstacle.y = y;
      obstacle.width = image.getWidth();
      obstacle.height = image.getHeight();

      return obstacle;
    }
  }
  Random random = new Random();
  private int firstX;
  private int Interval;
  private int geschwindigkeit;
  
  private ArrayList<BufferedImage> imageList;
  private ArrayList<Hindernisse> obList;

  public Hindernis(int firstPos) {
    obList = new ArrayList<Hindernisse>();
    imageList = new ArrayList<BufferedImage>();
    
    firstX = firstPos;
    Interval = 200;
    geschwindigkeit = 11;
    
    imageList.add(new Bilder().gibBild("Bild/H-Stormtrooper.png"));
    imageList.add(new Bilder().gibBild("Bild/Jawa-1.png"));
    imageList.add(new Bilder().gibBild("Bild/Jawa-1.png"));
    imageList.add(new Bilder().gibBild("Bild/Tusk.png"));
    imageList.add(new Bilder().gibBild("Bild/H-Stormtrooper.png"));
    
    imageList.add(new Bilder().gibBild("Bild/H-Stormtrooper.png"));
    imageList.add(new Bilder().gibBild("Bild/Tusk.png"));
    
    int x = firstX;
    
    for(BufferedImage bi : imageList) {
      
      Hindernisse H = new Hindernisse();
      
      H.image = bi;
      H.x = x;
      H.y = Boden.BODENY - bi.getHeight() + 5;
      x += Interval;
      
      obList.add(H);
    }
  }
  
  public void update() {
    Iterator<Hindernisse> looper = obList.iterator();
    
    Hindernisse firstOb = looper.next();
    firstOb.x -= geschwindigkeit;
    
    while(looper.hasNext()) {
      Hindernisse H = looper.next();
      H.x -= geschwindigkeit;
    }
    
    Hindernisse lastOb = obList.get(obList.size() - 1);
    
    if(firstOb.x < -firstOb.image.getWidth()) {
      obList.remove(firstOb);
      firstOb.x = obList.get(obList.size() - 1).x + Interval + random.nextInt(100);
      obList.add(firstOb);
    }
  }
  
  public void erstelle(Graphics g) {
    for(Hindernisse H : obList) {
      g.setColor(Color.black);
      g.drawImage(H.image, H.x, H.y, null);
    }
  }
  
  public boolean hasCollided() {
    for(Hindernisse H : obList) {
      if(Spieler.getSpieler().intersects(H.getHindernis())) {
        return true;
      }   
    }
    return false;
  }

  public void weiter() {
    
    int x = firstX/2;   
    obList = new ArrayList<Hindernisse>();
    
    for(BufferedImage bi : imageList) {
      
      Hindernisse H = new Hindernisse();
      
      H.image = bi;
      H.x = x;
      H.y = Boden.BODENY - bi.getHeight() + 5;
      x += Interval + random.nextInt(100);
      
      obList.add(H);
    }
  }
}
