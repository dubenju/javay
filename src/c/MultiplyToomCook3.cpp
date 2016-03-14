// g++ -Wall -O2 -o MultiplyToomCook3 MultiplyToomCook3.cpp
/*********************************************
 * 多倍長乗算 ( by Toom-Cook 法 (3-way) )
 *   - 最下位の桁を配列の先頭とする考え方
 *********************************************/
#include <cstdlib>   // for rand()
#include <iostream>  // for cout
#include <math.h>    // for pow()
#include <stdio.h>   // for printf()

//#define TEST      // テスト ( 乗算回数・処理時間 ) するならコメント解除
#define D_MAX 729  // 計算可能な最大桁数 ( 3 のべき乗 )
#define D     729  // 実際に計算する桁数 ( D_MAX 以下 )

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
        Calc();                                             // コンストラクタ
        void calcToomCook();                                // 計算 ( Toom-Cook 法 )

    private:
        void multiplyNormal(int *, int *, int, int *);      // 乗算 ( 標準(筆算)法 )
        void multiplyToomCook3(int *, int *, int , int *);  // 乗算 ( Toom-Cook 法 (3-way) )
        void doCarry(int *, int);                           // 繰り上がり・借り処理
        void display(int *, int *, int *);                  // 結果出力
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
 * 計算 ( Toom-Cook 法 )
 */
