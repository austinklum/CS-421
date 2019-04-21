///**
// * Simple parsing routine for the CS 421/521 Assignment 04 programming language.
// * 
// * @author M. Allen
// */
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.LinkedList;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class Parser
//{
//    private final static String methodRegex = "(int|void)\\s+[a-z]+\\s*\\(.*\\)\\s*\\{.*\\}.*";
//    private final static String programRegex = String.format("(.*)(%s)", methodRegex);
//    private final static Pattern programPattern = Pattern.compile(programRegex);
//
//    /**
//     * Divides a program in the specified language into component parts (some of
//     * which may be empty). Prints an enumeration of those components after
//     * parsing to separate them.
//     *
//     * @param args Command line arguments: args[0] should contain the name of an
//     *            available program source-file.
//     */
////    public static void main(String[] args)
////    {
////        Parser parser = new Parser();
////        String programString = parser.readFile(args[0]);
////        String[] parts = parser.getParts(programString);
////        for (int i = 0; i < parts.length; i++)
////        {
////            System.out.printf("%02d: %s\n", i, parts[i]);
////        }
////    }
//
//    /**
//     * Reads a program file into a single string.
//     * 
//     * @param fileName Name of a text-file; presumed to be in program form.
//     * 
//     * @return A single string containing the program file text, with
//     *         line-breaks removed and replaced by spaces, putting the program
//     *         onto a single line.
//     */
//    public static String readFile(String fileName)
//    {
//        StringBuilder builder = new StringBuilder();
//        try
//        {
//            BufferedReader reader = new BufferedReader(new FileReader(fileName));
//            String line = reader.readLine();
//            while (line != null)
//            {
//                builder.append(line);
//                builder.append(' ');
//                line = reader.readLine();
//            }
//            reader.close();
//        }
//        catch (IOException e)
//        {
//            e.printStackTrace();
//        }
//
//        return builder.toString();
//    }
//
//    /**
//     * Divides the text of a program into parts, consisting of the global region
//     * (if any), helper methods (if any), and the main() method (which must
//     * exist, by the definition of the language syntax).
//     * 
//     * @param programText Text of program, as a single line, without breaks.
//     * 
//     * @return An array consisting of the various portions of the program, in
//     *         order (top to bottom in programText).
//     */
//    public static String[] getParts(String programText)
//    {
//        LinkedList<String> parts = new LinkedList<>();
//        Matcher match = programPattern.matcher(programText);
//        while (match.find())
//        {
//            parts.addFirst(match.group(2));
//            programText = match.group(1);
//            match = programPattern.matcher(programText);
//        }
//        parts.addFirst(programText);
//        return parts.toArray(new String[parts.size()]);
//    }
//    
//    public static void print(String[] parts) {
//      for (int i = 0; i < parts.length; i++)
//      {
//          System.out.printf("%02d: %s\n", i, parts[i]);
//      }
//    }
//}

/**
 * Simple parsing routine for the CS 421/521 Assignment 04 programming language.
 * 
 * @author M. Allen
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser
{
    private final static String methodRegex = "(int|void)\\s+[a-z]+\\s*\\(.*\\)\\s*\\{.*\\}.*";
    private final static String programRegex = String.format("(.*)(%s)", methodRegex);
    private final static Pattern programPattern = Pattern.compile(programRegex);

    /**
     * Divides a program in the specified language into component parts (some of
     * which may be empty). Prints an enumeration of those components after
     * parsing to separate them.
     *
     * @param args Command line arguments: args[0] should contain the name of an
     *            available program source-file.
     */
//    public static void main(String[] args)
//    {
//        Parser parser = new Parser();
//        String programString = parser.readFile(args[0]);
//        String[] parts = parser.getParts(programString);
//        for (int i = 0; i < parts.length; i++)
//        {
//            System.out.printf("%02d: %s\n", i, parts[i]);
//        }
//    }

    /**
     * Reads a program file into a single string.
     * 
     * @param fileName Name of a text-file; presumed to be in program form.
     * 
     * @return A single string containing the program file text, with
     *         line-breaks removed and replaced by spaces, putting the program
     *         onto a single line.
     */
    public static String readFile(String fileName)
    {
        StringBuilder builder = new StringBuilder();
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();
            while (line != null)
            {
                builder.append(line);
                builder.append(' ');
                line = reader.readLine();
            }
            reader.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return builder.toString();
    }

    /**
     * Divides the text of a program into parts, consisting of the global region
     * (if any), helper methods (if any), and the main() method (which must
     * exist, by the definition of the language syntax).
     * 
     * @param programText Text of program, as a single line, without breaks.
     * 
     * @return An array consisting of the various portions of the program, in
     *         order (top to bottom in programText).
     */
    public static String[] getParts(String programText, String methodRegex)
    {
    	
    	//private final static String methodRegex = "(int|void)\\s+[a-z]+\\s*\\(.*\\)\\s*\\{.*\\}.*";
        String programRegex = String.format("(.*)(%s)", methodRegex);
        Pattern programPattern = Pattern.compile(programRegex);
    	
        LinkedList<String> parts = new LinkedList<>();
        Matcher match = programPattern.matcher(programText);
        while (match.find())
        {
            parts.addFirst(match.group(2));
            programText = match.group(1);
            match = programPattern.matcher(programText);
        }
        parts.addFirst(programText);
        return parts.toArray(new String[parts.size()]);
    }
    
    public static void print(String[] parts) {
    	for (int i = 0; i < parts.length; i++) {
    		System.out.printf("%02d: %s\n", i, parts[i]);
    	}
    }

}


