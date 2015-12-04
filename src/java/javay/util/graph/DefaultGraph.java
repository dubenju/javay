/**
 *
 */
package javay.util.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import javay.util.graph.x.ParentTree;
import javay.util.heap.BinaryHeap;

/**
 * 图
 * @author DBJ(dubenju@126.com)
 */
public class DefaultGraph implements Graph {

    /** 顶点集  */
    private ArrayList<Vertex> vertexes;
    /** 邻接表  */
    private ArrayList<LinkedList<Edge>> adjacencyList;
    private int edgeSize;

    /**
     * 图
     */
    public DefaultGraph() {
        this.vertexes = new ArrayList<Vertex>();
        this.adjacencyList = new ArrayList<LinkedList<Edge>>();
        this.edgeSize = 0;
    }

    /**
     * 顶点数
     */
    public int size() {
        return this.vertexes.size();
    }

    public boolean isEdge(Edge edge) {
        return (edge != DefaultEdge.NullEdge);
    }

    /**
     * 边
     * @param from 起始顶点
     * @param to 终止顶点
     * @param weight 权
     */
    public void setEdge(Vertex from, Vertex to, int weight) {
        if(weight < 0 || from.equals(to) == true ) {
            throw new IllegalArgumentException();
        }

        DefaultEdge edge = new DefaultEdge(from, to, weight);
        edge.setNextEdge(DefaultEdge.NullEdge);

        int idx = this.vertexes.indexOf(from);
        if (idx >= 0) {
            from.setIndex(idx);
            LinkedList<Edge> edges = this.adjacencyList.get(from.getIndex());
            if (edges.size() > 0) {
                Edge prevEdge = edges.get(edges.size() - 1);
                prevEdge.setNextEdge(edge);
            }
            edges.add(edge);
            edgeSize ++;
        } else {
            idx = this.vertexes.size();
            this.vertexes.add(from);
            from.setIndex(idx);
            LinkedList<Edge> edges = new LinkedList<Edge>();
            this.adjacencyList.add(edges);
            from.setAdjacencyEdges(edges);
            edges.add(edge);
            edgeSize ++;
        }
        idx = this.vertexes.indexOf(to);
        if (idx < 0) {
            idx = this.vertexes.size();
            this.vertexes.add(to);
            to.setIndex(idx);
            LinkedList<Edge> edges = new LinkedList<Edge>();
            this.adjacencyList.add(edges);
            to.setAdjacencyEdges(edges);
        }
    }

    /** 获取顶点v在图中的位置 */
    public int indexOf(Vertex vertex) {
        return this.vertexes.indexOf(vertex);
    }

    /** 获取顶点v在图中的值 */
    /** 向图中放入值位value的顶点v */

    /** 指定顶点vertex的第一邻接顶点 */
    /** 指定顶点vertex的下一个邻接顶点 */
    /** 向图中放入顶点v */
    /** 删除顶点v */
    /** 向图中放入边e */
    /** s删除边e */

    public DefaultEdge firstEdge(Vertex vertex) {
        DefaultEdge result = DefaultEdge.NullEdge;
        List<Edge> a = this.adjacencyList.get(vertex.getIndex());
        if (a.size() > 0) {
            result = (DefaultEdge) a.get(0);
        }
        return result;
    }

    /**
     * 遍历
     */

    /**
     * 深度优先遍历(DFS)
     */
    public void depthFirstTraversal(GraphVisitor visitor) {
        // reset Visited
        initVisit();
        for(int i = 0; i < this.vertexes.size(); i ++) {
            Vertex vertex = this.vertexes.get(i);
            if(vertex.isVisited() == false) {
                do_DFS(vertex,  firstEdge(vertex), visitor);
            }
        }
    }

    /**
     * 深度优先遍历x
     * @param v 顶点
     * @param edge
     * @param visitor
     */
    private final void do_DFS(Vertex v, Edge edge, GraphVisitor visitor) {
        // first visit this vertex
        visitor.visit(this, edge, v);
        v.setVisit(true);

        // for each edge from this vertex, we do one time and this for loop is very classical in all graph algorithms
        for(Edge e = firstEdge(v); isEdge(e); e = e.getNextEdge()) {
            if(e.getTo().isVisited() == false) {
                do_DFS(e.getTo(), e, visitor);
            }
        }
    }

