package javay.jvm;

import java.util.HashMap;

import javay.util.UBytes;

public class ClassMagic {
	private static final HashMap<Integer, Integer> tagBytes = new HashMap<Integer, Integer>();
	static {
		tagBytes.put(1, 3);
		tagBytes.put(3, 4);
		tagBytes.put(4, 4);
		tagBytes.put(5, 8);
		tagBytes.put(6, 8);
		tagBytes.put(7, 2);
		tagBytes.put(8, 2);
		tagBytes.put(9, 4);
		tagBytes.put(10, 4);
		tagBytes.put(11, 4);
		tagBytes.put(12, 4);
	}
	/** 魔数: 0xCAFEBABE 4 */
	private byte[] maigc;
	/** Class 文件格式版本号：class 文件的主次版本号（the minor and major versions） 2 2*/
	private byte[] minorVersion;
	private byte[] majorVersion;
	/** 常量池（Constant Pool）：包含 class 中的所有常量 2 */
	private byte[] constantPoolCount;
	private int cPlCnt;
	private byte[] constantPool;
	/** 访问标记（Access Flags）：例如该 class 是否为抽象类、静态类，等等。 2 */
	private byte[] accessFlags; // 2
	/** 该类（This Class）：当前类的名称 */
	private byte[] thisClass; // 2
	/** 父类（Super Class）：父类的名称 */
	private byte[] superClass; // 2
	/** 接口（Interfaces）：该类的所有接口 */
	private byte[] interfaceCount; // 2
	private int ifCnt;
	private byte[] interfaces;
	/** 字段（Fields）：该类的所有字段 */
	private byte[] fieldsCount; // 2
	private int fldCnt;
	private byte[] fields;
	/** 方法（Methods）：该类的所有方法 */
	private byte[] methodsCount; // 2
	private int mthdCnt;
	private byte[] methods;
	/** 属性（Attributes）：该类的所有属性（例如源文件名称，等等） */
	private byte[] attributesCount; // 2
	private int attrCnt;
	private byte[] attributes;
	
	public ClassMagic(byte[] in) {
		this.maigc = new byte[4];
		this.minorVersion = new byte[2];
		this.majorVersion = new byte[2];
		this.constantPoolCount = new byte[2];
		int start = 0;
		int length = 4;
		System.arraycopy(in, start, this.maigc, 0, length);
		start += length;
		length = 2;
		System.arraycopy(in, start, this.minorVersion, 0, length);
		start += length;
		length = 2;
		System.arraycopy(in, start, this.majorVersion, 0, length);
		start += length;
		length = 2;
		System.arraycopy(in, start, this.constantPoolCount, 0, length);
		this.cPlCnt = UBytes.toInt(this.constantPoolCount, 2);
		System.out.println("常量池个数:" + this.cPlCnt);
		System.out.println("常量池个数:" + UBytes.toHexString(this.constantPoolCount));

		start += length;
//		length = this.cPlCnt * 2;
//		this.constantPool = new byte[length];
//		System.arraycopy(in, start, this.constantPool, 0, length - 2);
		length = readCP(in, start, this.constantPool, this.cPlCnt);

		this.accessFlags = new byte[2];
		start += length;
		length = 2;
		System.arraycopy(in, start, this.accessFlags, 0, length);
		System.out.print("accessFlags:");
		System.out.println(UBytes.toHexString(this.accessFlags));

		this.thisClass = new byte[2];
		start += length;
		length = 2;
		System.arraycopy(in, start, this.thisClass, 0, length);
		this.superClass = new byte[2];
		start += length;
		length = 2;
		System.arraycopy(in, start, this.superClass, 0, length);
		this.interfaceCount = new byte[2];

		start += length;
		length = 2;
		System.arraycopy(in, start, this.interfaceCount, 0, length);
		this.ifCnt = UBytes.toInt(this.interfaceCount, 2);
		System.out.println("接口个数:" + this.ifCnt);
		System.out.println("接口个数:" + UBytes.toHexString(this.interfaceCount));
		start += length;
		length = this.ifCnt * 2;
		this.interfaces = new byte[length];
		System.arraycopy(in, start, this.interfaces, 0, length);

		this.fieldsCount = new byte[2];
		start += length;
		length = 2;
		System.arraycopy(in, start, this.fieldsCount, 0, length);
		this.fldCnt = UBytes.toInt(this.fieldsCount, 2);
		start += length;
//		length = this.fldCnt;
//		this.fields = new byte[length];
//		System.arraycopy(in, start, this.fields, 0, length);
		length = readFLD(in, start, this.fields, this.fldCnt);

		this.methodsCount = new byte[2];
		start += length;
		length = 2;
		System.arraycopy(in, start, this.methodsCount, 0, length);
		this.mthdCnt = UBytes.toInt(this.methodsCount, 2);
		start += length;
//		length = this.mthdCnt * 2;
//		this.methods = new byte[length];
//		System.arraycopy(in, start, this.methods, 0, length);
		length = readMTHD(in, start, this.methods, this.mthdCnt);

		this.attributesCount = new byte[2];
		start += length;
		length = 2;
		System.arraycopy(in, start, this.attributesCount, 0, length);
		this.attrCnt = UBytes.toInt(this.attributesCount, 2);
		start += length;
		length = this.attrCnt * 2;
		this.attributes = new byte[length];
		System.arraycopy(in, start, this.attributes, 0, length);
		
		System.out.println(in.length + "," + (start + length));
	}

