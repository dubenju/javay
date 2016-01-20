/**
 * 
 */
package javay.math.expr;

/**
 * @author dubenju
 *
 */

public interface IKeyValue<T> extends IBean {
    public String getKey();
    public T      getVale¥ue();
    public void setKey(String k);
    public void setValue(T v);
}
