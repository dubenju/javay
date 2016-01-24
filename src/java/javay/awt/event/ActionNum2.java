package javay.awt.event;

import javay.fsm.transition.Action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    return in;
  }

}
