package javay.util;

import java.util.ArrayList;
import java.util.List;

import javay.math.expr.IBean;

public class UList {
    public static <T> List<T> getSubList(List<T> in, String ColumnName, Object value) {
        List<T> res = new ArrayList<T>();
        for(T t : in) {
            if (t instanceof IBean) {
                IBean bean = (IBean) t;
                Object val = bean.getByName(ColumnName);
                if (value instanceof Comparable) {
                	if (((Comparable) value).compareTo(val) == 0) {
                		res.add(t);
                	}
                } else {
                	if (value.equals(val)) {
                		res.add(t);
                	}
                }
            } else {

            }
        }
        return res;
    }
    public static <T> List<T> getSublist() {
    	return null;
    }
}
