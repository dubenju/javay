package javay.test;


import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class Calculator {
    public static void main(String[] args) {
        JFrame frame = new JFrame("计算器");
//        frame.setBounds(500,250,100,100);
        Dimension min = new Dimension(500,500);
        frame.setMinimumSize(min);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension max = toolkit.getScreenSize();
        frame.setMaximumSize(max);

        frame.setLayout(new BorderLayout());
        frame.add(new TextField(40), BorderLayout.NORTH);
        frame.add(gridPanel());


        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    protected static Panel gridPanel(){
        Panel panel = new Panel(new GridLayout(3, 5,5,5));
        String[] key = {"0","1","2","3","4","5","6","7","8","9","+","-","*","/","="};
        for(int i=0; i<key.length;i++){
            panel.add(new Button(key[i]));
        }
        return panel;
    }
}