package javay.regex.expr;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javay.math.expr.ExprException;

public class RExprParser {
    /**
     * 4:Character->MetaCharacter
     * 3:MetaCharacter
     * 2:MetaCharacter->Character
     * 1:Character
     * @param ch
     * @return
     */
    protected static int checkCharacter(char ch, Stack<Character> stack) {
        Character top = null;
        int iReturnCd = 1; // Character

        if (stack != null && stack.size() > 0) {
            top = stack.peek();
        }
        if (ch == '\\') {
            // 是一个元字符，将下一个字符标记为一个特殊字符、或一个原义字符、或一个 向后引用、或一个八进制转义符。例如， 'n' 匹配字符 'n'。'\n' 匹配一个换行符。序列 '\\' 匹配 ""\"" 而 ""\("" 则匹配 ""(""。
            iReturnCd = 3; // MetaCharacter
            if (top != null && top.equals('\\')) {
                iReturnCd = 2; // MetaCharacter->Character
            }
        }

        if (ch == '(') {
            // 标记一个子表达式的开始。子表达式可以获取供以后使用。要匹配(字符，请使用 \(。
            iReturnCd = 3; // MetaCharacter
            if (top != null && top.equals('\\')) {
                iReturnCd = 2; // MetaCharacter->Character
            }
        }

        if (ch == ')') {
            // 标记一个子表达式的结束。子表达式可以获取供以后使用。要匹配)字符，请使用 \)。
            iReturnCd = 3; // MetaCharacter
            if (top != null && top.equals('\\')) {
                iReturnCd = 2; // MetaCharacter->Character
            }
        }

        if (ch == '[') {
            // 标记一个范围的开始。要匹配 [，请使用 \[。
            iReturnCd = 3; // MetaCharacter
            if (top != null && top.equals('\\')) {
                iReturnCd = 2; // MetaCharacter->Character
            }
        }

        if (ch == ']') {
            // 标记一个范围的结束。要匹配 ]，请使用 \]。
            iReturnCd = 3; // MetaCharacter
            if (top != null && top.equals('\\')) {
                iReturnCd = 2; // MetaCharacter->Character
            }
        }

        if (ch == '*') {
            // 匹配前面的子表达式零次或多次。例如，zo* 能匹配 ""z"" 以及 ""zoo""。* 等价于{0,}。要匹配 * 字符，请使用 \*。
            iReturnCd = 3; // MetaCharacter
            if (top != null && top.equals('\\')) {
                iReturnCd = 2; // MetaCharacter->Character
            }
        }

        if (ch == '+') {
            // 匹配前面的子表达式一次或多次。例如，'zo+' 能匹配 ""zo"" 以及 ""zoo""，但不能匹配 ""z""。+ 等价于 {1,}。要匹配 + 字符，请使用 \+。
            iReturnCd = 3; // MetaCharacter
            if (top != null && top.equals('\\')) {
                iReturnCd = 2; // MetaCharacter->Character
            }
        }

        if (ch == '?') {
            // 匹配前面的子表达式零次或一次(例如，""do(es)?"" 可以匹配 ""do"" 或 ""does"" 中的""do"" 。? 等价于 {0,1}。)，或指明一个非贪婪限定符。当该字符紧跟在任何一个其他限制符 (*, +, ?, {n}, {n,}, {n,m}) 后面时，匹配模式是非贪婪的。非贪婪模式尽可能少的匹配所搜索的字符串，而默认的贪婪模式则尽可能多的匹配所搜索的字符串。例如，对于字符串 ""oooo""，'o+?' 将匹配单个 ""o""，而 'o+' 将匹配所有 'o'。要匹配 ? 字符，请使用 \?。
            iReturnCd = 3; // MetaCharacter
            if (top != null && top.equals('\\')) {
                iReturnCd = 2; // MetaCharacter->Character
            }
        }

        if (ch == '{') {
            // 标记限定符表达式的开始。要匹配 {，请使用 \{。
            iReturnCd = 3; // MetaCharacter
            if (top != null && top.equals('\\')) {
                iReturnCd = 2; // MetaCharacter->Character
            }
        }

        if (ch == '}') {
            // 标记限定符表达式的结束。要匹配 }，请使用 \}。
            iReturnCd = 3; // MetaCharacter
            if (top != null && top.equals('\\')) {
                iReturnCd = 2; // MetaCharacter->Character
            }
        }

        if (ch == '^') {
            // 匹配输入字符串的开始位置，除非在方括号表达式中使用，此时它表示不接受该字符集合。如果设置了 RegExp 对象的 Multiline 属性，^ 也匹配 '\n' 或 '\r' 之后的位置。要匹配 ^ 字符本身，请使用 \^。
            iReturnCd = 3; // MetaCharacter
            if (top != null && top.equals('\\')) {
                iReturnCd = 2; // MetaCharacter->Character
            }
        }

        if (ch == '$') {
            // 匹配输入字符串的结束位置。如果设置了 RegExp 对象的 Multiline 属性，则 $ 也匹配 '\n' 或 '\r' 之前的位置。要匹配 $ 字符本身，请使用 \$。
            iReturnCd = 3; // MetaCharacter
            if (top != null && top.equals('\\')) {
                iReturnCd = 2; // MetaCharacter->Character
            }
        }

        if (ch == '.') {
            // 匹配除换行符 ""\n"" 之外的任何单个字符（也包括点字符自身）。要匹配包括 '\n' 在内的任何字符，请使用象 '[.\n]' or '(.|\n)' 的模式。要匹配 .，请使用 \.。
            iReturnCd = 3; // MetaCharacter
            if (top != null && top.equals('\\')) {
                iReturnCd = 2; // MetaCharacter->Character
            }
        }

        if (ch == '|') {
            // 指明两项之间的一个选择。要匹配 |，请使用 \|。
            iReturnCd = 3; // MetaCharacter
            if (top != null && top.equals('\\')) {
                iReturnCd = 2; // MetaCharacter->Character
            }
        }

        if (ch == 'd') {
            if (top != null && top.equals('\\')) {
                iReturnCd = 4; // Character->MetaCharacter
            }
        }
        if ('0' <= ch && ch <= '9') {
            if (top != null && top.equals('\\')) {
                iReturnCd = 4; // Character->MetaCharacter
            }
        }
        return iReturnCd;
    }

