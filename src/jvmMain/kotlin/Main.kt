


fun main(args: Array<String>) {
    /**Mi estructura de los argumentos:
    * -pi (nombre del archivo) -mo (nombre del PARAMETRO_MODULO) -salida (estilo de la salida)
    * en mi codigo si o si debes poner la parte del -pi el de la carpeta donde estan los archivos .csv,
    * la parte el -mo, da igual si pones especificas o no el PARAMETRO_MODULO, (solo se puede excluir esta parte cuando la salida es para fichero)
    * y la parte de -salida e indicar la salida que quieres que haga.
    * Las opciones de -salida son:
     *
    *  * fichero -> sobrecribe los ficheros con las notas correspondientes con la informacion. (por favor, comprobar el ultimo o hacer rollback una vez sobrescrito el fichero)
     *
    *  * bd -> crea la base de datos, depues tendrias que llamarla con las opciones de abajo.
     *
    *  * compose -> abre la interfaz grafica
     *
    *  * consola -> imprime en pantalla con formato tabla la informacion del archivo y luego crea un fichero.log con dicha informacion.
     *
     * Otra opcion añadida una vez implementada la base de datos es usar -bd.
     * -bd (opcion de la bd) (inicial alumno si se pone i)
     * Sus opciones son:
     *
     *  * d -> elimina la informacion de la base de datos.
     *
     *  * q -> muestra la informacion que haya en la base de datos.
     *
     *  * i -> muestra la informacion que haya en la base de datos del alumno (sus iniciales) que se haya introducido despues del i.*/


    val parametros = ProcesadorArgumentos.procesar(args)

    if(parametros.isNotEmpty()){
        LanzadorAplicacion.lanzar(parametros)
    }
}