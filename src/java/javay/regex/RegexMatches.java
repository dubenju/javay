package javay.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexMatches {
    public static void main( String args[] ) {
        int flag = 0;
        //flag |= Pattern.UNICODE_CHARACTER_CLASS; // \\u
        flag |= Pattern.DOTALL;

        // 按指定模式在字符串查找
        String line = "This order was placed for QT3000! OK?";
        String pattern = "(\\D*)(\\d+)(.*)";

        // 创建 Pattern 对象
        Pattern r = Pattern.compile(pattern, flag);
        System.out.println(r.pattern());

        // 现在创建 matcher 对象
        Matcher m = r.matcher(line);
        System.out.println(m.groupCount());
        if (m.find( )) {
             System.out.println("Found value: " + m.group(0) );
             System.out.println("Found value: " + m.group(1) );
             System.out.println("Found value: " + m.group(2) );
             System.out.println("Found value: " + m.group(3) );
        } else {
             System.out.println("NO MATCH");
        }
    }
}
