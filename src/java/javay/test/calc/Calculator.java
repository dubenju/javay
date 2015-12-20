package javay.test.calc;

import java.util.ArrayList;   
import java.util.List;   
import java.util.Stack;   
 
public class Calculator {   
      
   private List<String> list = new ArrayList<String>();   
   private Stack<String> stack = new Stack<String>();   
      
      
   private List<String> resolveExpr(String exp){      
       String opert=exp.replaceAll("\\d*\\.\\d+?", "");   
       List<String> list=new ArrayList<String>();      
       int pidx=-1;      
       for(int i=0;i<opert.length();i++) {
           String p=opert.substring(i, i+1);      
           pidx=exp.indexOf(p);      
           if(exp.substring(0,pidx).trim().length()!=0){      
               list.add(exp.substring(0, pidx));      
           }      
           list.add(exp.substring(pidx, pidx+1));      
           exp=exp.substring(pidx+1);      
       }      
       if(exp.length()>0){      
           list.add(exp);      
       }      
       return list;      
   }      
      
   private void dealSign(String s){   
       if(stack.size()==0){   
           stack.push(s);   
           return;   
       }   
       String ps = stack.pop();   
       if(Op.compare(s, ps)>0||ps.equals("(")){   
           if(s.equals(")")){   
               list.add(ps);   
               while(stack.size()>0){   
                   ps = stack.pop();   
                   if(ps.equals("("))   
                       break;   
                   list.add(ps);   
               }   
           }else{   
               stack.push(ps);   
               stack.push(s);   
           }   
       }else{   
           list.add(ps);   
           dealSign(s);   
       }   
   }   
      
   private void dealVar(String s){   
       list.add(s);   
   }   
      
   private Double getResult(){   
       for(String s:list){   
           if(!Op.isSign(s)){   
               stack.push(s);   
               continue;   
           }   
           Object a = 0,b = 0;   
           if(stack.size()>0)   
               b = stack.pop();   
           if(stack.size()>0)   
               a = stack.pop();   
           stack.push(Op.cal(a, b, s)+"");   
       }   
       return Double.valueOf(stack.pop());   
   }   
      
   public Double calculate(String expression){   
       List<String> ss = resolveExpr(expression);   
       for(String s:ss){   
           if(Op.isSign(s)){   
               dealSign(s);   
           }else{   
               dealVar(s);   
           }   
       }   
       while(stack.size()>0){   
           list.add(stack.pop());   
       }   
       System.out.println(list);   
          
       return getResult();   
   }   
      
      
 
      
   public static void main(String[] args) {   
       System.out.println(new Calculator().calculate("1.5+2.1+((4/2)-6/((2+1)*2))+6%4"));   
   }   
}
