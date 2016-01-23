package javay.distance.city;

import java.awt.Color;

import javay.awt.ViewMapPanel;
import javay.util.graph.Edge;
import javay.util.graph.Graph;
import javay.util.graph.GraphVisitor;
import javay.util.graph.Vertex;

public class TGraph implements GraphVisitor {
  private ViewMapPanel panel;
//  private ViewPath vp;
  private Color color;
//  private Runnable run = new Runnable() {
//    public void run() {
//      try {
//        Thread.sleep(100);
//      } catch (InterruptedException e) {
//        e.printStackTrace();
//      }
//      if (vp != null) {
//        vp.repaint();
//      }
//      panel.repaint();
//System.out.println("★★★★");
//    }
//  };
  public TGraph(ViewMapPanel panel, Color color) {
    this.panel = panel;
    this.color = color;
  }

  @Override
  public void visit(Graph g, Edge edge, Vertex vertex) {
    System.out.print("TGraph:vertex=" + vertex + ",from:" + edge.getFrom() + ",to:" + edge.getTo());
    ViewPath vp = new ViewPath(edge, this.color);
    int x = edge.getFrom().getX() <= edge.getTo().getX() ? edge.getFrom().getX() : edge.getTo().getX();
    int y = edge.getFrom().getY() <= edge.getTo().getY() ? edge.getFrom().getY() : edge.getTo().getY();
    int width = edge.getFrom().getX() <= edge.getTo().getX() ? (edge.getTo().getX() - edge.getFrom().getX()) : (edge.getFrom().getX() - edge.getTo().getX());
    int height = edge.getFrom().getY() <= edge.getTo().getY() ? (edge.getTo().getY() - edge.getFrom().getY()) : (edge.getFrom().getY() - edge.getTo().getY());
    if (width <= 0) {
      width = 3;
    }
    if (height <= 0) {
      height = 3;
    }
    vp.setBounds(x, y, width, height);
    panel.add(vp);
    System.out.println("[" + x + "," + y + "," +  width + "," +  height + "]size:" + panel.getComponentCount());
//    vp.setVisible(true);
//    vp.repaint();
//    vp.revalidate();
//    if (SwingUtilities.isEventDispatchThread()) {
//      System.out.println("★★★★run.run();");
//      run.run();
//    } else {
//      System.out.println("★★★★SwingUtilities.invokeLater(run);");
//      SwingUtilities.invokeLater(run);//将对象排到事件派发线程的队列中
//    }
  }
}
