package javay.test.xml;

public class TextXml {

	public static void main(String[] args) {
		Dom4jDemo demo = new Dom4jDemo();
		demo.parserXml("./conf/test.xml");
		demo.build01();
	}

}
