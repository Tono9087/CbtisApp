package com.example.cbtisapp

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.net.toUri

data class Contact(
    val name: String,
    val relation: String,
    val phoneNumber: String,
    val areaCode : String
)

val mexicoAreaCodes = listOf(
    "1", "1-340", "1-670", "1-671", "1-684", "1-787", "1-939", "1-441", "299", "508", "52",
    "1-242", "1-246", "1-264", "1-268", "1-284", "1-345", "1-473", "1-649", "1-664", "1-721",
    "1-758", "1-767", "1-784", "1-809", "1-829", "1-849", "1-868", "1-869", "1-876", "1-658",
    "297", "509", "53", "590", "596", "599", "501", "502", "503", "504", "505", "506", "507",
    "500", "51", "54", "55", "56", "57", "58", "591", "592", "593", "594", "595", "597", "598",
    "20", "211", "212", "213", "216", "218", "220", "221", "222", "223", "224", "225", "226",
    "227", "228", "229", "230", "231", "232", "233", "234", "235", "236", "237", "238", "239",
    "240", "241", "242", "243", "244", "245", "246", "247", "248", "249", "250", "251", "252",
    "253", "254", "255", "256", "257", "258", "260", "261", "262", "263", "264", "265", "266",
    "267", "268", "269", "27", "290", "291", "298", "30", "31", "32", "33", "34", "350", "351",
    "352", "353", "354", "355", "356", "357", "358", "359", "36", "370", "371", "372", "373",
    "374", "375", "376", "377", "378", "379", "380", "381", "382", "383", "385", "386", "387",
    "389", "39", "40", "41", "420", "421", "423", "43", "44", "45", "46", "47", "48", "49", "7",
    "81", "82", "84", "850", "852", "853", "855", "856", "86", "880", "886", "90", "91", "92",
    "93", "94", "95", "960", "961", "962", "963", "964", "965", "966", "967", "968", "970",
    "971", "972", "973", "974", "975", "976", "977", "98", "992", "993", "994", "995", "996", "998",
    "60", "61", "62", "63", "64", "65", "66", "670", "672", "673", "674", "675", "676", "677",
    "678", "679", "680", "681", "682", "683", "685", "686", "687", "688", "689", "690", "691", "692"
)

