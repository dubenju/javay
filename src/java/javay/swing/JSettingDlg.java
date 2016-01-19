package javay.swing;

import java.awt.Container;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class JSettingDlg extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JSettingDlg(JFrame frame){
        super(frame, "第一个JDialog窗体", true);//实例化一个JDialog类对象，指定对话框的父窗体、标题、类型
        Container container=getContentPane();//创建一个容器
        container.add(new JLabel("这是一个对话框"));
        setBounds(frame.getX(),frame.getY(), 200, 200);
    }
}