	private int readCP(byte[] in, int start, byte[] constantPool, int cPlCnt) {
		int cnt = 1;
		byte tag;
		int pos = start;
		while(cnt < cPlCnt) {
			if (cnt == 253 || cnt == 563) {
				cnt ++;
				continue;
			}
			tag = in[pos];
			pos ++;
			int len = tagBytes.get((int) tag);
			System.out.println(cnt + ",tag:" + tag + ",len=" + len);
			byte[] dat = new byte[len];
			if (tag == 1) {
				dat = new byte[len - 1];
				System.arraycopy(in, pos, dat, 0, len - 1);
				pos += (len - 1);
				int lenUft8 = UBytes.toInt(dat, 2);
				System.out.print("utf8_len:" + lenUft8);
				byte[] utf8 = new byte[lenUft8];
				System.arraycopy(in, pos, utf8, 0, lenUft8);
				pos += lenUft8;
				System.out.println(",utf8:" + new String(utf8));
			} else {
				System.arraycopy(in, pos, dat, 0, len);
				pos += len;
			}
			cnt ++;
		}
		return pos - start;
	}
	private int readFLD(byte[] in, int start, byte[] constantPool, int cPlCnt) {
		int cnt = 0;
//		byte tag;
		int pos = start;
		while(cnt < cPlCnt) {
//			if (cnt == 253 || cnt == 563) {
//				cnt ++;
//				continue;
//			}
			byte[] access_flags = new byte[2];
			System.arraycopy(in, pos, access_flags, 0, 2);
			pos += 2;
			byte[] name_index = new byte[2];
			System.arraycopy(in, pos, name_index, 0, 2);
			pos += 2;
			byte[] descriptor_index = new byte[2];
			System.arraycopy(in, pos, descriptor_index, 0, 2);
			pos += 2;
			byte[] attributes_count = new byte[2];
			System.arraycopy(in, pos, attributes_count, 0, 2);
			pos += 2;
			int len = UBytes.toInt(attributes_count, 2);
			System.out.println(cnt + "," + UBytes.toHexString(access_flags) + ",name_index=" + 
					UBytes.toHexString(name_index) + ",descriptor_index=" + 
					UBytes.toHexString(descriptor_index) + ",attributes_count=" + 
					UBytes.toHexString(attributes_count) + "," + 
					"len=" + len);
			byte[] dat = new byte[len]; // 2-4-2
			System.arraycopy(in, pos, dat, 0, len);
			pos += len;
			cnt ++;
		}
		return pos - start;
	}
	private int readMTHD(byte[] in, int start, byte[] constantPool, int cPlCnt) {
		int cnt = 0;
//		byte tag;
		int pos = start;
		while(cnt < cPlCnt) {
//			if (cnt == 253 || cnt == 563) {
//				cnt ++;
//				continue;
//			}
			byte[] access_flags = new byte[2];
			System.arraycopy(in, pos, access_flags, 0, 2);
			pos += 2;
			byte[] name_index = new byte[2];
			System.arraycopy(in, pos, name_index, 0, 2);
			pos += 2;
			byte[] descriptor_index = new byte[2];
			System.arraycopy(in, pos, descriptor_index, 0, 2);
			pos += 2;
			byte[] attributes_count = new byte[2];
			System.arraycopy(in, pos, attributes_count, 0, 2);
			pos += 2;
			int len = UBytes.toInt(attributes_count, 2);
			System.out.println(cnt + "," + UBytes.toHexString(access_flags) + ",name_index=" + 
					UBytes.toHexString(name_index) + ",descriptor_index=" + 
					UBytes.toHexString(descriptor_index) + ",attributes_count=" + 
					UBytes.toHexString(attributes_count) + "," + 
					"len=" + len);
			byte[] dat = new byte[len]; // 2-4-2
			System.arraycopy(in, pos, dat, 0, len);
			pos += len;
			cnt ++;
		}
		return pos - start;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append(",magic:");buf.append(UBytes.toHexString(this.maigc));
		buf.append(",minorVersion:");buf.append(UBytes.toHexString(this.minorVersion));
		buf.append(",majorVersion:");buf.append(UBytes.toInt(this.majorVersion, 2));
		buf.append(",constantPoolCount:");buf.append(UBytes.toHexString(this.constantPoolCount));
		buf.append(",accessFlags:");buf.append(UBytes.toHexString(this.accessFlags));
		buf.append(",thisClass:");buf.append(UBytes.toHexString(this.thisClass));
		buf.append(",superClass:");buf.append(UBytes.toHexString(this.superClass));
		buf.append(",interfaceCount:");buf.append(UBytes.toHexString(this.interfaceCount));
		buf.append(",fieldsCount:");buf.append(UBytes.toHexString(this.fieldsCount));
		buf.append(",methodsCount:");buf.append(UBytes.toHexString(this.methodsCount));
		buf.append(",attributesCount:");buf.append(UBytes.toHexString(this.attributesCount));
		return buf.toString();
	}
}
