package javay.jvm.cpinfo;

public abstract class AbstractCpInfo {
	
	private byte[] info;
	private int infoLength;
//	private int infoLength = getInfoLength();
	public abstract int getInfoLength();
	public abstract int getTags();
	public AbstractCpInfo(int length) {
		infoLength = length;
	}
}
