package javay.test.java;

public class FooB extends FooA {

	public static void main(String[] args) {
		FooB.fb.setId(1);
		FooB.fb.setName("test");
		System.out.println(FooB.fb.getId() + FooB.fb.getName());
	}
}
