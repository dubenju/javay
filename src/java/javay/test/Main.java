package javay.test;

import java.util.Scanner;
import java.math.BigDecimal;

/**
 * 用java保证精度。。。当x>=32时，在精度范围内log2(2^x+1)=x。否则将a-b转化为double类型直接计算。
 * 看题目不是直接求大数的对数，试着推导了一下
 * 已经知a=lgx,b=lgy,求lg(x+y) =lg(2^a+2^b) , 假设a>=b，令k=a-b
 * 式子为 lg(2^b(2^k+1)) == b + lg(2^k+1) 如果k>=32，那么lg(2^k+1)应该接近为k，否则double的pow可以直接计算。
 * 剩下就是写一个大数的相加和相减
 * @author dubenju
 *
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BigDecimal a, b, ans, c;
        c = BigDecimal.valueOf(32);
        int t = scanner.nextInt();
        for(int i = 1; i <= t; i++) {
            a = scanner.nextBigDecimal();
            b = scanner.nextBigDecimal();
            if(a.compareTo(b) <= 0) {
            	// 假设a>=b
                ans = a;
                a = b;
                b = ans;
            }
            // 令k=a-b
            a = a.subtract(b);
            if(a.compareTo(c) >= 0) {
            	// 如果k>=32，那么lg(2^k+1)应该接近为k
            	ans = a.add(b);
            } else {
            	// 否则double的pow可以直接计算。
                double tmp = Math.pow(2, a.doubleValue()) + 1;
                tmp = Math.log(tmp)/Math.log((double)2);
                ans = b.add(BigDecimal.valueOf(tmp));
            }
            ans = ans.setScale(9, BigDecimal.ROUND_HALF_UP);
            System.out.println("Case " + i + ": " + ans.toPlainString());
        }
        scanner.close();
    }
}

/*
大数求自然对数数值算法

我们学过幂级数的应该对下面这个公式不陌生
Ln(1+x)=x-x^2/2+x^3/3-x^4/4+……(-1<x≤1)
那我们直接从这个公式入手,由于上面的式子对x是有限制,那我们先做如下变换,令x=-x有
Ln(1-x)=-x-x^2/2-x^3/3-x^4/4-……(-1≤x<1)
那么
=Ln(a)
=Ln((1+x)/(1-x))
=Ln(1+x)-Ln(1-x)
=2(x+x^3/3+x^5/5+……+x^n/n+……)
=2x(1+x^2/3+x^4/5+x^6/7+……)
其中a=(1+x)/(1-x),同样x=(a-1)/(a+1)
于是我们有如下的求Ln(a)的算法

算法1:
首先x=(a-1)/(a+1)
1、y(n+1)=1/(2n+1)
2、y(k)=1/(2k-1)+x^2*y(k+1)【其中k=n,n-1,n-2,……,1】
3、Ln(a)=2*x*y(1)
其中误差＜x^(2n+1)/(n*(1-x^2))
【参考《实用数值计算方法》.甄西丰.清华大学出版社.2006.60-61页】

算法优化:
    其实上面的方法很久就发现了,但是一直没有下手写代码,因为当时一直无法处理收敛速度慢的问题,参考书上也没有具体说明,这几天无意之间发现算法可以进行如下的优化,特写成一篇文章,以为有缘人作参考.
    根据上面的算法我们可以求一个大于0的数a的对数,即Ln(a),在上面的算法中与a密切相关的是x=(a-1)/(a+1),明显当a趋近1的时候，上面的算法才会收敛快一点,当a远离1的时候，收敛速度是慢得惊人的.那在这里我们有没有好的方法使上面的算法对任意数都收敛较快呢。其实是有的，看下面的式子
Ln(a)=Ln(10^n*a/10^n)=Ln(10^n)+Ln(a/10^n)=n*Ln(10)+Ln(a/10^n)=n*Ln(10)+Ln(b),其中b=a/10^n
(1)如果a的第一个有效数字为1,7,8,9那么b必须计算到0.7≤b<2,进而得到相应的n.例如我们计算
   Ln(7456789.123456)=7*Ln(10)+Ln(0.7456789123456)
   Ln(0.007456789123456)=-2*Ln(10)+Ln(0.7456789123456)
(2)如果a的第一个有效数字为2,3,4,5,6那么还应Ln(a)=Ln(4*10^n*a/(10^n*4))=Ln(4)+n*Ln(10)+Ln(a/(10^n*4))，其中b=a/(10^n*4),那么b最终将变成0.5≤b<7/4,进而计算相应的n.例如我们计算
   Ln(234.56789)=2*Ln(10)+Ln(2.3456789)=2*Ln(10)+Ln(4)+Ln(2.3456789/4)=2*Ln(10)+Ln(4)+Ln(0.586419725)

在上面的算法中,很明显你可以提前把Ln(10)与Ln(4)的值给计算出来,那么接下来就是计算Ln(b)的问题了.
对于上面(1)的情况,可以得到0<x<1/3
对于上面(2)的情况,可以得到0<x<3/11
很明显,即使带入最差的情况x=1/3时,误差<1/（8*n*3^(2n-1)),当n=200的时候也可以精确到小数点后190多位,当然实际精确的位数会更多.在这里不再多说.

在此,在这里给出完整的伪代码算法.
Ln(a)
{
  If (a=0 or a<0)
     { Return 数据有误; }
  Else
     {
       start=获取a的第一个有效数字;
       If (start=1,7,8,9)
          { b=对a移动n位,移位后b满足0.7≤b<2;
            c=把b带入算法1计算出Ln(b);
            Return n*Ln(10)+c;
           }
       Else
          { b=对a移动n位后除以4,最终b满足0.5≤b<7/4;
            c=把b带入算法1计算出Ln(b);
            Return Ln(4)+n*Ln(10)+c;
          }
      }
}

看源码示例《高精度取对数BigNumberLog》
函数:BigNumberLog(f)
f:字符串形式的数据
函数执行成功返回Ln(f),否则返回空字符串

原理:《大数求自然对数数值算法》，下面代码的精度是很高的.
引用:
BigNumberRun:大数的四则运算函数
BigNumberMul_:大自然数乘法
BigNumberDiv_:大自然数除法
BigNumberAdd_:大自然数加法
BigNumberSub_:大自然数减法

例子:
 BigNumberLog("32.5456789")
="3.4826446071539881741146646353284187029746747200307891932558330476125109223380248329791593527535938440072737921290242587270555761141223296092191782718254159845620021998931397205063229516160669196474962774693811596048059547524464204057729717687697840666657591268801548413845106758603694185193840053426560801184641282315604727723129028287481534032156120751113185847548620983134895814293180879339111786113678505117000844879348849616609262633859607841184692903034251568623507565283535614523068755024250145732623086898601711809058772348985928"
=Ln(32.5456789)

源代码:
Public Function BigNumberLog(ByVal f As String) As String
        Dim f1, f2 As String
        Dim IsOver0 As Boolean
        If BigNumberGet(f, f1, f2, IsOver0) = False Then
            Return ""
        End If
        If IsOver0 = False Then
            Return ""
        End If
        If f1 = "0" Then
            If f2 = "" Then
                Return ""
            End If
            f1 = ""
        End If
        Dim i As Integer = f1.Length
        If i > 0 Then
            f = f1 + f2
        Else
            Dim j As Integer = f2.Length
            While j > 0
                If f2.StartsWith("0") Then
                    j -= 1
                    i -= 1
                    f2 = Mid(f2, 2, j)
                Else
                    Exit While
                End If
            End While
            f = f2
        End If
        f1 = Mid(f, 1, 1)
        If f1 = "1" Then
            i -= 1
            f = "1." + Mid(f, 2, f.Length) + "0"
            f2 = ""
        ElseIf f1 = "7" Or f1 = "8" Or f1 = "9" Then
            f = "0." + f
            f2 = ""
        Else
            i -= 1
            f = f1 + "." + Mid(f, 2, f.Length) + "0"
            f = BigNumberRun(f, "4", 300, 4)
            f2 = "1"
        End If
        f1 = BigNumberLog_Call(f)
        If f2 <> "" Then
            f1 = BigNumberRun(f1, "1.38629436111989061883446424291635313615100026872051050824136001898678724393938943121172665399283737508400296204114137146737104047151626111406534150327015192386145514165674287038061407724778334693638836824916485677414663926926303520882545058180287575423519508266161195298", 300, 1)
        End If
        f2 = BigNumberRun(i.ToString, MyLog10, 300, 3)
        f = BigNumberRun(f1, f2, 300, 1)
        Return f
    End Function
‘*********************************核心算法部分*****************************
    Private Function BigNumberLog_Call(ByVal a As String) As String
        Dim x As String = BigNumberRun(a, "1", 300, 2)
        a = BigNumberRun(a, "1", 300, 1)
        x = BigNumberRun(x, a, 300, 4)
        Dim IsOver As Boolean
        BigNumberGet(x, "", a, IsOver)
        Dim i As Integer = a.Length
        If i < 265 Then
            a += StrDup(265 - i, "0")
        ElseIf i > 265 Then
            a = Mid(a, 1, 265)
        End If
        '每个数据扩大10^(9*30)
        Dim x1(29), x2(0), One(30), k(0), Div(58), yk(0), temp(0), temp2(0) As Long
        One(30) = 1
        Div(58) = 100000000
        BigNumberGetArry(a, x1)
        BigNumberMul_(x1, x1, x2)
        k(0) = 401
        BigNumberDiv_(One, k, yk, Nothing)
        While k(0) > 1
            k(0) -= 2
            BigNumberMul_(x2, yk, temp)
            BigNumberDiv_(temp, Div, temp2, Nothing)
            BigNumberDiv_(One, k, temp, Nothing)
            BigNumberAdd_(temp, temp2, yk)
        End While
        k(0) = 2
        BigNumberMul_(x1, k, x2)
        BigNumberMul_(x2, yk, temp)
        i = temp.Length - 1
        x = temp(i)
        While i > 0
            i -= 1
            a = temp(i).ToString
            x += StrDup(9 - a.Length, "0") + a
        End While
        i = x.Length
        If x.Length < 265 + 270 + 1 Then
            x = StrDup(265 + 270 + 1 - x.Length, "0") + x
        End If
        i = x.Length - 265 - 270
        x = Mid(x, 1, i) + "." + Mid(x, i + 1, 265 + 270)
        If IsOver = False Then
            x = "-" + x
        End If
        Return x
    End Function
’**************************************************************************************
‘下面的2个函数的作用是把类似这种数据3.789装数组之中
Private Sub BigNumberGetArry(ByVal f As String, ByRef ret() As Long)
        '由大整数f获得数组形式的ret
        Dim i, j As Integer
        i = f.Length
        j = i \ 9
        If j * 9 = i Then
            j -= 1
        End If
        ReDim ret(j)
        j = 0
        While i > 9
            ret(j) = CLng(Mid(f, i - 8, 9))
            j += 1
            i -= 9
            f = Mid(f, 1, i)
        End While
        ret(j) = CLng(f)
    End Sub
Public Function BigNumberGet(ByVal f As String, ByRef f1 As String, ByRef f2 As String, ByRef IsOver0 As Boolean) As Boolean
        '获取数据f,并返回整数部分f1,小数部分f2,IsOver0为False表示f为负数,f不为10进制数据返回False
        '例子f=2.356时f1=2 f2=365 IsOver0=True
        'f=-45时 f1=45 f2=  IsOver0=False
        If f.StartsWith("-") Then
            IsOver0 = False
            If f.Length = 1 Then
                Return False
            End If
            f = Mid(f, 2, f.Length - 1)
        Else
            IsOver0 = True
        End If
        Dim i As Integer = f.IndexOf(".")
        If i = 0 Or i = f.Length - 1 Then
            Return False
        ElseIf i > 0 Then
            f1 = Mid(f, 1, i)
            f2 = Mid(f, i + 2, f.Length - i - 1)
        Else
            f1 = f
            f2 = ""
        End If
        f = f1 + f2
        Dim reg As New System.Text.RegularExpressions.Regex("\d+")
        If reg.IsMatch(f) = False Then
            Return False
        ElseIf reg.Match(f).Value <> f Then
            Return False
        End If
        i = f2.Length
        While i > 0
            If f2.EndsWith("0") Then
                i -= 1
                f2 = Mid(f2, 1, i)
            Else
                Exit While
            End If
        End While
        i = f1.Length
        While i > 1
            If f1.StartsWith("0") Then
                i -= 1
                f1 = Mid(f1, 2, i)
            Else
                Exit While
            End If
        End While
        Return True
    End Function
*/
