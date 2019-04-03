/**
 * Created by Ian on 2/24/2018.
 */
//Define Tokens, Unsure if I needed to add keywords or reserved words. Sorry -\_("/)_/-
public enum TokenType {
    ID, DIGIT, ASSIGN_OP, LE_OP,
    LT_OP, GE_OP, GT_OP, EQ_OP,
    NE_OP, ADD_OP, SUB_OP,MULT_OP,
    DIV_OP, LPAREN, RPAREN, IF_TOK,
    FUNCTION_TOK, THEN_TOK, END_TOK,
    ELSE_TOK, WHILE_TOK, DO_TOK,
    PRINT_TOK, REPEAT_TOK, UNTIL_TOK, EOS_TOKEN;
}
