// g++ CalcBitDigits.cpp -o CalcBigDigits
/*********************************************
 * 多桁計算
 *********************************************/
#include <iostream>  // for cout
#include <stdio.h>   // for printf()

#define KETA 40  // 最大桁数
#define N    5   // 配列サイズ

using namespace std;

/*
 * 計算クラス
 */
class Calc
{
    // 各種変数
    int i;              // LOOP インデックス
    int carry, borrow;  // 繰り上がり、借り
    long w;             // 被乗数、被除数ワーク
    long remainder;     // 剰余ワーク
    int z[N];           // 計算結果保存用

    public:
        // 計算
        void calc();
        // ロング + ロング
        void ladd(int *, int *);
        // ロング - ロング
        void lsub(int *, int *);
        // ロング * ショート
        void lmul(int *, int);
        // ロング / ショート
        void ldiv(int *, int);
        // 結果出力（ロング用）
        void displayL(int *);
        // 結果出力（ショート用）
        void displayS(int);
};

/*
 * 計算
 */
void Calc::calc()
{
    // 計算する数字（a, b: 加減算用、c, d: 剰除算用、z: 結果格納用）
    int a[N] = {56789012,34567890,12345678,90123456,78901234};
    int b[N] = {12345678,90123456,78901234,56789012,34567890};
    int c[N] = {      12,34567890,12345678,90123456,78901234};
    int d    = 99;

    // ロング + ロング
    ladd(a, b);

    // ロング - ロング
    lsub(a, b);

    // ロング * ショート
    lmul(c, d);

    // ロング / ショート
    ldiv(c, d);
}

/*
 * ロング + ロング
 */
void Calc::ladd(int a[], int b[])
{
    // 計算
    carry = 0;
    for (i = N - 1; i >=0; i--) {
        z[i] = a[i] + b[i] + carry;
        if (z[i] < 100000000) {
            carry = 0;
        } else {
            z[i] = z[i] - 100000000;
            carry = 1;
        }
    }

    // 結果出力
    printf(" ");
    displayL(a);
    printf("+");
    displayL(b);
    printf("=");
    displayL(z);
    printf("\n");
}

/*
 * ロング - ロング
 */
void Calc::lsub(int a[], int b[])
{
    // 計算
    borrow = 0;
    for (i = N - 1; i >=0; i--) {
        z[i] = a[i] - b[i] - borrow;
        if (z[i] >= 0) {
            borrow = 0;
        } else {
            z[i] = z[i] + 100000000;
            borrow = 1;
        }
    }

    // 結果出力
    printf(" ");
    displayL(a);
    printf("-");
    displayL(b);
    printf("=");
    displayL(z);
    printf("\n");
}

/*
 * ロング * ショート
 */
void Calc::lmul(int c[], int d)
{
    // 計算
    carry = 0;
    for (i = N - 1; i >=0; i--) {
        w = c[i];
        z[i] = (w * d + carry) % 100000000;
        carry = (w * d + carry) / 100000000;
    }

    // 結果出力
    printf(" ");
    displayL(c);
    printf("*");
    displayS(d);
    printf("=");
    displayL(z);
    printf("\n");
}

/*
 * ロング / ショート
 */
void Calc::ldiv(int c[], int d)
{
    // 計算
    remainder = 0;
    for (i = 0; i < N; i++) {
        w = c[i];
        z[i] = (w + remainder) / d;
        remainder = ((w + remainder) % d) * 100000000;
    }

    // 結果出力
    printf(" ");
    displayL(c);
    printf("/");
    displayS(d);
    printf("=");
    displayL(z);
    printf("\n");
}

/*
 * 結果出力（ロング用）
 */
void Calc::displayL(int s[])
{
    for (i = 0; i < N; i++)
        printf(" %08d", s[i]);
    printf("\n");
}

/*
 * 結果出力（ショート用）
 */
void Calc::displayS(int s)
{
    for (i = 0; i < N - 1; i++)
        printf(" %8s", " ");
    printf(" %08d\n", s);
}

/*
 * メイン処理
 */
int main()
{
    try
    {
        // 計算クラスインスタンス化
        Calc objCalc;

        // 多桁計算
        objCalc.calc();
    }
    catch (...) {
        cout << "例外発生！" << endl;
        return -1;
    }

    // 正常終了
    return 0;
}
