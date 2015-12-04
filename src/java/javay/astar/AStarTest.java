/**
 * A star
 */
package javay.astar;

import java.util.Date;
import java.util.List;

/**
 * @author DBJ(dubenju@126.com)
 */
public class AStarTest {

    /**
     * @param args
     */
    public static void main(String[] args) {

        int[][] mySearchArea = new int[][] {
        {1, 1, 1, 1, 1, 1, 1, 1},
        {1, 1, 1, 1, 0, 1, 1, 1},
        {1, 1, 1, 1, 0, 1, 1, 1},
        {1, 1, 1, 1, 0, 1, 1, 1},
        {1, 1, 1, 1, 1, 1, 1, 1},
        {1, 1, 1, 1, 1, 1, 1, 1}
        };
        AStar astar = new AStar(mySearchArea);

        System.out.println("Pathfinding by A*...");
        long t = new Date().getTime();

        List<AStarNode> list = astar.find(2, 2, 6, 2);
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                mySearchArea[list.get(i).getY()][list.get(i).getX()] = AStarConstants.NOTE_ONPATH;
            }

            System.out.println("--------------->X");
            System.out.println(" 012345678901234");
            for (int i = 0; i < mySearchArea.length; i ++) {
                System.out.print(i % 10);
                for (int j = 0; j < mySearchArea[0].length; j++) {
                    if (mySearchArea[i][j] == AStarConstants.NOTE_ONPATH) {
                        System.out.print("*");
                    } else {
                        if (mySearchArea[i][j] == AStarConstants.NOTE_UNWALKABLE) {
                            System.out.print("=");
                        } else {
                            System.out.print(" ");
                        }
                    }
                }
                System.out.println();
            }
        } else {
            System.out.println("没有路径。");
        }
        System.out.println("Y------------------");
        System.out.println("end 耗时:" + (new Date().getTime() - t));
    }
}
