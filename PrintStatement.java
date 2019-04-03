/**
 * Created by Ian on 4/8/2018.
 */
public class PrintStatement extends Statement{
    ArithmeticExpression expr;
    
    public PrintStatement() {}
    
    public PrintStatement(ArithmeticExpression expr) {
        this.expr = expr;
    }
}
