package javay.game.fire_emble;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;

import javay.game.fire_emble.listener.InstructionListener;
import javay.game.fire_emble.ui.FireEmblePanel;


public class Main {

    public static void main(String[] args){
        JFrame frame = new JFrame();
        JPanel panel = new FireEmblePanel();
        frame.setUndecorated(true);
        frame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        frame.setLocation(50, 100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setTitle("A星测试");
        frame.getContentPane().add(panel);
        frame.setLocationByPlatform(true);
        frame.pack();
        frame.setVisible(true);

        InstructionListener il = new InstructionListener(panel);
        frame.addKeyListener(il);
    }
}
