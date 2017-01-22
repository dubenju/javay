package javay.test;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
/**
 *
 * @author Fish
 *
 */
public class ColorFrame extends JFrame{
    //变色内部类:当移动滑块时，将面板的背景色设为三个滑块组合成的RGB值，并将三个文本框的值对应修改
    private class Change implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e) {
            jp1.setBackground(new Color(sl1.getValue(),sl2.getValue(),sl3.getValue()));
            jt1.setText(sl1.getValue()+"");
            jt2.setText(sl2.getValue()+"");
            jt3.setText(sl3.getValue()+"");
        }

    }

    private JPanel jp1,jp2,jp3,jp4,jp5;//一个主面板显示颜色，三个面板用于放对应标语、滑块和文本框，最后一个面板用于组合
    private JLabel jl1,jl2,jl3;//三个标语：红、绿、蓝
    private JSlider sl1,sl2,sl3;//三个滑块
    private JTextField jt1,jt2,jt3;//三个文本框用于显示RGB
    public ColorFrame() {
        this.setTitle("调色板");
        jp1=new JPanel();
        jp2=new JPanel();
        jp3=new JPanel();
        jp4=new JPanel();
        jp5=new JPanel();
        jl1=new JLabel("红");
        jl2=new JLabel("绿");
        jl3=new JLabel("蓝");
        sl1=new JSlider();
        sl1.setMaximum(255);
        sl1.setMinimum(0);//设置滑块最大最小值
        sl2=new JSlider();
        sl2.setMaximum(255);
        sl2.setMinimum(0);
        sl3=new JSlider();
        sl3.setMaximum(255);
        sl3.setMinimum(0);
        jt1=new JTextField(2);
        jt2=new JTextField(2);
        jt3=new JTextField(2);
        jt1.setEditable(false);//将文本框设置为不可编辑
        jt2.setEditable(false);
        jt3.setEditable(false);
        jt1.setText(0+"");
        jt2.setText(0+"");
        jt3.setText(0+"");

        sl1.addChangeListener(new Change());
        sl2.addChangeListener(new Change());
        sl3.addChangeListener(new Change());
        sl1.setValue(0);//设置滑块初始值
        sl2.setValue(0);
        sl3.setValue(0);

        jp1.setBackground(new Color(sl1.getValue(),sl2.getValue(),sl3.getValue()));
        jp2.add(jl1);
        jp2.add(sl1);
        jp2.add(jt1);
        jp3.add(jl2);
        jp3.add(sl2);
        jp3.add(jt2);
        jp4.add(jl3);
        jp4.add(sl3);
        jp4.add(jt3);
        jp5.setLayout(new GridLayout(3,1));
        jp5.add(jp2);
        jp5.add(jp3);
        jp5.add(jp4);

        this.add(jp1);
        this.add(jp5,BorderLayout.SOUTH);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(300,300);
    }

    public static void main(String[] args) {
        new ColorFrame().setVisible(true);
    }

}