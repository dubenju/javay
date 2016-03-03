package javay.similarity;

import javay.math.BigNum;

public class TestSimilarity {

	public static void main(String[] args) {
		String str1 = "abc";
		String str2 = "abd";
		CosineDistance cd = new CosineDistance();
		BigNum res = cd .check(str1, str2);
		System.out.println(str1 + " vs " + str2 + "=" + res );
		res = cd .check(str1, str1);
		System.out.println(str1 + " vs " + str1 + "=" + res );
		res = cd .check(str2, str2);
		System.out.println(str2 + " vs " + str2 + "=" + res );
	}

}
