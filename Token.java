/**
 * Created by Ian on 2/24/2018.
 */
//Define token
public class Token {
    private int rowNumber;
    private int columnNumber;
    private String lexeme;
    private TokenType tokType;

    public Token(TokenType tokType, String lexeme, int rowNumber, int columnNumber)
    {
        if(rowNumber <= 0)
            throw new IllegalArgumentException("invalid row number argument");
        if(columnNumber <= 0)
            throw new IllegalArgumentException("invalid column number argument");
        if(lexeme == null || lexeme.length() == 0)
            System.out.println("invalid lexeme argument"); // Error Message
        if(tokType == null)
            System.out.println("invalid TokenType argument"); // Error Message
        this.rowNumber = rowNumber;
        this.columnNumber = columnNumber;
        this.lexeme = lexeme;
        this.tokType = tokType;
    }
//Methods to retrieve token information
    public int getRowNumber() {
        return rowNumber;
    }

    public int getColumnNumber() {
        return columnNumber;
    }

    public String getLexeme() {
        return lexeme;
    }

    public TokenType getTokType() {
        return tokType;
    }
}
