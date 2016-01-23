package javay.game.fire_emble.listener;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;

import javay.game.fire_emble.ui.Cursor;
import javay.game.fire_emble.ui.FireEmblePanel;
import javay.game.fire_emble.util.Util;
import javay.game.fire_emble.entity.Character;
import javay.game.fire_emble.entity.Location;
public class InstructionListener extends KeyAdapter{

  private JPanel panel;

  public InstructionListener(JPanel panel){
    this.panel = panel;
  }

  public void keyPressed(KeyEvent e){
    int[][] map   = ((FireEmblePanel)panel).getMap();
    Cursor cursor = ((FireEmblePanel)panel).getCursors();
    Character c   = ((FireEmblePanel)panel).getC();

    int k = e.getKeyCode();
    int direction = 0;
    int x = 0;
    int y = 0;
    switch(k){
      case KeyEvent.VK_UP:
        direction = -2;
        break;

      case KeyEvent.VK_DOWN:
        direction = 2;
        break;

      case KeyEvent.VK_LEFT:
        direction = -1;
        break;

      case KeyEvent.VK_RIGHT:
        direction = 1;
        break;

      case KeyEvent.VK_G:  // g:create grass
        x = cursor.getX();
        y = cursor.getY();
        map[y][x] = 5; // 5:grass：草地
         ((FireEmblePanel)panel).setMap(map);
        break;

      case KeyEvent.VK_H:  // h:create hill
        x = cursor.getX();
        y = cursor.getY();
        map[y][x] = 6; // 6:hill：丘陵
         ((FireEmblePanel)panel).setMap(map);
        break;

      case KeyEvent.VK_S:  // s:create swamp
        x = cursor.getX();
        y = cursor.getY();
        map[y][x] = 7; // 7:swamp:沼泽
         ((FireEmblePanel)panel).setMap(map);
        break;

      case KeyEvent.VK_R:  // r:create river
        x = cursor.getX();
        y = cursor.getY();
        map[y][x] = 8; // 8:river：河流
         ((FireEmblePanel)panel).setMap(map);
        break;

      case KeyEvent.VK_B:  // b:create bridge
        x = cursor.getX();
        y = cursor.getY();
        map[y][x] = 9; // 9:bridge：桥
         ((FireEmblePanel)panel).setMap(map);
        break;

      case KeyEvent.VK_W:  // w:create wall
        x = cursor.getX();
        y = cursor.getY();
        map[y][x] = 2; // 2:墙
         ((FireEmblePanel)panel).setMap(map);
        break;

      case KeyEvent.VK_D: // d:create ground
        x = cursor.getX();
        y = cursor.getY();
        map[y][x] = 1; // 1:walkbale,
         ((FireEmblePanel)panel).setMap(map);
        break;

      case KeyEvent.VK_T: // t:target:mark
        x = cursor.getX();
        y = cursor.getY();
        map[y][x] = 4; // 4:目标
         ((FireEmblePanel)panel).setMap(map);

        break;

      case KeyEvent.VK_X: // choose & move
        x = cursor.getX();
        y = cursor.getY();
        if(cursor.isChosen()){
          System.out.println("move");
          c.setBlink(false);
          Location lo = new Location(x, y);
          Thread move = new MoveThread(c, lo, map, cursor);
          move.start();
        } else {
          if(c.getLo().getX()==x && c.getLo().getY() == y){
            System.out.println("chosen");
            c.setBlink(true);
            cursor.setChosen(true);
          }
        }
        break;

      case KeyEvent.VK_P: // print arrays of current map
        Util.printMap(map);
        break;
    }
    cursor.move(direction);
  }
}
