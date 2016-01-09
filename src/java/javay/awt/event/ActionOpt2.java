package javay.awt.event;

import javay.fsm.transition.Action;
import javay.swing.CalcultorConts;

public class ActionOpt2 implements Action<ExprInfo> {

	@Override
	public ExprInfo doAction(ExprInfo in) {
		// 表达式
		String s = in.getExpr();
		StringBuffer buf = new StringBuffer();
		buf.append(CalcultorConts.LEFT);
		buf.append(s);
		buf.append(CalcultorConts.RIGHT);
		in.setExpr(buf.toString());

		// 二元操作符的全角向半角变换
		String opt = in.getInput();
		String st = Converter.conv(opt);
		in.setOpt(st); // !!

		// 为操组数2输入作准备，输入缓冲初始化
		in.setInbuf(new StringBuffer());
		return in;
	}
}
