package javay.swing;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;

import javay.main.PerpetualCalendar;  
  
public class CalendarFrame extends JFrame {  
  private static final long serialVersionUID = 1L;  
  private PerpetualCalendar calendar;  
  private JLabel yearAndMonth;  
  private JLabel monthInc;  
  private JLabel monthDec;  
  private JLabel[] weeks = {   
      new JLabel("日"),   
      new JLabel("一"),  
      new JLabel("二"),  
      new JLabel("三"),   
      new JLabel("四"),   
      new JLabel("五"),  
      new JLabel("六"), };  
  private JLabel[] days = new JLabel[42];  
  public void setYearAndMonth(String yearAndMonth) {  
    this.yearAndMonth.setText(yearAndMonth);  
  }  
  public void setCalendar(int begin, int maxDay, int currentDay)  
 {  
    for (int i = 0; i < days.length; i++) {  
      days[i].setText("");  
      days[i].setForeground(Color.BLACK);  
    }  
    for (int i = 1; i <= maxDay; i++) {  
      String dateStr = (i <= 9 ? " " + i : "" + i);  
      days[begin + i - 2].setText(dateStr);  
      if (currentDay == i) {  
        days[begin + i - 2].setForeground(Color.RED);  
      }  
    }  
  }  
  public CalendarFrame(PerpetualCalendar calendar) {  
    this.calendar = calendar;  
    setTitle("PerpetualCalendar");  
    setSize(400, 350);  
    init();  
    Dimension d =  
 Toolkit.getDefaultToolkit().getScreenSize();  
    setLocation((d.width - getWidth()) / 2,   
(d.height - getHeight()) / 2);  
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
    setResizable(false);  
  }  
  private void init() {  
    Container contentPane = getContentPane();  
    contentPane.setLayout(null);  
    for (int i = 0; i < weeks.length; i++) {  
      weeks[i].setBounds(40 + i * 50, 50, 50, 30);  
      if(i==0||i==6) weeks[i].setForeground(Color.BLUE);  
      contentPane.add(weeks[i]);  
    }  
    for (int i = 0; i < days.length; i++) {  
      days[i] = new JLabel("");  
      days[i].setBounds(40 + (i % 7) * 50, 90 + (i / 7) * 30,  
 50, 30);  
      contentPane.add(days[i]);  
    }  
    monthInc = new JLabel(">>");  
    monthInc.setBounds(250, 20, 20, 20);  
    contentPane.add(monthInc);  
    monthDec = new JLabel("<<");  
    monthDec.setBounds(130, 20, 20, 20);  
    contentPane.add(monthDec);  
    yearAndMonth = new JLabel("");  
    yearAndMonth.setBounds(165, 20, 80, 20);  
    contentPane.add(yearAndMonth);  
    monthInc.addMouseListener(new MouseAdapter() {  
      @Override  
      public void mouseEntered(MouseEvent e) {  
        monthInc.setForeground(Color.RED);  
      }  
      @Override  
      public void mouseExited(MouseEvent e) {  
        monthInc.setForeground(Color.BLACK);  
      }  
      @Override  
      public void mouseClicked(MouseEvent e) {  
        calendar.incMonth();  
      }  
    });  
    monthDec.addMouseListener(new MouseAdapter() {  
      @Override  
      public void mouseEntered(MouseEvent e) {  
        monthDec.setForeground(Color.RED);  
      }  
      @Override  
      public void mouseExited(MouseEvent e) {  
        monthDec.setForeground(Color.BLACK);  
      }  
      @Override  
      public void mouseClicked(MouseEvent e) {  
        calendar.decMonth();  
          
      }  
    });  
  } 
//  private final static ScheduledThreadPoolExecutor scheduler = new ScheduledThreadPoolExecutor(10);
//  static {
//    scheduler.setKeepAliveTime(20, TimeUnit.MINUTES);
//    scheduler.allowCoreThreadTimeOut(true);
//  }
// 
//  public static void init() {
//    long target = 5;
//    Calendar now = Calendar.getInstance();
//    int hour = now.get(Calendar.HOUR_OF_DAY);
//    long m = now.get(Calendar.HOUR_OF_DAY) * 60;
//    long minute = now.get(Calendar.MINUTE) + m;
//    long initialDelay = 0;
//    if (target > minute) {
//      initialDelay = target - minute;
//    } else {
//      initialDelay = 24 * 60 + target - minute;
//    }
// 
//    if (hour >= 0 && hour < 5) {
//      initialDelay = 0;
//    }
//    long period = 24 * 60;
//    scheduler.scheduleAtFixedRate(new Runnable() {
//      @Override
//      public void run() {
//        BService.prize();
//      }
//    }, initialDelay, period, TimeUnit.MINUTES);
//  }
} 