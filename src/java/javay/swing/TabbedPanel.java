package javay.swing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.GridLayout;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import javay.xml.Dbjcalc;
import javay.xml.Website;

public class TabbedPanel extends JPanel {
  /**
   * serialVersionUID.
   */
  private static final long serialVersionUID = 1L;
  private static final Logger log = LoggerFactory.getLogger(TabbedPanel.class);
  private Dbjcalc conf;

  JCheckBox autoUpdate = new JCheckBox("是否自动更新");
  JComboBox<String> comboBox = new JComboBox<String>();
  JTextField retry = new JTextField(2);

  /**
   * TabbedPanel.
   * @param conf Dbjcalc
   */
  public TabbedPanel(Dbjcalc conf) {
    super(new GridLayout(1, 1));
    log.debug("----- begin -----");
    this.conf = conf;
    JTabbedPane tabbedPane = new JTabbedPane();
//    ImageIcon icon = createImageIcon("images/middle.gif");
    ImageIcon icon = null;

    JComponent panel1 = makeTextPanel1("Panel #1");
    tabbedPane.addTab("计算器", icon, panel1, "关于计算器的设置");
    tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

    JComponent panel2 = makeTextPanel2("Panel #2");
    tabbedPane.addTab("日志", icon, panel2, "关于日志的设置");
    tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

    JComponent panel3 = makeTextPanel3("Panel #3");
    tabbedPane.addTab("更新", icon, panel3, "关于自动更新的设置");
    tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);

    JComponent panel4 = makeTextPanel4("Panel #4 (has a preferred size of 410 x 50).");
    panel4.setPreferredSize(new Dimension(410, 50));
    tabbedPane.addTab("Tab 4", icon, panel4,  "Does nothing at all");
    tabbedPane.setMnemonicAt(3, KeyEvent.VK_4);

    //Add the tabbed pane to this panel.
    add(tabbedPane);

    //The following line enables to use scrolling tabs.
    tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    log.debug("-----   end -----");
  }

  protected JComponent makeTextPanel1(String text) {
    log.debug("----- begin -----");
    JPanel panel = new JPanel(false);
    JLabel filler = new JLabel(text);
    filler.setHorizontalAlignment(JLabel.CENTER);
    panel.setLayout(new GridLayout(1, 1));
    panel.add(filler);
    log.debug("-----   end -----");
    return panel;
  }

  protected JComponent makeTextPanel2(String text) {
    log.debug("----- begin -----");
    JPanel panel = new JPanel(false);
    JLabel filler = new JLabel(text);
    filler.setHorizontalAlignment(JLabel.CENTER);
    panel.setLayout(new GridLayout(1, 1));
    panel.add(filler);
    log.debug("-----   end -----");
    return panel;
  }

  protected JComponent makeTextPanel3(String text) {
    log.debug("----- begin -----");
    JPanel panel = new JPanel(false);
    // panel.setLayout(new GridLayout(1, 1));
    panel.setLayout(null);
    panel.setBounds(0, 0, 300, 300);

    JLabel filler = new JLabel(text);
    filler.setHorizontalAlignment(JLabel.LEFT);
    filler.setBounds(10, 10, 120, 20);


    autoUpdate.setBounds(10, 30, 120, 20);
    autoUpdate.setSelected(this.conf.getAutoUpdate().getisAutoUpdate());

    JLabel label = new JLabel("下载网站：");
    label.setHorizontalAlignment(JLabel.LEFT);
    label.setBounds(10, 50, 120, 20);


    List<Website> list = this.conf.getAutoUpdate().getWebsites();
    int anIndex = -1;
    int idx = -1;
    for (Website site : list) {
      comboBox.addItem(site.getName());
      idx ++;
      if (site.isSelected()) {
        anIndex = idx;
      }
    }
    comboBox.setSelectedIndex(anIndex);
    comboBox.setBounds(10, 70, 120, 20);

    JLabel label2 = new JLabel("最大重试回shu：");
    label2.setHorizontalAlignment(JLabel.LEFT);
    label2.setBounds(10, 90, 120, 20);


    retry.setHorizontalAlignment(JTextField.RIGHT);
    retry.setBounds(10, 110, 120, 20);
    retry.setText(String.valueOf(this.conf.getAutoUpdate().getRetry()));

    panel.add(filler);
    panel.add(autoUpdate);
    panel.add(label);
    panel.add(comboBox);
    panel.add(label2);
    panel.add(retry);
    log.debug("-----   end -----");
    return panel;
  }

  protected JComponent makeTextPanel4(String text) {
    log.debug("----- begin -----");
    JPanel panel = new JPanel(false);
    JLabel filler = new JLabel(text);
    filler.setHorizontalAlignment(JLabel.CENTER);
    panel.setLayout(new GridLayout(1, 1));
    panel.add(filler);
    log.debug("-----   end -----");
    return panel;
  }

  /** Returns an ImageIcon, or null if the path was invalid. */
  protected static ImageIcon createImageIcon(String path) {
    URL imgUrl = TabbedPanel.class.getResource(path);
    if (imgUrl != null) {
      return new ImageIcon(imgUrl);
    } else {
      System.err.println("Couldn't find file: " + path);
      return null;
    }
  }

  /**
   * Create the GUI and show it.  For thread safety,
   * this method should be invoked from
   * the event dispatch thread.
   */
  private static void createAndShowGui() {
    //Create and set up the window.
    JFrame frame = new JFrame("TabbedPaneDemo");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    InputStream inStream = null;
    try {
      inStream = new   FileInputStream("./conf/dbjcalc.xml");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    Dbjcalc conf = javax.xml.bind.JAXB.unmarshal(inStream, Dbjcalc.class);
    //Add content to the window.
    frame.add(new TabbedPanel(conf), BorderLayout.CENTER);

    //Display the window.
    frame.pack();
    frame.setVisible(true);
  }

  /**
   * main.
   * @param args String[]
   */
  public static void main(String[] args) {
    //Schedule a job for the event dispatch thread:
    //creating and showing this application's GUI.
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        //Turn off metal's use of bold fonts
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        createAndShowGui();
      }
    });
  }
}
