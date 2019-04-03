import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ian on 4/7/2018.
 * Edited by Alex on 4/22/18 to add execution implementation
 */


/**
 * 
 * @author Alex and Ian
 *Main class that does the execution. The program class holds a block which contains all the statements and ways to execute them. 
 *Variables are stored in a map data structure where a character can be linked to an object which can be determined as a int
 */
public class Program {
    Block bl;
    Map<Character, Object> variables = new HashMap<Character, Object>();
    
    /**
     * Default constructor for program    
     * @param bl the main block for the function
     */
    public Program(Block bl) {
        this.bl = bl;
    }

    
    /**
     * Execute goes through the block, and pulls each statement and determines what type of statement they are so that they can be processed.
     * After executing a statement, it is removed from the block
     */
    public void execute() {
    	System.out.println("EXECUTING .... \n");
    	while(!bl.statements.isEmpty()){				//Continues until all statements have been processed
        	if(AssignmentStatement.class.isInstance(bl.statements.get(0))){		//checks if the statement is off a class, if is, executes it
        		executeAssignment((AssignmentStatement)bl.statements.get(0));
        	}
        	else if(PrintStatement.class.isInstance(bl.statements.get(0))){
        		executePrint((PrintStatement)bl.statements.get(0));
        	}
        	else if(RepeatStatement.class.isInstance(bl.statements.get(0))){
        		executeRepeat((RepeatStatement)bl.statements.get(0));
        		
        	}
        	else if(WhileStatement.class.isInstance(bl.statements.get(0))){
        		executeWhile((WhileStatement)bl.statements.get(0));
        	}
        	else if(IfStatement.class.isInstance(bl.statements.get(0))){
        		executeIF((IfStatement)bl.statements.get(0));
        	}
        	bl.statements.remove(0);										//removing statement from the blocks array list
    	}
    }
       
    
    /**
     * Given an assignment statement, either set a character to the map, or reassign a char to another. 
     * Also may need to evaluate an arithmetic expression before setting
     * @param s an AssignmentStatement
     */
    private void executeAssignment(AssignmentStatement s){
    	
    	if(s.expr.op == null){		//If the expression has no operator, it can only be a single token, either a INT_LIT or an ID	
    		
    		//Assigning an integer to a variable eg: x = 4;
    		if(s.expr.var == null){							
    			variables.put(s.var.c, Integer.valueOf(s.expr.a));
    		}
    		//Assigns another variable to a variable
    		else{
    			variables.put(s.var.c, variables.get(s.expr.var.c));
    		}
    	}
    	//case where a variable is assigned to an expression eg: X = 4 + 3;
    	else{
    		variables.put(s.var.c, executeAE(s.expr));
    	}	
    }
    
    /**
     * Prints a statement/var to console
     * @param s a PrintStatement
     */
    private void executePrint(PrintStatement s){
    	//case where there is no operator
    	if(s.expr.op == null){
    		
    		//Printing an integer to console eg: 4; since there is no variable field, and no operator, it must be an integer
    		if(s.expr.var == null){							
    			System.out.println(s.expr.a);
    		}
    		//printing out a variable saved in memory
    		else{
    			System.out.println(variables.get(s.expr.var.c));
    		}
    	}
    	//case where a variable is assigned to an expression eg: X = 4 + 3;
    	else{
    		System.out.println(executeAE(s.expr));
    	}	
    }
    
