package javay.awt;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.io.IOException;

import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.DimensionUIResource;

import javay.sound.UWave;

public class ViewWavPanel  extends Container {

	private int[] data;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ViewWavPanel() {
	    this.setPreferredSize(new DimensionUIResource(1320, 700));
	    this.setLayout(null);
	    this.setBackground(ColorUIResource.BLACK);
	    try {
			data = UWave.getData("./classes/javay/sound/shangxinlei.wav");
		} catch (IOException e) {
			e.printStackTrace();
			data = new int[0];
		}
	    repaint();
	}
	  public void paint(Graphics gh) {
	    gh.setColor(ColorUIResource.BLACK);
	    gh.fillRect(0, 0, this.getWidth(), this.getHeight());
	    drawWav(gh);
	    super.paint(gh);
	  }
	  private void drawWav(Graphics gh) {
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
		    g2d.setColor(ColorUIResource.BLUE);
		    g2d.drawLine(30, 330, 1280, 330); // Mid
		    g2d.setStroke(stroke);

		    drawData(this.data, g2d);

		    g2d.setColor(color);
	  }
	  public void drawData(int[] p, Graphics2D g2d) {
		  final Color color = g2d.getColor();
		  g2d.setColor(ColorUIResource.RED);
		  System.out.println("p:" + p.length);
		  int m = 1000;
		for(int i = 0; i < p.length; i ++) {
		  //g2d.drawLine(30 + i * 2, (int)(30 + 300 * (1.0 - p[i] / 65535.0) ), 30 + i * 2, 330); // Y
			if ((i % m) == 0) {
				double r = p[i] / 65535.0;
				int y = (int) ((328 - 32) * r);
				g2d.drawLine(31 + i / m, 32 + (328 - 32 - y), 31 + i / m, 328); // Y
			}
		}
		g2d.setColor(color);
	  }
}
