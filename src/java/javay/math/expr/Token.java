package javay.math.expr;

public class Token implements IBean {
    private TokenType type;
    private String token;
    public Token(TokenType ty, String str) {
    	this.type = ty;
    	this.token = str;
    }
	/**
	 * @return the type
	 */
	public TokenType getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(TokenType type) {
		this.type = type;
	}
	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}
	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append(this.type);
		buf.append(this.token);
		return buf.toString();
	}
	@Override
	public void setByName(String name, Object value) {
		if (IBean.TYPE.equals(name)) {
			this.setType((TokenType) value);
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
}
