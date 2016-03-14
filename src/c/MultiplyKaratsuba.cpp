// g++ -Wall -O2 -o MultiplyKaratsuba MultiplyKaratsuba.cpp
/*********************************************
 * 多倍長乗算 ( by Karatsuba 法 )
 *   - 多倍長 * 多倍長
 *   - 最下位の桁を配列の先頭とする考え方
 *********************************************/
#include <cstdlib>   // for rand()
#include <iostream>  // for cout
#include <math.h>    // for pow()
#include <stdio.h>   // for printf()

//#define TEST       // テスト ( 乗算回数・処理時間 ) するならコメント解除
#define D_MAX 1024   // 計算可能な最大桁数 ( 2のべき乗 )
#define D     1024   // 実際に計算する桁数 ( D_MAX 以下の 4 の倍数 )

using namespace std;

/*
 * 計算クラス
 */
class Calc
{
    int A[D];  // 被乗数配列
    int B[D];  // 乗数配列
#ifdef TEST
    int cnt_mul;       // 乗算回数
    clock_t t1, t2;    // 計算開始CPU時刻、計算終了CPU時刻
    double tt;         // 計算時間
#endif

    public:
        Calc();                                            // コンストラクタ
        void calcKaratsuba();                              // 計算 ( Karatsuba 法 )

    private:
        void multiplyNormal(int *, int *, int, int *);     // 乗算 ( 標準(筆算)法 )
        void multiplyKaratsuba(int *, int *, int ,int *);  // 乗算 ( Karatsuba 法 )
        void doCarry(int *, int);                          // 繰り上がり・借り処理
        void display(int *, int *, int *);                 // 結果出力
};

/*
 * コンストラクタ
 */
Calc::Calc()
{
    /* ====================================== *
     * テストなので、被乗数・乗数は乱数を使用 *
     * ====================================== */

    int i;  // LOOP インデックス

    // 被乗数・乗数桁数設定
    for (i = 0; i < D; i++) {
        A[i] = rand() % 10;
        B[i] = rand() % 10;
    }
}

/*
 * 計算 ( Karatsuba 法 )
 */
void Calc::calcKaratsuba()
{
    int a[D_MAX];      // 被乗数配列
    int b[D_MAX];      // 乗数配列
    int z[D_MAX * 6];  // 計算結果用配列
    int i;             // LOOPインデックス

#ifdef TEST
    t1 = clock();  // 計算開始時刻
    for (int l = 0; l < 1000; l++) {
        cnt_mul = 0;  // 乗算回数リセット
#endif
    // 配列初期設定 ( コンストラクタで設定した配列を設定 )
    for (i = 0; i < D; i++) {
        a[i] = A[i];
        b[i] = B[i];
    }

    // 最大桁に満たない部分は 0 を設定
    for (i = D; i < D_MAX; i++) {
        a[i] = 0;
        b[i] = 0;
    }

    // 乗算 ( Karatsuba 法 )
    multiplyKaratsuba(a, b, D_MAX, z);

    // 繰り上がり・借り処理
    doCarry(z, D_MAX * 2);
#ifdef TEST
    }
    t2 = clock();  // 計算終了時刻
    tt = (double)(t2 - t1) / CLOCKS_PER_SEC;  // ==== 計算時間
#endif

    // 結果出力
    display(a, b, z);
}

/*
 * 乗算 ( 標準(筆算)法 )
 */
void Calc::multiplyNormal(int *a, int *b, int tLen, int *z)
{
    int i, j;  // ループインデックス

    // 計算結果初期化
    for(i = 0; i < tLen * 2; i++) z[i] = 0;

    // 各配列を各桁とみなして乗算
    for (j = 0; j < tLen; j++) {
        for (i = 0; i < tLen; i++) {
            z[j + i] += a[i] * b[j];
#ifdef TEST
            cnt_mul++;  // 乗算カウント
#endif
        }
    }
}

/*
 * 乗算 ( Karatsuba 法 )
 */
