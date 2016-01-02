package javay.state;

public class Door2Test {
	public static void main(String[] args){
    	Door2 door=new Door2();

    	//1. 初始状态
    	System.out.println(door.status());

    	//2. 转移到Opening状态
    	door.touch();
    	System.out.println(door.status());

    	//3. 转移到Open状态
    	door.complete();
    	System.out.println(door.status());

    	//4. 转移到Closing状态
    	door.timeout();
    	System.out.println(door.status());

    	//5. 回到Closed状态
    	door.complete();
    	System.out.println(door.status());
    }

}
