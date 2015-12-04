/**
 * A star
 */
package javay.astar;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * A*搜索算法，A星算法。
 * 这是一种在图形平面上，有多个节点的路径，求出最低通过成本的算法。
 * 常用于游戏中的NPC的移动计算，或在线游戏的BOT的移动计算上。
 * 该算法像Dijkstra算法一样，可以找到一条最短路径；也像BFS一样，进行启发式的搜索。
 * 在此算法中，如果以 g(n)表示从起点到任意顶点n的实际距离，
 * h(n)表示任意顶点n到目标顶点的估算距离，
 * 那么 A*算法的公式为：f(n)=g(n)+h(n)。 这个公式遵循以下特性：
 * 如果h(n)为0，只需求出g(n)，即求出起点到任意顶点n的最短路径，则转化为单源最短路径问题，即Dijkstra算法
 * 如果h(n)<=“n到目标的实际距离”，则一定可以求出最优解。而且h(n)越小，需要计算的节点越多，算法效率越低。
 *
 * @author DBJ(dubenju@126.com) 2015.06.14 新建
 * @author DBJ(dubenju@126.com) 2015.06.26 把状态和地形用节点保存，坐标系修正，搜索区域的宽高删除
 */
public class AStar {
    /** 搜索区域 */
    private AStarNode[][] searchArea;
    private int width;
    private int height;
    private boolean bFlag = false;

    /** 开启列表 */
    private PriorityQueue<AStarNode> openList;
    /** FComparator */
    public static Comparator<AStarNode> fComparator = new Comparator<AStarNode>() {
        @Override
        public int compare(AStarNode c1, AStarNode c2) {
            return (int) (c1.getF() - c2.getF());
        }
    };

    /**
     * AStart
     * @param searchArea 搜索区域
     */
    public AStar(int[][] searchArea) {
        this.width = searchArea[0].length;
        this.height = searchArea.length;

        this.searchArea = new AStarNode[this.height][this.width];
        for (int h = 0; h < this.height; h ++) {
            for (int w = 0; w < this.width; w ++) {
                this.searchArea[h][w] = new AStarNode(w, h, searchArea[h][w]);
            }
        }
        this.openList = new PriorityQueue<>(10, fComparator);
    }

    /**
     * 查找路径
     *
     * @param x1 起点A的x坐标
     * @param y1 起点A的y坐标
     * @param x2 终点B的x坐标
     * @param y2 终点B的y坐标
     * @return   起点A到终点B的路径
     */
    public List<AStarNode> find(int x1, int y1, int x2, int y2) {
        AStarNode startNode = this.searchArea[y1][x1];
        AStarNode endNode   = this.searchArea[y2][x2];

        return this.find(startNode, endNode);
    }

