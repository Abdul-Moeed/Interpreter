package ap_lab2;

import java.io.IOException;
import java.util.HashMap;

/**
 *
 * @author Abdul Moeed
 */
public class Tests {
    public void unit_test_1() throws IOException{
        Reader fr = new Reader();
        fr.read_file("unit_test_1.txt");
        //fr.dump_file();
        Interpreter intr = new Interpreter();
        intr.variables = new HashMap<String, Float>();
        intr.exec_program(fr);
    }
    
    public void unit_test_2() throws IOException{
        Reader fr = new Reader();
        fr.read_file("unit_test_2.txt");
        //fr.dump_file();
        Interpreter intr = new Interpreter();
        intr.variables = new HashMap<String, Float>();
        intr.exec_program(fr);
    }
    
    public void unit_test_3() throws IOException{
        Reader fr = new Reader();
        fr.read_file("unit_test_3.txt");
        //fr.dump_file();
        Interpreter intr = new Interpreter();
        intr.variables = new HashMap<String, Float>();
        intr.exec_program(fr);
    }
}
