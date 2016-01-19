package javay.swing;

import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JSettingDlg extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(JSettingDlg.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JSettingDlg(JFrame frame){
        super(frame, "第一个JDialog窗体", true);//实例化一个JDialog类对象，指定对话框的父窗体、标题、类型
        log.debug("----- begin -----");
        Container container = getContentPane();//创建一个容器
        container.add(new JLabel("这是一个对话框"));
        container.add(new TabbedPanel());
        // setBounds(frame.getX(),frame.getY(), 200, 200);
        JButton btnOK = new JButton("确定");
        JButton btnCancel = new JButton("取消");
//        container.add(btnOK);
//        container.add(btnCancel);
        setBounds(100, 100, 400, 400);
        log.debug("-----   end -----");
    }
}
