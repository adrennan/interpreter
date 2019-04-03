/**
 * Created by Ian on 3/28/2018.
 * Edited by Alex on 4/27/18 with changes to ArithmeticOperator, BinaryOperator
 */
import java.io.FileNotFoundException;
import java.lang.Exception;

public class Parser {
    private LexAnalyzer lex;

    /**
     * @param fileName cannot be null - checked in LexicalAnalyzer
     * @throws FileNotFoundException if file cannot be found
     * postcondition: parser object has been created
     */
    public Parser (String fileName) throws FileNotFoundException, LexicalException
    {
        lex = new LexAnalyzer(fileName);
    }

    /**
     * @return Program object containing an intermediate representation of the program
     * @throws ParserException if a parsing error occurred
     * This method is called (invoked) in 'main'
     * implements the production <program> → function id ( ) <block> end
     */
    public Program parse () throws ParserException
    {
        // getNextToken() method invokes the scanner method to
        // fetch next token (see below in line 348)
        Token tok = getNextToken();
        match (tok, TokenType.FUNCTION_TOK);
        Id functionName = getId();
        tok = getNextToken ();
        match (tok, TokenType.LPAREN);
        tok = getNextToken ();
        match (tok, TokenType.RPAREN);
        Block blk = getBlock();
        tok = getNextToken ();
        match (tok, TokenType.END_TOK);
        tok = getNextToken();
        if (tok.getTokType() != TokenType.EOS_TOKEN)
            throw new ParserException ("Token received was " + tok.getTokType() + " garbage at end of file");
        return new Program (blk);
    }

    /**
     * @return Block object
     * @throws ParserException if a parsing error occurred
     * implements the production <block> → <statement> | <statement> <block>
     */
    private Block getBlock() throws ParserException
    {
        System.out.println("Enter: Block");
        Block blk = new Block();
        Token tok = getLookaheadToken();
        while (isValidStartOfStatement (tok))
        {
            Statement stmt = getStatement();
            blk.add (stmt);
            tok = getLookaheadToken();
        }
        return blk;
    }

    /**
     * @return statement object
     * @throws ParserException if a parsing error occurred
     * implements the production <statement> → <if_statement> | <assignment_statement> | <while_statement> | <print_statement> | <repeat_statement>
     */
    private Statement getStatement() throws ParserException
    {
        System.out.println("Enter: Statement");
        Statement stmt;
        Token tok = getLookaheadToken();
        if (tok.getTokType() == TokenType.IF_TOK)
            stmt = getIfStatement();
        else if (tok.getTokType() == TokenType.WHILE_TOK)
            stmt = getWhileStatement();
        else if (tok.getTokType() == TokenType.PRINT_TOK)
            stmt = getPrintStatement();
        else if (tok.getTokType() == TokenType.REPEAT_TOK)
            stmt = getRepeatStatement();
        else if (tok.getTokType() == TokenType.ID)
            stmt = getAssignmentStatement();
        else
            throw new ParserException ("invalid statement at row " +
                    tok.getRowNumber()  + " and column " + tok.getColumnNumber());
        return stmt;
    }

    /**
     * @return assignment statement
     * @throws ParserException if a parsing error occurred
     * implements the production <assignment_statement> -> id <assignment_operator> <arithmetic_expression>
     */
    private Statement getAssignmentStatement() throws ParserException
    {
        Id var = getId();
        Token tok = getNextToken();
        match (tok, TokenType.ASSIGN_OP);
        ArithmeticExpression expr = getArithmeticExpression();
        return new AssignmentStatement (var, expr);
    }

    /**
     * @return repeat statement
     * @throws ParserException if a parsing error occurred
     * implements the production <repeat_statement> -> repeat <block> until <boolean_expression>
     */
    private Statement getRepeatStatement() throws ParserException
    {
        Token tok = getNextToken();
        match (tok, TokenType.REPEAT_TOK);
        Block blk = getBlock();
        tok = getNextToken();
        match (tok, TokenType.UNTIL_TOK);
        BooleanExpression expr = getBooleanExpression();
        return new RepeatStatement (blk, expr);
    }