void Calc::multiplyKaratsuba(int *a, int *b, int tLen, int *z)
{
    // ==== 変数宣言
    int *a0 = &a[0];                    // 被乗数/右側配列ポインタ
    int *a1 = &a[tLen / 2];             // 被乗数/左側配列ポインタ
    int *b0 = &b[0];                    // 乗数  /右側配列ポインタ
    int *b1 = &b[tLen / 2];             // 乗数  /左側配列ポインタ
    int *v  = &z[tLen * 5];             // v  (= a1 + a0) 用配列ポインタ
    int *w  = &z[tLen * 5 + tLen / 2];  // w  (= b1 + b0) 用配列ポインタ
    int *x1 = &z[tLen * 0];             // x1 (= a0 * b0) 用配列ポインタ
    int *x2 = &z[tLen * 1];             // x2 (= a1 * b1) 用配列ポインタ
    int *x3 = &z[tLen * 2];             // x3 (= v * w)   用配列ポインタ
    int i;                              // LOOPインデックス

    // ==== 配列が４個になった場合は標準乗算
    if (tLen <= 4) {
        multiplyNormal(a, b, tLen, z);
        return;
    }

    // ==== v = a1 + a0, w = b1 + b0
    for(i = 0; i < tLen / 2; i++) {
        v[i] = a1[i] + a0[i];
        w[i] = b1[i] + b0[i];
    }

    // ==== x1 = a0 * b0
    multiplyKaratsuba(a0, b0, tLen / 2, x1);

    // ==== x2 = a1 * b1
    multiplyKaratsuba(a1, b1, tLen / 2, x2);

    // ==== x3 = (a1 + a0) * (b1 + b0)
    multiplyKaratsuba(v,  w,  tLen / 2, x3);

    // ==== x3 = x3 - x1 - x2
    for(i = 0; i < tLen; i++) x3[i] -= x1[i] + x2[i];

    // ==== z = x2 * R^2 + (x3 - x2 - x1) * R + x1
    //      ( x1, x2 は既に所定の位置にセットされているので、x3 のみ加算 )
    for(i = 0; i < tLen; i++) z[i + tLen / 2] += x3[i];
}

/*
 * 繰り上がり・借り処理
 */
void Calc::doCarry(int *a, int tLen) {
    int cr;  // 繰り上がり
    int i;   // ループインデックス

    cr = 0;
    for(i = 0; i < tLen; i++) {
        a[i] += cr;
        if(a[i] < 0) {
            cr = -(-(a[i] + 1) / 10 + 1);
        } else {
            cr = a[i] / 10;
        }
        a[i] -= cr * 10;
    }

    // オーバーフロー時
    if (cr != 0) printf("[ OVERFLOW!! ] %d\n", cr);
}

/*
 * 結果出力
 */
void Calc::display(int *a, int *b, int *z)
{
    int i;  // LOOPインデックス

    // 上位桁の不要な 0 を削除するために、配列サイズを取得
    int aLen = D_MAX, bLen = D_MAX, zLen = D_MAX * 2;
    while (a[aLen - 1] == 0) if (a[aLen - 1] == 0) aLen--;
    while (b[bLen - 1] == 0) if (b[bLen - 1] == 0) bLen--;
    while (z[zLen - 1] == 0) if (z[zLen - 1] == 0) zLen--;

    // a 値
    printf("a =\n");
    for (i = aLen - 1; i >= 0; i--) {
        printf("%d", a[i]);
        if ((aLen - i) % 10 == 0) printf(" ");
        if ((aLen - i) % 50 == 0) printf("\n");
    }
    printf("\n");

    // b 値
    printf("b =\n");
    for (i = bLen - 1; i >= 0; i--) {
        printf("%d", b[i]);
        if ((bLen - i) % 10 == 0) printf(" ");
        if ((bLen - i) % 50 == 0) printf("\n");
    }
    printf("\n");

    // z 値
    printf("z =\n");
    for (i = zLen - 1; i >= 0; i--) {
        printf("%d", z[i]);
        if ((zLen - i) % 10 == 0) printf(" ");
        if ((zLen - i) % 50 == 0) printf("\n");
    }
    printf("\n\n");

#ifdef TEST
    printf("Counts of multiply / 1 loop = %d\n", cnt_mul);     // 乗算回数
    printf("Total time of all loops     = %f seconds\n", tt);  // 処理時間
#endif
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

        // 乗算 ( Karatsuba 法 )
        objCalc.calcKaratsuba();
    }
    catch (...) {
        cout << "例外発生！" << endl;
        return -1;
    }

    // 正常終了
    return 0;
}
