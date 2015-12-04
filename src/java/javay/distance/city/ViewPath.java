package javay.distance.city;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

import javax.swing.JComponent;

import javay.util.graph.Edge;

public class ViewPath extends JComponent {

	private Edge edge;
	private Color color;

	public ViewPath(Edge edge, Color color) {
		this.edge = edge;
		this.color = color;
	}

	/**
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		Stroke stroke = g2d.getStroke();
		g2d.setStroke(new BasicStroke(3.0f));
		g2d.setColor(this.color);
		System.out.print("[" + this.getX() + "," + this.getY() + "," + this.getWidth() + "," + this.getHeight() + "]");
		System.out.print("[" + edge.getFrom().getX() + "," + edge.getFrom().getY() + "," + edge.getTo().getX() + "," + edge.getTo().getY() + "]");
		int x1 = edge.getFrom().getX() <= edge.getTo().getX() ? (edge.getFrom().getX() - edge.getFrom().getX()) : (edge.getFrom().getX() - edge.getTo().getX());
		int y1 = edge.getFrom().getY() <= edge.getTo().getY() ? (edge.getFrom().getY() - edge.getFrom().getY()) : (edge.getFrom().getY() - edge.getTo().getY());
		int x2 = edge.getFrom().getX() <= edge.getTo().getX() ? (edge.getTo().getX() - edge.getFrom().getX()) : (edge.getTo().getX() - edge.getTo().getX());
		int y2 = edge.getFrom().getY() <= edge.getTo().getY() ? (edge.getTo().getY() - edge.getFrom().getY()) : (edge.getTo().getY() - edge.getTo().getY());
		System.out.println("[" + x1 + "," + y1 + "," + x2 + "," + y2 + "]");
		g2d.drawLine(x1, y1, x2, y2);
		g2d.setStroke(stroke);
	}
}
