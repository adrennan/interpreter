/**
 * Created by Ian on 4/8/2018.
 */
public class WhileStatement extends Statement{
    BooleanExpression be;
    Block b;
    public WhileStatement() {}
    public WhileStatement(BooleanExpression be, Block b) {
        this.b = b;
        this.be = be;
    }
}
