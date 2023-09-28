grammar Hello;

// Parser rules
parse: hello EOF;
hello: ('H' | 'h') ('E' | 'e') ('L' | 'l') ('L' | 'l') ('O' | 'o');

// Lexer rules
WS: [ \t\r\n]+ -> skip;