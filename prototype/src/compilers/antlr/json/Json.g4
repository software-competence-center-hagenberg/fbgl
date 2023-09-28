grammar Json;

// Parser rules
parse: json EOF;
json: element;
value: object | array | string | number | 't' | 'f' | 'n';
object: '{' ws (string ws ':' element (',' members)?)? '}';
members: member (',' members)?;
member: ws string ws ':' element;
array: '[' ws (value ws ( ',' elements )?)? ']';
elements: element ( ',' elements )?;
element: ws value ws;
string: '!' characters '!';
characters: '^' | character characters;
character: 'a' | 'b' | 'c';
number: integer fraction exponent;  
integer: '0' | onenine | onenine digits;
digits: digit | digit digits;
digit: '0' | onenine;
onenine: '1' | '2' | '3'; 
fraction: '^' | '.' digits;
exponent: '^' | 'E' sign digits | 'e' sign digits;
sign: '^' | '+' | '-';
ws: '_';

// Lexer rules