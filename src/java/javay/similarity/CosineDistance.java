package javay.similarity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javay.math.BigNum;
import javay.math.BigNumRound;
import javay.math.MathBn;

public class CosineDistance implements EditDistance {

	@Override
	public BigNum check(String left, String right) {
		Map<Character, int[]> map = new HashMap<Character, int[]>();
		map = getMap(left, 0, map);
		map = getMap(right, 1, map);
		Iterator<Character> iterator = map.keySet().iterator();
		BigNum a = new BigNum("0.0");
		BigNum b = new BigNum("0.0");
		BigNum c = new BigNum("0.0");
		while (iterator.hasNext()) {
			int[] val = map.get(iterator.next());
			a = a.add(new BigNum(val[0] * val[1]));
			b = b.add(new BigNum(val[0] * val[0]));
			c = c.add(new BigNum(val[1] * val[1]));
		}
		BigNum d1 = MathBn.root(b, new BigNum("2.0"));
		BigNum d2 = MathBn.root(c, new BigNum("2.0"));
		return a.divide(d1.multiply(d2), 40, BigNumRound.HALF_EVENT);
	}
	protected Map<Character, int[]> getMap(String str, int idx, Map<Character, int[]> res) {
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
