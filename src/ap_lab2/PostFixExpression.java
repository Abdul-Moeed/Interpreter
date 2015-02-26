package ap_lab2;

import java.util.Arrays;

/**
* This class converts a given infix expression to postfix and then evaluates it
* @author Abdul Moeed
*
*/
public class PostFixExpression {

	private String infix;
        
	public void PostFixExpression_setter(String s){
		infix=s;
	}
	
	public String getPostFix(){
		//TODO implement this method
		boolean first=true;
		StringBuilder postfix = new StringBuilder();
		int length = infix.length(),breakcode=0;
		StackArray exp = new StackArray(length);
		char current,current1,current2;
		for(int i=0;i<length;i++){
			if(!first){
				if(!Character.isDigit(infix.charAt(i)) && infix.charAt(i)!='.'){
					postfix.append(" ");
					first=true;
				}
			}
			current=infix.charAt(i);
			if(current==')'){
                             current1=exp.pop();
				while(current1!='(')
                {
					postfix.append(current1);
                    current1=exp.pop();
                }
			}
			else if(current=='+'||current=='-'||current=='*'||current=='/'){
				if(current=='/'){
					exp.push(current);
				}
				else if(current=='*'){
					if(exp.isEmpty()){
						exp.push(current);
					}
					else {
						while(!exp.isEmpty() && breakcode==0){
							current2=exp.pop();
							if(current2=='/'){
								postfix.append(current2);
							}
							else{
								exp.push(current2);
								exp.push(current);
								breakcode=1;
							}	
						}
						if(breakcode==0)
							exp.push(current);
					}
				}
				else if(current=='-'){
					if(exp.isEmpty()){
						exp.push(current);
					}
					else {
						while(!exp.isEmpty() && breakcode==0){
							current2=exp.pop();
							if(current2=='/'){
								postfix.append(current2);
							}
							else if(current2=='*')
								postfix.append(current2);
							else{
								exp.push(current2);
								exp.push(current);
								breakcode=1;
							}	
						}
						if(breakcode==0)
							exp.push(current);
					}
				}
				else if(current=='+'){
					if(exp.isEmpty()){
						exp.push(current);
					}
					else {
						while(!exp.isEmpty() && breakcode==0){
							current2=exp.pop();
							if(current2=='/'){
								postfix.append(current2);
							}
							else if(current2=='*')
								postfix.append(current2);
							else{
								exp.push(current2);
								exp.push(current);
								breakcode=1;
							}	
						}
						if(breakcode==0)
							exp.push(current);
					}
				}
				breakcode=0;
				
			}
			else if(current=='('){
                 exp.push(current);}
				
			else{
				if(first){
					first=false;
					postfix.append(" ");
				}
				postfix.append(current);
				
			}
				
		}
		return postfix.toString();
	}
	
	public float postFixValue(String s){
            
		//TODO implement this method
		StackArray numStack= new StackArray(s.length());
		char current;
		float operand1,operand2;
		for(int i=0;i<s.length();i++){
			current= s.charAt(i);
			if(current==' '){
				String value="";
				i++;
				while(Character.isDigit(s.charAt(i)) || s.charAt(i)=='.'){
					value+=s.charAt(i);
					i++;
				}
				numStack.push_num(Float.parseFloat(value));		
			}
			//if(Character.isDigit(current))
				//numStack.push_num((int)current-48);
			else {
				operand2=numStack.pop_num();
				operand1=numStack.pop_num();
				if(current=='+'){
					numStack.push_num(operand1+operand2);
				}
				else if(current=='-'){
					numStack.push_num(operand1-operand2);
				}
				else if(current=='*'){
					numStack.push_num(operand1*operand2);
				}
				else if(current=='/'){
					numStack.push_num(operand1/operand2);
				}
			}
				
		}
		return numStack.pop_num();
	}

	public class StackArray {

		private char s[];
                int N, top, top1;
		private float arr[];

		public StackArray(int num) {
			N = num;
			s = new char[N];
			arr = new float[N];
			top = top1= 0;
		}

		public void push(char item) {
			if (top == N) {
				System.out.println("cannot push");
			} else
				s[top++] = item;
		}
		public void push_num(float item) {
			if (top1 == N) {
				System.out.println("cannot push num");
			} else
				arr[top1++] = item;
		}

		public char pop() {
			if(isEmpty()){
				System.out.println("Canot pop, Stack Empty!");
				return 'c';
			} else {
				char temp = s[--top];
				s[top] = 0;
				return temp;
			}
		}
			
		public float pop_num() {
				if(isEmpty_num()){
					System.out.println("Canot pop num, Stack Empty!");
					return 1;
				} else {
					float temp = arr[--top1];
					arr[top1] = 0;

					return temp;
				}
		}

		public boolean isEmpty(){
			return top == 0;
		}
		
		public boolean isFull(){
			return top == N;
		}
		
		public boolean isEmpty_num(){
			return top1 == 0;
		}
		
		public boolean isFull_num(){
			return top1 == N;
		}
		@Override
		public String toString() {
			return "StackArray [s=" + Arrays.toString(s) + ", top=" + top + "]";
		}
}
}