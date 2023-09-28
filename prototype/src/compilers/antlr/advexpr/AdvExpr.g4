grammar AdvExpr;

// Parser rules
parse: expr EOF;
expr: term | term ('+' | '-') expr;
term: factor | factor ('*' | '/') term;
factor: num | '(' expr ')';
num: digit | digit num;
digit: '1' | '2' | '3';

// Lexer rules
WS: [ \t\r\n]+ -> skip;