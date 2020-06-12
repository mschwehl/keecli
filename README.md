# keecli
java command line to keepass2 (read only)

#usecase
getting keepass-passwords in the bash and query keepass-stores

##usage: 
java -jar keecli -q username:test -o field:password -t clipboard -dbPath xxx -DbPassword yyy
java -jar keecli -o path:username -dbPath xxx -DbPassword yyy

if you just type -DbPassword, the app will ask.

#attention
security is your part, do not blame me. Do not expose passwords in bash-scripts
crypt them with openssl and store them only you can access

#implementation
the java-app will read the database in memory so no modification is possible and database-corruption can not happen (hopefully) 

## credits
this is a wrapper to org.linguafranca.pwdb.KeePassJava2 using com.beust.jcommander


