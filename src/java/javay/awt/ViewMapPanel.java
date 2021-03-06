package javay.awt;

import javay.distance.city.ModelCity;
import javay.util.UGraph;
import javay.util.graph.DefaultGraph;
import javay.util.graph.Edge;
import javay.util.graph.Graph;
import javay.util.graph.Vertex;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Stroke;
import java.util.LinkedList;

import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.DimensionUIResource;

public class ViewMapPanel extends Container {
  /**
   * serialVersionUID.
   */
  private static final long serialVersionUID = 1L;
  private DefaultGraph graph;

  /**
   * ViewMapPanel.
   * @param graph Graph
   */
  public ViewMapPanel(Graph graph) {
    this.graph = (DefaultGraph) graph;
    UGraph.setVertexXY(this.graph, 30, 30, 1240, 640);
    this.setPreferredSize(new DimensionUIResource(1320, 700));
    this.setLayout(null);
    this.setBackground(ColorUIResource.BLACK);
    repaint();
  }

  /**
   * @see java.awt.Container#paint(java.awt.Graphics)
   */
  @Override
  public void paint(Graphics gh) {
    gh.setColor(ColorUIResource.BLACK);
    gh.fillRect(0, 0, this.getWidth(), this.getHeight());
    drawMap(gh);
    super.paint(gh);
  }

  private void drawMap(Graphics gh) {
    Graphics2D g2d = (Graphics2D) gh.create();

    final Color color = g2d.getColor();
    g2d.setColor(ColorUIResource.GRAY);
    g2d.drawRect(30, 30, 1240, 640);
    for (int i = 30; i <= 670; i += 20) {
      g2d.drawLine(30, i, 1270, i); // X
      g2d.drawLine(i, 30, i, 670); // Y
    }
    for (int i = 690; i <= 1270; i += 20) {
      g2d.drawLine(i, 30, i, 670); // Y
    }

    Stroke stroke = g2d.getStroke();
    g2d.setStroke(new BasicStroke(3.0f));
    g2d.setColor(ColorUIResource.YELLOW);
    g2d.drawLine(30, 30, 1280, 30); // X
    g2d.drawLine(30, 30, 30, 680); // Y
    g2d.setStroke(stroke);

    // 顶点
    for (Vertex vertex : this.graph.getVertexes()) {
      g2d.fillOval(vertex.getX() - 3, vertex.getY() - 3, 7, 7);
      g2d.drawString(((ModelCity) vertex.getValue()).getName(), vertex.getX(), vertex.getY());
    }

    // 边
    for (LinkedList<Edge> edgs : this.graph.getAdjacencyList()) {
      for (Edge edge : edgs) {
        g2d.setColor(ColorUIResource.RED);
        g2d.drawLine(edge.getFrom().getX(), edge.getFrom().getY(), 
            edge.getTo().getX(), edge.getTo().getY());
      }
    }

    g2d.setColor(color);
  }

  /**
   * drawTriangle.
   * @param g2d Graphics2D
   * @param x1 int
   * @param y1 int
   * @param x2 int
   * @param y2 int
   * @param x3 int
   * @param y3 int
   */
  public void drawTriangle(Graphics2D g2d, int x1, int   y1,int   x2, int   y2,
      int   x3,int   y3) {
    Polygon filledPolygon = new Polygon();
    filledPolygon.addPoint(x1,y1);
    filledPolygon.addPoint(x2,y2);
    filledPolygon.addPoint(x3,y3);
    g2d.drawPolygon(filledPolygon);
    g2d.fillPolygon(filledPolygon);
  }
}