    /**
     * 广度优先遍历(BFS)
     */
    public void breathFirstTraversal(GraphVisitor visitor) {
        // reset Visited
        initVisit();
        for(int i = 0; i < this.vertexes.size(); i ++) {
            Vertex vertex = this.vertexes.get(i);
            if(vertex.isVisited() == false) {
                do_BFS(vertex, firstEdge(vertex), visitor);
            }
        }
    }

    /**
     * 广度优先遍历x
     * @param v 顶点
     * @param visitor
     */
    private void do_BFS(Vertex v, Edge edge, GraphVisitor visitor) {
        // BFS will use an queue to keep the unvisited vertexes. we can also just use java.util.LinkedList
        LinkedList<Vertex> queue = new LinkedList<Vertex>();
        LinkedList<Edge> queueE = new LinkedList<Edge>();
        queue.add(v);
        queueE.add(edge);
        while(!queue.isEmpty()) {
            Vertex fromV = queue.poll();
            Edge ed = queueE.poll();;
            if (fromV.isVisited() == false) {
                visitor.visit(this, ed, fromV);
                fromV.setVisit(true);

                for(Edge e = firstEdge(fromV); isEdge(e); e = e.getNextEdge()) {
                    Vertex toV = e.getTo();
                    if(toV.isVisited() == false) {
                        queue.add(toV);
                        queueE.add(e);
                    }
                }
            }
        }
    }

    private void initVisit() {
        for (Vertex vertext : this.vertexes) {
            vertext.setVisit(false);
        }
    }

    /**
     * A星
     */

    /**
     * Flood fill
     */

    /**
     * 强连通分量
     */

    /**
     * Kosaraju算法
     * 1.初始化设置所有的顶点为未访问的；
     * 2.从任意顶点 v 开始进行 DFS 遍历，如果遍历结果没有访问到所有顶点，则说明图不是强连通的；
     * 3.构造G的反向图GT
     * 4.设置置换后的图中的所有顶点为未访问过的；
     * 5.从与步骤 2 中相同的顶点 v 开始做 DFS 遍历，如果遍历没有访问到所有顶点，则说明图不是强连通的，否则说明图是强连通的。
     * @param visitor
     * @return 函数最终返回一个二维单链表slk，单链表每个节点又是一个单链表， 每个节点处的单链表表示一个连通区域；slk的长度代表了图中联通区域的个数。
     */
    public boolean kosaraju(GraphVisitor visitor) {
    	boolean bres = false;
        // 1.初始化设置所有的顶点为未访问的；
        initVisit();

        // 2.从任意顶点 v 开始进行 DFS 遍历，如果遍历结果没有访问到所有顶点，则说明图不是强连通的；
        Vertex vertex = this.vertexes.get(0);
        // 对原图进行深度优先搜索
        do_DFS(vertex, firstEdge(vertex), visitor);
        for (Vertex vt : this.vertexes) {
        	if (vt.isVisited() == false) {
        		return bres;
        	}
        }

        // 3.构造G的反向图GT
        DefaultGraph gt = new DefaultGraph();
        for (LinkedList<Edge> edges : this.adjacencyList) {
        	for (Edge edge : edges) {
        		gt.setEdge(edge.getTo(), edge.getFrom(), edge.getWeight());
        	}
        }

        // 4.设置置换后的图中的所有顶点为未访问过的；
        gt.initVisit();

        // 5.从与步骤 2 中相同的顶点 v 开始做 DFS 遍历，如果遍历没有访问到所有顶点，则说明图不是强连通的，否则说明图是强连通的。
        Vertex vertext = gt.vertexes.get(0);
        gt.do_DFS(vertext,  gt.firstEdge(vertex), visitor);
        for (Vertex vt : gt.vertexes) {
        	if (vt.isVisited() == false) {
        		return bres;
        	}
        }

        // 返回连通区域链表
        return true;
    }

