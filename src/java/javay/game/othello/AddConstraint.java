package javay.game.othello;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class AddConstraint {
  public static void addConstraint(Container container, Component component,
      int grid_x, int grid_y, int grid_width, int grid_height,
      int fill, int anchor, double weight_x, double weight_y,
      int top, int left, int bottom, int right) {

    GridBagConstraints c = new GridBagConstraints();
    c.gridx = grid_x;
    c.gridy = grid_y;
    c.gridwidth = grid_width;
    c.gridheight = grid_height;
    c.fill = fill;
    c.anchor = anchor;
    c.weightx = weight_x;
    c.weighty = weight_y;
    c.insets = new Insets(top,left, bottom,right);
    ((GridBagLayout) container.getLayout()).setConstraints(component,c);
    container.add(component);
  }
}
