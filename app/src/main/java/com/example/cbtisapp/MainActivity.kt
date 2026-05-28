package com.example.cbtisapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.net.toUri
import com.example.cbtisapp.ui.theme.CbtisAppTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberUpdatedMarkerState
import kotlinx.coroutines.launch

// 📍 Coordenadas de los Puntos de Reunión del Plantel
object PuntosReunion {
    val BICEFALO = LatLng(28.616682, -106.029799)
    val CANCHAS = LatLng(28.614407, -106.029071)
    val CASETA = LatLng(28.616681, -106.028659)
}

enum class Screen {
    Home,
    MapSelection,
    Protocols,
    Contacts,
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 🚀 Inicializar Google Maps preventivamente para evitar crashes con BitmapDescriptorFactory
        try {
            MapsInitializer.initialize(applicationContext, MapsInitializer.Renderer.LATEST) {
                Log.d("MapsInit", "Google Maps SDK initialized with renderer: ${it.name}")
            }
        } catch (e: Exception) {
            Log.e("MapsInit", "Error initializing Maps SDK", e)
        }

        setContent {
            CbtisAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainAppNavigation()
                }
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MainAppNavigation() {
    val context = LocalContext.current
    val themeManager = remember { ThemeManager(context) }
    val scope = rememberCoroutineScope()
    val isDarkMode by themeManager.isDarkModeFlow.collectAsState(initial = false)

    var currentScreen by remember { mutableStateOf(Screen.Home) }
    var showCredits by remember { mutableStateOf(false) }

    val cbtis122 = LatLng(28.615472, -106.029222)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(cbtis122, 18f)
    }

    val locationPermissionState = rememberPermissionState(
        android.Manifest.permission.ACCESS_FINE_LOCATION
    )

