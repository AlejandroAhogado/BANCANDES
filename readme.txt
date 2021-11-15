Para iniciar la aplicación correctamente se debe correr primero el archivo llamado CreacionTablas.sql, este se ejecuta como script sin ningún problema, es decir en el orden que ya está. En segundo lugar se debe correr el archivo llamado DatosNuevos.sql, este archivo agregara correctamente todos los datos necesarios para poder hacer uso de los requerimientos. Al igual que el anterior archivo, este también se ejecuta en el orden que está sin ningún problema.  Después de esto, es necesario realizar un commit para evitar que al intentar iniciar sesión según un tipo de usuario, se presente un error por las credenciales.
Después de iniciada la aplicación, el usuario tendrá la posibilidad de elegir como que tipo de usuario desear ingresar al sistema. Es importante tener en cuenta que para poder ingresar al sistema las credenciales deben ser correctas y corresponder con el mismo tipo de usuario.
Estos son algunos ejemplos de  credenciales, sin embargo se debe tener en cuenta que cada tipo de usuario tiene ciertas restricciones sobre las tareas que puede realizar y también las restricciones planteadas en el caso, como por ejemplo el hecho de que una cuenta no puede ser cerrada por un gerente de oficina que no corresponda a la oficina donde se abrió la cuenta. 


Gerentes de Oficina:
Login: camila3	
Contraseña: 123456789
Gerente General:
Login: juanl2
Constraseña:	123456789

Cajeros:
Login: rom2716	
Contraseña: 123456789
Login: andres123
Contraseña: 123456789
Login: nady231
Contraseña:  123456789

Clientes:
Login: alkosto
Contraseña: 123456789
Login: luisp8	
Contraseña: 123456789
Login: pedrof3	
Contraseña: 123456789
Login: val763	
Contraseña: 123456789
