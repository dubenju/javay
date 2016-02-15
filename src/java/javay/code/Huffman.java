package javay.code;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
/**
 * Huffman编码介绍
Huffman编码处理的是字符以及字符对应的二进制的编码配对问题，分为编码和解码，目的是压缩字符对应的二进制数据长度。我们知道字符存贮和传输的时候都是二进制的(计算机只认识0/1)，那么就有字符与二进制之间的mapping关系。字符属于字符集(Charset), 字符需要通过编码(encode)为二进制进行存贮和传输，显示的时候需要解码(decode)回字符，字符集与编码方法是一对多关系(Unicode可以用UTF-8,UTF-16等编码)。理解了字符集，编码以及解码，满天飞的乱码问题也就游刃而解了。以英文字母小写a为例, ASCII编码中，十进制为97，二进制为01100001。ASCII的每一个字符都用8个Bit(1Byte)编码，假如有1000个字符要传输，那么就要传输8000个Bit。问题来了，英文中字母e的使用频率为12.702%，而z为0.074%，前者是后者的100多倍，但是确使用相同位数的二进制。可以做得更好，方法就是可变长度编码，指导原则就是频率高的用较短的位数编码，频率低的用较长位数编码。Huffman编码算法就是处理这样的问题。
 * @author dubenju
 *
 */
public class Huffman {

	/**
	 * Huffman编码算法主要用到的数据结构是完全二叉树(full binary tree)和优先级队列。后者用的是java.util.PriorityQueue，前者自己实现(都为内部类)
	 * @author dubenju
	 *
	 */
static class Tree {  
    private Node root;  
    
    public Node getRoot() {  
        return root;  
    }  

    public void setRoot(Node root) {  
        this.root = root;  
    }  
}  

static class Node implements Comparable<Node> {  
    private String chars = "";  
    private int frequence = 0;  
    private Node parent;  
    private Node leftNode;  
    private Node rightNode;  

    @Override  
    public int compareTo(Node n) {  
        return frequence - n.frequence;  
    }  

    public boolean isLeaf() {  
        return chars.length() == 1;  
    }  

    public boolean isRoot() {  
        return parent == null;  
    }  

    public boolean isLeftChild() {  
        return parent != null && this == parent.leftNode;  
    }  

    public int getFrequence() {  
        return frequence;  
    }  

    public void setFrequence(int frequence) {  
        this.frequence = frequence;  
    }  

    public String getChars() {  
        return chars;  
    }  

    public void setChars(String chars) {  
        this.chars = chars;  
    }  

    public Node getParent() {  
        return parent;  
    }  

    public void setParent(Node parent) {  
        this.parent = parent;  
    }  

    public Node getLeftNode() {  
        return leftNode;  
    }  

    public void setLeftNode(Node leftNode) {  
        this.leftNode = leftNode;  
    }  

    public Node getRightNode() {  
        return rightNode;  
    }  

