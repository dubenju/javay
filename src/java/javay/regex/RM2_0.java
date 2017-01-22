package javay.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RM2_0 {

    public static void main(String[] args) {
        int flag = 0;
        int i = 0;
        int j = 0;

//        flag |= Pattern.UNICODE_CHARACTER_CLASS; // \\u
//        flag |= Pattern.UNICODE_CASE;
//        flag |= Pattern.DOTALL; // 点匹配任意字符
//        flag |= Pattern.MULTILINE;
//        flag |= Pattern.UNIX_LINES; //
//        flag |= Pattern.CASE_INSENSITIVE; // 忽略大小

        // 按指定模式在字符串查找
        byte[] chs = new byte[256];
        for (i = 0; i < 256; i ++) {
            chs[i] = (byte) i;
        }
        String line = new String(chs);

        char[] keys = new char[52];
        for (j = 0; j < 26; j ++) {
            keys[j] = (char)('A' + j);
        }
        for (j = 26; j < 52; j ++) {
            keys[j] = (char)('a' + j - 26);
        }
        for (j = 0; j < 52; j ++) {
            System.out.println("--------------");
            String pattern = "\\c" + keys[j];

            // 创建 Pattern 对象
            Pattern r = Pattern.compile(pattern, flag);
            System.out.println("  pattern(): " + r.pattern());

            // 创建 matcher 对象
            Matcher m = r.matcher(line);

            if (m.find( )) {
                do {
                    System.out.println(" groupCount: " + m.groupCount());
                    for (i = 0; i <= m.groupCount(); i ++) {
                        System.out.println("Found value: " + m.group(i) );
                    }
                    System.out.println("    start(): " + m.start());
                    System.out.println("      end(): " + m.end());
                } while(m.find( ));
            } else {
                 System.out.println("NO MATCH");
            }
        }
    }
}