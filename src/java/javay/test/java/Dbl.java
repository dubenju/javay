package javay.test.java;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javay.util.DBGFloat;

public class Dbl {

  public static void main(String[] args) {
    double pi = 3.141592653589793;
    double d = 30.0;
//    double a = d * pi;
    // 1.96“1.96
    double a = 0.0 + 7 * 0.1;
    //double a = 0.7;
//    double b = 0.5;
//    double a = 0.1 + 0.2;
//    DBGFloat.debugDouble(a);
    System.out.println(a);
//    System.out.println(fixedNum(a));
    System.out.println(fixedNum2(Double.toString(a)));
  }
  public static double fixedNum(double d) {
	  DecimalFormat dcmFmt = new DecimalFormat("0.000000000000000");
	  return Double.parseDouble(dcmFmt.format(d));
  }
  public static double fixedNum2(String str) {
	  System.out.println("str=" + str);
	  Pattern pattern = Pattern.compile("\\.(\\d*?)(9|0)\\2{5,}(\\d{1,5})$");
	  Matcher matcher = pattern.matcher(str);
	  System.out.println("matcher.groupCount()=" + matcher.groupCount());
	  if (matcher.find()) {
          // return num.toFixed(match[1].length)-0;
		  System.out.println("0=" + matcher.group());
		  System.out.println("0=" + matcher.group(0));
		  System.out.println("1=" + matcher.group(1));
		  System.out.println("2=" + matcher.group(2));
		  System.out.println("3=" + matcher.group(3));
      } else {
    	  System.out.println("none");
      }
	  return 0;
  }
}
