package javay.test.java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class JListSample extends JFrame implements ActionListener{

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
  protected JList<String> list;
  protected JTextField text;
  protected DefaultListModel<String> model;

  public static void main(String[] args){
    JListSample test = new JListSample("JListSample");

    /* 終了処理を変更 */
    test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    test.setBounds( 10, 10, 250, 180);
    test.setVisible(true);
  }

  JListSample(String title){
    setTitle(title);

    model = new DefaultListModel<String>();

    /* 初期データをモデルに追加する */
    StringBuffer sb;
    for (int i = 0 ; i < 15 ; i++){
      sb = new StringBuffer();
      sb.append("項目");
      sb.append(i);
      model.addElement(new String(sb));
    }

    list = new JList<String>(model);

    JScrollPane sp = new JScrollPane();
    sp.getViewport().setView(list);
    sp.setPreferredSize(new Dimension(200, 80));

    JPanel p = new JPanel();
    p.add(sp);

    getContentPane().add(p, BorderLayout.CENTER);

    JButton addButton = new JButton("AddElement");
    addButton.addActionListener(this);
    addButton.setActionCommand("addButton");

    JButton insertButton = new JButton("Add");
    insertButton.addActionListener(this);
    insertButton.setActionCommand("insertButton");

    JPanel p2 = new JPanel();
    p2.add(addButton);
    p2.add(insertButton);

    getContentPane().add(p2, BorderLayout.SOUTH);

    text = new JTextField(10);
    JPanel p3 = new JPanel();
    p3.add(text);

    getContentPane().add(p3, BorderLayout.NORTH);
  }

  public void actionPerformed(ActionEvent e){
    String actionCommand = e.getActionCommand();

    if (actionCommand.equals("addButton")){
      model.addElement(text.getText());
      list.ensureIndexIsVisible(model.getSize() - 1);
    }else if (actionCommand.equals("insertButton")){
      /* 現在リストで選択されている項目のIndexを調べる */
      int index = list.getSelectedIndex();
      if (index == -1){
        model.add(0, text.getText());
      }else{
        model.add(index + 1, text.getText());
      }

      list.ensureIndexIsVisible(index + 1);
    }else{
      return;
    }
  }
}