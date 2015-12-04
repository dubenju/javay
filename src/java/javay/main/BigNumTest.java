/**
 *
 */
package javay.main;

import javay.math.BigNum;
import javay.math.BigNumRound;

/**
 * @author DBJ
 *
 */
public class BigNumTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("byte" + Byte.MIN_VALUE + "...＋" + Byte.MAX_VALUE); // 8
        System.out.println("short" + Short.MIN_VALUE + "...＋" + Short.MAX_VALUE); // 16
        System.out.println("int" + Integer.MIN_VALUE + "...＋" + Integer.MAX_VALUE); // 32
        System.out.println("long" + Long.MIN_VALUE + "...＋" + Long.MAX_VALUE); // 64
        System.out.println(BigNumRound.UP.ordinal());

//        BigNum test1 = new BigNum("0");
//        System.out.println("---------------");
//        BigNum test2 = new BigNum("1");
//        System.out.println("---------------");
//        BigNum test3 = new BigNum("9");
//        System.out.println("---------------");
//        BigNum test4 = new BigNum("10");
//        for(int i = 0; i <=100; i ++) {
//            // System.out.println("---------------");
//            BigNum t = new BigNum("" + i);
//        }

//        System.out.println("---------------");
//        BigNum test4 = new BigNum("126.0257");
//        System.out.println(test4.toString());
//        System.out.println("---------------");
//        BigNum test5 = new BigNum("37");
//        System.out.println(test5.toString());
//        System.out.println("---------------");
//        BigNum test6 = new BigNum("+655.36");
//        System.out.println("---------------");
//        BigNum test7 = new BigNum("65537");
//        System.out.println("---------------");
//        BigNum test8 = test4.add(test5);
//        System.out.println(test8.toString());
//        System.out.println("---------------");
//        BigNum test9 = test4.multiply(test5);
//        System.out.println(test9.toString());
//        System.out.println("---------------");
//        System.out.println(test4.compareTo(test5));
//        System.out.println("---------------");
//        BigNum test9 = test4.divide(test5, 0, 0);
//        System.out.println(test9.toString());
//        System.out.println("---------------");
//        System.out.println(new BigNum("0.01260257").divide(test5, 0, 0));
//        System.out.println(new BigNum("0.1260257").divide(test5, 0, 0));
//        System.out.println(new BigNum("1.260257").divide(test5, 0, 0));
//        System.out.println("---------------");
//        System.out.println(new BigNum("12.60257").divide(test5, 0, 0));
//        System.out.println("---------------");
//        System.out.println(new BigNum("126.0257").divide(test5, 0, 0));
//        System.out.println(new BigNum("1260.257").divide(test5, 0, 0));
//        System.out.println(new BigNum("12602.57").divide(test5, 0, 0));
//        System.out.println(new BigNum("126025.7").divide(test5, 0, 0));
//        System.out.println(new BigNum("1260257.0").divide(test5, 0, 0));
//        System.out.println(new BigNum("12602570.0").divide(test5, 0, 0));
//
//        char[][] a = new char[2][3];
//        a[0][0] = 'a';
//        a[0][1] = 'b';
//        a[0][2] = 'c';
//        a[1][0] = 'A';
//        a[1][1] = 'B';
//        a[1][2] = 'C';
//
//        for(int y = 0; y < a[0].length; y ++) {
//            //System.out.print("y=" + y + "a=" + a.length + "a[0]=" + a[0].length);
//            for (int x = 0; x < a.length; x ++) {
//                System.out.print("a[" + x + "][" + y + "]=" + a[x][y]);
//            }
//            System.out.println();
//        }
//        System.out.println("End.");

//        System.out.println(new BigNum("10").divide(new BigNum("3"), 1, 0));
        BigNum test12 = new BigNum("12");
        System.out.println(test12);
        BigNum test23 = new BigNum("23");
        System.out.println(test23);
        BigNum value = test12.add(test23);
        System.out.println(value);
    }

}
/*
访问控制	
private	私有的
protected	受保护的
public	公共的

类、方法和变量修饰符	
abstract	声明抽象
class	类
extends	扩允,继承
final	终极,不可改变的
implements	实现
interface	接口
native	本地
new	新,创建
static	静态
strictfp	严格,精准
synchronized	线程,同步
transient	短暂
volatile	易失

程序控制语句	
break	跳出循环
continue	继续
return	返回
do	运行
while	循环
if	如果
else	否则
for	循环
instanceof	实例
switch	根据值选择执行
case	定义一个值以供switch选择
default	默认

错误处理	
assert	断言表达式是否为真
catch	捕捉异常
finally	有没有异常都执行
throw	抛出一个异常对象
throws	声明一个异常可能被抛出
try	捕获异常

包相关	
import	引入
package	包

基本类型	
boolean	布尔型
byte	字节型
char	字符型
double	双精度浮点
float	单精度浮点
int	整型
long	长整型
short	短整型
null	空

变量引用	
super	父类,超类
this	本类
void	无返回值

保留关键字	
goto	是关键字，但不能使用
*/