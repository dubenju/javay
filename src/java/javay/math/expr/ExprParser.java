package javay.math.expr;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ExprParser {
    /**
     * 表达式的分解
     * @param str 表达式
     * @return
     */
    public static List<Token> parse(String str) throws ExprException {
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
            			tokenOperate(tokens, buf.toString());
            			System.out.println(stat + "[OPRa]" + buf.toString());
            		}
            		buf = null;
            		stat = 1;
            	}
            	if (buf == null) {
            		buf = new StringBuffer();
            	}
            	buf.append(ch);
            } else if (Character.isWhitespace(ch)) {
                // skip
            	if (stat == 1) {
            		// refresh number
            		if (buf != null) {
            			// tokens.append(buf.toString());
            			tokens.add(new Token(TokenType.NUMBER__, buf.toString()));
            			System.out.println(stat + "[NUMa]" + buf.toString());
            		}
            		buf = null;
            	}
            	if (stat == 2) {
            		// refresh operator
            		if (buf != null) {
            			// tokens.append(buf.toString());
            			tokenOperate(tokens, buf.toString());
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
            			tokens.add(new Token(TokenType.NUMBER__, buf.toString()));
            			System.out.println(stat + "[NUMb]" + buf.toString());
            		}
            		buf = null;
            		stat = 2;
            	} else {
            		if (ExprConts.OPERATOR_CHARS.indexOf(ch) >= 0) {
            			// refresh operator
                		if (buf != null) {
                			// tokens.append(buf.toString());
                			tokenOperate(tokens, buf.toString());
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
            		tokenOperate(tokens, buf.toString());
            		System.out.println(stat + "[OPRe]" + buf.toString());
            		buf = new StringBuffer();
            	}
            	buf.append(ch);
            }
        } //for
        if (buf != null) {
        	if (stat == 1) {
        		// tokens.append(buf.toString());
        		tokens.add(new Token(TokenType.NUMBER__, buf.toString()));
        		System.out.println(stat + "[NUMc]" + buf.toString());
        	}
        	if (stat == 2) {
        		// tokens.append(buf.toString());
        		tokenOperate(tokens, buf.toString());
        		System.out.println(stat + "[OPRc]" + buf.toString());
        	}
        }//if
        System.out.println(tokens.toString());
        return tokens;
    }

    /**
     * 区分是操作符还是变量
     * @param tokens
     * @param key
     * @throws ExprException
     */
    public static void tokenOperate(List<Token> tokens, String key) throws ExprException {
    	if (Operators.isExist(key)) {
    		tokens.add(new Token(TokenType.OPERATOR, key));
    	} else if (Variables.isExist(key)) {
    		tokens.add(new Token(TokenType.VARIABLE, key));
    	} else {
    		throw new ExprException("不能被识别的记号。");
    	}
    }
    /**
     * 获取中缀表达式
     * @param list
     * @param pos
     * @param precedence
     * @return
     * @throws ExprException
     */
    public static Expression toExprFromInfix(List<Token> list, int pos) throws ExprException {
        Stack<Expression> stkExpr = new Stack<Expression>();
        Stack<Operator>   stkOp   = new Stack<Operator>();
        Expression        res     = null;
        List<Token>       subList = null;
        Stack<Token>      stkLeft = new Stack<Token>();

        int listSize = list.size();
        for(int i = pos; i < listSize; i ++) {
        	Token token = list.get(i);
        	System.out.println(i + ":" + token + stkExpr.toString() + ":" + stkOp.toString());
            if (TokenType.OPERATOR.equals(token.getType())) {
            	// 操作符的时候
            	String operate = token.getToken();
            	if (ExprConts.ADD.equals(operate) || ExprConts.SUB.equals(operate)) {
            		// 对正负号的预处理
            		Token preToken = null;
            		if ((i - 1) >= 0) {
            			preToken = list.get(i - 1);
            		}
            		if (preToken == null) {
            			operate = operate + ":";
            		} else if (TokenType.OPERATOR.equals(preToken.getType())) {
            			// 前面的操作符
            			String preOperate = preToken.getToken();
            			if (!(ExprConts.PER.equals(preOperate) || ExprConts.FAC.equals(preOperate) || ExprConts.RIGHT.equals(preOperate))) {
            				// 当前面的操作符不是%!)的时候，+/-是正负号。
            				operate = operate + ":";
            			}
            		}
            	}
            	if (ExprConts.LEFT.equals(operate)) {
            		// (的时候
            		subList = new ArrayList<Token>();
            		stkLeft.push(token);
            		i++;
            		while(stkLeft.size() > 0 && i < listSize) {
            			// 当一直存在的时候
            			Token token2 = list.get(i);
            			if (ExprConts.LEFT.equals(token2.getToken())) {
            				stkLeft.push(token2);
            			}
            			if (ExprConts.RIGHT.equals(token2.getToken())) {
            				stkLeft.pop();
            				if (stkLeft.size() <= 0) {
            					break;
            				}
            			}
            			subList.add(token2);
            			i ++;
            		}
            		System.out.println("()" + subList + ",i=" + i);
            		Expression exprLeft = toExprFromInfix(subList, 0);
            		System.out.println("()" + exprLeft);
            		
                	Operator optRight = null;
                	if ((i + 1 ) < listSize) {
                		Token tokenNext = list.get(i + 1);
                		if (TokenType.OPERATOR.equals(tokenNext.getType())) {
                			optRight = Operators.getOperator(tokenNext.getToken());
                		}
                	}
                	res = setExpr(stkOp, exprLeft, optRight, stkExpr);
                	System.out.println("res=" + res);
                	stkExpr.push(res);
                	
                	// 中止当前的处理，继续下一个元素的处理。
            		continue;
            	}
            	Operator op = Operators.getOperator(operate);
            	int optPri = -1;
            	int optArity = op.getArity();
            	if (optArity == 2) {
            		optPri = op.getPriority();
            	}
            	if (optArity == 1) {
            		int optDir = op.getDirection();
            		if (optDir == -1) {
            			optPri = op.getPriority();
            		} else {
            			stkExpr.push(ExprParser.makeExpression(op, null));
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
            	Expression exprN = ExprParser.makeExpressionN(token);
            	if (TokenType.VARIABLE.equals(token.getType())) {
            		exprN = Variables.getVariable(token.getToken());
            	}
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
    
    /**
     * 逆波兰式
     * @param list
     * @return
     */
    public static Expression toExprFromPostfix(List<Token> list) {
    	Stack<Expression> stkExpr = new Stack<Expression>();
    	
    	
        int listSize = list.size();
        for(int i = 0; i < listSize; i ++) {
        	Token token = list.get(i);
        	System.out.println(i + ":" + token + stkExpr.toString());
            if (TokenType.OPERATOR.equals(token.getType())) {
            	// 操作符的时候
            	String operate = token.getToken();
            	Operator op = Operators.getOperator(operate);
            	int arity = op.getArity();
            	if (arity == 2) {
            		Expression expr2 = stkExpr.pop();
            		Expression expr1 = stkExpr.pop();
            		Expression2 exprr = new Expression2(expr1, op, expr2);
            		stkExpr.push(exprr);
            	}
            	if (arity == 1) {
            		Expression expr1 = stkExpr.pop();
            		Expression1 exprr = new Expression1(op, expr1);
            		stkExpr.push(exprr);
            	}
            	
            }
            if (!TokenType.OPERATOR.equals(token.getType())) {
            	// 操作数或变量的时候
            	Expression exprN = ExprParser.makeExpressionN(token);
            	if (TokenType.VARIABLE.equals(token.getType())) {
            		exprN = Variables.getVariable(token.getToken());
            	}
            	stkExpr.push(exprN);
            }
        }// for

        Expression        res     = stkExpr.pop();
        return res;
    }

    /**
     * 
     * @param t
     * @return
     */
    public static ExpressionN makeExpressionN(Token t) {
        return new ExpressionN(t.getToken());
    }
    /**
     * 
     * @param op
     * @param num
     * @return
     */
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
