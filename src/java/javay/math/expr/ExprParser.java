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

    	// 
        Stack<Expression> stkExpr = new Stack<Expression>();
        Stack<Operator> stkOp = new Stack<Operator>();
        Expression res = null;

        int listSize = list.size();
        for(int i = pos; i < listSize; i ++) {
        	Token token = list.get(i);
        	System.out.println(i + ":" + token + stkExpr.toString() + ":" + stkOp.toString());
            if (TokenType.OPERATOR.equals(token.getType())) {
            	// 操作符的时候
            	Operator op = Operators.getOperator(token.getToken());
            	int optPri = -1;
            	int optArity = op.getArity();
            	if (optArity == 2) {
            		optPri = op.getPriority();
            	}
            	if (optArity == 1) {
            		int optDir = op.getDirection();
            		if (optDir == -1) {
            			optPri = op.getPriority();
            		}
            	}
            	if (optPri > -1) {
            		Expression left = stkExpr.pop();
            		Expression newExpr = ExprParser.makeExpression(op, left);
            		if (newExpr != null) {
	            		if (res == left) {
	            			res = newExpr;
	            		}
	            		stkExpr.push(newExpr);
            		} else {
            			throw new ExprException("Error");
            		}
            	}
            	stkOp.push(op);
            }
            if (!TokenType.OPERATOR.equals(token.getType())) {
            	// 操作数或变量的时候
            	ExpressionN exprN = ExprParser.makeExpressionN(token);
            	if (res == null) {
            		res = exprN;
            	}
            	Operator optRight = null;
            	if ((i + 1 ) < listSize) {
            		Token tokenNext = list.get(i + 1);
            		if (TokenType.OPERATOR.equals(tokenNext.getType())) {
            			optRight = Operators.getOperator(tokenNext.getToken());
            		}
            	}
            	res = setExpr(stkOp, exprN, optRight, stkExpr);
            	System.out.println("res=" + res);
            	stkExpr.push(res);
            } // TokenType
        } // for
        return res;
    }
    protected static Expression setExpr(Stack<Operator> opLefts, Expression exprN, 
    	Operator opRight, Stack<Expression> stkExpr) throws ExprException {
    	// 左操作符权限，不存在时为－1
    	int optLePri = -1;
        if (opLefts.size() > 0) {
        	Operator optLeft = opLefts.peek();
        	int optLeArity = optLeft.getArity();
        	if (optLeArity == 2) {
        		// 二元操作符
        		optLePri = optLeft.getPriority();
        	}
        	if (optLeArity == 1) {
        		// 一元操作符
        		int optLeDir = optLeft.getDirection();
        		if (optLeDir == 1) {
        			// 右结合
        			optLePri = optLeft.getPriority();
        		}
        	}
        }// 左操作符权限
        
        // 右操作符权限，不存在时为－1
        int optRiPri = -1;
        if (opRight != null) {
        	int optRiArity = opRight.getArity();
        	if (optRiArity == 2) {
        		// 二元操作符
        		optRiPri = opRight.getPriority();
        	}
        	if (optRiArity == 1) {
        		// 一元操作符
        		int optRiDir = opRight.getDirection();
        		if (optRiDir == -1) {
        			// 左结合
        			optRiPri = opRight.getPriority();
        		}
        	}
        }// 右操作符权限
        
    	if (optLePri == -1 && optRiPri == -1) {
    		return exprN;
    	}
    	if (optLePri >= optRiPri) {
    		// 左结合
    		Expression expr = stkExpr.pop();
    		if (expr instanceof Expression1) {
    			Expression1 expr1 = (Expression1) expr;
    			expr1.setNumber(exprN);
    		} else if (expr instanceof Expression2) {
    			Expression2 expr2 = (Expression2) expr;
    			expr2.setNum2(exprN);
    		} else {
    			throw new ExprException("error.");
    		}
    		opLefts.pop();
    		// next
    		return setExpr(opLefts, expr, opRight, stkExpr);
    		
    	} else {
    		// 右结合，暂且入栈
    		// stkExpr.push(exprN);
    		return exprN;
    	}
    }
    public static ExpressionN makeExpressionN(Token t) {
        return new ExpressionN(t.getToken());
    }
    public static Expression makeExpression(Operator op, Expression num) {
    	int arity = op.getArity();
    	if (arity == 1) {
    		return new Expression1(op, num);
    	}
    	if (arity == 2) {
    		return new Expression2(num, op);
    	}
    	return null;
    }
}