    public static List<RToken> parse(String str) throws  ExprException {
        System.out.println(str);
        char[] charAry = str.toCharArray();
        System.out.println(charAry.length);

        List<RToken> tokens = new ArrayList<RToken>();
        StringBuffer buf = new StringBuffer();
        Stack<Character> stack = new Stack<Character>();

        int stat = 0; // 0:nothing,1:Character,2:MetaCharacter
        for (int i = 0; i < charAry.length; i ++) {
            char ch = charAry[i];
            System.out.print(i + "{" + ch + "}");
            int j = checkCharacter(ch, stack);
            if (j == 3) {
                buf = new StringBuffer();
                buf.append(ch);
                stack.push(ch);
                //System.out.print("==>  元字符" + ch + ":" + j + ":" + stack + ":" + MetaCharacters.getComment(String.valueOf(ch)));
                System.out.print("==>  元字符" + ch + ":" + j + ":" + stack);
                RToken k = new RToken(RTokenType.ESCAPE, buf.toString());
                tokens.add(k);

                if (ch == ')') {
                    Character top = stack.pop();
                    while(top != null && top != '(') {
                        top = stack.pop();
                    }
                }
                if (ch == '}') {
                    Character top = stack.pop();
                    while(top != null && top != '{') {
                        top = stack.pop();
                    }
                }
                if (ch == ']') {
                    Character top = stack.pop();
                    while(top != null && top != '[') {
                        top = stack.pop();
                    }
                }
            } else {
                if (stat == 2) {
                    // out
                    stat = 1;
                }
                buf = new StringBuffer();
                if (j == 2 || j == 4) {
                    buf.append(stack.pop());
                }
                buf.append(ch);
                System.out.print("==>普通字符" + ch + ":" + j + ":" + stack);
                RToken k = new RToken(RTokenType.NORMAL, buf.toString());
                tokens.add(k);
            }
            System.out.println();
        }
        System.out.println(tokens.toString());
        return tokens;
    }
}
