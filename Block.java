import java.util.ArrayList;

/**
 * Created by Ian on 4/6/2018.
 * Edited by Alex on 4/27/18 to have statements as an arrayList instead of recursively 
 * called the block inside of a block
 */


public class Block {
    Block b;
    ArrayList<Statement> statements;
    
    public Block(Block test, Statement s) {
        this.b = test;
        statements = new ArrayList<>();
        this.statements.add(s);
    }

    public Block(Statement s) {
    	statements = new ArrayList<>();
        this.statements.add(s);
        this.b = null;
    }
    
    //No arg constructor
    public Block(){
    	statements = new ArrayList<>();
    	this.b = null;
    }
    
    public void add(Statement s) {
        this.statements.add(s);
    }
}
