/**
 * Created by Ian on 4/7/2018.
 */
public class Id extends ArithmeticExpression{
    char c;

    public Id(char c) {
        this.c = c;
    }
    
    public void getChar(){
    	System.out.println(c);
    }
}
