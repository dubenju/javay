package javay.math.expr;

public class ExprParser {
    public static void parse(String str) {
        System.out.println(str);
        char[] charAry = str.toCharArray();
        StringBuffer buf = new StringBuffer();
        int stat = 0; // 0:nothing,1:number,2:operator
        for(int i = 0; i < charAry.length; i ++) {
            char ch = charAry[i];
            System.out.print("{" + ch + "}");
            if ('0' <= ch && ch <= '9' || ch == '.') {
                // number
            	if (stat != 1) {
            		// refresh operator
            		if (buf != null) {
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
            		System.out.println(stat + "[NUMa]" + buf.toString());
            		buf = null;
            	}
            	if (stat == 2) {
            		// refresh operator
            		System.out.println(stat + "[OPRb]" + buf.toString());
            		buf = null;
            	}
            	stat = 0;
            	continue;
            } else {
                // operator
            	if (stat != 2) {
            		// refresh number
            		if (buf != null) {
            			System.out.println(stat + "[NUMb]" + buf.toString());
            		}
            		buf = null;
            		stat = 2;
            	}
            	if (buf == null) {
            		buf = new StringBuffer();
            	}
            	buf.append(ch);
            }
        } //for
        if (buf != null) {
        	System.out.println(stat + "[NUMc]" + buf.toString());
        }
    }
}
