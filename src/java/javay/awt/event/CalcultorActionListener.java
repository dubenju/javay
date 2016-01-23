package javay.awt.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javay.fsm.state.State;
import javay.math.BigNum;
import javay.math.MathBn;
import javay.swing.CalcultorConts;
import javay.swing.CalcultorPanel;

/**
 * 第一种：自身类实现ActionListener接口，作为事件监听器.
 * 第一种的监听处理部分，如果有多个，那就需要一个个去判断，从理论上说是影响程序速度的。
 * 第二种：通过匿名类处理。
 * 第二种和第三种比较常用，如果程序的监听事件比较少，可以用第二种，匿名类很合适。
 * 第三种：通过内部类处理。
 * 第三种符合面向对象编程（可以设置内部类只提供自身类使用，而且方便使用自身类的资源），尤其是适合多个监听事件的处理，当然也适合第二种方法情况。
 * 第四种：通过外部类处理。
 * 第四种是外部类，如果多个监听事件相同，就可以选用此种方法。
 * ActionListener接口，作为事件监听器。
 * @author dubenju
 */
public class CalcultorActionListener implements ActionListener {
  private static final Logger log = LoggerFactory.getLogger(CalcultorActionListener.class);
  private CalcultorPanel panel;
  private Map<String, Integer> ns = new HashMap<String, Integer>();
  CalcultorFsm fsm = new CalcultorFsm();

  /**
   * CalcultorActionListener.
   */
  public CalcultorActionListener(CalcultorPanel panel) {
    this.panel = panel;
    ns.put(CalcultorConts.BINARY, 2);
    ns.put(CalcultorConts.OCTAL, 8);
    ns.put(CalcultorConts.DECIMAL, 10);
    ns.put(CalcultorConts.HEXADECIMAL, 16);
  }

  private boolean isControl(String scmd) {
    return scmd.equals(CalcultorConts.MR) || scmd.equals(CalcultorConts.MC)
      || scmd.equals(CalcultorConts.MS) || scmd.equals(CalcultorConts.MP)
      || scmd.equals(CalcultorConts.MM) || scmd.equals(CalcultorConts.BINARY)
      || scmd.equals(CalcultorConts.OCTAL) || scmd.equals(CalcultorConts.DECIMAL)
      || scmd.equals(CalcultorConts.HEXADECIMAL) || scmd.equals(CalcultorConts.SCI);
  }

