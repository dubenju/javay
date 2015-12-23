package javay.math.expr;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ExprParser {
    public static List<Token> parse(String str) {
        System.out.println(str);
        String st = str.replaceAll(",", "");
        char[] charAry = st.toCharArray();
        //StringBuffer tokens = new StringBuffer();
        List<Token> tokens = new ArrayList<Token>();
        StringBuffer buf = null;
        int stat = 0; // 0:nothing,1:number,2:operator
        for(int i = 0; i < charAry.length; i ++) {
            char ch = charAry[i];
            System.out.print("{" + ch + "}");
            if ('0' <= ch && ch <= '9' || ch == '.') {
                // number
            	if (stat != 1) {
            		// refresh operator
            		if (buf != null) {
            			// tokens.append(buf.toString());
            			tokens.add(new Token(TokenType.OPERATOR, buf.toString()));
            			System.out.println(stat + "[OPRa]" + buf.toString());
            		}
            		buf = null;
            		stat = 1;
            	}
            	if (buf == null) {
            		buf = new StringBuffer();
            	}
            	buf.append(ch);
            //} else if (ch == ' ') {
            } else if (Character.isWhitespace(ch)) {
                // skip
            	if (stat == 1) {
            		// refresh number
            		if (buf != null) {
            			// tokens.append(buf.toString());
            			tokens.add(new Token(TokenType.NUMBER, buf.toString()));
            			System.out.println(stat + "[NUMa]" + buf.toString());
            		}
            		buf = null;
            	}
            	if (stat == 2) {
            		// refresh operator
            		if (buf != null) {
            			// tokens.append(buf.toString());
            			tokens.add(new Token(TokenType.OPERATOR, buf.toString()));
            			System.out.println(stat + "[OPRb]" + buf.toString());
            		}
            		buf = null;
            	}
            	stat = 0;
            	continue;
            } else {
                // operator
            	if (stat != 2) {
            		// refresh number
            		if (buf != null) {
            			// tokens.append(buf.toString());
            			tokens.add(new Token(TokenType.NUMBER, buf.toString()));
            			System.out.println(stat + "[NUMb]" + buf.toString());
            		}
            		buf = null;
            		stat = 2;
            	} else {
            		if (ExprConts.OPERATOR_CHARS.indexOf(ch) >= 0) {
            			// refresh operator
                		if (buf != null) {
                			// tokens.append(buf.toString());
                			tokens.add(new Token(TokenType.OPERATOR, buf.toString()));
                			System.out.println(stat + "[OPRd]" + buf.toString());
                		}
                		buf = null;
            		}
            	}
            	if (buf == null) {
            		buf = new StringBuffer();
            	}
            	if (buf.length() == 1 && ExprConts.OPERATOR_CHARS.indexOf(buf.toString()) >= 0) {
            		//tokens.append(buf.toString());
            		tokens.add(new Token(TokenType.OPERATOR, buf.toString()));
            		System.out.println(stat + "[OPRe]" + buf.toString());
            		buf = new StringBuffer();
            	}
            	buf.append(ch);
            }
        } //for
        if (buf != null) {
        	if (stat == 1) {
        		// tokens.append(buf.toString());
        		tokens.add(new Token(TokenType.NUMBER, buf.toString()));
        		System.out.println(stat + "[NUMc]" + buf.toString());
        	}
        	if (stat == 2) {
        		// tokens.append(buf.toString());
        		tokens.add(new Token(TokenType.OPERATOR, buf.toString()));
        		System.out.println(stat + "[OPRc]" + buf.toString());
        	}
        }//if
        System.out.println(tokens.toString());
        return tokens;
    }
    public static Expression toExpr(List<Token> list) {
        Stack<Token> stk = new Stack<Token>();
        Expression res = null;
        boolean bPush = true;
        for(Token token : list) {
            if (TokenType.OPERATOR.equals(token.getType())) {
                stk.push(token);
                bPush = false;
            } else {
                if (bPush) {
                    stk.push(token);
                } else {
                    // make 
                    
                    bPush = true;
                }
            }
        }
        return res;
    }
}
