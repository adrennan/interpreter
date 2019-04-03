/**
 * Created by Ian on 4/6/2018.
 * Edited by Alex on 4/27/18 to include constructors for an Arithmetic Expression
 * that is only a single int, or single var
 */
public class ArithmeticExpression {
    int a;
    Id var;
    BinaryExpression op;
    ArithmeticExpression var1;
    ArithmeticExpression var2;
    
    public ArithmeticExpression () {}

    //Does a literal
    public ArithmeticExpression(int a) {
        this.a = a;
    }
    
    //Does a variable
    public ArithmeticExpression(Id var) {
        this.var = var;
    }
    
    //does a Expression
    public ArithmeticExpression(BinaryExpression op, ArithmeticExpression var1, ArithmeticExpression var2) {
        this.op = op;
        this.var1 = var1;
        this.var2 = var2;
    }
}
