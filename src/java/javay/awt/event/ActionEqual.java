package javay.awt.event;

import javay.fsm.transition.Action;
import javay.math.expr.ExprException;
import javay.math.expr.ExprParser;
import javay.math.expr.Expression;
import javay.math.expr.Token;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ActionEqual implements Action<ExprInfo> {
  private static final Logger log = LoggerFactory.getLogger(ActionEqual.class);

  @Override
  public ExprInfo doAction(ExprInfo in, Object params) {
    log.debug(this.getClass().getName());
    /* *** 表达式求值 *** */
    String expression = in.getExpr();
    String opt2 = in.getOpt();
    if (opt2 != null && opt2.length() > 0) {
      String num2 = in.getInbuf().toString();
      expression = "(" + expression + ")" + opt2 + num2;
    }
    List<Token> ts = new ArrayList<Token>();
    try {
      ts = ExprParser.parse(expression);
    } catch (ExprException e1) {
      e1.printStackTrace();
    }
    List<Token> pots = ExprParser.toPostfix(ts);
    Expression expr2 = ExprParser.toExprFromPostfix(pots);
    System.out.println("[outIN ]" + expr2.toInfixString() + "=" + expr2.value());
    /* *** 表达式求值 *** */
    String val = expr2.value().toString();
    StringBuffer buf = new StringBuffer();
    buf.append(val);
    in.setInbuf(buf);
    in.setExpr(val); // 旧的表达式可以放到历史纪录里了。
    in.setOpt("");
    return in;
  }

}
