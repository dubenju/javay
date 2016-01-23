package javay.game.fire_emble.ui;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JPanel;

import javay.game.fire_emble.listener.MoveThread;
import javay.game.fire_emble.map.Maps;
import javay.game.fire_emble.util.Consts;
import javay.game.fire_emble.util.ImageManager;
import javay.game.fire_emble.entity.Character;
import javay.game.fire_emble.entity.Location;
import javay.game.fire_emble.entity.Sentry;

public class FireEmblePanel extends JPanel  {

//  private int offset = Consts.HCS;
  private int offset = 0;
  private Cursor cursors;
  private int[][] map = Maps.MAP5;
  private Character c;
  private Sentry c2;

  public FireEmblePanel() {
    int width = Consts.HCS * this.map[0].length;
    int height = Consts.VCS * this.map.length;
    this.setPreferredSize(new Dimension(width, height));
    this.setLayout(null);
    this.setBackground(Color.BLUE);
    this.cursors = new Cursor(6,16);

    Location lo = new Location(6,16);
    this.c = new Character(lo);

    Location lo2 = new Location(7, 16);
    this.c2 = new Sentry(map, lo2, new Location(10, 1));

    new Thread(new Runnable() {
      public void run() {
        while(true){
          repaint();
          try{
            // TODO:40
            Thread.sleep(500);
          }catch(Exception e){
            e.printStackTrace();
          }
        }
      }
    }).start();

    new MoveThread(c2).start();
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    drawMap(g);
    drawCharacter(g);
    drawCursor(g);
  }

  private void drawMap(Graphics g) {
    int type = 0;
    for(int row = 0 ; row < map.length; row ++) {
      for(int col = 0; col < map[row].length; col ++) {
        type = map[row][col];
        Image img = ImageManager.getInstance().getImageByType(type);
        if (img != null) {
          g.drawImage(img, col * Consts.HCS + offset, row * Consts.VCS, this);
        }
        Color color = g.getColor();
        g.setColor(Color.GRAY);
        g.drawRect(col * Consts.HCS + offset, row * Consts.VCS, 30, 30);
        g.setColor(color);
      }
    }
  }

  private void drawCursor(Graphics g){
    cursors.drawSelf((Graphics2D)g);
  }

  private void drawCharacter(Graphics g){
    c.drawSelf((Graphics2D) g);
    c2.drawSelf((Graphics2D) g);
  }

  public Cursor getCursors() {
    return cursors;
  }

  public int[][] getMap() {
    return map;
  }

  public void setMap(int[][] map) {
    this.map = map;
  }

  public Character getC() {
    return c;
  }
}
