/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javatest1;
import java.io.IOException; 
/**
 *
 * @author jiewdue
 */
public class JavaTest1 {

    /**
     * @param args the command line arguments
     */

    public static void main(String[] args) throws IOException {
        //Step 1. Load input 
        Input input = new Input();
        input.loadXml("xml/firstuml.xml"); // return object for all decision point ? 
        
        // Step 2. Create CCTM for every decision point 
        CCTM cctm = new CCTM();
        cctm.createCCTM("TODO");
        
        // Step 3. create test case table
        testCaseTable testCase = new testCaseTable();
        testCase.generateTestCaseTable("TODO");
        
        // Step 4. create tree diagram 
        treeDiagram tree = new treeDiagram();
        tree.generateTreeDiagram("TODO");
        
        // Step 5. Display 
        Output output = new Output();
        output.display("TODO");

    }
}
