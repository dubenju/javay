package javay.test.util;

import java.util.ArrayList;
import java.util.List;

public class TestList{
	public static void main(String[] args){
		List<Object> list = new ArrayList<Object>();
		ArrayList<Object> arrayList = new ArrayList<Object>();
		// list.trimToSize(); //错误，没有该方法。
		arrayList.trimToSize();   //ArrayList里有该方法。
	}
}

