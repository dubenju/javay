package javay.math.expr;

public interface IBean {
  public static final String TOKEN = "Token";
  public static final String TYPE = "Type";
  public void setByName(String name, Object value);
  public Object getByName(String name);
}
