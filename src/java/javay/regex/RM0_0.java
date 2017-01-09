package javay.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RM0_0 {

    public static void main(String[] args) {
        int flag = 0;
        int i =0;

//        flag |= Pattern.UNIX_LINES;              // 0x0001:单字节处理还是双字节处理 or Windows，体现在\n还是\r\n。
        flag |= Pattern.CASE_INSENSITIVE;        // 0x0002:忽略大小
//        flag |= Pattern.COMMENTS;                // 0x0004:
        flag |= Pattern.MULTILINE;              // 0x0008:Multiline 属性
//        flag |= Pattern.LITERAL;                // 0x0010:
        flag |= Pattern.DOTALL;                  // 0x0020:匹配所有字符，级别大于Pattern.UNIX_LINES。
//        flag |= Pattern.UNICODE_CASE;            // 0x0040:
//        flag |= Pattern.CANON_EQ;                // 0x0080:
//        flag |= Pattern.UNICODE_CHARACTER_CLASS; // 0x0100:\\u

        // 按指定模式在字符串查找
        String line = "0.7000000000000001";
        String pattern = "\\.(\\d*?)(9|0)\\2{5,}(\\d{1,5})$";

        // 创建 Pattern 对象
        Pattern r = Pattern.compile(pattern, flag);
        System.out.println("pattern()=" + r.pattern());

        // 创建 matcher 对象
        Matcher m = r.matcher(line);

        if (m.find( )) {
            do {
                System.out.println("----------------------------");
                System.out.println("groupCount():" + m.groupCount());
                for (i = 0; i <= m.groupCount(); i ++) {
                    System.out.println(i + " Found value: " + m.group(i));
                }
                System.out.println("start(): " + m.start());
                System.out.println("  end(): " + m.end());
            } while(m.find( ));
            System.out.println("----------------------------");
        } else {
             System.out.println("NO MATCH");
        }
    }
}
