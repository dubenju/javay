package javay.game.fire_emble.util;
import java.util.Map;

import javay.game.fire_emble.entity.Location;

public class Util {

    public static boolean reachDestination(Location lo,int x,int y){
        if(lo != null && lo.getX() == x && lo.getY() == y){
            return true;
        }
        return false;
    }

    public static boolean checkPositionAvail(int x,int y,Map<String,String> route,int[][] map){
        if(!(x>=0 && x<map[0].length && y>=0 && y<map.length)){
            return false;
        }
        int type = map[y][x];
        String id = ""+x+y;
        String result = route.get(id);
        boolean hasBeen = false;
        if(result != null){
            hasBeen = true;
        }
        if(type ==  Consts.GROUND && !hasBeen){
            return true;
        }
        return false;
    }

    public static void printMap(int[][] map){
        for(int j = 0; j < map.length; j ++) {
            System.out.print("{");
            for(int i = 0; i < map[0].length; i ++){
                System.out.print(map[j][i] + (i < (map[0].length - 1) ? "," : ""));
            }
            System.out.print("},");
            System.out.println();
        }
    }
}
