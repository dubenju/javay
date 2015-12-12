package javay.test.util;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class TestCollection {

	public static void main(String[] args) {
		String a = "A", b = "B", c = "C", d = "D", e = "E";
		List<String> list = new LinkedList<String>();

		list.add(a);
		list.add(e);
		list.add(d);

		list.set(1, b);// 将索引位置为1的对象e修改为对象b
		list.add(2, c);// 将对象c添加到索引位置为2的位置

		Iterator<String> it = list.iterator();
		while (it.hasNext()) {
			System.out.println(it.next());
		}
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));// 利用get(int index)方法获得指定索引位置的对象
		}

	}
}
