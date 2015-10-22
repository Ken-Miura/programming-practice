package ch10.ex10_01;

public class EscapeSequenceReplacement {
	private static final String BS = "\\";
	
	public static void main(String[] args) {  
		String test = "test\ntest";  
		System.out.println(test);  
		System.out.println(replaceEscapeSequences(test));
	}
	
	
	private static String replaceEscapeSequences(String source) {  
		int length = source.length();  
		for (int i = 0; i < length; i++) {   
			if (source.substring(i, i + 1).equals("\b")) {    
				source = source.replace("\b", BS + "b");   
			} else if (source.substring(i, i + 1).equals("\t")) {
				source = source.replace("\t", BS + "t");   
			} else if (source.substring(i, i + 1).equals("\n")) {    
				source = source.replace("\n", BS + "n");
			} else if (source.substring(i, i + 1).equals("\f")) {
				source = source.replace("\f", BS + "f");
			} else if (source.substring(i, i + 1).equals("\r")) {    
				source = source.replace("\r", BS + "r");
			} else if (source.substring(i, i + 1).equals("\"")) {
				source = source.replace("\"", BS + "\"");
			} else if (source.substring(i, i + 1).equals("\'")) {
				source = source.replace("\'", BS + "'");   
			} else if (source.substring(i, i + 1).equals("\\")) {    
				source = source.replace("\\", BS + BS);    i++;
			} else {    
				// do nothing
			}
		}
		return source;
	}
}
