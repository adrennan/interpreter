/**
 * Created by Ian on 4/7/2018.
 * Edited by Alex on 4/28/2018 to change op to token type and fix constructor
 */
public class BooleanExpression {
    Token op;
    ArithmeticExpression expr1;
    ArithmeticExpression expr2;
    
    public static BooleanExpression EQ_OP;
    public static BooleanExpression NE_OP;
    public static BooleanExpression GT_OP;
    public static BooleanExpression GE_OP;
    public static BooleanExpression LE_OP;
    public static BooleanExpression LT_OP;
    public BooleanExpression() {

    }
    public BooleanExpression(Token op, ArithmeticExpression expr1, ArithmeticExpression expr2) {
    	this.op = op;
    	this.expr1 = expr1;
    	this.expr2 = expr2;
    }
}
