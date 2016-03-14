// g++ CalcBitDigits2.cpp -o CalcBigDigits2
/*********************************************
 * 多桁計算
 * ( 符号は考慮しない。)
 *********************************************/
#include <cstdlib>   // for rand()
#include <iostream>  // for cout
#include <math.h>    // for pow()
#include <stdio.h>   // for printf()

#define N_A    1000                     // 計算桁数 ( 被加減乗除数 )
#define N_B     996                     // 計算桁数 ( 加減乗除数 )
#define LIMIT     4                     // 配列１つあたり桁数
#define SIZE_A ((N_A - 1) / LIMIT + 1)  // 配列サイズ
#define SIZE_B ((N_B - 1) / LIMIT + 1)  // 配列サイズ

using namespace std;

/*
 * 計算クラス
 */
class Calc
{
    int A[SIZE_A];  // 被加減乗除数配列
    int B[SIZE_B];  // 加減数配列
    int C;          // 乗除数

    public:
        Calc();                                    // コンストラクタ
        void calcAdd();                            // 計算 ( 加算 )
        void calcSub();                            // 計算 ( 減算 )
        void calcMul();                            // 計算 ( 乗算 )
        void calcDiv();                            // 計算 ( 除算 )
        void ladd(int *, int, int *, int, int *);  // ロング + ロング
        void lsub(int *, int, int *, int, int *);  // ロング - ロング
        void lmul(int *, int, int, int *);         // ロング * ショート
        void ldiv(int *, int, int, int *);         // ロング / ショート
        void display(int *, int);                  // 結果出力
};

/*
 * コンストラクタ
 */
Calc::Calc()
{
    // 使用する被加減乗除数・加減乗除数を設定（テストなので乱数を使用）
    for (int i = 0; i < SIZE_A; i++) A[i] = rand() % (int)pow(10, LIMIT);
    for (int i = 0; i < SIZE_B; i++) B[i] = rand() % (int)pow(10, LIMIT);
    C = rand() % (int)pow(10, LIMIT);
    printf("A =\n"); display(A, SIZE_A);
    printf("B =\n"); display(B, SIZE_B);
    printf("C =\n%d\n\n", C);
}

/*
 * 計算 ( 加算 )
 */
void Calc::calcAdd()
{
    // 各種宣言
    int a[SIZE_A];                         // 被加数配列
    int b[SIZE_B];                         // 加数配列
    int size_z = max(SIZE_A, SIZE_B) + 1;  // 結果格納配列サイズ
    int z[size_z];                         // 計算結果配列

    // 初期設定
    for (int i = 0; i < SIZE_A; i++) a[i] = A[i];  // 被加数配列
    for (int i = 0; i < SIZE_B; i++) b[i] = B[i];  // 加数配列
    for (int i = 0; i < size_z; i++) z[i] = 0;     // 計算結果配列

    // ロング + ロング
    ladd(a, SIZE_A, b, SIZE_B, z);

    // 結果出力
    printf("A + B =\n"); display(z, size_z);
}

/*
 * 計算 ( 減算 )
 */
void Calc::calcSub()
{
    // 各種宣言
    int a[SIZE_A];                     // 被減数配列
    int b[SIZE_B];                     // 減数配列
    int size_z = max(SIZE_A, SIZE_B);  // 結果格納配列サイズ
    int z[size_z];                     // 計算結果配列

    // 初期設定
    for (int i = 0; i < SIZE_A; i++) a[i] = A[i];  // 被減数配列
    for (int i = 0; i < SIZE_B; i++) b[i] = B[i];  // 減数配列
    for (int i = 0; i < size_z; i++) z[i] = 0;     // 計算結果配列

    // ロング - ロング
    lsub(a, SIZE_A, b, SIZE_B, z);

    // 結果出力
    printf("A - B =\n"); display(z, size_z);
}

/*
 * 計算 ( 乗算 )
 */
void Calc::calcMul()
{
    // 各種宣言
    int a[SIZE_A];            // 被乗数配列
    int c;                    // 乗数
    int size_z = SIZE_A + 1;  // 結果格納配列サイズ
    int z[size_z];            // 計算結果配列

    // 初期設定
    for (int i = 0; i < SIZE_A; i++) a[i] = A[i];  // 被乗数配列
    c = C;                                         // 乗数
    for (int i = 0; i < size_z; i++) z[i] = 0;     // 計算結果配列

    // ロング * ショート
    lmul(a, SIZE_A, c, z);

    // 結果出力
    printf("A * C =\n"); display(z, size_z);
}

/*
 * 計算 ( 除算 )
 */
void Calc::calcDiv()
{
    // 各種宣言
    int a[SIZE_A];        // 被除数配列
    int c;                // 除数配列
    int size_z = SIZE_A;  // 結果格納配列サイズ
    int z[size_z];        // 計算結果配列

    // 配列初期設定
    for (int i = 0; i < SIZE_A; i++) a[i] = A[i];  // 被除数配列
    c = C;                                         // 除数
    for (int i = 0; i < size_z; i++) z[i] = 0;     // 計算結果配列

    // ロング / ショート
    ldiv(a, SIZE_A, c, z);

    // 結果出力
    printf("A / C =\n"); display(z, size_z);
}

/*
 * ロング + ロング
 */
void Calc::ladd(int *a, int size_a, int *b, int size_b, int *z)
{
    for (int i = 0; i < size_a; i++) z[i] = a[i];
    for (int i = 0; i < size_b; i++) {
        z[i] += b[i];
        if (z[i] >= (int)pow(10, LIMIT)) {
            z[i] -= (int)pow(10, LIMIT);
            z[i + 1] += 1;
        }
    }
}

/*
 * ロング - ロング
 */
void Calc::lsub(int *a, int size_a, int *b, int size_b, int *z)
{
    for (int i = 0; i < size_a; i++) z[i] = a[i];
    for (int i = 0; i < size_b; i++) {
        z[i] -= b[i];
        if (z[i] < 0) {
            z[i] += (int)pow(10, LIMIT);
            z[i + 1] -= 1;
        }
    }
}

/*
 * ロング * ショート
 */
void Calc::lmul(int *a, int size_a, int c, int *z)
{
    for (int i = 0; i < size_a; i++) {
        z[i] += a[i] * c;
        if (z[i] >= (int)pow(10, LIMIT)) {
            z[i + 1] += z[i] / pow(10, LIMIT);
            z[i] %= (int)pow(10, LIMIT);
        }
    }
}

/*
 * ロング / ショート
 */
void Calc::ldiv(int *a, int size_a, int c, int *z)
{
    for (int i = size_a - 1; i >= 0; i--) {
        z[i] += a[i] / c;
        if (i > 0) a[i - 1] += (a[i] % c) * (int)pow(10, LIMIT);
    }
}

/*
 * 結果出力
 */
void Calc::display(int *s, int size)
{
    // 最上位で繰り上がりが無かった場合の処置
    if (s[size - 1] == 0) size--;

    // 1行に配列10個分出力
    for (int i = size - 1; i >= 0; i--) {
        printf("%04d ", s[i]);
        if ((size - i) % 10 == 0 && i != 0)
            printf("\n");
    }
    printf("\n\n");
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

        // 計算 ( 加算 )
        objCalc.calcAdd();

        // 計算 ( 減算 )
        objCalc.calcSub();

        // 計算 ( 乗算 )
        objCalc.calcMul();

        // 計算 ( 除算 )
        objCalc.calcDiv();
    }
    catch (...) {
        cout << "例外発生！" << endl;
        return -1;
    }

    // 正常終了
    return 0;
}