  @Override
  public void actionPerformed(ActionEvent ev) {
    String scmd = ev.getActionCommand();
    log.debug("scmd=" + scmd);

    if (scmd.equals(CalcultorConts.APP)) {
      if (this.panel.inv.isSelected()) {
        int idx = this.panel.modelStatistics.getSize() - 1;
        if (this.panel.listStatistics.getSelectedIndex() != -1) {
          idx = this.panel.listStatistics.getSelectedIndex();
        }
        if (idx != -1) {
          this.panel.modelStatistics.remove(idx);
        }
      } else {
        this.panel.modelStatistics.addElement(this.panel.textField.getText());
        this.panel.listStatistics.ensureIndexIsVisible(this.panel.modelStatistics.getSize() - 1);
        this.panel.textField.setText("");
      }
      if (this.panel.modelStatistics.getSize() > 0) {
        this.panel.btnAve.setEnabled(true);
        this.panel.btnSum.setEnabled(true);
        this.panel.btnS.setEnabled(true);
        this.panel.btnDat.setEnabled(true);
      } else {
        this.panel.btnAve.setEnabled(false);
        this.panel.btnSum.setEnabled(false);
        this.panel.btnS.setEnabled(false);
        this.panel.btnDat.setEnabled(false);
      }
      return ;
    }
    if (scmd.equals(CalcultorConts.AVE)
        || scmd.equals(CalcultorConts.SUM)
        || scmd.equals(CalcultorConts.SSD)) {
      int cnt = this.panel.modelStatistics.size();
      if (cnt > 0) {
        List<BigNum> ins = new ArrayList<BigNum>();
        for (int i = 0; i < cnt; i ++) {
          String str = this.panel.modelStatistics.get(i);
          ins.add(new BigNum(str));
        }
        BigNum val = new BigNum("0.0");
        if (this.panel.inv.isSelected()) {
          if (scmd.equals(CalcultorConts.AVE)) {
            val = MathBn.rms(ins);
          }
          if (scmd.equals(CalcultorConts.SUM)) {
            val = MathBn.sos(ins);
          }
          if (scmd.equals(CalcultorConts.SSD)) {
            val = MathBn.psd(ins);
          }
        } else {
          if (scmd.equals(CalcultorConts.AVE)) {
            val = MathBn.ave(ins);
          }
          if (scmd.equals(CalcultorConts.SUM)) {
            val = MathBn.sum(ins);
          }
          if (scmd.equals(CalcultorConts.SSD)) {
            val = MathBn.ssd(ins);
          }
        }
        this.panel.textField.setText(val.toString());
      }
      return ;
    }
    if (this.isControl(scmd)) {
      control(scmd);
      return ;
    }

    final Map<String, String> context = new HashMap<String, String>();
    String value = "d"; // 度
    if (this.panel.rad.isSelected()) {
      //弧度
      value = "r";
    }
    if (this.panel.grad.isSelected()) {
      //百分度
      value = "g";
    }
    String inverse = "";
    if (this.panel.inv.isSelected()) {
      inverse = "inverse";
    }
    String hyperbolic = "";
    if (this.panel.hyp.isSelected()) {
      hyperbolic = "hyperbolic";
    }
    context.put(CalcultorConts.TRIGONOMETRIC_FUNCTION, value);
    context.put(CalcultorConts.INVERSE, inverse);
    context.put(CalcultorConts.HYPERBOLIC, hyperbolic);
    context.put(CalcultorConts.INPUT, scmd);
    // 在状态机内对操作符进行变换
    ExprInfo expr = this.fsm.receive(scmd, context);

    /* *** 表达式求值 *** */
    String expression = expr.getExpr();
    this.panel.expr.setText(expression);
    String display = expr.getInbuf().toString();
    this.panel.textField.setText(display);
    /* *** 表达式求值 *** */

    /* 控制 */
    if (scmd.equals(CalcultorConts.DMS)) {
      if (this.panel.inv.isSelected()) {
        this.panel.inv.setSelected(false);
      }
    }
    if (scmd.equals(CalcultorConts.EQUAL)) {
      this.panel.modelHistory.addElement(this.panel.expr.getText());
      this.panel.listHistory.ensureIndexIsVisible(this.panel.modelHistory.getSize() - 1);
    }
    if (scmd.equals(CalcultorConts.CLEAR)) {
      this.panel.modelHistory.removeAllElements();
    }

  }

