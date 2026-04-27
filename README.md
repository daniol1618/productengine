# ProductEngineFrancoCipulli
Trabajo Practico propuesto por Dani por sobre un proyecto que empezamos para practicar con springboot
Notas del notepad:

CONSIGNA:

Generar una API en Springboot que permita:
Generar una orden que contenga un único producto. 
Cada orden debe tener una manera de diferenciarse de otras ordenes.
El sistema debe persistir la información en una base de datos relacional de su 
preferencia.

Restricciones: 
    - La orden no puede tener 0 productos. El producto debe existir para poder ser agregado
    a la orden.
    - La orden debe tener un saldo positivo.
    - El producto debe contar con existencias (stock) parar poder ser generada la orden.
    - No es necesario agregar otras Entidades como Cliente.

Cambie el puerto de ejecución del servidor Web de la API del 8080 al 8181
Utilice anotaciones, inyección de dependencias por constructor,
capture las excepciones que considere pertinentes.

Una vez terminado, o durante la ejecución del proyecto, se solicitará
exponer el avance. Es una actividad práctica individual que no tendrá una evaluación.
Pues es preparatoria para alguna evaluaciónfutura.
Se valora que el estudiante pueda acercarse al instructor para exponer alguna duda


CONSTRAINTS:
Los productos pueden estar en 0, hay que validar que cuando se cea una orden haya stock de ese producto. L
Las ordines son por producto.
Cada orden tiene un producto.
Tirar las excepciones al services
Ver como retornar la excepcion por ejhemplo cuando no se cumplen las invariantes



Les comparto el Repositorio:
https://github.com/daniol1618/productengine

El entregable debe ser el pull request en Github. Por favor no usen la cuenta de GlobalLogic para subir el Pull request en github.

No es necesario:
Usar el capturador de exceptions global.
No es necesario usar DTOs.




usuarioPruebaSpring1