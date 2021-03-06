package com.sin.java.waveaccess;
import javax.swing.JFrame;


public class WaveFileReadDemo {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        String filename = "./in/wav_20_8_1_pcm.wav";
        WaveFileReader reader = new WaveFileReader(filename);
        if(reader.isSuccess()){
            int[] data = reader.getData()[0]; //获取第一声道
            DrawPanel drawPanel = new DrawPanel(data); // 创建一个绘制波形的面板

            JFrame frame = new JFrame();
            frame.add(drawPanel);
            frame.setTitle(filename);
            frame.setSize(800, 400);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        }
        else{
            System.err.println(filename + "不是一个正常的wav文件");
        }
    }
}