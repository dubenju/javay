package javay.jvm.cpinfo;

/*
 *  7CONSTANT_Class_info 12
 *  3CONSTANT_Integer_info 14
 *  5CONSTANT_Long_info 144
 *  4CONSTANT_Float_info 14
 *  6CONSTANT_Double_info 144
 *  8CONSTANT_String_info 12
 *  9CONSTANT_Fieldref_info 122
 * 10CONSTANT_Methodref_info122
 * 11CONSTANT_InterfaceMethodref_info 122
 * 12CONSTANT_NameAndType_info 122
 * 1CONSTANT_Utf8_info 121
 * 15CONSTANT_MethodHandle1?
 * 16CONSTANT_MethodType1?
 * 18CONSTANT_InvokeDynamic1?
 */
public class CpiUtf8 extends AbstractCpInfo {
	private byte tag;
	private int infoLength;
	public CpiUtf8(byte tag, int length) {
		super(length);
		this.tag = tag;
		infoLength = length;
	}
//	@Override
	public int getInfoLength() {
		return this.infoLength;
	}
	@Override
	public int getTags() {
		return this.tag;
	}

}
