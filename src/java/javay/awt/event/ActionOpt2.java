package javay.awt.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import javay.fsm.transition.Action;
import javay.swing.CalcultorConts;

public class ActionOpt2 implements Action<ExprInfo> {
  private static final Logger log = LoggerFactory.getLogger(ActionOpt2.class);

  @Override
  public ExprInfo doAction(ExprInfo in, Object params) {
    log.debug(this.getClass().getName());
    @SuppressWarnings("unchecked")
    Map<String, String> context = (Map<String, String>) params;
    String fromStateId = context.get("from");
    if ("6".equals(fromStateId)) {
      // 操作数2(State6)
      String se = in.getExpr();
      String preOpt = in.getOpt();

      StringBuffer buf = new StringBuffer();
      buf.append(CalcultorConts.LEFT);
      buf.append(se);
      buf.append(CalcultorConts.RIGHT);
      buf.append(preOpt);
      buf.append(in.getInbuf().toString());
      in.setExpr(buf.toString());

      in.setOpt("");
      in.setInbuf(new StringBuffer());
    }
    // 表达式:以前的结果(state4)

    if ("3".equals(fromStateId)) {
      // 操作数1(State3)
      StringBuffer buf = new StringBuffer();
      buf.append(CalcultorConts.LEFT);
      buf.append(in.getInbuf().toString());
      buf.append(CalcultorConts.RIGHT);
      in.setExpr(buf.toString());
    }

    // 二元操作符的全角向半角变换
    String opt = in.getInput();
    String st = Converter.conv(opt, params);
    in.setOpt(st); // !!

    // 为操组数2输入作准备，输入缓冲初始化
    in.setInbuf(new StringBuffer());
    return in;
  }
}
