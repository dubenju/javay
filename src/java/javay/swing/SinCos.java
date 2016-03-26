package javay.swing;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JRadioButton;

public class SinCos extends JFrame{
    private final int WIDTH = 900, HEIGHT = 450;//窗口默认的宽度、高度
    private final int offsetX=15;//原点的距离窗口左边空白
    private final int offsetY=5; //原点的距离窗口顶边空白
    private final int stepX=200; //横向步进
    private final int stepY=200; //纵向步进
    private final Color colBack = Color.white;     //背景颜色
    private final Color colText = Color.blue;      //文字标签颜色
    private final Color colCros = Color.DARK_GRAY; //轴颜色
    private final Color colLine = Color.red;       //线颜色
    
    private JRadioButton rdSin=new JRadioButton("Sin");
    private JRadioButton rdCos=new JRadioButton("Cos");
    private JButton btnDraws = new JButton("画线");
    private JButton btnClear = new JButton("清空");
    private boolean bDraw = false;
    
    public void paint(Graphics g){
        super.paint(g);   //让父类先处理
        int w=getWidth(); //窗口的宽度
        int h=getHeight();//窗口的高度
        
        g.setColor(colBack);    //底色用白色
        g.fillRect(0, 0, w, h);    //填充整个窗口
        rdSin.repaint();
        rdCos.repaint();
        btnDraws.repaint();
        btnClear.repaint();
        if(!bDraw){
            return ;
        }
        
        int mid_y=(h-offsetY)/2+10;//半高
        g.setFont(g.getFont().deriveFont(10f));//字体大小
        g.setColor(colCros);        //横线和竖线，用深灰色
        g.drawLine(0, offsetY+mid_y, w, mid_y+offsetY);        //横线中线
        g.drawLine(offsetX, offsetY, offsetX, h);    //竖线中线
        
        g.setColor(colText); //刻度用蓝色
        int maxX=(int)Math.round( (w-offsetX) / stepX);//计算一下横向最大刻度
        for(int i=0;i<=maxX;i++){
            g.drawLine(offsetX+stepX*i, offsetY+mid_y-5, offsetX+stepX*i, offsetY+mid_y+5);//横线刻度,90步进，方便后面画线的计算
            g.drawString(String.valueOf(90*i), offsetX+stepX*i-5, offsetY+mid_y+10+5); //刻度是90度
        }
        
        int maxY=(int)Math.round( mid_y / stepY);//计算一下纵向最大刻度
        for(int i=1;i<=maxY;i++){
            g.drawLine(offsetX-5, offsetY+mid_y-stepY*i, offsetX+5, offsetY+mid_y-stepY*i);//竖线正刻度,100步进
            g.drawString(String.valueOf(stepY*i), offsetX+10, offsetY+mid_y-stepY*i+5);

            g.drawLine(offsetX-5, offsetY+mid_y+stepY*i, offsetX+5, offsetY+mid_y+stepY*i);//竖线负刻度,100步进
            g.drawString(String.valueOf(-stepY*i), offsetX+10, offsetY+mid_y+stepY*i+5);
        }
        
        g.setColor(colLine);   //曲线用红色
        int x1, y1, x_=-1,y_=0;
        for(int i=0; i<=w; i++){ //从0度到窗口宽度，开始画Sin()点
            x1=((Double)(offsetX+i/90.0*stepX) ).intValue();
            if(rdSin.isSelected()){
                y1=offsetY+((Double)( mid_y+stepY*Math.sin( Math.toRadians(i) )) ).intValue();
            }else{
                y1=offsetY+((Double)( mid_y+stepY*Math.cos( Math.toRadians(i) )) ).intValue();
            }
            if(x_==-1){
                x_=x1;y_=y1;
            }
            g.drawLine(x_, y_, x1, y1);
            x_=x1;y_=y1;
        }
    }

    public SinCos(){
        super("测试Graphics+Sin/Cos");
        this.setSize(WIDTH, HEIGHT);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        ButtonGroup group = new ButtonGroup();
        group.add(rdSin);
        group.add(rdCos);
        this.getContentPane().add(rdSin);
        this.getContentPane().add(rdCos);
        this.getContentPane().add(btnDraws);
        this.getContentPane().add(btnClear);
        this.setVisible(true);
        this.doLayout();
        btnDraws.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                bDraw=true; SinCos.this.repaint();
            }
        });
        btnClear.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                bDraw=false;SinCos.this.repaint();
            }
        });
        rdSin.setSelected(true);
    }
    public void doLayout(){
        super.doLayout();
        rdSin.setBounds(10, 15, 50, 20);
        rdCos.setBounds(rdSin.getX()+rdSin.getWidth()+5, 15, 50, 20);
        btnDraws.setBounds(rdCos.getX()+rdCos.getWidth()+5, 12, 70, 25);
        btnClear.setBounds(btnDraws.getX()+btnDraws.getWidth()+3, 12, 70, 25);
    }
    
    public static void main(String[] args){
        EventQueue.invokeLater(new Runnable()  {
            public void run()   {
                new SinCos();
            }
        });
    }
}
