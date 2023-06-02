package gui

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.AwtWindow
import androidx.compose.ui.window.MenuBar
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import java.awt.FileDialog
import java.awt.Frame
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment

internal fun AppGui() = application {
    val titleWindowIni = "Procesa archivo: "
    var isDirectoryChooserOpen by remember { mutableStateOf(false) } //Indica que se quiere abrir el FileChooser
    var titleWindow by remember { mutableStateOf(titleWindowIni) } //Titulo de la ventana
    var directoryPath by remember { mutableStateOf("") } //Path al fichero que se está procesando
    var isActiveProcess by remember { mutableStateOf(false) } //Activar o desactivar el menuItem  procesar
    var textProcesed by remember { mutableStateOf("") } //El texto a poner en el campo de procesado.

    //Ventana principal.
    Window(
        title = titleWindow,
        onCloseRequest = ::exitApplication
    ) {
        //MenuBar de la ventana
        MenuBar {
            Menu("Archivo") {
                Item("Abrir", onClick = { isDirectoryChooserOpen = true })
                Item("Procesar", onClick = { isActiveProcess = true })
                Item("Salir", onClick = ::exitApplication)
            }
        }
        //El resto de la ventana
        FrameWindow(
            isDirectoryChooserOpen,
            directoryPath = directoryPath,
            onCloseDirectoryChooser = { directory: String? -> //Cuando se elige en archivo.
                //textProcesed = PicturesRenamerHelper.ProcessPictureNames("$directory")//pendiente de modificar para leer los tres csv seleccionando solo el directorio(mirar el bingo)
            },
            onClickSelectDirectory = { isDirectoryChooserOpen = true },
            textProcesed
        )
    }
}

@Composable
internal fun FrameWindow(
    isDirectoryChooserOpen: Boolean = false,
    directoryPath: String = "",
    onCloseDirectoryChooser: (directory: String?) -> Unit,
    onClickSelectDirectory: () -> Unit,
    textProcesed: String
) {
    MaterialTheme {
        if (isDirectoryChooserOpen)
            DirectoryChooser(onCloseDirectoryChooser = onCloseDirectoryChooser)
        Box(
            modifier = Modifier.fillMaxSize()
                .background(color = Color(180, 180, 180))
                .padding(10.dp)
        )
        {
            Column {
                // La barra de Informacion de la Aplicacion
                TopAppBar(
                    title = { Text("Notas del curso") }
                )
                val stateVertical = rememberScrollState(0)
                val stateHorizontal = rememberScrollState(0)

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(stateVertical)
                        .horizontalScroll(stateHorizontal),
                    contentAlignment = Alignment.TopCenter
                ){
                    TextField( // Contiene el path+nombre del fichero leido
                        value = directoryPath,
                        onValueChange = { },
                        label = { Text("Seleccione la carpeta con los csv") },
                        placeholder = { Text("Carpeta no seleccionada") },
                        modifier = Modifier.padding(top = 10.dp, bottom = 100.dp)
                            .fillMaxSize().width(500.dp).fillMaxHeight(),
                        leadingIcon = { //Icono del lapiz que funciona igual que la opción del menuItem Abrir
                            IconButton(onClick = onClickSelectDirectory) {
                                Icon(
                                    imageVector = Icons.Filled.Create,
                                    contentDescription = "Seleccione la carpeta con los csv"
                                )
                            }
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Color(0xFF120524),
                            backgroundColor = Color.White,
                            focusedLabelColor = Color(0xFF120524).copy(alpha = ContentAlpha.high),
                            focusedIndicatorColor = Color.Transparent,
                            cursorColor = Color(0xFF120524),
                        ),
                        readOnly = false,
                    )
                    Space()
                    TextField( // Campo de texto que contiene el texto en el que se vuelcan los resultados
                        value = textProcesed,
                        onValueChange = { },
                        label = { Text("Resultado") },
                        placeholder = { Text("No obtenido resultados") },
                        modifier = Modifier.padding(top = 100.dp)
                            .fillMaxSize().width(500.dp).height(250.dp),
                        readOnly = false,
                    )
                }
            }
        }
    }
}

@Composable
internal fun DirectoryChooser(
    parent: Frame? = null,
    onCloseDirectoryChooser: (directory: String?) -> Unit
) = AwtWindow(
    create = {
        object : FileDialog(parent, "Elige la carpeta con los csv", LOAD) {
            override fun setVisible(value: Boolean) {
                super.setVisible(value)
                if (value) {
                    onCloseDirectoryChooser(directory)
                }
            }
        }
    },
    dispose = FileDialog::dispose
)


@Composable
internal fun Space() {
    Spacer(modifier = Modifier.size(16.dp))
}