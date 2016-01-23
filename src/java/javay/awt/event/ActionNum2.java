package javay.awt.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javay.fsm.transition.Action;

public class ActionNum2 implements Action<ExprInfo> {
  private static final Logger log = LoggerFactory.getLogger(ActionNum2.class);

  @Override
  public ExprInfo doAction(ExprInfo in, Object params) {
    log.debug(this.getClass().getName());
    String curIn = in.getInput();

    StringBuffer buf = in.getInbuf();
    buf = new StringBuffer();
    buf.append(curIn);
    in.setInbuf(buf);

//    String opt = in.getOpt();
//    String expr = in.getExpr();
//    in.setExpr(expr + opt + buf.toString());
//    in.setNum1(cur_in);
    return in;
  }

}
