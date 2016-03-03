package javay.similarity;

import java.util.Map;

public abstract class AbstractEditDistance implements EditDistance {
	public Map<Character, int[]> getMap(String str, int idx, Map<Character, int[]> res) {
		char[] chs = str.toCharArray();
		for (char ch : chs) {
			if (res.get(ch) == null) {
				int[] init = new int[2];
				init[0] = 0;
				init[1] = 0;
				init[idx] = 1;
				res.put(ch, init);
			} else {
				int[] val = res.get(ch);
				val[idx] += 1;
				res.put(ch, val);
			}
		}
		return res;
	}
}
