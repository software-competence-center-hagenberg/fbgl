grammar Url;

// Parser rules
parse			: url EOF;
url 			: httpaddress | ftpaddress | telnetaddress | gopheraddress;
httpaddress 	: 'h' 't' 't' 'p' ':' '/' '/' hostport ( '/' path )? ( '?' search )?;
ftpaddress 		: 'f' 't' 'p' ':' '/' '/' login '/' path;
telnetaddress 	: 't' 'e' 'l' 'n' 'e' 't' ':' '/' '/' login;
gopheraddress 	: 'g' 'o' 'p' 'h' 'e' 'r' ':' '/' '/' hostport ('/' gtype ( selector )? )? ( '?' search )?;
login 			: user ( ':' password )? '@' hostport;
hostport 		: host ( ':' port )?;
host 			: hostname | hostnumber;
hostname 		: ialpha ( '.' hostname )?;
hostnumber 		: digits '.' digits '.' digits '.' digits;
port 			: digits;
path 			: xalphas ( '/' path )?;
search 			: xalphas ( '+' search )?;
selector 		: path;
user 			: xalphas;
password 		: xalphas;
gtype 			: xalpha;
xalpha 			: alpha | digit | safe | extra;
xalphas 		: xalpha ( xalphas )?;
ialpha 			: alpha ( xalphas )?;
alpha 			: 'a' | 'b' | 'c' | 'A' | 'B' | 'C';
digit 			: '0' | '1' | '2' | '3';
safe 			: '$' | '-' | '_' | '&';
extra 			: '!' | '*' | '(' | ')' | ';' | ',';
digits 			: digit ( digits )?;
	
// Lexer rules