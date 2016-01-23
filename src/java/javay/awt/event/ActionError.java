package javay.awt.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javay.fsm.transition.Action;

public class ActionError implements Action<ExprInfo> {
  private static final Logger log = LoggerFactory.getLogger(ActionError.class);

  @Override
  public ExprInfo doAction(ExprInfo in, Object params) {
    log.debug("doAction");
    in.setNum1("");
    in.setNum2("");
    in.setOpt("");
    in.setExpr("0");
    StringBuffer inbuf = new StringBuffer();
    inbuf.append("输入错误");
    in.setInbuf(inbuf);
    return in;
  }

}