    public void setRightNode(Node rightNode) {  
        this.rightNode = rightNode;  
    }
}
/**
 * 统计数据
既然要按频率来安排编码表，那么首先当然得获得频率的统计信息。我实现了一个方法处理这样的问题。如果已经有统计信息，那么转为Map<Character,Integer>即可。如果你得到的信息是百分比，乘以100或1000，或10000。总是可以转为整数。比如12.702%乘以1000为12702，Huffman编码只关心大小问题。
 * @param charArray
 * @return
 */
public static Map<Character, Integer> statistics(char[] charArray) {  
    Map<Character, Integer> map = new HashMap<Character, Integer>();  
    for (char c : charArray) {  
        Character character = new Character(c);  
        if (map.containsKey(character)) {  
            map.put(character, map.get(character) + 1);  
        } else {  
            map.put(character, 1);  
        }  
    }  

    return map;  
}
/**
 * 构建树
构建树是Huffman编码算法的核心步骤。思想是把所有的字符挂到一颗完全二叉树的叶子节点，任何一个非页子节点的左节点出现频率不大于右节点。算法为把统计信息转为Node存放到一个优先级队列里面，每一次从队列里面弹出两个最小频率的节点，构建一个新的父Node(非叶子节点), 字符内容刚弹出来的两个节点字符内容之和，频率也是它们的和，最开始的弹出来的作为左子节点，后面一个作为右子节点，并且把刚构建的父节点放到队列里面。重复以上的动作N-1次，N为不同字符的个数(每一次队列里面个数减1)。结束以上步骤，队列里面剩一个节点，弹出作为树的根节点。
 * @param statistics
 * @param leafs
 * @return
 */
private static Tree buildTree(Map<Character, Integer> statistics,  
        List<Node> leafs) {  
    Character[] keys = statistics.keySet().toArray(new Character[0]);  

    PriorityQueue<Node> priorityQueue = new PriorityQueue<Node>();  
    for (Character character : keys) {  
        Node node = new Node();  
        node.chars = character.toString();  
        node.frequence = statistics.get(character);  
        priorityQueue.add(node);  
        leafs.add(node);  
    }  

    int size = priorityQueue.size();  
    for (int i = 1; i <= size - 1; i++) {  
        Node node1 = priorityQueue.poll();  
        Node node2 = priorityQueue.poll();  

        Node sumNode = new Node();  
        sumNode.chars = node1.chars + node2.chars;  
        sumNode.frequence = node1.frequence + node2.frequence;  

        sumNode.leftNode = node1;  
        sumNode.rightNode = node2;  

        node1.parent = sumNode;  
        node2.parent = sumNode;  

        priorityQueue.add(sumNode);  
    }  

    Tree tree = new Tree();  
    tree.root = priorityQueue.poll();  
    return tree;  
}
/**
 * 编码
某个字符对应的编码为，从该字符所在的叶子节点向上搜索，如果该字符节点是父节点的左节点，编码字符之前加0，反之如果是右节点，加1，直到根节点。只要获取了字符和二进制码之间的mapping关系，编码就非常简单。
 * @param originalStr
 * @param statistics
 * @return
 */
public static String encode(String originalStr,  
        Map<Character, Integer> statistics) {  
    if (originalStr == null || originalStr.equals("")) {  
        return "";  
    }  

    char[] charArray = originalStr.toCharArray();  
    List<Node> leafNodes = new ArrayList<Node>();  
    buildTree(statistics, leafNodes);  
    Map<Character, String> encodInfo = buildEncodingInfo(leafNodes);  

    StringBuffer buffer = new StringBuffer();  
    for (char c : charArray) {  
        Character character = new Character(c);  
        buffer.append(encodInfo.get(character));  
    }  

    return buffer.toString();  
}
private static Map<Character, String> buildEncodingInfo(List<Node> leafNodes) {  
    Map<Character, String> codewords = new HashMap<Character, String>();  
    for (Node leafNode : leafNodes) {  
        Character character = new Character(leafNode.getChars().charAt(0));  
        String codeword = "";  
        Node currentNode = leafNode;  

        do {  
            if (currentNode.isLeftChild()) {  
                codeword = "0" + codeword;  
            } else {  
                codeword = "1" + codeword;  
            }  

            currentNode = currentNode.parent;  
        } while (currentNode.parent != null);  

        codewords.put(character, codeword);  
    }  

    return codewords;  
}
/**
 * 解码
因为Huffman编码算法能够保证任何的二进制码都不会是另外一个码的前缀，解码非常简单，依次取出二进制的每一位，从树根向下搜索，1向右，0向左，到了叶子节点(命中)，退回根节点继续重复以上动作。
 * @param binaryStr
 * @param statistics
 * @return
 */
public static String decode(String binaryStr,  
        Map<Character, Integer> statistics) {  
    if (binaryStr == null || binaryStr.equals("")) {  
        return "";  
    }  

    char[] binaryCharArray = binaryStr.toCharArray();  
    LinkedList<Character> binaryList = new LinkedList<Character>();  
    int size = binaryCharArray.length;  
    for (int i = 0; i < size; i++) {  
        binaryList.addLast(new Character(binaryCharArray[i]));  
    }  

    List<Node> leafNodes = new ArrayList<Node>();  
    Tree tree = buildTree(statistics, leafNodes);  

    StringBuffer buffer = new StringBuffer();  

    while (binaryList.size() > 0) {  
        Node node = tree.root;  

        do {  
            Character c = binaryList.removeFirst();  
            if (c.charValue() == '0') {  
                node = node.leftNode;  
            } else {  
                node = node.rightNode;  
            }  
        } while (!node.isLeaf());  

        buffer.append(node.chars);  
    }  

    return buffer.toString();  
}
/**
 * 测试以及比较
以下测试Huffman编码的正确性(先编码，后解码，包括中文)，以及Huffman编码与常见的字符编码的二进制字符串比较。
 * @param args
 */
public static void main(String[] args) {  
    String oriStr = "Huffman codes compress data very effectively: savings of 20% to 90% are typical, "  
            + "depending on the characteristics of the data being compressed. 中华崛起";  
    Map<Character, Integer> statistics = statistics(oriStr.toCharArray());  
    String encodedBinariStr = encode(oriStr, statistics);  
    String decodedStr = decode(encodedBinariStr, statistics);  

    System.out.println("Original sstring: " + oriStr);  
    System.out.println("Huffman encoed binary string: " + encodedBinariStr);  
    System.out.println("decoded string from binariy string: " + decodedStr);  

    System.out.println("binary string of UTF-8: "  
            + getStringOfByte(oriStr, Charset.forName("UTF-8")));  
    System.out.println("binary string of UTF-16: "  
            + getStringOfByte(oriStr, Charset.forName("UTF-16")));  
    System.out.println("binary string of US-ASCII: "  
            + getStringOfByte(oriStr, Charset.forName("US-ASCII")));  
    System.out.println("binary string of GB2312: "  
            + getStringOfByte(oriStr, Charset.forName("GB2312")));  
}  

public static String getStringOfByte(String str, Charset charset) {  
    if (str == null || str.equals("")) {  
        return "";  
    }  

    byte[] byteArray = str.getBytes(charset);  
    int size = byteArray.length;  
    StringBuffer buffer = new StringBuffer();  
    for (int i = 0; i < size; i++) {  
        byte temp = byteArray[i];  
        buffer.append(getStringOfByte(temp));  
    }  

    return buffer.toString();  
}  

public static String getStringOfByte(byte b) {  
    StringBuffer buffer = new StringBuffer();  
    for (int i = 7; i >= 0; i--) {  
        byte temp = (byte) ((b >> i) & 0x1);  
        buffer.append(String.valueOf(temp));  
    }  

    return buffer.toString();  
}
}