package javay.test.calc;

import java.util.HashMap;
import java.util.Map;


public class Op {

    private static final Map<String, Integer> ops = new HashMap<String, Integer>();
    static{
        ops.put("+",  10);
        ops.put("-",  10);
        ops.put("*",  20);
        ops.put("/",  20);
        ops.put("%",  20);
        ops.put("(", 100);
        ops.put(")", 100);
    }

    public static boolean isSign(String sign1){
        Integer s = ops.get(sign1);
        if(s==null) {
            return false;
        }
        return true;
    }

    public static int compare(String sign1,String sign2){
        Integer p1 = ops.get(sign1);
        Integer p2 = ops.get(sign2);
        if(p1==null)
            throw new IllegalArgumentException("符号："+sign1+"不存在！");
        if(p2==null)
            throw new IllegalArgumentException("符号："+sign2+"不存在！");
        return p1-p2;
    }

    public static Object cal(Object x,Object y,String sign){
        Double a=0.0,b=0.0;
        a = Double.valueOf(x+"");
        b = Double.valueOf(y+"");
        if(sign.equals("+"))
            return a+b;
        if(sign.equals("-"))
            return a-b;
        if(sign.equals("*"))
            return a*b;
        if(sign.equals("/"))
            return a/b;
        if(sign.equals("%"))
            return a%b;
        throw new IllegalArgumentException("操作符不合法！");
    }
}

//* 乘
//multiply
//! 阶乘
//^ 次方
/// 除
//devide
//% 模
//mod
//+ 加
//add
//- 减
//subtract
//sub
//(
//)
//.
//= 等于
//equal
//and
//C
//CE
//dms

//exp
//f-e
//int
//ln
//log
//lsh
//m-
//m+
//mc
//mr
//ms
//not
//or
//pi
//rsh
//cos
//sin
//tan
//xor

