package javay.fsm;

public enum State0 {
  OFF {
    @Override public void power() {
    }

    @Override public void cool() {
    }
  },
  FANONLY {
    @Override public void power() {
    }

    @Override public void cool() {
    }
  },
  COOL {
    @Override public void power() {
    }

    @Override public void cool() {
    }
  };
  public abstract void power();

  public abstract void cool();
}
