/**
 * Created by Ian on 3/28/2018.
 */
import java.io.FileNotFoundException;

public class Interpreter
{

    public static void main(String[] args)
    {
    	
        try
        {
            Parser p = new Parser ("Parser/src/test.txt");
            Program prog = p.parse();
            prog.execute();
            
        }
        catch (ParserException e)
        {
            System.out.println ("Parser" + e.getMessage());
        }
        catch (LexicalException e)
        {
            System.out.println ("Lexical" + e.getMessage());
        }
        catch (IllegalArgumentException e)
        {
            System.out.println (e.getMessage());
        }
        catch (FileNotFoundException e)
        {
            System.out.println ("source file is not found");
        }
        catch (Exception e)
        {
            System.out.println ("unknown error occurred - terminating");
        }
    }

}