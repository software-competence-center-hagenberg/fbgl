grammar Expr;

// Parser rules
parse: expr EOF;
expr: term | term ('+' | '-') term;
term: factor | factor ('*' | '/') factor;
factor: NUMBER | '(' expr ')';

// Lexer rules
NUMBER: [1-3];
WS: [ \t\r\n]+ -> skip;