    /**
     * Tarjan算法
     * Trajan 算法实现图的强连通区域求解；
     * 算法步骤：
     * @param visitor
     * @return一个二维单链表slk，单链表每个节点也是一个单链表，每个节点处的单链表表示一个联通区域；slk的长度代表了图中联通区域的个数。
     */
    public LinkedList<LinkedList<Integer>> tarjan(GraphVisitor visitor) {
        // 算法需借助于栈结构
    	Stack<Integer> ls = new Stack<Integer>();
        int pre[] = new int[this.vertexes.size()];
        int low[] = new int[this.vertexes.size()];
        int cnt[] = new int[1];
        // 初始化
        for(int i = 0; i < this.vertexes.size(); i ++) {
            pre[i] = -1;
            low[i] = -1;
        }

        // 对顶点运行递归函数TrajanDFS
        LinkedList<LinkedList<Integer>> slk = new LinkedList<LinkedList<Integer>>();
        for(int i = 0; i < this.vertexes.size(); i ++) {
            if(pre[i] == -1) {
                tarjanDFS(i, pre, low, cnt, ls, slk);
            }
        }

        // 打印所有的连通区域
        for(LinkedList<Integer> comp_i: slk) {
            // 获取一个链表元素项，即一个连通区域，打印这个连通区域的每个图节点
            for(Integer elem : comp_i) {
                System.out.print(elem + "\t");
            }
            System.out.println();
        }
        return slk;
    }

    /**
     * Tarjan算法的递归DFS函数
     */
    public void tarjanDFS(int w, int pre[], int low[], int cnt[], Stack<Integer> ls, LinkedList<LinkedList<Integer>> slk) {
        int t , min = low[w] = pre[w] = cnt[0]++;
        // 将当前顶点号压栈
        ls.push(new Integer(w));
        // 对当前顶点的每个邻点循环
        for(Edge e = this.firstEdge(this.vertexes.get(w)); this.isEdge(e); e = e.getNextEdge()) {
            // 如果邻点没有遍历过，则对其递归调用
            if(pre[e.getTo().getIndex()] == -1) {
                tarjanDFS(e.getTo().getIndex(), pre, low, cnt, ls, slk);
            }
            /* 如果邻点已经被遍历过了，比较其编号与min,
             * 如果编号较小，则更新min的大小*/
            if(low[e.getTo().getIndex()] < min)
                min = low[e.getTo().getIndex()];
        }
        // 如果此时min小于low[w]，则返回
        if(min < low[w]){
            low[w] = min;
            return ;
        }
        // 否则，弹出栈中元素，并将元素添加到链表中
        LinkedList<Integer> gnslk = new LinkedList<Integer>();
        do{
            //弹出栈顶元素
            t = ls.pop().intValue();
            low[t] = this.vertexes.get(t).getIndex();
            //添加到链表中
            gnslk.add(t);
        } while(t != w);
        // 将该链表添加到slk链表中
        slk.add(gnslk);
    }

    /**
     * Gabow算法
     */
    /* Gabow 算法实现图的强连通区域查找；
     * @param G    输入为图结构
     * @return:
     * 函数最终返回一个二维单链表slk，单链表每个节点又是一个单链表，
     * 每个节点处的单链表表示一个联通区域；
     * slk的长度代表了图中联通区域的个数。
     */
    public LinkedList<LinkedList<Integer>> Gabow(GraphVisitor visitor) {
        // 函数使用两个堆栈
        Stack<Integer> ls = new Stack<Integer>();
        Stack<Integer> P = new Stack<Integer>();
        int pre[] = new int[this.vertexes.size()];
        int cnt[] = new int[1];
        // 标注各个顶点所在的连通分支的名称
        int id[]  = new int[this.vertexes.size()];
        // 初始化
        for(int i = 0; i < this.vertexes.size(); i++){
            pre[i] = -1;
            id[i] = -1;
        }

        LinkedList<LinkedList<Integer>> slk = new LinkedList<LinkedList<Integer>>();
        for(int i = 0; i < this.vertexes.size(); i ++) {
            if(pre[i] == -1) {
                gabowDFS(i, pre, id, cnt, ls, P, slk);
            }
        }

        //打印所有的联通区域
        for(LinkedList<Integer> comp_i: slk) {
            //获取一个链表元素项，即一个联通区域
            //打印这个联通区域的每个图节点
        	for(Integer elem : comp_i) {
                System.out.print(elem + "\t");
            }
            System.out.println();
        }
        return slk;
    }

