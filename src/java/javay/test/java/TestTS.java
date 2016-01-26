package javay.test.java;

import java.util.Arrays;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

public class TestTS {

	public static void main(String[] args) {
		SortedSet<String> se = new TreeSet<String>(Arrays.asList("o tw th fo fi si se ei".split(" ")));
		System.out.println(se);
		Iterator<String> iterator = se.iterator();
		String h = "";
		String l = "";
		for (int i = 0; i <= 6; i ++) {
			if (i == 3) {
				l = iterator.next();
			}
			if (i == 6) {
				h = iterator.next();
			} else {
				iterator.next();
			}
		}
//		String a = iterator.next();
//		a = iterator.next();
//		a = iterator.next();
//		a = iterator.next();
//		a = iterator.next();
//		a = iterator.next();
//		a = iterator.next();
//		a = iterator.next();
//		a = iterator.next();
//		a = iterator.next();
//		a = iterator.next();
//		System.out.println(a);
		System.out.println(l + "," + h);

	}

}
