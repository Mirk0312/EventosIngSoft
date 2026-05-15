package com.miranda.eventosingsoft.ui.screens.home
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.miranda.eventosingsoft.data.local.EventoEntity
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNavigateToCreate: () -> Unit,
    onNavigateToDetail: (Int) -> Unit,
    onNavigateToFavorites: () -> Unit
) {
    val listaDeEventos by viewModel.listaEventos.collectAsState()
    // Menu
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var showAboutDialog by remember { mutableStateOf(false) }

    // Confirmacion de eliminacion
    var showDeleteDialog by remember { mutableStateOf(false) }
    var eventoParaBorrar by remember { mutableStateOf<EventoEntity?>(null) }

    // Diálogo de confirmación de eliminación
    if (showDeleteDialog && eventoParaBorrar != null) {
        AlertDialog(
            onDismissRequest = {
                showDeleteDialog = false
                eventoParaBorrar = null
            },
            title = { Text("¿Eliminar evento?", fontWeight = FontWeight.Bold) },
            text = { Text("¿Estás seguro de que deseas borrar \"${eventoParaBorrar?.titulo}\"? Esta acción no se puede deshacer.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        eventoParaBorrar?.let { viewModel.borrarEvento(it) }
                        showDeleteDialog = false
                        eventoParaBorrar = null
                    }
                ) {
                    Text("ELIMINAR", color = Color.Red, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDeleteDialog = false
                    eventoParaBorrar = null
                }) {
                    Text("CANCELAR")
                }
            },
            shape = RoundedCornerShape(16.dp)
        )
    }

    // acerca de
    if (showAboutDialog) {
        AlertDialog(
            onDismissRequest = { showAboutDialog = false },
            confirmButton = {
                TextButton(onClick = { showAboutDialog = false }) {
                    Text("Cerrar", color = Color(0xFF6750A4))
                }
            },
            title = { Text("Acerca de la App", fontWeight = FontWeight.Bold) },
            text = {
                Column {
                    Text("Proyecto: Gestión de Eventos", fontWeight = FontWeight.SemiBold)
                    Text("Materia: Desarrollo de aplicaciones moviles", fontSize = 13.sp, color = Color.Gray)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Desarrollado por:", fontWeight = FontWeight.Bold, color = Color(0xFF6750A4))
                    Text(
                        "• Karim Miranda\n• Martin Durán\n• Emiliano Vásquez",
                        lineHeight = 20.sp
                    )
                }
            },
            shape = RoundedCornerShape(16.dp)
        )
    }

    // estructura del menu lateral
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = Color.White,
                modifier = Modifier.width(300.dp)
            ) {
                // Encabezado del Menú
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .background(Color(0xFF6750A4)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Surface(
                            modifier = Modifier.size(70.dp),
                            shape = RoundedCornerShape(12.dp),
                            color = Color.White.copy(alpha = 0.2f)
                        ) {
                            Icon(Icons.Default.School, contentDescription = null, tint = Color.White, modifier = Modifier.padding(12.dp))
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Text("EVENTOS UES", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Opción Inicio
                NavigationDrawerItem(
                    label = { Text("Inicio", fontWeight = FontWeight.Medium) },
                    selected = true,
                    onClick = { scope.launch { drawerState.close() } },
                    icon = { Icon(Icons.Default.Home, contentDescription = null) },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                    colors = NavigationDrawerItemDefaults.colors(selectedContainerColor = Color(0xFF6750A4).copy(alpha = 0.1f), selectedIconColor = Color(0xFF6750A4), selectedTextColor = Color(0xFF6750A4))
                )

                // Opción Acerca de
                NavigationDrawerItem(
                    label = { Text("Acerca de", fontWeight = FontWeight.Medium) },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        showAboutDialog = true
                    },
                    icon = { Icon(Icons.Default.Info, contentDescription = null) },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
            }
        }
    ) {

        Scaffold(
            containerColor = MaterialTheme.colorScheme.background,
            topBar = {
                CenterAlignedTopAppBar(
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Abrir Menú", tint = Color(0xFF6750A4))
                        }
                    },
                    title = {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "EVENTOS UES",
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
                    actions = {
                        IconButton(onClick = onNavigateToFavorites) {
                            Icon(Icons.Default.Star, contentDescription = "Ver Favoritos", tint = Color(0xFFFFC107))
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background
                    )
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = onNavigateToCreate,
                    containerColor = Color(0xFF6750A4), // Morado institucional
                    elevation = FloatingActionButtonDefaults.elevation(8.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Crear", tint = Color.White)
                }
            }
        ) { innerPadding ->
            if (listaDeEventos.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize().padding(innerPadding), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.EventNote, contentDescription = null, modifier = Modifier.size(64.dp), tint = Color.LightGray)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "No hay eventos registrados", color = Color.Gray, style = MaterialTheme.typography.bodyLarge)
                    }
                }
            } else {
                AnimatedVisibility(visible = true, enter = fadeIn(animationSpec = tween(500))) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize().padding(innerPadding).padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(top = 16.dp, bottom = 80.dp)
                    ) {
                        items(items = listaDeEventos, key = { it.id }) { evento ->
                            EventItemCard(
                                evento = evento,
                                onClick = { onNavigateToDetail(evento.id) },
                                onToggleFavorite = { viewModel.cambiarFavorito(evento) },
                                onDelete = {
                                    // En lugar de borrar directo, activamos el diálogo
                                    eventoParaBorrar = evento
                                    showDeleteDialog = true
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

// EventItemCard se mantiene igual, ya que recibe el lambda onDelete
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventItemCard(
    evento: EventoEntity,
    onClick: () -> Unit,
    onToggleFavorite: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (evento.imagenUri != null && evento.imagenUri!!.isNotEmpty()) {
                AsyncImage(
                    model = evento.imagenUri,
                    contentDescription = null,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Event,
                        contentDescription = null,
                        modifier = Modifier.size(30.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = evento.titulo,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )
                Text(
                    text = evento.fecha,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }

            Row {
                IconButton(onClick = onToggleFavorite) {
                    Icon(
                        imageVector = if (evento.esFavorito) Icons.Default.Star else Icons.Default.StarBorder,
                        contentDescription = null,
                        tint = if (evento.esFavorito) Color(0xFFFFC107) else Color.Gray
                    )
                }

                IconButton(onClick = onDelete) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Borrar",
                        tint = MaterialTheme.colorScheme.error.copy(alpha = 0.7f)
                    )
                }
            }
        }
    }
}