  private void control(String scmd) {
    System.out.println("cmd=" + scmd);
    if (CalcultorConts.MC.equals(scmd)) {
      this.panel.textField.setMemory("");
      this.panel.textField.setText(this.panel.textField.getText());
    }
    if (CalcultorConts.MR.equals(scmd)) {
      String mem = this.panel.textField.getMemory();
      if (mem.length() <= 0) {
        mem = "0";
      }
      this.panel.textField.setText(mem);
      State<ExprInfo> cur = this.fsm.getCurrentState();
      ExprInfo val = cur.getValue();
      StringBuffer buf = new StringBuffer();
      buf.append(mem);
      val.setInbuf(buf);
    }
    if (CalcultorConts.MS.equals(scmd)) {
      this.panel.textField.setMemory(this.panel.textField.getText());
      this.panel.textField.setText(this.panel.textField.getText());
    }
    if (CalcultorConts.MP.equals(scmd)) {
      String mem = this.panel.textField.getMemory();
      if (mem.length() <= 0) {
        mem = "0";
      }
      BigNum mm = new BigNum(mem);
      BigNum bn = new BigNum(this.panel.textField.getText());
      mem = mm.add(bn).toString();
      this.panel.textField.setMemory(mem);
      this.panel.textField.setText(this.panel.textField.getText());
    }
    if (CalcultorConts.MM.equals(scmd)) {
      String mem = this.panel.textField.getMemory();
      if (mem.length() <= 0) {
        mem = "0";
      }
      BigNum mm = new BigNum(mem);
      BigNum bn = new BigNum(this.panel.textField.getText());
      mem = mm.subtract(bn).toString();
      this.panel.textField.setMemory(mem);
      this.panel.textField.setText(this.panel.textField.getText());
    }
    if (CalcultorConts.BINARY.equals(scmd)) {
      for (int i = 2; i < 10; i ++) {
        this.panel.numButtons[i].setEnabled(false);
      }
      this.panel.btnA.setEnabled(false);
      this.panel.btnB.setEnabled(false);
      this.panel.btnC.setEnabled(false);
      this.panel.btnD.setEnabled(false);
      this.panel.btnE.setEnabled(false);
      this.panel.btnF.setEnabled(false);

      this.panel.btnFE.setEnabled(false);
      this.panel.btnDMS.setEnabled(false);
      this.panel.btnSin.setEnabled(false);
      this.panel.btnCos.setEnabled(false);
      this.panel.btnTan.setEnabled(false);
      this.panel.btnExp.setEnabled(false);
      String val = this.panel.textField.getText();
      BigNum valn = new BigNum(val, ns.get(this.panel.textField.getNumberSystem()));
      this.panel.textField.setText(valn.toBinaryString());
      this.panel.textField.setNumberSystem(CalcultorConts.BINARY);
    }
    if (CalcultorConts.OCTAL.equals(scmd)) {
      for (int i = 2; i < 8; i ++) {
        this.panel.numButtons[i].setEnabled(true);
      }
      for (int i = 8; i < 10; i ++) {
        this.panel.numButtons[i].setEnabled(false);
      }
      this.panel.btnA.setEnabled(false);
      this.panel.btnB.setEnabled(false);
      this.panel.btnC.setEnabled(false);
      this.panel.btnD.setEnabled(false);
      this.panel.btnE.setEnabled(false);
      this.panel.btnF.setEnabled(false);

      this.panel.btnFE.setEnabled(false);
      this.panel.btnDMS.setEnabled(false);
      this.panel.btnSin.setEnabled(false);
      this.panel.btnCos.setEnabled(false);
      this.panel.btnTan.setEnabled(false);
      this.panel.btnExp.setEnabled(false);
      String val = this.panel.textField.getText();
      BigNum valn = new BigNum(val, ns.get(this.panel.textField.getNumberSystem()));
      this.panel.textField.setText(valn.toOctalString());
      this.panel.textField.setNumberSystem(CalcultorConts.OCTAL);
    }
    if (CalcultorConts.DECIMAL.equals(scmd)) {
      for (int i = 2; i < 10; i ++) {
        this.panel.numButtons[i].setEnabled(true);
      }
      this.panel.btnA.setEnabled(false);
      this.panel.btnB.setEnabled(false);
      this.panel.btnC.setEnabled(false);
      this.panel.btnD.setEnabled(false);
      this.panel.btnE.setEnabled(false);
      this.panel.btnF.setEnabled(false);

      this.panel.btnFE.setEnabled(true);
      this.panel.btnDMS.setEnabled(true);
      this.panel.btnSin.setEnabled(true);
      this.panel.btnCos.setEnabled(true);
      this.panel.btnTan.setEnabled(true);
      this.panel.btnExp.setEnabled(true);
      String val = this.panel.textField.getText();
      BigNum valn = new BigNum(val, ns.get(this.panel.textField.getNumberSystem()));
      this.panel.textField.setText(valn.toString());
      this.panel.textField.setNumberSystem(CalcultorConts.DECIMAL);
    }
    if (CalcultorConts.HEXADECIMAL.equals(scmd)) {
      for (int i = 2; i < 10; i ++) {
        this.panel.numButtons[i].setEnabled(true);
      }
      this.panel.btnA.setEnabled(true);
      this.panel.btnB.setEnabled(true);
      this.panel.btnC.setEnabled(true);
      this.panel.btnD.setEnabled(true);
      this.panel.btnE.setEnabled(true);
      this.panel.btnF.setEnabled(true);

      this.panel.btnFE.setEnabled(false);
      this.panel.btnDMS.setEnabled(false);
      this.panel.btnSin.setEnabled(false);
      this.panel.btnCos.setEnabled(false);
      this.panel.btnTan.setEnabled(false);
      this.panel.btnExp.setEnabled(false);
      String val = this.panel.textField.getText();
      BigNum valn = new BigNum(val, ns.get(this.panel.textField.getNumberSystem()));
      this.panel.textField.setText(valn.toHexString());
      this.panel.textField.setNumberSystem(CalcultorConts.HEXADECIMAL);
    }
    if (CalcultorConts.SCI.equals(scmd)) {
      int display = this.panel.textField.getDisplay();
      if (display == 0) {
        display = 1;
      } else {
        display = 0;
      }
      this.panel.textField.setDisplay(display);
      this.panel.textField.setText(this.panel.textField.getText()); // refresh
    }
  }
}