    /**
     * Executes the IF logic. If (BooleanExpression) then (BLOCK1) else (BLOCK2) end
     * @param s an if statement
     */
    private void executeIF(IfStatement s){
    	if(executeBE(s.be)){	//If boolean is true, execute the THEN BLOCK
    		for(int i = 0; i < s.thenBlock.statements.size(); i++){						//goes through all statements in THENBLOCK and executes them
        		if(AssignmentStatement.class.isInstance(s.thenBlock.statements.get(i))){
            		executeAssignment((AssignmentStatement)s.thenBlock.statements.get(i));
            		
            	}
            	else if(PrintStatement.class.isInstance(s.thenBlock.statements.get(i))){
            		executePrint((PrintStatement)s.thenBlock.statements.get(i));
            	}
            	else if(RepeatStatement.class.isInstance(s.thenBlock.statements.get(i))){
            		executeRepeat((RepeatStatement)s.thenBlock.statements.get(i));
            		
            	}
            	else if(WhileStatement.class.isInstance(s.thenBlock.statements.get(i))){
            		executeWhile((WhileStatement)s.thenBlock.statements.get(i));
            	}		
            	else if(IfStatement.class.isInstance(s.thenBlock.statements.get(i))){
            		executeIF((IfStatement)s.thenBlock.statements.get(i));
            	}
        	}
    	}
    	
    	else{					//ELSE, execute the ELSE BLOCK						**Could use another method to have less code here, but just copy pasted it
    		for(int i = 0; i < s.elseBlock.statements.size(); i++){					//goes through all statements in ELSE BLOCK and executes them
        		if(AssignmentStatement.class.isInstance(s.elseBlock.statements.get(i))){
            		executeAssignment((AssignmentStatement)s.elseBlock.statements.get(i));           		
            	}
            	else if(PrintStatement.class.isInstance(s.elseBlock.statements.get(i))){
            		executePrint((PrintStatement)s.elseBlock.statements.get(i));
            	}
            	else if(RepeatStatement.class.isInstance(s.elseBlock.statements.get(i))){
            		executeRepeat((RepeatStatement)s.elseBlock.statements.get(i));
            		
            	}
            	else if(WhileStatement.class.isInstance(s.elseBlock.statements.get(i))){
            		executeWhile((WhileStatement)s.elseBlock.statements.get(i));
            	}	
            	else if(IfStatement.class.isInstance(s.elseBlock.statements.get(i))){
            		executeIF((IfStatement)s.elseBlock.statements.get(i));
            	}
        	}
    	}
    	
    }
    
    
    /**
     * Executes the logic for a while statement while *boolean expression* do *block* end
     * @param s a whileStatement
     */
    private void executeWhile(WhileStatement s){
    	while (executeBE(s.be)){						//checks the BooleanExpression to be true or false
    		for(int i = 0; i < s.b.statements.size(); i++){							//executes all statements in the block
        		if(AssignmentStatement.class.isInstance(s.b.statements.get(i))){
            		executeAssignment((AssignmentStatement)s.b.statements.get(i));
            		
            	}
            	else if(PrintStatement.class.isInstance(s.b.statements.get(i))){
            		executePrint((PrintStatement)s.b.statements.get(i));
            	}
            	else if(RepeatStatement.class.isInstance(s.b.statements.get(i))){
            		executeRepeat((RepeatStatement)s.b.statements.get(i));
            		
            	}
            	else if(WhileStatement.class.isInstance(s.b.statements.get(i))){
            		executeWhile((WhileStatement)s.b.statements.get(i));
            	}	
            	else if(IfStatement.class.isInstance(s.b.statements.get(i))){
            		executeIF((IfStatement)s.b.statements.get(i));
            	}
    		}
    	}
    }
    
    
    /**
     * executes the Repeat statement with logic repeat *block* until *boolean expression*
     * @param s a repeatStatement
     */
    private void executeRepeat(RepeatStatement s){
    	do{																//loop until boolean expression is false, do it at least once
    		for(int i = 0; i < s.b.statements.size(); i++){
        		if(AssignmentStatement.class.isInstance(s.b.statements.get(i))){
            		executeAssignment((AssignmentStatement)s.b.statements.get(i));
            		
            	}
            	else if(PrintStatement.class.isInstance(s.b.statements.get(i))){
            		executePrint((PrintStatement)s.b.statements.get(i));
            	}
            	else if(RepeatStatement.class.isInstance(s.b.statements.get(i))){
            		executeRepeat((RepeatStatement)s.b.statements.get(i));
            		
            	}
            	else if(WhileStatement.class.isInstance(s.b.statements.get(i))){
            		executeWhile((WhileStatement)s.b.statements.get(i));
            	}		
            	else if(IfStatement.class.isInstance(s.b.statements.get(i))){
            		executeIF((IfStatement)s.b.statements.get(i));
            	}
        	}	
    	}while (executeBE(s.be));				//end condition
    }
    
    
    /**
     * Evaluates a Boolean Expression and returns true or false based on the values
     * @param expr a Boolean Expression which has parameters operator, expr1, expr2
     * @return a true or false
     */
    private boolean executeBE(BooleanExpression expr){
    	switch(expr.op.getLexeme()){				//determine which logical operator to use
    		case "==":{
    			return executeAE(expr.expr1) == executeAE(expr.expr2);		//returns the comparison
    		}
    		case "<=":{
    			return executeAE(expr.expr1) <= executeAE(expr.expr2);
    		}
    		case ">=":{
    			return executeAE(expr.expr1) >= executeAE(expr.expr2);
    		}
    		case "~=":{
    			return executeAE(expr.expr1) != executeAE(expr.expr2);
    		}
    		case ">":{
    			return executeAE(expr.expr1) > executeAE(expr.expr2);
    		}
    		case "<":{
    			return executeAE(expr.expr1) < executeAE(expr.expr2);
    		}
    	}
    	return false;					//if it reaches here there is a token mismatch, if have time add a thrown expection
    }
    
    /**
     * Executes an Arithmetic Expression to get a value by *, /, +, - given in PREFIX Notation
     * For expressions like X = + x 1, recursively calls itself until an integer is found 
     * @param expr an ArithmeticExpression, which can be further determined to be an INT_LIT, VAR, or expression
     * @return an integer
     */
    private int executeAE(ArithmeticExpression expr){
    	//base case where there is no operator, only an int or variable
    	if(expr.op == null){
    		//No var, must be an int lit
    		if(expr.var == null){				
    			return expr.a;
    		}
    		//printing out a variable saved in memory
    		else{
    			return (int)variables.get(expr.var.c);
    		}
    	}
    	//recursive cases for add, subtract, multiply, divide
    	else{
    		if((expr.op.op.getLexeme()).equals("+")){
    			return executeAE(expr.var1) + executeAE(expr.var2);
    		}
    		else if((expr.op.op.getLexeme()).equals("-")){
    			return executeAE(expr.var1) - executeAE(expr.var2);
    		}
    		else if((expr.op.op.getLexeme()).equals("*")){
    			return executeAE(expr.var1) * executeAE(expr.var2);
    		}
    		else if ((expr.op.op.getLexeme()).equals("/")){
    			return executeAE(expr.var1) / executeAE(expr.var2);
    		}

    	}
    	return -1;	//should never reach this, potentially throw error if have time
    }
}

