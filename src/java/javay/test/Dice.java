package javay.test;

public class Dice {

    public static void main(String[] args) {
	int n[][] = new int[3][2];
	int a = 4;
	int b = 1;

	n[0][0] = a;
	n[0][1] = 7 -n[0][0];

	n[1][0] = b;
	n[1][1] = 7 - n[1][0];

	if ((n[0][0] == 4 || n[0][0] == 6 ) && (n[1][1] == 4 || n[1][1] == 6)) {
		n[2][0] = 5;
	}
	if ((n[0][0] == 4 || n[0][0] == 5 ) && (n[1][1] == 4 || n[1][1] == 5)) {
		n[2][0] = 6;
	}
	if ((n[0][0] == 6 || n[0][0] == 5 ) && (n[1][1] == 6 || n[1][1] == 5)) {
		n[2][0] = 4;
	}
	if ((n[0][0] == 3 || n[0][0] == 1 ) && (n[1][1] == 3 || n[1][1] == 1)) {
		n[2][0] = 2;
	}
	if ((n[0][0] == 3 || n[0][0] == 2 ) && (n[1][1] == 3 || n[1][1] == 2)) {
		n[2][0] = 1;
	}
	if ((n[0][0] == 1 || n[0][0] == 2 ) && (n[1][1] == 1 || n[1][1] == 2)) {
		n[2][0] = 3;
	}
	n[2][1] = 7 - n[2][0];
	for (int idx = 0; idx < 3; idx ++) {
	    for (int idy = 0; idy < 2; idy ++) {
		int c = n[idx][idy];
		System.out.print(" " + c);
	    }
	    System.out.println();
	}
    }

}
