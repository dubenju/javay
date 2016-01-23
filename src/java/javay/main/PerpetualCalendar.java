package javay.main;

import java.text.SimpleDateFormat;  
import java.util.Calendar;  
import javax.swing.UIManager;  
import javax.swing.UIManager.LookAndFeelInfo;

import javay.swing.CalendarFrame;

public class PerpetualCalendar {

  private CalendarFrame view;
  private Calendar calendar = Calendar.getInstance();
  private SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月");
  public void incMonth() {
  calendar.add(Calendar.MONTH, 1);
  updateCalendar();
  }
  public void decMonth() {  
    calendar.add(Calendar.MONTH, -1);  
    updateCalendar();  
  }  
  public PerpetualCalendar() {  
    view = new CalendarFrame(this);  
    updateCalendar();  
  }  
  private void updateCalendar() {  
    int maxDay =  
        calendar.getActualMaximum(Calendar.DAY_OF_MONTH);  
    int miniDay =  
        calendar.getActualMinimum(Calendar.DAY_OF_MONTH);  
    int currentDay = -1;  
    Calendar c = Calendar.getInstance();  
    if (c.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)  
        && c.get(Calendar.MONTH) == calendar.get(Calendar.MONTH)) {
      currentDay = c.get(Calendar.DATE);
    }
    c.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),1);
    int begin = c.get(Calendar.DAY_OF_WEEK);
    view.setCalendar(begin, maxDay, currentDay);
    view.setYearAndMonth(sdf.format(calendar.getTime()));
  }
  public void show() {
    view.setVisible(true);
  }
  public static void main(String[] args) throws Exception {
    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
      if ("Nimbus".equals(info.getName())) {
        UIManager.setLookAndFeel(info.getClassName());
        break;
      }
    }
    PerpetualCalendar calendar = new PerpetualCalendar();
    calendar.show();
  }
}
