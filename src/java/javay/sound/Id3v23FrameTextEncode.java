package javay.sound;

public enum Id3v23FrameTextEncode {
	ISO88591("ISO-8859-1", 0),
	UTF16LE("UTF-16LE", 1),
	UTFF16BE("UTF-16BE", 2),
	UTF8("UTF-8", 3);

    // 成员变量
    private String name;
    private int index;

    // 构造方法
    private Id3v23FrameTextEncode(String name, int index) {
        this.name = name;
        this.index = index;
    }

    // 普通方法
    public static String getName(int index) {
    	System.out.println("index=" + index);
    	String res = null;
        for (Id3v23FrameTextEncode c : Id3v23FrameTextEncode.values()) {
            if (c.getIndex() == index) {
                res = c.name;
                break;
            }
        }
        System.out.println("name=" + res);
        return res;
    }

    // get set 方法
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
