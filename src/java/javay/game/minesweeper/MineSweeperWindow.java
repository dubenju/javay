package javay.game.minesweeper;

import java.awt.FlowLayout;

import javax.swing.JFrame;

/**
 * 扫雷
 */
public class MineSweeperWindow extends JFrame {
    public MineSweeperWindow() {
        getContentPane().setLayout(new FlowLayout());

        MineSweeperBoard model = new MineSweeperBoard();
        GameBoardView view     = new MineSweeperView(model);
        GameController control = new GameController(view, model);

        view.setController(control);
        model.setView(view);
        getContentPane().add(view);

        setSize(400, 400);
        setVisible(true);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public static void main(String[] args) {
        new MineSweeperWindow();
    }
}
