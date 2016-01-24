package javay.swing;

import javay.main.Launcher;
import javay.xml.Dbjcalc;
import javay.xml.Website;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class JSettingDlg extends JDialog  implements ActionListener {
  private static final Logger log = LoggerFactory.getLogger(JSettingDlg.class);
  private Dbjcalc conf;
  TabbedPanel tabp;

  /**
   * serialVersionUID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * JSettingDlg.
   * @param frame JFrame.
   */
  public JSettingDlg(JFrame frame) {
    super(frame, "第一个JDialog窗体", true);//实例化一个JDialog类对象，指定对话框的父窗体、标题、类型
    log.debug("----- begin -----");
    this.conf = Launcher.getConf();

    Container container = getContentPane();//创建一个容器
    container.setLayout(null);
    JLabel label = new JLabel("这是一个对话框");
    label.setBounds(10, 10, 160, 20);
    container.add(label);

    tabp = new TabbedPanel(conf);
    tabp.setBounds(10, 30, 400, 200);
    container.add(tabp);
    // setBounds(frame.getX(),frame.getY(), 200, 200);
    JButton btnOk = new JButton("确定");
    btnOk.setBounds(100, 240, 40, 20);
    btnOk.addActionListener(this);
    JButton btnCancel = new JButton("取消");
    btnCancel.setBounds(160, 240, 40, 20);
    container.add(btnOk);
    container.add(btnCancel);
    setBounds(100, 100, 400, 400);
    log.debug("-----   end -----");
  }

  @Override
  public void actionPerformed(ActionEvent ev) {
    dispose();
    String scmd = ev.getActionCommand();
    if (scmd.equals("确定")) {
      this.conf.getAutoUpdate().setisAutoUpdate(this.tabp.autoUpdate.isSelected());
      List<Website> list = this.conf.getAutoUpdate().getWebsites();
      int index = this.tabp.comboBox.getSelectedIndex();
      for (int i = 0;i < list.size(); i ++) {
        Website site = list.get(i);
        site.setSelected(false);
        if (i == index) {
          site.setSelected(true);
        }
      }
      this.conf.getAutoUpdate().setRetry(Integer.parseInt(this.tabp.retry.getText()));
      Launcher.saveConf(this.conf);
    }
  }
}
