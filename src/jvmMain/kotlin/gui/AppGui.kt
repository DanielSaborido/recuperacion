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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.AwtWindow
import androidx.compose.ui.window.MenuBar
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import java.awt.FileDialog
import java.awt.Frame
import java.io.File

internal fun AppGui() = application {
    val titleWindowIni = "Notas del curso: "
    var isFileChooserOpen by remember { mutableStateOf(false) } //Indica que se quiere abrir el FileChooser
    var titleWindow by remember { mutableStateOf(titleWindowIni) } //Titulo de la ventana
    var filePath by remember { mutableStateOf("") } //Path al fichero que se está procesando
    var textProcesed by remember { mutableStateOf("") } //El texto a poner en el campo de procesado.
    var folderContent by remember { mutableStateOf(emptyList<(Any)>()) }

    //Ventana principal.
    Window(
        title = titleWindow,
        onCloseRequest = ::exitApplication
    ) {
        //MenuBar de la ventana
        MenuBar {
            Menu("Archivo") {
                Item("Abrir", onClick = { isFileChooserOpen = true })
                Item("Salir", onClick = ::exitApplication)
            }
        }
        //El resto de la ventana
        FrameWindow(
            isFileChooserOpen = isFileChooserOpen,
            filePath = filePath,
            onCloseFileChooser = { directory: String ->
                val file = File(directory)
                val fileName = file.name
                filePath = fileName // Actualizar el estado de directoryPath
                folderContent = CSVFileHandler(fileName).readCSV()
                folderContent.forEach { it->
                    println(it)
                }
                isFileChooserOpen = false
            },
            onClickSelectFile = { isFileChooserOpen = true },
            textProcesed = textProcesed
        )
    }
}

@Composable
internal fun FrameWindow(
        isFileChooserOpen: Boolean = false,
        filePath: String,
        onCloseFileChooser: (String) -> Unit,
        onClickSelectFile: () -> Unit,
        textProcesed: String
) {
    MaterialTheme {
        if (isFileChooserOpen)
            fileChooser(onCloseDirectoryChooser = onCloseFileChooser)
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
                    TextField(
                        value = filePath,
                        onValueChange = {},
                        label = { Text("Seleccione el archivo csv") },
                        placeholder = { Text("Archivo no seleccionada") },
                        modifier = Modifier.padding(top = 10.dp, bottom = 100.dp)
                            .fillMaxSize().width(500.dp).fillMaxHeight(),
                        leadingIcon = {
                            IconButton(onClick = onClickSelectFile) {
                                Icon(
                                    imageVector = Icons.Filled.Create,
                                    contentDescription = "Seleccione el archivo csv"
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
                        readOnly = false
                    )
                    Space()
                    TextField( // Campo de texto que contiene el texto en el que se vuelcan los resultados
                        value = textProcesed,
                        onValueChange = { },
                        label = { Text("Resultado") },
                        placeholder = { Text("No obtenido resultados") },
                        modifier = Modifier.padding(top = 100.dp)
                            .fillMaxSize().width(500.dp).height(250.dp),
                        readOnly = false
                    )
                }
            }
        }
    }
}

@Composable
internal fun fileChooser(
    parent: Frame? = null,
    onCloseDirectoryChooser: (String) -> Unit
) = AwtWindow(
    create = {
        object : FileDialog(parent, "Elige el archivo csv", LOAD) {
            override fun setVisible(value: Boolean) {
                super.setVisible(value)
                if (value) {
                    onCloseDirectoryChooser(file)
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