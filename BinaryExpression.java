/**
 * Created by Ian on 4/7/2018.
 * Edited by Alex on 4/27/18 to change Operator into a Token class instead
 * of another BinaryExpression.
 */
public class BinaryExpression extends ArithmeticExpression{
    Token op;
    ArithmeticExpression arith;
    ArithmeticExpression arith2;
    
    public BinaryExpression() {
    }
    
    public BinaryExpression(Token op){
    	this.op = op;
    }
    
    public BinaryExpression(Token op, ArithmeticExpression arith, ArithmeticExpression arith2) {
        this.op = op;
        this.arith = arith;
        this.arith2 = arith2;
    }
}