void Calc::calcToomCook()
{
    int a[D_MAX];       // 被乗数配列
    int b[D_MAX];       // 乗数配列
    int z[D_MAX * 2];  // 計算結果用配列
    int i;              // LOOPインデックス

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

    // 乗算 ( Toom-Cook 法 (3-way) )
    multiplyToomCook3(a, b, D_MAX, z);

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
 * 乗算 ( Toom-Cook 法 (3-way) )
 *   結果用配列は以下のように配置し、
 *     +----+----+----+----+----+----+----+----+----+----+
 *     |   c0    |   c2    |   c4    |   c1    |   c3    |
 *     +----+----+----+----+----+----+----+----+----+----+
 *   最後に、c1, c3 を所定の位置に加算する。
 *     +----+----+----+----+----+----+
 *     |   c0    |   c2    |   c4    |
 *     +----+----+----+----+----+----+
 *          +----+----+----+----+
 *          |   c1    |   c3    |
 *          +----+----+----+----+
 *   その他、計算に必要な配列ポインタを詳細に設定。
 */
void Calc::multiplyToomCook3(int *a, int *b, int tLen, int *z)
{
    // ==== 変数宣言
    int *a0 = &a[0];                // 被乗数/右側配列ポインタ
    int *a1 = &a[tLen / 3];         // 被乗数/中央配列ポインタ
    int *a2 = &a[tLen * 2/ 3];      // 被乗数/左側配列ポインタ
    int *b0 = &b[0];                // 乗数  /右側配列ポインタ
    int *b1 = &b[tLen / 3];         // 乗数  /中央配列ポインタ
    int *b2 = &b[tLen * 2/ 3];      // 乗数  /左側配列ポインタ
    int *c0 = &z[(tLen / 3) *  0];  // c0     用配列ポインタ
    int *c2 = &z[(tLen / 3) *  2];  // c2     用配列ポインタ
    int *c4 = &z[(tLen / 3) *  4];  // c4     用配列ポインタ
    int c1      [(tLen / 3) * 2];   // c1     用配列
    int c3      [(tLen / 3) * 2];   // c3     用配列
    int a_m2    [tLen / 3];         // a( -2) 用配列
    int a_m1    [tLen / 3];         // a( -1) 用配列
    int a_0     [tLen / 3];         // a(  0) 用配列
    int a_1     [tLen / 3];         // a(  1) 用配列
    int a_inf   [tLen / 3];         // a(inf) 用配列
    int b_m2    [tLen / 3];         // b( -2) 用配列
    int b_m1    [tLen / 3];         // b( -1) 用配列
    int b_0     [tLen / 3];         // b(  0) 用配列
    int b_1     [tLen / 3];         // b(  1) 用配列
    int b_inf   [tLen / 3];         // b(inf) 用配列
    int c_m2    [(tLen / 3) * 2];   // c( -2) 用配列
    int c_m1    [(tLen / 3) * 2];   // c( -1) 用配列
    int c_0     [(tLen / 3) * 2];   // c(  0) 用配列
    int c_1     [(tLen / 3) * 2];   // c(  1) 用配列
    int c_inf   [(tLen / 3) * 2];   // c(inf) 用配列
    int i;                          // LOOPインデックス

    // ==== 9 桁（配列 9 個）になった場合は標準乗算
    if (tLen <= 9) {
        multiplyNormal(a, b, tLen, z);
        return;
    }

    // ==== a(-2) = 4 * a2 - 2 * a1 + a0, b(1) = 4 * b2 - 2 * b1 + b0 (by シフト演算)
    for(i = 0; i < tLen / 3; i++) {
        a_m2[i] = (a2[i] << 2) - (a1[i] << 1) + a0[i];
        b_m2[i] = (b2[i] << 2) - (b1[i] << 1) + b0[i];
    }

    // ==== a(-1) = a2 - a1 + a0, b(1) = b2 - b1 + b0
    for(i = 0; i < tLen / 3; i++) {
        a_m1[i] = a2[i] - a1[i] + a0[i];
        b_m1[i] = b2[i] - b1[i] + b0[i];
    }

    // ==== a(0) = a0, b(0) = b0
    for(i = 0; i < tLen / 3; i++) {
        a_0[i] = a0[i];
        b_0[i] = b0[i];
    }

    // ==== a(1) = a2 + a1 + a0, b(1) = b2 + b1 + b0
    for(i = 0; i < tLen / 3; i++) {
        a_1[i] = a2[i] + a1[i] + a0[i];
        b_1[i] = b2[i] + b1[i] + b0[i];
    }

    // ==== a(inf) = a2, b(inf) = b2
    for(i = 0; i < tLen / 3; i++) {
        a_inf[i] = a2[i];
        b_inf[i] = b2[i];
    }

    // ==== c(-2) = a(-2) * b(-2)
    multiplyToomCook3(a_m2,  b_m2,  tLen / 3, c_m2 );

    // ==== c(-1) = a(-1) * b(-1)
    multiplyToomCook3(a_m1,  b_m1,  tLen / 3, c_m1 );

    // ==== c(0) = a(0) * b(0)
    multiplyToomCook3(a_0,   b_0,   tLen / 3, c_0  );

    // ==== c(1) = a(1) * b(1)
    multiplyToomCook3(a_1,   b_1,   tLen / 3, c_1  );

    // ==== c(inf) = a(inf) * b(inf)
    multiplyToomCook3(a_inf, b_inf, tLen / 3, c_inf);

    // ==== c4 = 6 * c(inf) / 6
    for(i = 0; i < (tLen / 3) * 2; i++)
        c4[i] = c_inf[i];

    // ==== c3 = -c(-2) + 3 * c(-1) - 3 * c(0) + c(1) + 12 * c(inf) / 6
    for(i = 0; i < (tLen / 3) * 2; i++) {
        c3[i]  = -c_m2[i];
        c3[i] += (c_m1[i] << 1) + c_m1[i];
        c3[i] -= (c_0[i] << 1) + c_0[i];
        c3[i] += c_1[i];
        c3[i] += (c_inf[i] << 3) + (c_inf[i] << 2);
        c3[i] /= 6;
    }

    // ==== c2 = 3 * c(-1) - 6 * c(0) + 3 * c(1) - 6 * c(inf) / 6
    for(i = 0; i < (tLen / 3) * 2; i++) {
        c2[i]  = (c_m1[i] << 1) + c_m1[i];
        c2[i] -= (c_0[i] << 2) + (c_0[i] << 1);
        c2[i] += (c_1[i] << 1) + c_1[i];
        c2[i] -= (c_inf[i] << 2) + (c_inf[i] << 1);
        c2[i] /= 6;
    }

    // ==== c1 = c(-2) - 6 * c(-1) + 3 * c(0) + 2 * c(1) - 12 * c(inf) / 6
    for(i = 0; i < (tLen / 3) * 2; i++) {
        c1[i]  = c_m2[i];
        c1[i] -= (c_m1[i] << 2) + (c_m1[i] << 1);
        c1[i] += (c_0[i] << 1) + c_0[i];
        c1[i] += (c_1[i] << 1);
        c1[i] -= (c_inf[i] << 3) + (c_inf[i] << 2);
        c1[i] /= 6;
    }

    // ==== c0 = 6 * c(0) / 6
    for(i = 0; i < (tLen / 3) * 2; i++)
        c0[i] = c_0[i];

    // ==== z = c4 * x^4 + c3 * x^3 + c2 * x^2 + c1 * x + c0
    //      (c0, c2, c4 は最初から所定の位置に格納されているので、
    //       c1, c3 のみ加算 )
    for(i = 0; i < (tLen / 3) * 2; i++) z[i + tLen / 3] += c1[i];
    for(i = 0; i < (tLen / 3) * 2; i++) z[i + (tLen / 3) * 3] += c3[i];
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

        // 乗算 ( Toom-Cook 法 )
        objCalc.calcToomCook();
    }
    catch (...) {
        cout << "例外発生！" << endl;
        return -1;
    }

    // 正常終了
    return 0;
}