package javay.util;

import java.util.LinkedList;

import javay.distance.city.ModelCity;
import javay.util.graph.Edge;
import javay.util.graph.Graph;
import javay.util.graph.Vertex;

public class UGraph {

  public static void setVertexXY(Graph graph, int startx, int starty, int width, int height) {
    Vertex left = graph.getVertexes().get(0);
    Vertex top = left;
    Vertex right = left;
    Vertex bottom = left;
    ModelCity leftcity = (ModelCity) left.getValue();
    ModelCity topcity = (ModelCity) top.getValue();
    ModelCity rightcity = (ModelCity) right.getValue();
    ModelCity bottomcity = (ModelCity) bottom.getValue();

    for (Vertex vertex : graph.getVertexes()) {
      ModelCity modelCity = (ModelCity) vertex.getValue();
      if (modelCity.getLongitude() < leftcity.getLongitude()) {
        left = vertex;
        leftcity = (ModelCity) left.getValue();
      }
      if (modelCity.getLongitude() > rightcity.getLongitude()) {
        right = vertex;
        rightcity = (ModelCity) right.getValue();
      }
      if (modelCity.getAtitude() > topcity.getAtitude()) {
        top = vertex;
        topcity = (ModelCity) top.getValue();
      }
      if (modelCity.getAtitude() < bottomcity.getAtitude()) {
        bottom = vertex;
        bottomcity = (ModelCity) bottom.getValue();
      }
    }
    System.out.println("left:" + left);
    System.out.println("top:" + top);
    System.out.println("right:" + right);
    System.out.println("bottom:" + bottom);

    double dw = UDouble.subtract(rightcity.getLongitude(), leftcity.getLongitude());
    double dh = UDouble.subtract(topcity.getAtitude(), bottomcity.getAtitude());
    System.out.println("dw:" + dw + ",dh:" + dh);
    double dwstep = UDouble.divide(width * 1.0, dw);
    double dhstep = UDouble.divide(height * 1.0, dh);
    System.out.println("dwstep:" + dwstep + ",dhstep:" + dhstep);
    for (Vertex vertex : graph.getVertexes()) {
      ModelCity modelCity = (ModelCity) vertex.getValue();
      double ddisl = UDouble.subtract(modelCity.getLongitude(), leftcity.getLongitude());
      double ddisx = UDouble.multiply(ddisl, dwstep);
      int x = new Double(ddisx).intValue() + startx;

      double ddisa = UDouble.subtract(topcity.getAtitude(), modelCity.getAtitude());
      double ddisy = UDouble.multiply(ddisa, dhstep);
      int y = new Double(ddisy).intValue() + starty;
      vertex.setX(x);
      vertex.setY(y);
      System.out.println(vertex);
    }
  }
  public static void setEdgeXY(Graph graph) {
    for (LinkedList<Edge> edges : graph.getAdjacencyList()) {
      for (Edge edge : edges) {
        Vertex from = edge.getFrom();
        Vertex to = edge.getTo();
        if (from.getY() < to.getY()) {
          if (from.getX() < to.getX()) {
            int x1 = to.getX() - 7;
            int y1 = to.getY();
            int x2 = to.getX() - 7;
            int y2 = to.getY() - 7;
            edge.setX1(x1);
            edge.setY1(y1);
            edge.setX2(x2);
            edge.setY2(y2);
          }
          if (from.getX() == to.getX()) {
            // 垂直
            int x1 = to.getX();
            int y1 = to.getY() - 7;
            int x2 = to.getX() - 7;
            int y2 = to.getY() - 7;
            edge.setX1(x1);
            edge.setY1(y1);
            edge.setX2(x2);
            edge.setY2(y2);
          }
          if (from.getX() > to.getX()) {
            int x1 = to.getX() + 7;
            int y1 = to.getY();
            int x2 = to.getX() + 7;
            int y2 = to.getY() + 7;
            edge.setX1(x1);
            edge.setY1(y1);
            edge.setX2(x2);
            edge.setY2(y2);
          }
        }
        if (from.getY() == to.getY()) {
          // 水平
          if (from.getX() < to.getX()) {
            int x1 = to.getX() - 7;
            int y1 = to.getY();
            int x2 = to.getX() - 7;
            int y2 = to.getY() - 7;
            edge.setX1(x1);
            edge.setY1(y1);
            edge.setX2(x2);
            edge.setY2(y2);
          }
//          if (from.getX() == to.getX()) {
//          }
          if (from.getX() > to.getX()) {
            int x1 = to.getX() + 7;
            int y1 = to.getY();
            int x2 = to.getX() + 7;
            int y2 = to.getY() - 7;
            edge.setX1(x1);
            edge.setY1(y1);
            edge.setX2(x2);
            edge.setY2(y2);
          }
        }
        if (from.getY() > to.getY()) {
          if (from.getX() < to.getX()) {
            int x1 = to.getX();
            int y1 = to.getY() - 7;
            int x2 = to.getX() + 7;
            int y2 = to.getY() - 7;
            edge.setX1(x1);
            edge.setY1(y1);
            edge.setX2(x2);
            edge.setY2(y2);
          }
          if (from.getX() == to.getX()) {
            // 垂直
            int x1 = to.getX();
            int y1 = to.getY() - 7;
            int x2 = to.getX() + 7;
            int y2 = to.getY() - 7;
            edge.setX1(x1);
            edge.setY1(y1);
            edge.setX2(x2);
            edge.setY2(y2);
          }
          if (from.getX() > to.getX()) {
            int x1 = to.getX();
            int y1 = to.getY() - 7;
            int x2 = to.getX() + 7;
            int y2 = to.getY() - 7;
            edge.setX1(x1);
            edge.setY1(y1);
            edge.setX2(x2);
            edge.setY2(y2);
          }
        }
      }
    }
  }
}
