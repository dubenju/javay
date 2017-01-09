package javay.regex.expr;

import javay.math.expr.IBean;

public class RToken implements IBean{
    private RTokenType type;
    private String token;
    private String comment;

    public RToken(RTokenType ty, String str) {
      this.type = ty;
      this.token = str;
    }

    /**
     * getType.
     * @return the type
     */
    public RTokenType getType() {
      return type;
    }

    /**
     * setType.
     * @param type the type to set
     */
    public void setType(RTokenType type) {
      this.type = type;
    }

    /**
     * getToken.
     * @return the token
     */
    public String getToken() {
      return token;
    }

    /**
     * setToken.
     * @param token the token to set
     */
    public void setToken(String token) {
      this.token = token;
    }

    /**
     * toString.
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append(this.type);
      buf.append(this.token);
      buf.append(MetaCharacters.getComment(this.token));
      buf.append("\n");
      return buf.toString();
    }

    @Override
    public void setByName(String name, Object value) {
      if (IBean.TYPE.equals(name)) {
        this.setType((RTokenType) value);
      }
      if (IBean.TOKEN.equals(name)) {
        this.setToken((String) value);
      }
    }

    @Override
    public Object getByName(String name) {
      Object res = null;
      if (IBean.TYPE.equals(name)) {
        res = this.getType();
      }
      if (IBean.TOKEN.equals(name)) {
        res = this.getToken();
      }
      return res;
    }

    /**
     * @return comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment セットする comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

}