    /**
     * @return print statement
     * @throws ParserException if a parsing error occurred
     * implements the production <print_statement> → print ( <arithmetic_expression> )
     */
    private Statement getPrintStatement() throws ParserException
    {
        Token tok = getNextToken();
        match (tok, TokenType.PRINT_TOK);
        tok = getNextToken ();
        match (tok, TokenType.LPAREN);
        ArithmeticExpression expr = getArithmeticExpression();
        tok = getNextToken ();
        match (tok, TokenType.RPAREN);
        return new PrintStatement (expr);
    }

    /**
     * @return while statement
     * @throws ParserException if a parsing error occurred
     * implements the production <while_statement> → while <boolean_expression> do <block> end
     */
    private Statement getWhileStatement() throws ParserException
    {
        Token tok = getNextToken ();
        match (tok, TokenType.WHILE_TOK);
        BooleanExpression expr = getBooleanExpression();
        tok = getNextToken ();
        match (tok, TokenType.DO_TOK);
        Block blk = getBlock();
        tok = getNextToken();
        match (tok, TokenType.END_TOK);
        return new WhileStatement (expr, blk);
    }

    /**
     * @return if statement
     * @throws ParserException if a parsing error occurred
     * implements the production <if_statement> → if <boolean_expression> then <block> else <block> end
     */
    private Statement getIfStatement() throws ParserException
    {
        Token tok = getNextToken ();
        match (tok, TokenType.IF_TOK);
        BooleanExpression expr = getBooleanExpression();
        tok = getNextToken ();
        match (tok, TokenType.THEN_TOK);
        Block blk1 = getBlock();
        tok = getNextToken ();
        match (tok, TokenType.ELSE_TOK);
        Block blk2 = getBlock();
        tok = getNextToken();
        match (tok, TokenType.END_TOK);
        return new IfStatement (expr, blk1, blk2);
    }

    /**
     * @param tok cannot be null - checked with assertion
     * @return whether tok can be the start of a statement
     */
    private boolean isValidStartOfStatement(Token tok)
    {
        assert (tok != null);
        return tok.getTokType() == TokenType.ID ||
                tok.getTokType() == TokenType.IF_TOK ||
                tok.getTokType() == TokenType.WHILE_TOK ||
                tok.getTokType() == TokenType.PRINT_TOK ||
                tok.getTokType() == TokenType.REPEAT_TOK;
    }

    /**
     * @return arithmetic expression
     * @throws ParserException if a parsing error occurred
     * implements the production <arithmetic_expression> → <id> | <literal_integer> | <arithmetic_op> <arithmetic_expression> <arithmetic_expression>
     */
    private ArithmeticExpression getArithmeticExpression() throws ParserException
    {
        System.out.println("Enter: ArithemeticExpression");
        ArithmeticExpression expr;
        Token tok = getLookaheadToken();
        
        if((tok.getTokType() == TokenType.ADD_OP) || (tok.getTokType() == TokenType.SUB_OP) || (tok.getTokType() == TokenType.MULT_OP) || (tok.getTokType() == TokenType.DIV_OP)){
        	BinaryExpression test = getBinaryExpression();
        	expr = new ArithmeticExpression(new BinaryExpression(tok), test.arith, test.arith2);
        }
        else if (tok.getTokType() == TokenType.ID){
        	expr = new ArithmeticExpression(getId());
        }
        else if (tok.getTokType() == TokenType.DIGIT){
        	expr = getLiteralInteger();
        }
        else
        {
        	expr = null;
        }
        
        return expr;
    }

    /**
     * @return binary expression
     * @throws ParserException if a parsing error occurred
     * implements the grammar expression <arithmetic_op> <arithmetic_expression> <arithmetic_expression>
     */
    private BinaryExpression getBinaryExpression() throws ParserException
    {
        System.out.println("Enter: BinaryExpression");
        BinaryExpression op = getArithmeticOperator();
        ArithmeticExpression expr1 = getArithmeticExpression();
        ArithmeticExpression expr2 = getArithmeticExpression();
        return new BinaryExpression (op.op, expr1, expr2);
    }

