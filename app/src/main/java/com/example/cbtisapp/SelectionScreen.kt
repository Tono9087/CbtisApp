package com.example.cbtisapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Edificio(
    val nombre: String,
    val id: String
)

data class ZonaEvacuacion(
    val puntoReunion: String,
    val edificios: List<Edificio>
)

@Composable
fun EdificioGridItem(nombre: String, isDarkMode: Boolean, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .padding(6.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = if (isDarkMode) Color(0xFF1E1E1E) else Color.White
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = nombre,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = if (isDarkMode) Color.White.copy(alpha = 0.9f) else Color(0xFF2D3748)
            )

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                tint = if (isDarkMode) Color(0xFFFF4D6D) else Color(0xFF830122),
                modifier = Modifier
                    .size(20.dp)
                    .align(Alignment.End)
            )
        }
    }
}

@Composable
fun SelectionScreen(onEdificioSelected: (String) -> Unit) {
    val context = LocalContext.current
    val themeManager = remember { ThemeManager(context) }
    val isDarkMode by themeManager.isDarkModeFlow.collectAsState(initial = false)

    val zonas = remember {
        listOf(
            ZonaEvacuacion(
                puntoReunion = "Canchas",
                edificios = listOf(
                    Edificio("Edificio A", "a_canchas"),
                    Edificio("Edificio B", "b_canchas"),
                    Edificio("Electromecánica", "em_canchas"),
                    Edificio("Robótica", "rb_canchas"),
                    Edificio("EBC", "ebc_canchas"),
                    Edificio("Motorolitas", "moto_canchas"),
                    Edificio("Cafetería", "cafe_canchas")
                )
            ),
            ZonaEvacuacion(
                puntoReunion = "Bicéfalo",
                edificios = listOf(
                    Edificio("Electrónica", "el_bicefalo"),
                    Edificio("Edificio J", "j_bicefalo"),
                    Edificio("Cómputo", "cp_bicefalo"),
                    Edificio("Edificio S", "s_bicefalo")
                )
            ),
            ZonaEvacuacion(
                puntoReunion = "Caseta",
                edificios = listOf(
                    Edificio("Edificio C", "c_caseta"),
                    Edificio("Administrativo", "ad_caseta")
                )
            )
        )
    }

    var zonaSeleccionada by remember { mutableStateOf("Canchas") }

    val edificiosFiltrados = zonas.firstOrNull { it.puntoReunion == zonaSeleccionada }?.edificios ?: emptyList()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(if (isDarkMode) Color(0xFF121212) else Color(0xFFF8F9FA))
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Seleccione su ubicación",
            color = if (isDarkMode) Color(0xFFFF4D6D) else Color(0xFF830122),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Seleccione el edificio donde se encuentra para ver la ruta de evacuación.",
            fontSize = 14.sp,
            color = if (isDarkMode) Color.White.copy(alpha = 0.6f) else Color.Gray,
            lineHeight = 20.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (isDarkMode) Color(0xFF1E1E1E) else Color(0xFFFCE8E6)
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                zonas.forEach { zona ->
                    val isActive = zona.puntoReunion == zonaSeleccionada

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(38.dp)
                            .background(
                                color = if (isActive) {
                                    if (isDarkMode) Color(0xFFFF4D6D) else Color(0xFF830122)
                                } else Color.Transparent,
                                shape = RoundedCornerShape(20.dp)
                            )
                            .clickable { zonaSeleccionada = zona.puntoReunion },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = zona.puntoReunion,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = if (isActive) Color.White else (if (isDarkMode) Color.Gray else Color(0xFF830122))
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        edificiosFiltrados.chunked(2).forEach { fila ->
            Row(modifier = Modifier.fillMaxWidth()) {
                fila.forEach { edificio ->
                    Box(modifier = Modifier.weight(1f)) {
                        EdificioGridItem(
                            nombre = edificio.nombre,
                            isDarkMode = isDarkMode
                        ) {
                            onEdificioSelected(edificio.id)
                        }
                    }
                }
                if (fila.size == 1) Spacer(modifier = Modifier.weight(1f))
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (isDarkMode) Color(0xFF2C1015) else Color(0xFF830122)
            )
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = "Mantenga la calma. Al seleccionar su ubicación, se mostrarán las flechas rojas específicas para su salida.",
                    fontSize = 13.sp,
                    color = Color.White.copy(alpha = 0.9f),
                    lineHeight = 18.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}