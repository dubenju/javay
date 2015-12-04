/**
 *
 */
package javay.game.othello;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * @author DBJ
 *
 */
public class MyDialog extends JDialog implements ActionListener {
    JLabel title;
    JTextField text;
    JButton done;

	/**
	 * @param owner
	 * @param title
	 */
	public MyDialog(Frame owner, String titl) {
		// super(owner, title);
        super(owner, titl, true);

        title = new JLabel("输入"+ titl +"名称");
        text = new JTextField(10);
        text.setEditable(true);

        done = new JButton("确定");
        done.addActionListener(this);

        Container con = this.getContentPane();
        con.setLayout(new FlowLayout());
        con.setSize(200,100);
        con.setVisible(true);
        con.add(title);
        con.add(text);
        con.add(done);

        this.pack();
        setModal(true);
        //this.setUndecorated(true);
        //this.setBackground(new Color(0,0,0,0));

	}

	/* (非 Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Window win = this.getOwner();
		Othello owner = (Othello) win;
        // MyWindow.returnName(text.getText());
		owner.test();
        setVisible(false);
        dispose();
	}
}
