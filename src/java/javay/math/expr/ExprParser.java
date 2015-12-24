package javay.math.expr;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javay.util.UList;

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
    public static Expression toExpr(List<Token> list, int pos, int precedence) throws ExprException {
    	List<Token> opst = UList.getSubList(list, "Type", TokenType.OPERATOR);
    	List<Operator> ops = new ArrayList<Operator>(opst.size());
    	for(Token t : opst) {
    		Operator op = Operators.getOperator(t.getToken());
    		ops.add(op);
    	}
    	System.out.println(ops.toString());

        Stack<ExpressionN> stkNu = new Stack<ExpressionN>();
        Stack<Operator> stkOp = new Stack<Operator>();
        Expression res = null;

        int listSize = list.size();
        for(int i = pos; i < listSize; i ++) {
        	Token token = list.get(i);
            if (TokenType.OPERATOR.equals(token.getType())) {
            	Operator op = Operators.getOperator(token.getToken());
            	stkOp.push(op);
            } else {
                if (stkOp.size() <= 0) {
                	// 
                	ExpressionN exprN = ExprParser.makeExpressionN(token);
                	if (res == null) {
                		res = exprN;
                	}
                	stkNu.push(exprN);
                } else {
                	Operator optLeft = stkOp.pop();
                	int optLeArity = optLeft.getArity();
                	int optLePri = -1;
                	if (optLeArity == 2) {
                		optLePri = optLeft.getPriority();
                	}
                	if (optLeArity == 1) {
                		int optLeDir = optLeft.getDirection();
                		if (optLeDir == 1) {
                			// 右结合
                			optLePri = optLeft.getPriority();
                		}
                	}
                    // make 
                    if ((i + 1 ) < listSize) {
                    	Token tokenNext = list.get(i + 1);
                    	if (!TokenType.OPERATOR.equals(tokenNext.getType())) {
                    		throw new ExprException("Error.");
                    	}
                    	Operator optRight = Operators.getOperator(tokenNext.getToken());
                    	int optRiArity = optRight.getArity();
                    	int optRiPri = -1;
                    	if (optRiArity == 2) {
                    		// binary arity
                    		optRiPri = optRight.getPriority();
                    	}
                    	if (optRiArity == 1) {
                    		// unarity
                    		int optRiDir = optRight.getDirection();
                    		if (optRiDir == -1) {
                    			// 左结合
                    			optRiPri = optLeft.getPriority();
                    		}
                    	}
                    	if (optLePri == -1 && optRiPri == -1) {
                    		throw new ExprException("Error2.");
                    	}
                    	if (optLePri >= optRiPri) {
                    		
                    	}
                    }
                }
            }
        }
        return res;
    }
    public static ExpressionN makeExpressionN(Token t) {
        return new ExpressionN(t.getToken());
    }
}