    /**
     * find 搜索
     * @param startNode 起点
     * @param endNode   终点
     * @return          路径
     */
    private List<AStarNode> find(AStarNode startNode, AStarNode endNode) {
        List<AStarNode> resultList = new ArrayList<AStarNode>();
        /* 是否找到 */
        boolean findFalg = false;

        /* 1.从起点A开始，并将它添加到 “开启列表”。 */
        this.openList.add(startNode);
        this.searchArea[startNode.getY()][startNode.getX()].setStatus(AStarConstants.NOTE_STATUS_OPEN);

        AStarNode curNode = this.openList.poll();
        while (curNode != null) {
            /* c）对于当前方格临近的8个方格的每一个....（For Each） */
//            System.out.println("find@AStar curNode=" + curNode);
            for (int i = 0; i < (bFlag ? 8 : 4); i++) {
                switch (i) {
                case 0:// 右
                    if ( (curNode.getY() + 1) < this.height) check(this.searchArea[curNode.getY() + 1][curNode.getX()], endNode, curNode, AStarConstants.COST_ORTHOGONAL); // 10
                    break;
                case 1:// 下
                    if ( (curNode.getX() + 1) < this.width) check(this.searchArea[curNode.getY()][curNode.getX() + 1], endNode, curNode, AStarConstants.COST_ORTHOGONAL); // 10
                    break;
                case 2:// 左
                    if ( (curNode.getY() - 1) > 0) check(this.searchArea[curNode.getY() - 1][curNode.getX()], endNode, curNode, AStarConstants.COST_ORTHOGONAL); // 10
                    break;
                case 3:// 上
                    if ( (curNode.getX() - 1) > 0) check(this.searchArea[curNode.getY()][curNode.getX() - 1], endNode, curNode, AStarConstants.COST_ORTHOGONAL); // 10
                    break;
                case 4:// 右上
                    if ( (curNode.getY() + 1) < this.height && (curNode.getX() - 1) > 0) check(this.searchArea[curNode.getY() + 1][curNode.getX() - 1], endNode, curNode, AStarConstants.COST_DIAGONAL); // 14
                    break;
                case 5:// 右下
                    if ( (curNode.getY() + 1) < this.height && (curNode.getX() + 1) < this.width) check(this.searchArea[curNode.getY() + 1][curNode.getX() + 1], endNode, curNode, AStarConstants.COST_DIAGONAL); // 14
                    break;
                case 6:// 左上
                    if ( (curNode.getY() - 1) > 0 && (curNode.getX() - 1) > 0) check(this.searchArea[curNode.getY() - 1][curNode.getX() - 1], endNode, curNode, AStarConstants.COST_DIAGONAL); // 14
                    break;
                case 7:// 左下
                    if ( (curNode.getY() - 1) > 0 && (curNode.getX() + 1) < this.width) check(this.searchArea[curNode.getY() - 1][curNode.getX() + 1], endNode, curNode, AStarConstants.COST_DIAGONAL); // 14
                    break;
                } // end switch
            } // end for

            // 加入关闭列表
            findFalg = this.addClosedList(curNode, endNode);
            if (findFalg) {
                break;
            }

            /* a）寻找开启列表上最小F值的方格。将它作为当前方格。 */
            curNode = this.openList.poll();
        } // while

        if (findFalg) {
            // 有
            read(resultList, curNode);
            return resultList;
        } else {
            /* 无法找到目标方格并且开启列表是空的时候，不存在路径。 */
            return resultList;
        }
    }

    /**
     * 读取所有节点，从起点开始返
     *
     * @param resultList
     * @param node
     */
    private void read(List<AStarNode> resultList, AStarNode node) {
        if (node.getParent() != null) {
            read(resultList, node.getParent());
        }
        resultList.add(0, node);
    }

    /**
     * hasNearbyUnwalkable
     * @param node 节点
     * @param parent 父
     * @return
     */
    private boolean hasNearbyUnwalkable(AStarNode node, AStarNode parent) {
        boolean bRes = false;
        if (node.getX() != parent.getX() && node.getY() != parent.getY()) {
            if (this.searchArea[node.getY()][parent.getX()].getTerrain().getWalkable() == AStarConstants.NOTE_UNWALKABLE) {
                bRes = true;
            }
            if (this.searchArea[parent.getY()][node.getX()].getTerrain().getWalkable() == AStarConstants.NOTE_UNWALKABLE) {
                bRes = true;
            }
        }
        return bRes;
    }

    /**
     * 检查 当前节点周围的节点，是否能行，是否在开启列表中，是否在关闭列表中 如果不在关闭与开启列表中则加入开启列表，如果在开启中则更新节点G值信息
     *
     * @param node 节点
     * @param endNode 终点
     * @param parent 父
     * @param step 步长
     * @return
     */
    private boolean check(AStarNode node, AStarNode endNode, AStarNode parent, int step) {
        if (node.getTerrain().getWalkable() == AStarConstants.NOTE_UNWALKABLE) {
            /* 如果不能走，忽略它。*/
            return false;
        }

        if (node.getStatus() == AStarConstants.NOTE_STATUS_CLOSED) {
            /* 如果它在关闭列表上，忽略它。 */
            return false;
        }

        /* 否则，执行以下操作。 */
        if (node.getStatus() == AStarConstants.NOTE_STATUS_NONE) {
            if (hasNearbyUnwalkable(node, parent)) {
                return false;
            }

            /* 如果不在开启列表中，把它添加到开启列表。使当前方格成为这个方格的父。记录的方格F值，G值和H值。*/
            this.addOpenList(node, endNode, parent, step);

            return true;
        } else if (node.getStatus() == AStarConstants.NOTE_STATUS_OPEN) {
            /* 如果在开启列表了，检查看看采用G值来衡量这个路径到那个方格是否是更好的。*/
            this.updateOpenList(node, endNode, parent, step);
            return true;
        }
        return false;
    }