    /**
     * @return arithmetic operator
     * @throws ParserException if a parsing error occurred
     * implements the production <arithmetic_op> → add_operator | sub_operator | mul_operator | div_operator
     */
    private BinaryExpression getArithmeticOperator() throws ParserException
    {
        System.out.println("Enter: Operator");
        BinaryExpression op;
        Token tok = getNextToken();
        if (tok.getTokType() == TokenType.ADD_OP)
            op = new BinaryExpression(tok);
        else if (tok.getTokType() == TokenType.SUB_OP)
        	op = new BinaryExpression(tok);
        else if (tok.getTokType() == TokenType.MULT_OP)
        	op = new BinaryExpression(tok);
        else if (tok.getTokType() == TokenType.DIV_OP)
        	op = new BinaryExpression(tok);
        else
            throw new ParserException ("arithmetic operator expected at row " +
                    tok.getRowNumber()  + " and column " + tok.getColumnNumber());
        return op;
    }

    /**
     * @return literal integer
     * @throws ParserException if a parsing error occurred
     */
    private ArithmeticExpression getLiteralInteger() throws ParserException
    {
        System.out.println("Enter: Digit");
        Token tok = getNextToken ();
        if (tok.getTokType() != TokenType.DIGIT)
            throw new ParserException ("literal integer expected at row " +
                    tok.getRowNumber()  + " and column " + tok.getColumnNumber());
        int value = Integer.parseInt(tok.getLexeme());
        return new ArithmeticExpression (value);
    }

    /**
     * @return an id
     * @throws ParserException if a parsing error occurred
     */
    private Id getId() throws ParserException
    {
        System.out.println("Enter: Id");
        Token tok = getNextToken();
        if (tok.getTokType() != TokenType.ID)
            throw new ParserException ("identifier expected at row " +
                    tok.getRowNumber()  + " and column " + tok.getColumnNumber());
        Id temp = new Id (tok.getLexeme().charAt(0));
        return temp;
    }

    /**
     * @return boolean expression
     * @throws ParserException if a parsing error occurred
     * implements the production <boolean_expression> → <relative_op> <arithmetic_expression> <arithmetic_expression>
     */
    private BooleanExpression getBooleanExpression() throws ParserException
    {
        System.out.println("Enter: BooleanExpression");
        Token op = getRelationalOperator();
        ArithmeticExpression expr1 = getArithmeticExpression();
        ArithmeticExpression expr2 = getArithmeticExpression ();
        return new BooleanExpression (op, expr1, expr2);
    }

    /**
     * @return relative operator
     * @throws ParserException if a parsing error occurred
     * implements the production <relative_op> → le_operator | lt_operator | ge_operator | gt_operator | eq_operator | ne_operator
     */
    private Token getRelationalOperator() throws ParserException
    {
        System.out.println("Enter: RelationalOperator");
        Token op;
        Token tok = getNextToken();
        if (tok.getTokType() == TokenType.EQ_OP)
            op = tok;
        else if (tok.getTokType() == TokenType.NE_OP)
            op = tok;
        else if (tok.getTokType() == TokenType.GT_OP)
            op = tok;
        else if (tok.getTokType() == TokenType.GE_OP)
            op = tok;
        else if (tok.getTokType() == TokenType.LT_OP)
            op = tok;
        else if (tok.getTokType() == TokenType.LE_OP)
            op = tok;
        else
            throw new ParserException ("relational operator expected at row " +
                    tok.getRowNumber()  + " and column " + tok.getColumnNumber());
        return op;
    }

    /**
     * @param tok cannot be null - checked by assertion
     * @param tokType cannot be null - checked by assertion
     * @throws ParserException if type of tok is not tokType
     */
    private void match(Token tok, TokenType tokType) throws ParserException
    {
        assert (tok != null);
        assert (tokType != null);
        if (tok.getTokType() != tokType)
            throw new ParserException (tokType + " expected at row " +
                    tok.getRowNumber()  + " and column " + tok.getColumnNumber());
    }

    /**
     * @return copy of next token
     * @throws ParserException if there are no more tokens
     */
    private Token getLookaheadToken() throws ParserException
    {
        Token tok = null;
        try
        {
            tok = lex.getLookaheadToken();
        }
        catch (LexicalException e)
        {
            throw new ParserException ("no more tokens");
        }
        return tok;
    }

    /**
     * @return next token
     * @throws ParserException if there are no more tokens
     */
    private Token getNextToken() throws ParserException
    {
        Token tok = null;
        try
        {
            tok = lex.getNextToken();
        }
        catch (LexicalException e)
        {
            throw new ParserException ("no more tokens");
        }
        return tok;
    }
}

