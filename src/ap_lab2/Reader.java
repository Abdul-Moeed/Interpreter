package ap_lab2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/** 
 * @author Abdul Moeed
 */
public class Reader {
    
    //class attributes
    int total_lines; //lines in file
    ArrayList<String> instructions; //operations derived from expression(if any)
    
    /*file reader for automated unit tests.
    Does not get any expression just data*/
    public void read_file(String path) throws FileNotFoundException, IOException {
        FileReader fr = new FileReader(path);
        String line;
        //get each line
        try (BufferedReader buffer = new BufferedReader(fr)) {
            total_lines = 0;
            instructions = new ArrayList<String>();
            
            while( (line = buffer.readLine()) != null){
                instructions.add(line);
                total_lines++;
            }
        }
    }
    
    public void dump_file() {
        for(int i=0;i<total_lines;i++) {
            System.out.println(instructions.get(i));
        }
    }
}
