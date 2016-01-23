package javay.xml;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.Iterator;

public class XmlbyDom4j {

  /**
   * parserXml.
   * @param fileName String
   */
  public static void parserXml(String fileName) {
    File inputXml = new File(fileName);
    SAXReader saxReader = new SAXReader();

    try {
      Document document = saxReader.read(inputXml);
      Element users = document.getRootElement();
      for (Iterator<Element> i = users.elementIterator(); i.hasNext();) {
        Element user = (Element) i.next();
        for (Iterator<Element> j = user.elementIterator(); j.hasNext();) {
          Element node = (Element) j.next();
          System.out.println(node.getName() + ":" + node.getText());
        }
        System.out.println();
      }
    } catch (DocumentException e) {
      System.out.println(e.getMessage());
    }
  }
}
