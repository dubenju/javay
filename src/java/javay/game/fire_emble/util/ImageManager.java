package javay.game.fire_emble.util;


import java.awt.Image;

import javax.swing.ImageIcon;

public class ImageManager {

  private Image ground;
  private Image topWall;
  private Image sideWall;
  private Image cursor;
  private Image army;
  private Image target;
  private Image grass;
  private Image hill;
  private Image swamp;
  private Image river;
  private Image bridge;

  private static ImageManager instance;

  public static ImageManager getInstance(){
      if(instance == null){
        return new ImageManager();
      }
      return instance;
  }

  private ImageManager(){
      ground   = loadImg("../../../../ground.jpg");
      topWall  = loadImg("../../../../wall.jpg");
      sideWall = loadImg("../../../../sidewall.jpg");
      cursor   = loadImg("../../../../select.png");
      army   = loadImg("../../../../army.jpg");
      target   = loadImg("../../../../target.jpg");
      grass  = loadImg("../../../../grass.jpg");
      hill   = loadImg("../../../../hill.jpg");
      swamp  = loadImg("../../../../swamp.jpg");
      river  = loadImg("../../../../river.jpg");
      bridge   = loadImg("../../../../bridge.jpg");
  }

  public Image loadImg(String imgUrl){
     ImageIcon icon = new ImageIcon(getClass().getResource(imgUrl));
     return icon.getImage();
  }

  public Image getImageByType(int type){
      Image img = null;
      switch(type) {
        case 1:
          img = ground;
          break;

        case 2:
          img = topWall;
          break;

        case 3:
          img = sideWall;
          break;

        case 4:
          img = target;
          break;

        case 5:
          img = grass;
          break;

        case 6:
          img = hill;
          break;

        case 7:
          img = swamp;
          break;

        case 8:
          img = river;
          break;

        case 9:
          img = bridge;
          break;
      }
      return img;
  }

  public Image getGround() {
    return ground;
  }

  public Image getTopWall() {
    return topWall;
  }

  public Image getSideWall() {
    return sideWall;
  }

  public Image getCursor() {
    return cursor;
  }

  public Image getArmy() {
    return army;
  }

  public Image getTarget() {
    return target;
  }

  public void setTarget(Image target) {
    this.target = target;
  }
}