    /**
     * 添加到关闭列表
     *
     * @param node    节点
     * @param endNode 终点
     * @return true:路径被发现
     */
    private boolean addClosedList(AStarNode node, AStarNode endNode) {
        if (node.getX() == endNode.getX() && node.getY() == endNode.getY()) {
            /* 在目标方格添加到关闭列表的情况下，路径已经被发现 */
            return true;
        }

        this.searchArea[node.getY()][node.getX()].setStatus(AStarConstants.NOTE_STATUS_CLOSED);
        return false;
    }

    /**
     * 添加到开启列表
     * 使当前方格成为这个方格的父。记录的方格F值，G值和H值。
     *
     * @param node 节点
     * @param endNode 终点
     * @param parent  父
     * @param step    步长
     * @return
     */
    private boolean addOpenList(AStarNode node, AStarNode endNode, AStarNode parent, int step) {
        /* 使当前方格成为这个方格的父。 */
        node.setParent(parent);
        /* 记录的方格F值，G值和H值。 */
        this.count(node, endNode, step);

        this.openList.add(node);
        node.setStatus(AStarConstants.NOTE_STATUS_OPEN);
//        System.out.println("    putOpenTable@AStar " + node + parent);

        return true;
    }

    /**
     * 已经在开启列表了更新节点F值
     * 更低的G值意味着这是一个更好的路径。如果是这样，把方格的父改为当前方格，并重新计算方格的G值和F值。如果你保持开启列表排序F值，由于这个变化你可能需重存列表。
     *
     * @param node 节点
     * @param y y坐标
     * @param endNode 终点
     * @param parent 父
     * @param step 步长
     * @return
     */
    private boolean updateOpenList(AStarNode node, AStarNode endNode, AStarNode parent, int step) {
        for (AStarNode nd : this.openList) {
            if (node.equals(nd)) {
                node = nd;
                break ;
            }
        }
        int g = parent.getG() + step;
        if (g < node.getG()) {
            /* 如果是更低的G值意味着这是一个更好的路径。把方格的父改为当前方格 */
            node.setParent(parent);
            /* 并重新计算方格的G值和F值。*/
            this.count(node, endNode, step);

            /* 如果你保持开启列表按F值排序，由于这个变化你可能需重存列表。 */
            this.openList.remove(node);
            this.openList.add(node);

            return true;
        }
        return false;
    }

    /**
     * 计算F=G+H值
     *
     * @param node 节点
     * @param endNode 终点
     * @param step 步长
     */
    private void count(AStarNode node, AStarNode endNode, int step) {
        this.countG(node, node.getParent(), step);
        this.countH(node, endNode);
        this.countF(node);
    }

    /**
     * 计算G值
     * 将指定每个移动水平或垂直方成本为10，对角线移动成本为14
     * 找出那个方块的父亲的G值，然后加10或14取决于它从父方格是正交（非对角线）还是对角线。
     *
     * @param node 节点
     * @param parent 父
     * @param step 步长
     */
    private void countG(AStarNode node, AStarNode parent, int step) {
        if (parent == null) {
            node.setG(step);
        } else {
        	// 考虑地形的成本
            node.setG(parent.getG() + step + node.getTerrain().getCost());
        }
    }

    /**
     * 计算H值
     * 曼哈顿方法
     * 计算从当前方块到目标方水平和垂直方向移动方块的总数
     * 将总数乘以10
     *
     * @param node 节点
     * @param endNode 终点
     */
    private void countH(AStarNode node, AStarNode endNode) {
        node.setH((Math.abs(endNode.getX() - node.getX()) + Math.abs(endNode.getY() - node.getY())) * 10);
    }

    /**
     * 计算F值
     * F = G + H
     *
     * @param node 节点
     */
    private void countF(AStarNode node) {
        node.setF(node.getG() + node.getH());
    }
}
