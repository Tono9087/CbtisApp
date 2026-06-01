package com.example.cbtisapp

import android.net.Uri
import kotlin.OptIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

@androidx.annotation.OptIn(UnstableApi::class)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoutePlayerScreen(
    edificioId: String,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val themeManager = remember { ThemeManager(context) }
    val isDarkMode by themeManager.isDarkModeFlow.collectAsState(initial = false)

    val colorGuinda = if (isDarkMode) Color(0xFFFF4D6D) else Color(0xFF830122)
    val colorFondo = if (isDarkMode) Color(0xFF121212) else Color(0xFFFDFBFB)
    val colorTarjetaDesc = if (isDarkMode) Color(0xFF1E1E1E) else Color(0xFFFFF8F8)
    val colorBordeDesc = if (isDarkMode) Color(0xFF3A1E24) else Color(0xFFFCE2E2)
    val colorTarjetaPunto = if (isDarkMode) Color(0xFF1E1E1E) else Color.White

    val (nombreEdificio, puntoReunion) = remember(edificioId) {
        when {
            edificioId.contains("canchas") -> {
                val nom = when(edificioId) {
                    "a_canchas" -> "Edificio A"
                    "b_canchas" -> "Edificio B"
                    "cafe_canchas" -> "Cafetería"
                    "ebc_canchas" -> "EBC"
                    "em_canchas" -> "Electromecánica"
                    "moto_canchas" -> "Motorolitas"
                    else -> "Módulo"
                }
                Pair(nom, "Canchas")
            }
            edificioId.contains("bicefalo") -> {
                val nom = when(edificioId) {
                    "el_bicefalo" -> "Electrónica"
                    "j_bicefalo" -> "Edificio J"
                    "cp_bicefalo" -> "Cómputo"
                    else -> "Edificio S"
                }
                Pair(nom, "Bicéfalo")
            }
            else -> {
                val nom = if (edificioId == "c_caseta") "Edificio C" else "Administrativo"
                Pair(nom, "Caseta")
            }
        }
    }

    val videoResId = remember(edificioId) { RutaRepository.obtenerVideoResource(edificioId) }

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            val videoUri = Uri.parse("android.resource://${context.packageName}/$videoResId")
            setMediaItem(MediaItem.fromUri(videoUri))
            repeatMode = Player.REPEAT_MODE_ALL
            prepare()
            playWhenReady = true
        }
    }

    DisposableEffect(Unit) {
        onDispose { exoPlayer.release() }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ruta: $nombreEdificio", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF830122))
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(colorFondo)
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(230.dp)
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                AndroidView(
                    factory = { ctx ->
                        PlayerView(ctx).apply {
                            player = exoPlayer
                            useController = true
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = colorTarjetaDesc),
                    border = androidx.compose.foundation.BorderStroke(1.dp, colorBordeDesc)
                ) {
                    Row(modifier = Modifier.padding(16.dp)) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = Color(0xFF830122),
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Descripción de la Ruta",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF830122)
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Esta ruta lo guiará desde el $nombreEdificio hasta el punto de reunión en las $puntoReunion. Siga las señales rojas en el piso y mantenga la calma.",
                                fontSize = 14.sp,
                                color = if (isDarkMode) Color.White.copy(alpha = 0.8f) else Color(0xFF4A5568),
                                lineHeight = 20.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "PUNTOS CRÍTICOS",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isDarkMode) Color.LightGray else Color(0xFFA0AEC0),
                    letterSpacing = 1.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                TimelineItem(
                    indicador = "1",
                    titulo = "Salida de Emergencia 1",
                    descripcion = "Ubicada al final del pasillo sur, $nombreEdificio.",
                    badgeText = "LIBRE",
                    isDarkMode = isDarkMode,
                    cardColor = colorTarjetaPunto
                )

                TimelineItem(
                    indicador = "2",
                    titulo = "Pasillo Principal",
                    descripcion = "Cruce directo hacia el patio central.",
                    isDarkMode = isDarkMode,
                    cardColor = colorTarjetaPunto
                )

                TimelineItem(
                    indicador = "✓",
                    titulo = "Punto de Reunión $puntoReunion",
                    descripcion = "Zona segura final. Espere instrucciones del coordinador.",
                    isFinal = true,
                    isDarkMode = isDarkMode,
                    cardColor = colorTarjetaPunto,
                    mostrarImagenCancha = (puntoReunion == "Canchas")
                )
            }
        }
    }
}

@Composable
fun TimelineItem(
    indicador: String,
    titulo: String,
    descripcion: String,
    badgeText: String? = null,
    isFinal: Boolean = false,
    isDarkMode: Boolean,
    cardColor: Color,
    mostrarImagenCancha: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(32.dp)
        ) {
            val esCheck = indicador == "✓"
            Box(
                modifier = Modifier
                    .size(26.dp)
                    .background(
                        color = if (esCheck) Color(0xFF107C41) else Color(0xFF6B021A),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (esCheck) {
                    Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
                } else {
                    Text(text = indicador, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }

            if (!isFinal) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(2.dp)
                        .background(Color(0xFFE2E8F0))
                        .padding(vertical = 4.dp)
                )
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        Card(
            modifier = Modifier
                .weight(1f)
                .padding(bottom = 16.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = cardColor),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(4.dp)
                        .background(if (indicador == "✓") Color(0xFF107C41) else Color(0xFF6B021A))
                )

                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = titulo,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isDarkMode) Color.White else Color(0xFF2D3748)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = descripcion,
                        fontSize = 13.sp,
                        color = if (isDarkMode) Color.White.copy(alpha = 0.6f) else Color(0xFF718096),
                        lineHeight = 18.sp
                    )

                    if (badgeText != null) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Box(
                            modifier = Modifier
                                .background(Color(0xFFFDE8E8), shape = RoundedCornerShape(4.dp))
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text(text = badgeText, color = Color(0xFFE53E3E), fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    if (mostrarImagenCancha) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0xFF042940)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Visualización de Canchas", color = Color(0xFF00FA9A).copy(alpha = 0.5f), fontSize = 12.sp)
                        }
                    }
                }
            }
        }
    }
}