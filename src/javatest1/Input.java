/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javatest1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Z510
 */
public class Input {
    public void loadXml(String filename) throws IOException {

        DocumentBuilderFactory factory =  DocumentBuilderFactory.newInstance();
        
        //Get the DOM Builder
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(JavaTest1.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Load and Parse the XML document
        //document contains the complete XML as a Tree.
        Document document = null;
        try {
            document = builder.parse(ClassLoader.getSystemResourceAsStream(filename));
        } catch (SAXException ex) {
            Logger.getLogger(JavaTest1.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<Employee> empList = new ArrayList<>();
        //Iterating through the nodes and extracting the data.
        
        NodeList nodeList = document.getDocumentElement().getChildNodes();
        //System.out.println(nodeList.getLength());
        for (int i = 0; i < nodeList.getLength(); i++) {
            //We have encountered an <employee> tag.
            Node node = nodeList.item(i);
            if (node instanceof Element) {
                Employee emp = new Employee();
                //emp.id = node.getAttributes().getNamedItem("id").getNodeValue();
                NodeList childNodes = node.getChildNodes();
                //System.out.println(childNodes.getLength());
                //printXmiElement(node);
                //if (resultNode != null){
                //    System.out.println(resultNode.getAttributes().getNamedItem("xmi:id").getNodeValue());
                //}
                
                for (int j = 0; j < childNodes.getLength(); j++) {
                    Node cNode = childNodes.item(j);
                    //Identifying the child tag of employee encountered.
                    if (cNode instanceof Element) {
                        //System.out.println("first Child :" + cNode.getFirstChild().getTextContent());
                        //System.out.println("last Child :" + cNode.getLastChild().getTextContent());
                        //System.out.println("Local name :" + cNode.getLocalName());
                        //System.out.println("Node name :" + cNode.getNodeName());
                        
                                         //printXmiElement(cNode);

                        //Node firstNode = childNodes.item(0).getFirstChild();
                        System.out.println("cNode child id = " + cNode.getAttributes().getNamedItem("xmi:id").getNodeValue());
                        Node resultNode = null;
                        try {
                            resultNode = searchTargetNode(cNode,childNodes );
                            System.out.println("result child id = " +resultNode.getAttributes().getNamedItem("xmi:id").getNodeValue());
                            break;
                        }catch (Exception e){
                            System.out.println("Not Found");
                        }
                    }
                }
            //empList.add(emp);
            }

        }
    //for (Employee emp : empList) {
    //  System.out.println(emp);
    //}

    }
    private static void printXmiElement (Node node){
        NamedNodeMap attr = node.getAttributes();
        for (int j = 0; j < attr.getLength(); j++) {
            System.out.println("Node name = " + attr.item(j).getNodeName());
            System.out.println("Node Value= " + attr.item(j).getNodeValue());
        }
    }
    private static Node searchTargetNode(Node sourceNode, NodeList nodeList){
        Node targetNode = null;
        String target_id ="";
        //System.out.println(childNodes.getLength());
        //printXmiElement(node);
        try {
            target_id = sourceNode.getAttributes().getNamedItem("target").getNodeValue();
            //System.out.println("Target id to search= " + target_id);
        }catch (Exception e){
            try{
                target_id = sourceNode.getAttributes().getNamedItem("outgoing").getNodeValue();
            }
            catch (Exception e1){
                return null;
            }
        }
            
       
        for (int j = 0; j < nodeList.getLength(); j++) {
            Node cNode = nodeList.item(j);
            //Identifying the child tag of employee encountered.
            if (cNode instanceof Element) {
                String source_id = new String(cNode.getAttributes().getNamedItem("source").getNodeValue());
                //System.out.println("Source id= " + source_id);
                boolean result = source_id.equals(target_id);
                //System.out.println("result = " + result);
                if (result == true){
                    targetNode = cNode;
                    break;
                }
            }
        }
        return targetNode;
    }
}
