package javay.test.xml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * @author Alexia
 * 
 * Dom4j 解析XML文档
 */
public class Dom4jDemo implements XmlDocument {

    public void parserXml(String fileName) {
        File inputXml = new File(fileName);
        SAXReader saxReader = new SAXReader();

        try {
            Document document = saxReader.read(inputXml);
            Element users = document.getRootElement();
            for (Iterator i = users.elementIterator(); i.hasNext();) {
                Element user = (Element) i.next();
                for (Iterator j = user.elementIterator(); j.hasNext();) {
                    Element node = (Element) j.next();
                    System.out.println(node.getName() + ":" + node.getText());
                }
                System.out.println();
            }
        } catch (DocumentException e) {
            System.out.println(e.getMessage());
        }
    }
    public void build01(){  
        try {  
            //DocumentHelper提供了创建Document对象的方法  
            Document document = DocumentHelper.createDocument();  
            //添加节点信息  
            Element rootElement = document.addElement("modules");  
            //这里可以继续添加子节点，也可以指定内容  
            rootElement.setText("这个是module标签的文本信息");  
            Element element = rootElement.addElement("module");  
              
            Element nameElement = element.addElement("name");  
            Element valueElement = element.addElement("value");  
            Element descriptionElement = element.addElement("description");  
            nameElement.setText("名称");  
            nameElement.addAttribute("language", "java");//为节点添加属性值  
            valueElement.setText("值");  
            valueElement.addAttribute("language", "c#");  
            descriptionElement.setText("描述");  
            descriptionElement.addAttribute("language", "sql server");  
            System.out.println(document.asXML()); //将document文档对象直接转换成字符串输出  
            Writer fileWriter = new FileWriter("./conf/module.xml");  
            //dom4j提供了专门写入文件的对象XMLWriter  
            XMLWriter xmlWriter = new XMLWriter(fileWriter);  
            xmlWriter.write(document);  
            xmlWriter.flush();  
            xmlWriter.close();  
            System.out.println("xml文档添加成功！");  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }
}