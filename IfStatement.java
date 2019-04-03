/**
 * Created by Ian on 4/8/2018.
 * Edited by Alex on 4/28/18 to change blocks names to better readability
 */
public class IfStatement extends Statement{
    Block thenBlock;
    Block elseBlock;
    BooleanExpression be;
    public IfStatement() {}
    public IfStatement(BooleanExpression be, Block thenBlock, Block elseBlock) {
        this.be = be;
        this.thenBlock = thenBlock;
        this.elseBlock = elseBlock;
    }
}