    /**
     * GabowDFS算法的递归DFS函数
     * @param ls    栈1,
     * @param P    栈2，决定何时弹出栈1中顶点
     */
    public void gabowDFS(int w, int pre[],  int[] id, int cnt[], Stack<Integer> ls, Stack<Integer> P, LinkedList<LinkedList<Integer>> slk) {
        int v;
        pre[w] = cnt[0] ++;
        //将当前顶点号压栈
        ls.push(w);
//        System.out.print("+0 stack1 ");ls.printStack();
        P.push(w);
//        System.out.print("+0 stack2 ");P.printStack();

        // 对当前顶点的每个邻点循环
        for(Edge e = firstEdge(this.vertexes.get(w)); isEdge(e); e = e.getNextEdge()){
            if(pre[e.getTo().getIndex()] == -1) {
                //如果邻点没有遍历过，则对其递归调用
                gabowDFS(e.getTo().getIndex(), pre, id, cnt, ls, P, slk);
            } else if(id[e.getTo().getIndex()] == -1) {
                // 否则，如果邻点被遍历过但又没有被之前的连通域包含，则循环弹出
                int ptop = ((Integer)(P.peek())).intValue();
                // 循环弹出，直到栈顶顶点的序号不小于邻点的序号
                while(pre[ptop] > pre[e.getTo().getIndex()]) {
                    P.pop();
//                    System.out.print("-1 stack2 ");P.printStack();
                    ptop = ((Integer)(P.peek())).intValue();
                }
            }
        }
        if (P.peek() != null && ((Integer)(P.peek())).intValue() == w) {
            // 遍历完顶点的所有相邻顶点后，如果栈2顶部顶点与w相同则弹出；
            P.pop();
//            System.out.print("-2 stack2 ");P.printStack();
        } else {
            // 否则函数返回
        	return;
        }

        // 如果栈2顶部顶点与w相同，则弹出栈1中顶点，形成连通分支
        LinkedList<Integer> gnslk = new LinkedList<Integer>();
        do {
            v = ls.pop().intValue();
            id[v] = 1;
            gnslk.add(v);
        } while(v != w);
//        System.out.print("-3 stack1 ");ls.printStack();
        slk.add(gnslk);
    }

    /**
     * 最小生成树
     */

    /**
     * 普里姆算法(1957)
     * @return
     */
    public Edge[] prim() {
        BinaryHeap<DefaultEdge> heap = new BinaryHeap<DefaultEdge>(true, null);
        Edge[] edges = new Edge[this.vertexes.size() - 1];
        //we start from 0
        int num = 0;//record already how many edges;
        int startV = 0;
        initVisit();
        DefaultEdge e;
        for(e = firstEdge(this.vertexes.get(startV)); isEdge(e); e = e.getNextEdge()) {
            heap.insert(e);
        }
        this.vertexes.get(startV).setVisit(true);

        while( num < this.vertexes.size() - 1 && !heap.isEmpty() ) {
            //tree's edge number was n-1
            e = heap.delete();

            startV = e.getTo().getIndex();
            if(this.vertexes.get(startV).isVisited() == true) {
                continue;
            }
            this.vertexes.get(startV).setVisit(true);
            edges[num ++] = e;
            for(e = firstEdge(this.vertexes.get(startV)); isEdge(e); e = e.getNextEdge()) {
                if(e.getTo().isVisited() == false) {
                    //can't add already visit vertex, this may cause cycle
                    heap.insert(e);
                }
            }
        }
        if(num < this.vertexes.size() - 1) {
            throw new IllegalArgumentException("not connected graph");
        }
        return edges;
    }

