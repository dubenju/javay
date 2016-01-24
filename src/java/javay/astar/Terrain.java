package javay.astar;

/**
 * @author DBJ(dubenju@126.com)
 */
public class Terrain {

  private int val;
  private int walkable;
  private int cost;

  /**
   * 构造函数.
   */
  public Terrain(int val) {
    this.val = val;
    if (this.val == 0) {
      // 0:unwalkable
      this.walkable = AStarConstants.NOTE_UNWALKABLE;
      this.cost = AStarConstants.COST_NONE;
    }
    if (this.val == 1) {
      // 1:walkbale,ground
      this.walkable = AStarConstants.NOTE_WALKABLE;
      this.cost = AStarConstants.COST_NONE;
    }
    if (this.val == 2) {
      // 2:墙
      this.walkable = AStarConstants.NOTE_UNWALKABLE;
      this.cost = AStarConstants.COST_NONE;
    }
    if (this.val == 3) {
      // 3:侧壁
      this.walkable = AStarConstants.NOTE_UNWALKABLE;
      this.cost = AStarConstants.COST_NONE;
    }
    if (this.val == 4) {
      // 4:target:目标
      this.walkable = AStarConstants.NOTE_WALKABLE;
      this.cost = AStarConstants.COST_NONE;
    }
    if (this.val == 5) {
      // 5:grass：草地
      this.walkable = AStarConstants.NOTE_WALKABLE;
      this.cost = AStarConstants.COST_GRASS;
    }
    if (this.val == 6) {
      // 6:hill：丘陵
      this.walkable = AStarConstants.NOTE_WALKABLE;
      this.cost = AStarConstants.COST_HILL;
    }
    if (this.val == 7) {
      // 7:swamp:沼泽
      this.walkable = AStarConstants.NOTE_WALKABLE;
      this.cost = AStarConstants.COST_SWAMP;
    }
    if (this.val == 8) {
      //  8:river：河流
      this.walkable = AStarConstants.NOTE_WALKABLE;
      this.cost = AStarConstants.COST_RIVER;
    }
    if (this.val == 9) {
      // 9:bridge：桥
      this.walkable = AStarConstants.NOTE_WALKABLE;
      this.cost = AStarConstants.COST_NONE;
    }
  }

  /**
   * getVal.
   * @return val int
   */
  public int getVal() {
    return val;
  }

  /**
   * setVal.
   * @param val int
   */
  public void setVal(int val) {
    this.val = val;
  }

  /**
   * getWalkable.
   * @return walkable int
   */
  public int getWalkable() {
    return walkable;
  }

  /**
   * setWalkable.
   * @param walkable int
   */
  public void setWalkable(int walkable) {
    this.walkable = walkable;
  }

  /**
   * getCost.
   * @return cost int
   */
  public int getCost() {
    return cost;
  }

  /**
   * setCost.
   * @param cost int
   */
  public void setCost(int cost) {
    this.cost = cost;
  }
}
