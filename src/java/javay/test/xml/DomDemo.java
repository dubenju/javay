package javay.test.xml;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author Alexia
 * 
 * DOM 解析XML文档
 */
public class DomDemo implements XmlDocument {
  private Document document;

  public void parserXml(String fileName) {
    try {
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder db = dbf.newDocumentBuilder();
      Document document = db.parse(fileName);
      NodeList users = document.getChildNodes();
      
      for (int i = 0; i < users.getLength(); i++) {
        Node user = users.item(i);
        NodeList userInfo = user.getChildNodes();
        
        for (int j = 0; j < userInfo.getLength(); j++) {
          Node node = userInfo.item(j);
          NodeList userMeta = node.getChildNodes();
          
          for (int k = 0; k < userMeta.getLength(); k++) {
            if(userMeta.item(k).getNodeName() != "#text")
              System.out.println(userMeta.item(k).getNodeName()
                  + ":" + userMeta.item(k).getTextContent());
          }
          
          System.out.println();
        }
      }
      
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (ParserConfigurationException e) {
      e.printStackTrace();
    } catch (SAXException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
