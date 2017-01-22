package javay.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import javay.awt.event.SoundActionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SoundPanel extends JPanel {
    private int btnWidth = 80;
    private int btnHeight = 28;
    private JPanel mainPanel = new JPanel();
    private JPanel topDisplay = new JPanel();

    private JLabel time = new JLabel("aaaaaaaaaa", JLabel.LEFT);
    private JLabel time2 = new JLabel("aaaaaaaaaa", JLabel.LEFT);

    public JButton btnReco   = new JButton("Record");
    public JButton btnPrev   = new JButton("Previous Track");
    public JButton btnPlay   = new JButton("Play");
    public JButton btnPause  = new JButton("Pause");
    public JButton btnStop   = new JButton("Stop");
    public JButton btnNext   = new JButton("Next Track");
    public JButton btnSave   = new JButton("Save");

    private JSlider sl =new JSlider();
    private JTextField jt =new JTextField(2);
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(SoundPanel.class);
    public SoundPanel() {
	    log.debug("SoundPanel");
	    this.mainPanel.setLayout(new BoxLayout(this.mainPanel, BoxLayout.Y_AXIS));
	    //create the text field
	    this.mainPanel.add(this.topDisplay);
	    this.mainPanel.setBorder(BorderFactory.createLineBorder(Color.green));

	    this.topDisplay.setLayout(null);
	    this.topDisplay.setPreferredSize(new Dimension((btnWidth + 1) * 11, 210));

	    this.topDisplay.setBorder(BorderFactory.createLineBorder(Color.red));

	    this.time.setFont(new Font("Dialog",   1,   15));
	    this.time.setForeground(Color.red);
	    this.time.setText("test");
	    this.time.setBorder(BorderFactory.createLineBorder(Color.blue));
	    this.time.setSize(new Dimension(200 ,24));

	    this.topDisplay.add(this.time);

	    this.time2.setFont(new Font("Dialog",   1,   15));
	    this.time2.setForeground(Color.red);
	    this.time2.setText("test");
	    this.time2.setBorder(BorderFactory.createLineBorder(Color.blue));
	    this.time2.setSize(new Dimension(200 ,24));
	    this.time2.setLocation(0, 25);

	    this.topDisplay.add(this.time2);

	    this.setTimer(time);
	    this.setTimer2(time2);

	    this.topDisplay.add(this.btnReco);
	    this.topDisplay.add(this.btnPrev);
	    this.topDisplay.add(this.btnPlay);
	    this.topDisplay.add(this.btnPause);
	    this.topDisplay.add(this.btnStop);
	    this.topDisplay.add(this.btnNext);
	    this.topDisplay.add(this.btnSave);

	    this.btnReco.setEnabled(true);
	    this.btnPrev.setEnabled(false);
	    this.btnPlay.setEnabled(true);
	    this.btnPause.setEnabled(false);
	    this.btnStop.setEnabled(false);
	    this.btnNext.setEnabled(false);
	    this.btnSave.setEnabled(false);

	    this.btnReco.setBounds(1, 50, btnWidth, btnHeight);
	    this.btnPrev.setBounds(1 + btnWidth, 50, btnWidth, btnHeight);
	    this.btnPlay.setBounds(1 + 2 * btnWidth, 50, btnWidth, btnHeight);
	    this.btnPause.setBounds(1 + 3 * btnWidth, 50, btnWidth, btnHeight);
	    this.btnStop.setBounds(1 + 4 * btnWidth, 50, btnWidth, btnHeight);
	    this.btnNext.setBounds(1 + 5 * btnWidth, 50, btnWidth, btnHeight);
	    this.btnSave.setBounds(1 + 6 * btnWidth, 50, btnWidth, btnHeight);

	    this.btnPrev.setActionCommand("Prev");
	    this.btnNext.setActionCommand("Next");

	    this.sl.setMinimum(0);//设置滑块最大最小值sl
	    this.sl.setMaximum(100);//设置滑块最大最小值sl
	    this.sl.setValue(0);

	    this.sl.setBounds(1,  100, 200, btnHeight);
	    this.topDisplay.add(this.sl);

	    this.jt.setEditable(false);//将文本框设置为不可编辑
	    this.jt.setText(0+"");

        this.jt.setBounds(1,  150, btnWidth, btnHeight);
        this.topDisplay.add(this.jt);

	    SoundActionListener controler = new SoundActionListener( this );
	    this.btnReco.addActionListener( controler );
	    this.btnPrev.addActionListener( controler );
	    this.btnPlay.addActionListener( controler );
	    this.btnPause.addActionListener( controler );
	    this.btnStop.addActionListener( controler );
	    this.btnNext.addActionListener( controler );
	    this.btnSave.addActionListener( controler );

	    this.sl.addChangeListener(new Change(controler));

	    this.setLayout(new BorderLayout());
	    //add things to calculator panel
	    add( mainPanel, BorderLayout.CENTER );

    }

    //设置Timer 1000ms实现一次动作 实际是一个线程
    private void setTimer(JLabel time){
        final JLabel varTime = time;
        //Timer timeAction = new Timer(1000, new ActionListener() {
        Timer timeAction = new Timer(100, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                long timemillis = System.currentTimeMillis();
                //转换日期显示格式
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                varTime.setText(df.format(new Date(timemillis)));
            }
        });
        timeAction.start();
    }
    //设置Timer 1000ms实现一次动作 实际是一个线程
    private void setTimer2(JLabel time){
        final JLabel varTime = time;
        //Timer timeAction = new Timer(1000, new ActionListener() {
        Timer timeAction = new Timer(100, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                // long timemillis = System.currentTimeMillis();
        	long timemillis = System.nanoTime();
                //转换日期显示格式
                // SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        	SimpleDateFormat df = new SimpleDateFormat("SSS");
                varTime.setText(df.format(new Date(timemillis)));
            }
        });
        timeAction.start();
    }
    //变色内部类:当移动滑块时，将面板的背景色设为三个滑块组合成的RGB值，并将三个文本框的值对应修改
    private class Change implements ChangeListener {
        private SoundActionListener sal;
        public Change(SoundActionListener sal) {
            this.sal = sal;
        }
        @Override
        public void stateChanged(ChangeEvent e) {
            //jp1.setBackground(new Color(sl1.getValue(),sl2.getValue(),sl3.getValue()));
            jt.setText(sl.getValue()+"");
            this.sal.setVolume(sl.getValue());
            //jt2.setText(sl2.getValue()+"");
            //jt3.setText(sl3.getValue()+"");
        }

    }
}