    LaunchedEffect(Unit) {
        if (!locationPermissionState.status.isGranted) {
            locationPermissionState.launchPermissionRequest()
        }
    }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = if (isDarkMode) Color(0xFF1E1E1E) else Color.White,
                tonalElevation = 8.dp,
                modifier = Modifier.clip(RoundedCornerShape(2.dp))
            ) {
                NavigationBarItem(
                    selected = currentScreen == Screen.Home,
                    onClick = { currentScreen = Screen.Home },
                    label = { Text("Inicio", fontSize = 12.sp) },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF830122),
                        selectedTextColor = Color(0xFF830122),
                        indicatorColor = if (isDarkMode) Color(0xFF2D2D2D) else Color(0xFFE9ECEF),
                        unselectedIconColor = Color.Gray,
                        unselectedTextColor = Color.Gray
                    )
                )
                NavigationBarItem(
                    selected = currentScreen == Screen.MapSelection,
                    onClick = { currentScreen = Screen.MapSelection },
                    label = { Text("Rutas", fontSize = 12.sp) },
                    icon = { Icon(Icons.Default.Map, contentDescription = "Routes") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF830122),
                        selectedTextColor = Color(0xFF830122),
                        indicatorColor = if (isDarkMode) Color(0xFF2D2D2D) else Color(0xFFE9ECEF),
                        unselectedIconColor = Color.Gray,
                        unselectedTextColor = Color.Gray
                    )
                )

                NavigationBarItem(
                    selected = currentScreen == Screen.Protocols,
                    onClick = { currentScreen = Screen.Protocols },
                    label = { Text("Protocolos", fontSize = 12.sp) },
                    icon = { Icon(Icons.Default.Description, contentDescription = "Protocols") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF830122),
                        selectedTextColor = Color(0xFF830122),
                        indicatorColor = if (isDarkMode) Color(0xFF2D2D2D) else Color(0xFFE9ECEF),
                        unselectedIconColor = Color.Gray,
                        unselectedTextColor = Color.Gray
                    )
                )

                NavigationBarItem(
                    selected = currentScreen == Screen.Contacts,
                    onClick = { currentScreen = Screen.Contacts },
                    label = { Text("Contactos", fontSize = 12.sp) },
                    icon = { Icon(Icons.Default.ContactPhone, contentDescription = "Contacts") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF830122),
                        selectedTextColor = Color(0xFF830122),
                        indicatorColor = if (isDarkMode) Color(0xFF2D2D2D) else Color(0xFFE9ECEF),
                        unselectedIconColor = Color.Gray,
                        unselectedTextColor = Color.Gray
                    )
                )
            }
        }
    ) { innerPadding ->
        if (showCredits) {
            CreditsBubble(isDarkMode = isDarkMode, onDismiss = { showCredits = false })
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(if (isDarkMode) Color(0xFF121212) else Color(0xFFF8F9FA))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF031A34))
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(onClick = { showCredits = true }) {
                    Icon(
                        imageVector = Icons.Outlined.Shield,
                        contentDescription = "Safe Icon",
                        tint = Color.Red,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "SchoolSafe",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                )

                Spacer(modifier = Modifier.weight(1f))

                IconButton(
                    onClick = {
                        scope.launch { themeManager.setDarkMode(!isDarkMode) }
                    }
                ) {
                    Icon(
                        imageVector = if (isDarkMode) Icons.Default.DarkMode else Icons.Default.LightMode,
                        contentDescription = "Theme Toggle",
                        tint = Color.White
                    )
                }

                Button(
                    onClick = {
                        val intent = Intent(Intent.ACTION_DIAL).apply { data = "tel:911".toUri() }
                        context.startActivity(intent)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red,
                        contentColor = Color.White
                    ),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(text = "SOS", fontWeight = FontWeight.ExtraBold, fontSize = 14.sp)
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                when (currentScreen) {
                    Screen.Home -> {
                        RoutesScreenContent(
                            isDarkMode = isDarkMode,
                            cameraPositionState = cameraPositionState,
                            isLocationGranted = locationPermissionState.status.isGranted,
                            onNavigateToSelection = { currentScreen = Screen.MapSelection }
                        )
                    }

                    Screen.MapSelection -> {
                        SelectionScreen(
                            onEdificioSelected = {
                                currentScreen = Screen.Home
                            }
                        )
                    }

                    Screen.Protocols -> {
                        ProtocolsScreen()
                    }

                    Screen.Contacts -> {
                        ContactsScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun RoutesScreenContent(
    isDarkMode: Boolean,
    cameraPositionState: CameraPositionState,
    isLocationGranted: Boolean,
    onNavigateToSelection: () -> Unit
) {
    // 🛠️ Definición del marcador verde de Protección Civil (con fallback para evitar crashes)
    val greenMarker = remember {
        try {
            BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
        } catch (_: Exception) {
            // Fallback al marcador rojo por defecto si falla la inicialización
            BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.size(120.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (isDarkMode) Color(0xFF1E1E1E) else Color.White
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_escudo_dgeti),
                    contentDescription = "Logo Institucional Dgeti",
                    modifier = Modifier.size(85.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Rutas de Evacuación",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = if (isDarkMode) Color(0xFFE63946) else Color(0xFF830122)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Rutas de evacuacion directas desde tu ubicación",
            fontSize = 14.sp,
            color = if (isDarkMode) Color.White.copy(alpha = 0.7f) else Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (isDarkMode) Color(0xFF1E1E1E) else Color.White
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            shape = RoundedCornerShape(24.dp)
        ) {
            // 🛰️ GoogleMap estructurado como bloque contenedor
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                properties = MapProperties(
                    isMyLocationEnabled = isLocationGranted,
                    mapType = MapType.HYBRID
                )
            ) {
                // 🟢 Marcador 1: Bicéfalo
                Marker(
                    state = rememberUpdatedMarkerState(position = PuntosReunion.BICEFALO),
                    title = "Punto de Reunión: Bicéfalo",
                    snippet = "Zona segura: Electrónica, Edificio J, Cómputo, Edificio S",
                    icon = greenMarker
                )

                // 🟢 Marcador 2: Canchas
                Marker(
                    state = rememberUpdatedMarkerState(position = PuntosReunion.CANCHAS),
                    title = "Punto de Reunión: Canchas",
                    snippet = "Zona segura: Edificios A y B, Electromecánica, Robótica, EBC",
                    icon = greenMarker
                )

                // 🟢 Marcador 3: Caseta
                Marker(
                    state = rememberUpdatedMarkerState(position = PuntosReunion.CASETA),
                    title = "Punto de Reunión: Caseta",
                    snippet = "Zona segura: Edificio C y Administrativo",
                    icon = greenMarker
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { onNavigateToSelection() },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF830122),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "Rutas",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun CreditsBubble(isDarkMode: Boolean, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (isDarkMode) Color(0xFF1E1E1E) else Color.White
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            shape = RoundedCornerShape(24.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.Stars,
                    contentDescription = null,
                    tint = Color(0xFFFFD700),
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Tono",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = if (isDarkMode) Color.White else Color.Black
                )
                Text(
                    text = "best developer of the world forever and ever ",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (isDarkMode) Color.White.copy(alpha = 0.7f) else Color.Gray,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF830122))
                ) {
                    Text("Cerrar")
                }
            }
        }
    }
}