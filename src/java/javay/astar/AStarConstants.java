/**
 * A star
 */
package javay.astar;

/**
 * AStarConstants
 * @author DBJ(dubenju@126.com)
 */
public class AStarConstants {
	public static int COST_NONE = 0;
    /** 正交移动一格的路径分值 */
    public static int COST_ORTHOGONAL = 10;
    /** 对角线移动一格的路径分值 */
    public static int COST_DIAGONAL = 14;
    public static int COST_GRASS = 12; // 草地
    public static int COST_HILL = 20; // 丘陵
    public static int COST_SWAMP = 30; // 沼泽
    public static int COST_RIVER = 40; // 河流

    /** 不能走 */
    public static int NOTE_UNWALKABLE = 0;
    /** 能走 */
    public static int NOTE_WALKABLE = 1;

    /** 没有使用 */
    public static int NOTE_STATUS_NONE = 0;
    /** 在开启列表中 */
    public static int NOTE_STATUS_OPEN = 1;
    /** 在关闭列表中 */
    public static int NOTE_STATUS_CLOSED = 2;
    /** 在路径上 */
    public static int NOTE_ONPATH = 99;
}
