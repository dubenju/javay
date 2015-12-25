package javay.math.expr;

import javay.math.BigNum;

/**
 * 由数字，操作符，变量，常量等有意义地组合而成并能求得结果的式子。
 * @author dubenju
 *
 */
public abstract class Expression {
	/**
	 * 能求得结果的
	 * @return 能求得的结果
	 */
    public abstract BigNum value();
    public abstract String toString();
}
