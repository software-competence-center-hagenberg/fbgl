grammar Mail;

// Parser rules
parse: mail EOF;
mail: string '@' string '.' tag;
string: character | character string;
tag: character character | character character character;
character: 'a' | 'b' | 'c' | 'd' | 'e' | 'f' | 'g' | 'h' | 'i' | 'j' | 'k' | 'l' | 'm' | 'n' | 'o' | 'p' | 'q' | 'r' | 's' | 't' | 'u' | 'v' | 'w' | 'x' | 'y' | 'z';

// Lexer rules
WS: [ \t\r\n]+ -> skip;