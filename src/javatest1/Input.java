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
import java.util.ArrayList;
/**
 *
 * @author Z510
 */
public class Input {
    private NodeList rawInputNodes;
    private ArrayList<XmiNode> xmiNodes = new ArrayList<XmiNode> ();
    ArrayList<NodeInfo> nodes_info = new ArrayList<NodeInfo>  ();
    public void openXml(String filename) throws IOException {

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
        //Iterating through the nodes and extracting the data.
        
        NodeList nodeList = document.getDocumentElement().getChildNodes();
        //System.out.println(nodeList.getLength());
        for (int i = 0; i < nodeList.getLength(); i++) {
            //We have encountered an <employee> tag.
            Node node = nodeList.item(i);
            if (node instanceof Element) {
                NodeList childNodes = node.getChildNodes();
                //printXmiElement(node);

                rawInputNodes = childNodes;
                for (int j = 0; j < childNodes.getLength(); j++) {
                    Node cNode = childNodes.item(j);
                    //Identifying the child tag of employee encountered.
                    if (cNode instanceof Element) {
                        XmiNode temp_node = new XmiNode(cNode);

                        boolean add = xmiNodes.add(temp_node);

                    }
                }
            }
        }
        populateSourceAndTargetNode();
        for (NodeInfo node_info : nodes_info){
            System.out.println("Node name = " + node_info.name);
            for (String name : node_info.sources_name){
                System.out.println("Source name = " + name);
            }
            for (String id : node_info.sources_id){
                System.out.println("Source id = " + id);
            }
            for (String name : node_info.targets_id){
                System.out.println("target name = " + name);
            }
            for (String id : node_info.targets_name){
                System.out.println("target id = " + id);
            }
            System.out.println("---------------------------------------");
        }
        
    }
    private static void printXmiElement (Node node){
        NamedNodeMap attr = node.getAttributes();
        for (int j = 0; j < attr.getLength(); j++) {
            System.out.println("Node name = " + attr.item(j).getNodeName());
            System.out.println("Node Value= " + attr.item(j).getNodeValue());
        }
    }
    private XmiNode searchTargetNode(String target_id){
        XmiNode targetNode = null;
        for (XmiNode node : xmiNodes){
            if (target_id.equals(node.id)){
                if (node.isNode){
                    // found it
                    targetNode = node;
                }
                else{
                    // if not node, it's flow. there is only one target
                    targetNode = searchTargetNode(node.targets.get(0));
                }
                break;
            }
        }
        return targetNode;
    }
    private ArrayList<XmiNode> searchTargetsNode(XmiNode inputNode){
        ArrayList<XmiNode>  targetsNode = new ArrayList<XmiNode> ();

        for (String target_id : inputNode.targets){
            XmiNode result = searchTargetNode(target_id);
            if (result != null)
            {
                targetsNode.add(result);
            }
            
        }
        return targetsNode;
    }
    private void populateSourceAndTargetNode(){
        XmiNode firstNode = xmiNodes.get(0);
      
        for (XmiNode node : xmiNodes){
            if (node.isNode){
                ArrayList<XmiNode> sources_nodes = searchSourcesNode(node);
                ArrayList<XmiNode> targets_nodes = searchTargetsNode(node);
                
                NodeInfo temp_nodeInfo = new NodeInfo();
                temp_nodeInfo.name = node.name;
                for (XmiNode target_node : targets_nodes){
                    temp_nodeInfo.targets_name.add(target_node.name);
                    temp_nodeInfo.targets_id.add(target_node.id);
                }
                for (XmiNode source_nodes : sources_nodes){
                    temp_nodeInfo.sources_name.add(source_nodes.name);
                    temp_nodeInfo.sources_id.add(source_nodes.id);
                }
                nodes_info.add(temp_nodeInfo);
            }
        }
        
    }
    private XmiNode searchSourceNode(String target_id){
        XmiNode targetNode = null;
        for (XmiNode node : xmiNodes){
            if (target_id.equals(node.id)){
                if (node.isNode){
                    // found it
                    targetNode = node;
                }
                else{
                    // if not node, it's flow. there is only one target
                    targetNode = searchTargetNode(node.sources.get(0));
                }
                break;
            }
        }
        return targetNode;
    }
    private ArrayList<XmiNode> searchSourcesNode(XmiNode inputNode){
        ArrayList<XmiNode>  targetsNode = new ArrayList<XmiNode> ();

        for (String target_id : inputNode.sources){
            XmiNode result = searchSourceNode(target_id);
            if (result != null)
            {
                targetsNode.add(result);
            }
            
        }
        return targetsNode;
    }
    
}
