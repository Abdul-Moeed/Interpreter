package ap_lab2;

import java.io.IOException;
import java.util.Map;
/**
 *
 * @author Abdul Moeed
 */
public class Interpreter {

    Map <String, Generics> variables;
    
    public static void main(String[] args) throws IOException {
        Tests run_test = new Tests();
        
        //run_test.unit_test_1();
        //run_test.unit_test_2();
        run_test.unit_test_3();
    }
    
    public void exec_program(Reader fr) {
        int ret;
        for(int i=0;i<fr.total_lines;i++) {
            ret = exec_ins(fr.instructions.get(i));
            if(ret == -1){
                System.out.println("Error at line "+(i+1)+": Syntax not correct.");
                break;
            }
            if(ret == -2){
                System.out.println("Error at line "+(i+1)+": Undefined variables found.");
                break;
            }
        }
    }
    
    //executes single line from file. 
    //If instruction fails, returns -1 for syntax error and -2 for undeclared variables
    public int exec_ins(String command) {
        if(check_syntax(command) == -1){
            return -1;
        }
        PostFixExpression exp = new PostFixExpression();
        StringBuilder infix = new StringBuilder();
        String[] tokens = command.split(" ");
        
        //for init line
        if (tokens[0].equalsIgnoreCase("Let")){
            Generics obj = new Generics();
            obj.value = tokens[3];
            
            if(tokens[3].contains(".")) 
                obj.type = "float";
            else if(tokens[3].contains("\"")) 
                obj.type = "string";
            else
                obj.type = "int";   
            variables.put(tokens[1], obj);
        }
        
        //for print line
        else if(tokens[0].equalsIgnoreCase("Print")){
            if(tokens[1].charAt(0)=='"' && tokens[1].charAt(tokens[1].length()-1)=='"'){
                String temp = tokens[1];
                temp = temp.substring(1, temp.length()-1);
                System.out.println(temp);
            }
            else if(!variables.containsKey(tokens[1])) {
                return -2;
            }
            else{
                Generics printable = new Generics();
                printable = variables.get(tokens[1]);
                String stripped = printable.value;
                if(printable.type.equals("string")){
                    stripped = stripped.replaceAll("\"", "");
                }
                System.out.println(stripped);
            }
        }
        
        //for assignment/operation line
        else{
            if(!variables.containsKey(tokens[0]))
                return -2;
            
            boolean is_string = false;
            infix.append("(");
            for(int i=2;i<tokens.length;i++){
                if(tokens[i].equals("+") || tokens[i].equals("-") || tokens[i].equals("/") || tokens[i].equals("*") || tokens[i].equals("(") || tokens[i].equals(")")){
                    infix.append(tokens[i]);
                }
                else if(Character.isDigit(tokens[i].charAt(0))){
                    infix.append(tokens[i]);
                }
                else if(variables.containsKey(tokens[i])) {
                    Generics gen = new Generics();
                    gen = variables.get(tokens[i]);
                    if(gen.type.equals("int")) {
                        infix.append(gen.value);
                        infix.append(".0");
                    }
                    else if(gen.type.equals("float")) {
                        infix.append(gen.value);
                    }
                    else{
                        infix.append(gen.value);
                        is_string = true;
                    }
                }
                else {
                    return -2;
                }
            }
            infix.append(")");            
            String temp = infix.toString();
            Generics gen2 = variables.get(tokens[0]);
            if(is_string){
                temp = temp.replaceAll("\"", "");
                temp = temp.replace("+","");
                temp = temp.replace("(","");
                temp = temp.replace(")","");
                temp = temp.replace(" ","");
                gen2.value = temp;
                variables.put(tokens[0],gen2);
                return 1;
            }
            exp.PostFixExpression_setter(temp);
            float result = exp.postFixValue(exp.getPostFix());
            
            if(gen2.type.equals("int")){
                gen2.value = Integer.toString((int)result);
                variables.put(tokens[0], gen2);
            }
            else {
                gen2.value = Float.toString(result);
                variables.put(tokens[0], gen2);
            }
        }
        return 1;
    }
    
    //checks line for any syntax error. Returns -1 on syntax error and 1 for no error
    public int check_syntax(String command) {
        String[] tokens = command.split(" ");
        //if only one word is written in line
        if(tokens[1] == null) {
                return -1;
        }
        //if init line
        if (tokens[0].equalsIgnoreCase("Let")) {
            if(Character.isDigit(tokens[1].charAt(0))) {
                return -1;
            }
            if(!"=".equals(tokens[2])) {
                return -1;
            }
        }
        //if print line
        else if (tokens[0].equalsIgnoreCase("Print")) {
            //if string
            if(tokens[1].charAt(0)=='"'){
                if(tokens[1].charAt(tokens[1].length()-1) != '"'){
                    return -1;
                }
            }
            if(tokens[1].contains("+") || tokens[1].contains("-") || tokens[1].contains("*") || tokens[1].contains("/")) {
                return -1;
            }
        }
        //if assignment/operation line
        else {
            if(!"=".equals(tokens[1])) {
                return -1;
            }
            if(tokens[0].contains("+") || tokens[0].contains("-") || tokens[0].contains("*") || tokens[0].contains("/")) {
                return -1;
            }
            for(int i=3; i<tokens.length; i++) {
                if(!tokens[i].contains("+") && !tokens[i].contains("-") && !tokens[i].contains("*") && !tokens[i].contains("/")) {
                    return -1;
                }
                i++;
            }
            for(int i=2; i<tokens.length; i++) {
                if(tokens[i].contains("+") || tokens[i].contains("-") || tokens[i].contains("*") || tokens[i].contains("/")) {
                    return -1;
                }
                i++;
            }
        }
        return 1;
    }
}
