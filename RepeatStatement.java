/**
 * Created by Ian on 4/8/2018.
 */
public class RepeatStatement extends Statement{
    Block b;
    BooleanExpression be;
    public RepeatStatement() {

    }
    public RepeatStatement(Block b, BooleanExpression be) {
        this.b = b;
        this.be = be;
    }
}