    /**
     * 克鲁斯克尔算法(1956)
     * @return
     */
    public Edge[] kruskal() {
        Edge[] edges = new Edge[this.vertexes.size() - 1];
        ParentTree ptree = new ParentTree(this.vertexes.size());
        BinaryHeap<DefaultEdge> heap = new BinaryHeap<DefaultEdge>(true, null);
        for(int i = 0; i < this.vertexes.size(); i ++) {
            for(DefaultEdge e = firstEdge(this.vertexes.get(i)); isEdge(e); e = e.getNextEdge()) {
                heap.insert(e);
            }
        }
        int index = 0;
        while(ptree.numPartions() > 1) {
            Edge e = heap.delete();
            if(ptree.union(e.getFrom().getIndex(), e.getTo().getIndex())) {
                edges[index ++] = e;
            }
        }
        if(index < this.vertexes.size() - 1) {
            throw new IllegalArgumentException("Not a connected graph");
        }
        return edges;
    }

    /**
     * 拓扑排序
     */

    /**
     * 计算各个顶点的入度
     * @param inDegrees
     */
    private final void calculateInDegrees(int[] inDegrees) {
        Arrays.fill(inDegrees, 0);
        for(int v = 0; v < this.vertexes.size(); v ++) {
            for(Edge e = firstEdge(this.vertexes.get(v)); isEdge(e); e = e.getNextEdge()) {
                inDegrees[e.getTo().getIndex()] ++;
            }
        }
    }

    /**
     * 拓扑排序(入度)
     * @param sortedVertexes
     */
    public void topologySort(Vertex[] sortedVertexes) {
        // first calculate the inDegrees
        int[] inDegrees=new int[this.vertexes.size()];
        calculateInDegrees(inDegrees);

        initVisit();

        LinkedList<Vertex> queue = new LinkedList<Vertex>();

        for(int v = 0; v < this.vertexes.size(); v ++) {
            if(inDegrees[v] == 0) {
                queue.add(this.vertexes.get(v));
            }
        }

        int index = 0;
        while(!queue.isEmpty()) {
            Vertex from = queue.poll() ;
            System.out.println("visit:"+from);
            sortedVertexes[index ++] = from;
            for(Edge e = firstEdge(from); isEdge(e); e = e.getNextEdge()) {

                if(--inDegrees[e.getTo().getIndex()] == 0) {
                    queue.add(e.getTo());
                }
            }
        }
        if(index < this.vertexes.size()) {
            throw new IllegalArgumentException("There is a loop");
        }

    }

    /**
     * 拓扑排序(DFS)
     * @param sortedVertexes
     */
    public void topologySort_byDFS(Vertex[] sortedVertexes) {
        initVisit();
        int num = 0;
        for(int i = 0; i < this.vertexes.size(); i ++) {
            Vertex v = this.vertexes.get(i);
            if(v.isVisited() == false) {
                num = do_topsort(v, sortedVertexes, num);
            }
        }

    }

    /**
     * 拓扑排序
     * @param v
     * @param sortedVertexes
     * @param num
     * @return
     */
    private final int do_topsort(Vertex v, Vertex[] sortedVertexes, int num) {
        v.setVisit(true);

        for(Edge e = firstEdge(v); isEdge(e); e = e.getNextEdge()) {
            if(e.getTo().isVisited() == false) {
                num = do_topsort(e.getTo(), sortedVertexes, num);
            }
        }
        num++;
        sortedVertexes[this.vertexes.size() - num] = v;

        return num;
    }

    /**
     * 最短路径
     * 单源最短路径问题
     */

