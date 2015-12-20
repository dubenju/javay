/**
 *
 */
package javay.test.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DBJ
 *
 */
public class TestT {

	/**
	 *
	 */
	public TestT() {
		List<String> a = new ArrayList<String>();
		a.add("init");
		set1(a);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}
	public static void set1(List<?> list) {
		System.out.println(list.get(list.size() - 1));
//		list.add("test");
//		Object o = new String("Object");
//		list.add(o);
	}
	public static void set2(List<Object> list){
		list.add("test");
	}
	public static void set3(List<? extends Object> list) {
//		list.add((Object) "test");
		list.add(null);
		list.set(0, null);
//		Object o = new String("Object");
//		list.add(o);
	}
}
