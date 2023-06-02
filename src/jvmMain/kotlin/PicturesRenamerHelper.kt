import java.io.File

object PicturesRenamerHelper {
    //metodo que procesa las fotos
    fun ProcessPictureNames(fileName:String) : String {

        var contenidoCompleto = File(fileName).readLines()

        require(contenidoCompleto.size >= 3) {"El fichero debe tener al menos tres lineas"}

        var pais = contenidoCompleto[0].split(" ")[0]
        var fotosMovil = contenidoCompleto[1].split(" ")
        var fotosCamara = contenidoCompleto[2].split(" ")

        var mapaCombinado = mutableMapOf<String,String>()

        //Ordenando fotos movil
        val regexMovil = """IMG_20([0-9][0-9])([0-9][0-9])([0-9][0-9])_([0-9][0-9][0-9][0-9][0-9][0-9]).jpg""".toRegex()

        //descompone la estructura y la guarda en un mapa
        for (foto in fotosMovil) {

            var matchResult = regexMovil.find(foto)
            if (matchResult != null) {
                var ficheroFoto = matchResult.groupValues[0]
                var anyo = matchResult.groupValues[1]
                var mes = matchResult.groupValues[2]
                var dia = matchResult.groupValues[3]
                var hora = matchResult.groupValues[4]

                mapaCombinado.put(anyo + mes + dia + hora, ficheroFoto)
            }
        }
        //Ordenando fotos de Cámara
        val regexCamara = """P([0-9][0-9])([0-9][0-9])([0-9][0-9])_([0-9][0-9][0-9][0-9][0-9][0-9]).jpg""".toRegex()

        for (foto in fotosCamara) {

            var matchResult = regexCamara.find(foto)
            if (matchResult != null) {
                var ficheroFoto = matchResult.groupValues[0]
                var dia = matchResult.groupValues[1]
                var mes = matchResult.groupValues[2]
                var anyo = matchResult.groupValues[3]
                var hora = matchResult.groupValues[4]

                mapaCombinado.put(anyo + mes + dia + hora, ficheroFoto)
            }
        }

        var scriptDeRenombrado = ""

        //retorna la salida del fichero
        var idFoto = 0
        for (foto in mapaCombinado.toSortedMap()) {
            scriptDeRenombrado += "mv ${foto.value} ${pais}_${"%03d".format(idFoto)}.jpg\n" //salida de fichero
            idFoto++
        }


        return scriptDeRenombrado
    }

    //exporta script
    fun ExportarScript(nombreOriginal:String,script:String) {

        var nombreFicheroScript = File(nombreOriginal).parent + "/" + File(nombreOriginal).nameWithoutExtension + ".sh"

        File(nombreFicheroScript).writeText(script)
    }

}

fun main() {
    println(PicturesRenamerHelper.ProcessPictureNames("kotlin/examples/sample-1.in"))
}