    /**
     * Floyd-Warshall算法(1962年)
     * 罗伯特·弗洛伊德,Robert W．Floyd与Warsall合作发布Floyd-Warshall算法。
     * Floyd-Warshall算法（Floyd-Warshall algorithm），简称Floyd算法
     * http://www.cppblog.com/mythit/archive/2009/04/21/80579.html
     * 每两个顶点之间的最短距离
     * @param dists
     */
    public void floyd(Distance[][] dists) {
        for(int i = 0; i < this.vertexes.size(); i ++) {
            dists[i] = new Distance[this.vertexes.size()];
            for(int j = 0; j < this.vertexes.size(); j ++) {
                dists[i][j] = new Distance(-1);
                dists[i][j].preV = -1;
                if(i == j) {
                    dists[i][j].distance = 0;
                } else {
                   dists[i][j].distance = Integer.MAX_VALUE;
                }
            }
        }

        for(int v = 0; v < this.vertexes.size(); v ++) {
            for(Edge e = firstEdge(this.vertexes.get(v)); isEdge(e); e = e.getNextEdge()) {
                int to = e.getTo().getIndex();
                dists[v][to].distance = e.getWeight();
                dists[v][to].preV = v;
            }
        }

        for(int v = 0; v < this.vertexes.size(); v ++) {
            for(int i = 0; i < this.vertexes.size(); i ++) {
                for(int j = 0; j < this.vertexes.size(); j ++) {
                    if((dists[i][v].distance != Integer.MAX_VALUE) && (dists[v][j].distance != Integer.MAX_VALUE) &&
                        (dists[i][v].distance + dists[v][j].distance < dists[i][j].distance)) {
                        dists[i][j].distance = dists[i][v].distance + dists[v][j].distance;
                        dists[i][j].preV = v;
                    }
                }
            }
        }

    }

    /**
     * 戴克斯特拉算法(1959)
     * Dijstra算法
     * 单源最短路径问题
     * @param fromV
     * @param dists 距离
     */
    public void dijkstra(int fromV, Distance[] dists) {
        BinaryHeap<Distance> heap = new BinaryHeap<Distance>(true, null);

        for(int v = 0; v < this.vertexes.size(); v ++) {
            dists[v] = new Distance(v);
        }

        initVisit();
        dists[fromV].distance = 0;
        dists[fromV].preV = -1;
        heap.insert(dists[fromV]);
        int num = 0;

        while(num < this.vertexes.size()) {
            Distance distance = heap.delete();
            if(this.vertexes.get(distance.curV).isVisited() == true) {
                continue;
            }
            this.vertexes.get(distance.curV).setVisit(true);
            num ++;
            for(Edge e = firstEdge(this.vertexes.get(distance.curV)); isEdge(e); e = e.getNextEdge()) {
                if(e.getTo().isVisited() == false && e.getWeight() + distance.distance < dists[e.getTo().getIndex()].distance) {
                    dists[e.getTo().getIndex()].distance = e.getWeight() + distance.distance;
                    dists[e.getTo().getIndex()].preV = distance.curV;
                    heap.insert(dists[e.getTo().getIndex()]);
                }
            }
        }
    }

	/**
	 * @return vertexes
	 */
	public ArrayList<Vertex> getVertexes() {
		return vertexes;
	}

	/**
	 * @param vertexes
	 */
	public void setVertexes(ArrayList<Vertex> vertexes) {
		this.vertexes = vertexes;
	}

	/**
	 * @return adjacencyList
	 */
	public ArrayList<LinkedList<Edge>> getAdjacencyList() {
		return adjacencyList;
	}

	/**
	 * @param adjacencyList セットする adjacencyList
	 */
	public void setAdjacencyList(ArrayList<LinkedList<Edge>> adjacencyList) {
		this.adjacencyList = adjacencyList;
	}

    /**
     * 贝尔曼-福特算法
     */

    /**
     * Kneser图
     */

    /**
     * 图匹配
     */

    /**
     * 匈牙利算法
     */
    /**
     * Hopcroft–Karp
     */
    /**
     * Edmonds's matching
     */

    /**
     * 网络流
     */

    /**
     * Ford-Fulkerson
     */
    /**
     * Edmonds-Karp
     */
    /**
     * Dinic
     */
    /**
     * Push-relabel maximum flow
     */
}
