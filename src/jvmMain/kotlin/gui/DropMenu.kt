package gui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import entities.Evaluacion

object DropMenu{
    @Composable
    fun showDropDownMenu(clase: Evaluacion) {
        val expandedState = remember { mutableStateOf(false) }
        val alumnos: MutableList<String> = mutableListOf()
        alumnos.add("Selecciona alumno")
        for (alumno in clase.alumno) {
            alumnos.add(alumno.alumno)
        }
        val selectedItem = remember { mutableStateOf(alumnos.first()) }

        MaterialTheme {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(modifier = Modifier.wrapContentSize(Alignment.Center)) {
                    Row(modifier = Modifier.clickable { expandedState.value = !expandedState.value }) {
                        Text(
                            text = selectedItem.value,
                            style = MaterialTheme.typography.h6
                        )
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    DropdownMenu(
                        expanded = expandedState.value,
                        onDismissRequest = { expandedState.value = false }
                    ) {
                        alumnos.forEach { item ->
                            DropdownMenuItem(onClick = {
                                selectedItem.value = item
                                expandedState.value = false
                            }) {
                                Text(text = item)
                            }
                        }
                    }
                }
                infoAlumno(clase, selectedItem.value)
            }
        }

    }

    @Composable
    private fun infoAlumno(clase: Evaluacion, alumnoseleccionado: String) {
        Column {
            for (alumno in clase.alumno) {
                if (alumno.alumno == alumnoseleccionado) {
                    Row(modifier = Modifier.width(1000.dp).border(width = 2.dp, color = Color.Black)) {
                        Text(
                            text = "  ${alumno.inicial}     ${alumno.alumno}\n  ${alumno.modulo}     ${alumno.notamedia}",
                            style = MaterialTheme.typography.h6,
                            modifier = Modifier.padding(end = 15.dp)
                        )
                    }
                    Column(
                        modifier = Modifier.wrapContentSize(Alignment.Center).padding(end = 15.dp)
                            .border(width = 2.dp, color = Color.Black)
                    ) {
                        clase.RA.forEach { resultadoAprendizaje ->
                            if (resultadoAprendizaje.inicial == alumno.inicial) {
                                Row(modifier = Modifier.width(1000.dp)) {
                                    Text(
                                        text = "  ${resultadoAprendizaje.RA}     ${resultadoAprendizaje.porcentaje}%     "+resultadoAprendizaje.nota.toString(),
                                        style = MaterialTheme.typography.h6,
                                        modifier = Modifier.padding(end = 15.dp)
                                    )
                                }
                                Row(modifier = Modifier.width(1000.dp)) {
                                    Text(
                                        text = " ",
                                        style = MaterialTheme.typography.h6,
                                        modifier = Modifier.padding(end = 15.dp)
                                    )
                                }
                                clase.CE.forEach { criterioEvaluacion ->
                                    if (criterioEvaluacion.inicial == alumno.inicial && criterioEvaluacion.UD.contains(resultadoAprendizaje.RA)) {
                                        Row(modifier = Modifier.width(1000.dp)) {
                                            Text(
                                                text = "  ${criterioEvaluacion.UD}     ${criterioEvaluacion.porcentaje}%     "+criterioEvaluacion.nota.toString(),
                                                style = MaterialTheme.typography.h6,
                                                modifier = Modifier.padding(end = 15.dp)
                                            )
                                        }
                                    }
                                }
                                Row(modifier = Modifier.width(1000.dp)) {
                                    Text(
                                        text = " ",
                                        style = MaterialTheme.typography.h6,
                                        modifier = Modifier.padding(end = 15.dp)
                                    )
                                }
                                clase.instrumentos.forEach { instrumento ->
                                    if (instrumento.inicial == alumno.inicial && instrumento.UD == resultadoAprendizaje.RA) {
                                        Row(modifier = Modifier.width(1000.dp)) {
                                            Text(
                                                text = "  ${instrumento.materia} ${instrumento.UD}     ${instrumento.porcentaje}%     "+instrumento.nota.toString(),
                                                style = MaterialTheme.typography.h6,
                                                modifier = Modifier.padding(end = 15.dp)
                                            )
                                        }
                                    }
                                }
                                Row(modifier = Modifier.width(1000.dp)) {
                                    Text(
                                        text = " ",
                                        style = MaterialTheme.typography.h6,
                                        modifier = Modifier.padding(end = 15.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