@Composable
fun ContactItem(contact: Contact, isDarkMode: Boolean) {
    val context = LocalContext.current
    val cardBg = if (isDarkMode) Color(0xFF1E1E1E) else Color.White
    val primaryText = if (isDarkMode) Color.White else Color.Black
    val secondaryText = if (isDarkMode) Color.White.copy(alpha = 0.6f) else Color.Gray
    val iconBg = if (isDarkMode) Color(0xFF2D2D2D) else Color(0xFFF0F0F0)
    val borderColor = if (isDarkMode) Color.DarkGray else Color.LightGray

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = cardBg),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (contact.name == "Natalia") {
                Image(
                    painter = painterResource(id = R.drawable.natalia_contact),
                    contentDescription = "Foto de ${contact.name}",
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .border(1.dp, borderColor, CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(iconBg)
                        .border(1.dp, borderColor, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = if (isDarkMode) Color.LightGray else Color.Gray,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = contact.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = primaryText
                )

                Spacer(modifier = Modifier.width(2.dp))

                Text(
                    text = contact.relation,
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                    color = secondaryText
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color(0xff830122), shape = CircleShape)
                    .clickable {
                        val fullNumber = "${contact.areaCode}${contact.phoneNumber}"
                        val callIntent = Intent(Intent.ACTION_DIAL).apply {
                            data = "tel:$fullNumber".toUri()
                        }
                        context.startActivity(callIntent)
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Call,
                    contentDescription = "Llamar a ${contact.name}",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
fun EmergencyGridItem(contact: Contact, isDarkMode: Boolean) {
    val context = LocalContext.current
    val cardBg = if (isDarkMode) Color(0xFF1E1E1E) else Color.White
    val accentColor = if (isDarkMode) Color(0xFFE63946) else Color(0xFF830122)
    val iconBg = if (isDarkMode) Color(0xFF2D1614) else Color(0xFFFCE8E6)

    Card(
        modifier = Modifier
            .width(160.dp)
            .height(130.dp)
            .padding(6.dp)
            .clickable {
                val fullNumber = "${contact.areaCode}${contact.phoneNumber}"
                val callIntent = Intent(Intent.ACTION_DIAL).apply {
                    data = "tel:$fullNumber".toUri()
                }
                context.startActivity(callIntent)
            },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = cardBg),
        elevation = CardDefaults.cardElevation(if (isDarkMode) 0.dp else 2.dp)
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(4.dp)
                    .background(accentColor)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .background(iconBg, shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Call,
                        contentDescription = null,
                        tint = accentColor,
                        modifier = Modifier.size(22.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = contact.name.uppercase(),
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 12.sp,
                    color = accentColor,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = contact.relation,
                    fontSize = 10.sp,
                    color = if (isDarkMode) Color.White.copy(alpha = 0.6f) else Color.Gray,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactsScreen() {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val themeManager = remember { ThemeManager(context) }
    val isDarkMode by themeManager.isDarkModeFlow.collectAsState(initial = false)

    var searchQuery by remember { mutableStateOf("") }
    val showAddDialog = remember { mutableStateOf(false) }
    var newName by remember { mutableStateOf("") }
    var newRelation by remember { mutableStateOf("") }
    var newNumber by remember { mutableStateOf("") }
    var selectedAreaCode by remember { mutableStateOf("52") }
    var expanded by remember { mutableStateOf(false) }

    val dialogBg = if (isDarkMode) Color(0xFF1E1E1E) else Color.White
    val dialogAccent = if (isDarkMode) Color(0xFFE63946) else Color(0xFF830122)
    val dialogText = if (isDarkMode) Color.White else Color.Black

    var institutionalContacts by remember {
        mutableStateOf(
            listOf(
                Contact("Natalia", "Loca Homeless", "1234567", "614")
            )
        )
    }

    if (showAddDialog.value) {
        Dialog(onDismissRequest = { showAddDialog.value = false }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = dialogBg)
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Añadir contacto",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = dialogAccent,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    OutlinedTextField(
                        value = newName,
                        onValueChange = { newName = it },
                        label = { Text("Nombre") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = dialogAccent,
                            unfocusedBorderColor = if (isDarkMode) Color.DarkGray else Color.LightGray,
                            focusedTextColor = dialogText,
                            unfocusedTextColor = dialogText,
                            focusedLabelColor = dialogAccent,
                            unfocusedLabelColor = Color.Gray
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = newRelation,
                        onValueChange = { newRelation = it },
                        label = { Text("Relación") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = dialogAccent,
                            unfocusedBorderColor = if (isDarkMode) Color.DarkGray else Color.LightGray,
                            focusedTextColor = dialogText,
                            unfocusedTextColor = dialogText,
                            focusedLabelColor = dialogAccent,
                            unfocusedLabelColor = Color.Gray
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ExposedDropdownMenuBox(
                            expanded = expanded,
                            onExpandedChange = { expanded = !expanded },
                            modifier = Modifier.width(100.dp)
                        ) {
                            OutlinedTextField(
                                value = selectedAreaCode,
                                onValueChange = {},
                                readOnly = true,
                                label = { Text("Lada") },
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                                modifier = Modifier.menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable, true),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = dialogAccent,
                                    unfocusedBorderColor = if (isDarkMode) Color.DarkGray else Color.LightGray,
                                    focusedTextColor = dialogText,
                                    unfocusedTextColor = dialogText,
                                    focusedLabelColor = dialogAccent,
                                    unfocusedLabelColor = Color.Gray
                                )
                            )
                            ExposedDropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false },
                                modifier = Modifier.background(dialogBg)
                            ) {
                                mexicoAreaCodes.forEach { code ->
                                    DropdownMenuItem(
                                        text = { Text(code, color = dialogText) },
                                        onClick = {
                                            selectedAreaCode = code
                                            expanded = false
                                        }
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        OutlinedTextField(
                            value = newNumber,
                            onValueChange = { newNumber = it },
                            label = { Text("Número") },
                            modifier = Modifier.weight(1f),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = dialogAccent,
                                unfocusedBorderColor = if (isDarkMode) Color.DarkGray else Color.LightGray,
                                focusedTextColor = dialogText,
                                unfocusedTextColor = dialogText,
                                focusedLabelColor = dialogAccent,
                                unfocusedLabelColor = Color.Gray
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = { showAddDialog.value = false }) {
                            Text("Cancelar", color = Color.Gray)
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Button(
                            onClick = {
                                if (newName.isNotBlank() && newNumber.isNotBlank()) {
                                    val newContact = Contact(
                                        name = newName,
                                        relation = newRelation,
                                        phoneNumber = newNumber,
                                        areaCode = selectedAreaCode
                                    )
                                    institutionalContacts = institutionalContacts + newContact
                                    
                                    newName = ""
                                    newRelation = ""
                                    newNumber = ""
                                    showAddDialog.value = false
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = dialogAccent),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("Guardar", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }

    val emergencyGridContacts = listOf(
        Contact("911 Emergencia", "Atención Inmediata", "911", ""),
        Contact("Cruz Roja", "Servicios Médicos", "065", ""),
        Contact("Bomberos", "Prevención de Incendios", "116", ""),
        Contact("Policía Escolar", "Seguridad Perimetral", "060", "")
    )

    val filteredInstitutional = institutionalContacts.filter {
        it.name.contains(searchQuery, ignoreCase = true) || it.relation.contains(searchQuery, ignoreCase = true)
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog.value = true },
                containerColor = dialogAccent,
                contentColor = Color.White,
                shape = CircleShape
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar Contacto")
            }
        },
        containerColor = Color.Transparent
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { focusManager.clearFocus() }
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Buscar contactos...", color = Color.Gray) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Color.Gray) },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = dialogAccent,
                    unfocusedBorderColor = if (isDarkMode) Color.DarkGray else Color.LightGray,
                    focusedContainerColor = if (isDarkMode) Color(0xFF1E1E1E) else Color.White,
                    unfocusedContainerColor = if (isDarkMode) Color(0xFF1E1E1E) else Color.White,
                    focusedTextColor = dialogText,
                    unfocusedTextColor = dialogText
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Directorio de Emergencia",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = dialogAccent
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Box(modifier = Modifier.weight(1f)) { EmergencyGridItem(emergencyGridContacts[0], isDarkMode) }
                Box(modifier = Modifier.weight(1f)) { EmergencyGridItem(emergencyGridContacts[1], isDarkMode) }
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Box(modifier = Modifier.weight(1f)) { EmergencyGridItem(emergencyGridContacts[2], isDarkMode) }
                Box(modifier = Modifier.weight(1f)) { EmergencyGridItem(emergencyGridContacts[3], isDarkMode) }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Contactos Institucionales",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = dialogAccent
            )

            Spacer(modifier = Modifier.height(12.dp))

            filteredInstitutional.forEach { contact ->
                ContactItem(contact = contact, isDarkMode = isDarkMode)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = dialogAccent)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "Protocolo de Llamada",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Mantenga la calma al llamar. Proporcione su ubicación exacta y la naturaleza de la emergencia de manera concisa.",
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.85f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}
