package com.miranda.eventosingsoft.ui.screens.create

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
object PastDateValidator : SelectableDates {
    override fun isSelectableDate(utcTimeMillis: Long): Boolean {
        // Bloquea fechas anteriores a hoy (ajustado a medianoche UTC)
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return utcTimeMillis >= calendar.timeInMillis
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrearEventoScreen(
    viewModel: CreateViewModel,
    onNavigateBack: () -> Unit,
    eventoId: Int? = null
) {
    val context = LocalContext.current
    // Estados del formulario
    var titulo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    var ubicacion by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    // Estado para la confirmacion de eliminacion
    var showDeleteConfirmation by remember { mutableStateOf(false) }
    val camposListos = titulo.isNotBlank() && fecha.isNotBlank() && ubicacion.isNotBlank()
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(selectableDates = PastDateValidator)
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    LaunchedEffect(eventoId) {
        if (eventoId != null && eventoId != 0) {
            viewModel.obtenerEventoPorId(eventoId).collect { eventoEncontrado ->
                eventoEncontrado?.let { data ->
                    titulo = data.titulo
                    descripcion = data.descripcion
                    fecha = data.fecha
                    ubicacion = data.ubicacion
                    imageUri = data.imagenUri?.let { Uri.parse(it) }
                }
            }
        }
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        uri?.let {
            try {
                context.contentResolver.takePersistableUriPermission(it, Intent.FLAG_GRANT_READ_URI_PERMISSION)
                imageUri = it
            } catch (e: Exception) { imageUri = it }
        }
    }

    // Confirmacion de eliminacion
    if (showDeleteConfirmation) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirmation = false },
            title = { Text("¿Eliminar evento?", fontWeight = FontWeight.Bold) },
            text = { Text("Esta acción borrará el evento de forma permanente. ¿Deseas continuar?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        // Solución al error: usamos una variable segura 'id' para el ViewModel
                        eventoId?.let { id ->
                            viewModel.eliminarEvento(id)
                            onNavigateBack()
                        }
                        showDeleteConfirmation = false
                    }
                ) {
                    Text("ELIMINAR", color = Color.Red, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteConfirmation = false }) {
                    Text("CANCELAR")
                }
            }
        )
    }

    // Calendario
    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    val selectedDate = datePickerState.selectedDateMillis
                    if (selectedDate != null) {
                        val timeZone = TimeZone.getDefault()
                        val offset = timeZone.getOffset(selectedDate)
                        val localTime = selectedDate + offset
                        fecha = formatter.format(Date(localTime))
                    }
                    showDatePicker = false
                }) {
                    Text("ACEPTAR", color = Color(0xFF6750A4), fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) { Text("CANCELAR") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = if (eventoId == null) "NUEVO EVENTO" else "EDITAR EVENTO",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = 1.5.sp,
                                color = Color(0xFF6750A4)
                            )
                        )
                        Surface(
                            modifier = Modifier.width(45.dp).height(3.dp).clip(RoundedCornerShape(2.dp)),
                            color = Color(0xFFFFC107)
                        ) {}
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Regresar", tint = Color(0xFF6750A4))
                    }
                },
                actions = {
                    // Boton de eliminar (Solo aparece si estamos editando)
                    if (eventoId != null && eventoId != 0) {
                        IconButton(onClick = { showDeleteConfirmation = true }) {
                            Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = Color.Red)
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // Imagen
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF6750A4).copy(alpha = 0.05f))
                    .clickable { launcher.launch(arrayOf("image/*")) },
                contentAlignment = Alignment.Center
            ) {
                if (imageUri != null) {
                    AsyncImage(
                        model = imageUri,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.AddAPhoto, contentDescription = null, modifier = Modifier.size(48.dp), tint = Color(0xFF6750A4))
                        Text("Añadir foto", color = Color(0xFF6750A4))
                    }
                }
            }

            OutlinedTextField(
                value = titulo,
                onValueChange = { titulo = it },
                label = { Text("Nombre del Evento *") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Default.Title, contentDescription = null) },
                shape = RoundedCornerShape(12.dp)
            )

            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = fecha,
                    onValueChange = { },
                    label = { Text("¿Cuándo será? *") },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = { Icon(Icons.Default.CalendarMonth, contentDescription = null) },
                    shape = RoundedCornerShape(12.dp),
                    readOnly = true,
                    enabled = false,
                    colors = OutlinedTextFieldDefaults.colors(
                        disabledTextColor = MaterialTheme.colorScheme.onSurface,
                        disabledBorderColor = MaterialTheme.colorScheme.outline,
                        disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        disabledLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
                Box(modifier = Modifier.matchParentSize().clickable { showDatePicker = true })
            }

            OutlinedTextField(
                value = ubicacion,
                onValueChange = { ubicacion = it },
                label = { Text("Lugar / Ubicación *") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = null) },
                trailingIcon = {
                    IconButton(onClick = {
                        try {
                            val uri = Uri.parse("geo:0,0?q=$ubicacion")
                            val intent = Intent(Intent.ACTION_VIEW, uri).apply {
                                setPackage("com.google.android.apps.maps")
                            }
                            context.startActivity(intent)
                        } catch (e: Exception) {
                            Toast.makeText(context, "Maps no disponible", Toast.LENGTH_SHORT).show()
                        }
                    }) {
                        Icon(Icons.Default.Map, contentDescription = null, tint = Color(0xFF6750A4))
                    }
                },
                shape = RoundedCornerShape(12.dp)
            )

            OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text("Detalles adicionales") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                leadingIcon = { Icon(Icons.Default.Description, contentDescription = null) },
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // seccion del boton con validacion
            Button(
                onClick = {
                    if (camposListos) {
                        viewModel.guardarEvento(
                            id = eventoId ?: 0,
                            titulo = titulo,
                            descripcion = descripcion,
                            fecha = fecha,
                            ubicacion = ubicacion,
                            imagenUri = imageUri?.toString()
                        )
                        onNavigateBack()
                    } else {
                        Toast.makeText(context, "Faltan campos obligatorios (*)", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (camposListos) Color(0xFF6750A4) else Color.Gray
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
            ) {
                Text(
                    text = if (eventoId == null) "REGISTRAR EVENTO" else "GUARDAR CAMBIOS",
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}