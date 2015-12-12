package javay.test;

import java.math.BigInteger;  
public class Test {
    public static String root3(String num) {
          
        BigInteger b=new BigInteger(num);  
        //不用多解释了吧  
        if(b.compareTo(BigInteger.ZERO)<0)  
            return "不是非负数";  
      
        String root="0"; //开立方结果  
        String pre="0"; //开立方过程中需要计算的被减数  
        BigInteger trynum; //试商，开立方过程中需要计算的减数  
        BigInteger flag;  //试商，得到满足要求减数的之后一个数  
        BigInteger _30=new BigInteger("30");   
        BigInteger _300=new BigInteger("300");   
        BigInteger dividend; //开立方过程中需要计算的被减数  
        BigInteger A;  
        BigInteger B;  
        BigInteger BB;  
          
        int len=num.length(); //数字的长度  
          
        if(len%3==1)  //长度是奇数的画，首位补上1个0凑成偶数位  
        {  
            num="00"+num;  
            len+=2;  
        }  
          
        if(len%3==2)  
        {  
            num="0"+num;  
            len++;  
        }  
                  
        for(int i=0;i<len/3;++i) //得到的平方根一定是len/2位  
        {  
            dividend=new BigInteger(pre+num.substring(3*i,3*i+3));    
            A=new BigInteger(root);  
              
            for(int j=0;j<=9;++j)  
            {  
                B=new BigInteger(j+"");  
                BB=new BigInteger((j+1)+"");  
                  
                trynum=_300.multiply(A.pow(2)).multiply(B)  
                        .add(_30.multiply(A).multiply(B.pow(2)))  
                        .add(B.pow(3));  
                  
                  
                flag=_300.multiply(A.pow(2)).multiply(BB)  
                        .add(_30.multiply(A).multiply(BB.pow(2)))  
                        .add(BB.pow(3));  
                  
                //满足要求的j使得试商与计算中的被减数之差为最小正数  
                if(trynum.subtract(dividend).compareTo(BigInteger.ZERO)<=0  
                        &&flag.subtract(dividend).compareTo(BigInteger.ZERO)>0)  
                {  
                    root+=j;  //结果加上得到的j  
                    pre=dividend.subtract(trynum).toString(); //更新开立方过程中需要计算的被减数  
                    break;  
                }  
            }         
        }  
          
        return root.substring(1);     
    }  
      
    public static void main(String[] args)  
    {  
        BigInteger b=new BigInteger("123456789123456789123456789");  
        b=b.pow(3);  
        System.out.println(root3(b.toString()));  
  
    }  
}