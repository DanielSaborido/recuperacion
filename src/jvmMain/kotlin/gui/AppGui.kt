package gui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.MenuBar
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import entities.Evaluacion

internal fun AppGui(info: Evaluacion) = application {
    val titleWindowIni = "Notas del curso: "
    val titleWindow by remember { mutableStateOf(titleWindowIni) } //Titulo de la ventana

    //Ventana principal.
    Window(
        title = titleWindow,
        onCloseRequest = ::exitApplication
    ) {
        //MenuBar de la ventana
        MenuBar {
            Menu("Archivo") {
                Item("Salir", onClick = ::exitApplication)
            }
        }
        //El resto de la ventana
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()) // Hace que el contenido sea desplazable verticalmente
        ) {
            DropMenu.showDropDownMenu(info)
        }
    }
}
