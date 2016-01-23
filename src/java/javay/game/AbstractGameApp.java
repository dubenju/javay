package javay.game;

import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import javay.game.othello.CloseWindow;
import javay.game.othello.ErrorDialog;
import javay.game.othello.MyDialog;
import javay.game.othello.OX;

/**
 * @author DBJ
 *
 */
public abstract class AbstractGameApp extends JFrame implements ActionListener {
    private MenuItem black;
    private MenuItem white;
    private OX oxBoard;	/**
	 *
	 */
	public AbstractGameApp(String title) {
		super(title);
        CloseWindow close = new CloseWindow(this, true);

        MenuBar mb  = new MenuBar();
        setMenuBar(mb); // Frame

        Menu m = new Menu("游戏");
        mb.add(m).add(new MenuItem("新游戏")).addActionListener(this);
        m.add(black = new MenuItem("电脑执黑")).addActionListener(this);
        m.add(white = new MenuItem("电脑执白")).addActionListener(this);
        m.add(new MenuItem("结束")).addActionListener(close);
        mb.add(new Menu("帮助")).add(new MenuItem("关于")).addActionListener(this);

        addWindowListener(close); // Window
        pack(); // Window
        setResizable(false); // Frame
        setVisible(true);  // Window
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	}

	@Override
    /**
     * implements the ActionListener interface
     */
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch(command) {
        case "关于":
            new ErrorDialog(this,"黑白棋\n作者:dubenju@126.com");
            break ;
        case "新游戏":
        	MyDialog dlg = new MyDialog(this, "水果");
        	dlg.setVisible(true);
            oxBoard.newGame();
            break;
        case "电脑执黑":
            oxBoard.setBlackPlayer(1);
            black.setLabel("ˇ电脑执黑");
            break;
        case "电脑执白":
            oxBoard.setWhitePlayer(1);
            white.setLabel("ˇ电脑执白");
            break;
        case "ˇ电脑执黑":
            oxBoard.setBlackPlayer(0);
            black.setLabel("电脑执黑");
            break;
        case "ˇ电脑执白":
            oxBoard.setWhitePlayer(0);
            white.setLabel("电脑执白");
            break;
        default:
            break;
        }
    }

}
