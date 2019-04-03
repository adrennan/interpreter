/**
 * Created by Ian on 4/7/2018.
 */
public class AssignmentStatement extends Statement {
    Id var;
    ArithmeticExpression expr;
    public AssignmentStatement(Id var, ArithmeticExpression expr) {
        this.var = var;
        this.expr = expr;
    }
}
