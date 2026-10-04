package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.NoteResource
import com.example.ui.theme.*
import com.example.viewmodel.UniverseUiState

@Composable
fun NotesScreen(
    uiState: UniverseUiState,
    onToggleBookmark: (String) -> Unit,
    onSearchChange: (String) -> Unit,
    onSubjectSelect: (String) -> Unit
) {
    var showUploadDialog by remember { mutableStateOf(false) }
    var selectedNoteForReading by remember { mutableStateOf<NoteResource?>(null) }

    val subjects = listOf("All", "Biology", "Chemistry", "Physics", "General Studies", "Computer Science")

    val filteredNotes = uiState.noteResources.filter { note ->
        val matchesSubject = if (uiState.selectedNotesSubject == "All") true
        else note.subject.equals(uiState.selectedNotesSubject, ignoreCase = true)

        val matchesSearch = if (uiState.notesSearchQuery.isBlank()) true
        else note.title.contains(uiState.notesSearchQuery, ignoreCase = true) ||
                note.contentPreview.contains(uiState.notesSearchQuery, ignoreCase = true)

        matchesSubject && matchesSearch
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Search & Upload Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = uiState.notesSearchQuery,
                onValueChange = onSearchChange,
                placeholder = { Text("Search revision notes, PDFs...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface
                ),
                singleLine = true,
                modifier = Modifier
                    .weight(1f)
                    .testTag("notes_search_input")
            )

            Spacer(modifier = Modifier.width(10.dp))

            IconButton(
                onClick = { showUploadDialog = true },
                modifier = Modifier
                    .size(46.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(CosmicPurplePrimary)
            ) {
                Icon(
                    imageVector = Icons.Default.UploadFile,
                    contentDescription = "Upload Note",
                    tint = Color.White
                )
            }
        }

        // Subject Filters
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(subjects) { subj ->
                val isSelected = uiState.selectedNotesSubject == subj
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = if (isSelected) CosmicPurplePrimary else MaterialTheme.colorScheme.surface,
                    border = if (isSelected) null else androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)),
                    modifier = Modifier.clickable { onSubjectSelect(subj) }
                ) {
                    Text(
                        text = subj,
                        fontSize = 12.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }
        }

        // Notes List
        LazyColumn(
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 80.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(filteredNotes) { note ->
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { selectedNoteForReading = note }
                ) {
                    Row(
                        modifier = Modifier
                            .padding(14.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(CosmicPurplePrimary.copy(alpha = 0.12f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Description,
                                contentDescription = null,
                                tint = CosmicPurplePrimary,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = note.title,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface,
                                maxLines = 1
                            )
                            Text(
                                text = "${note.subject} • ${note.author}",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "${note.pagesCount} Pages • ${note.fileSize}",
                                fontSize = 10.sp,
                                color = CosmicPurplePrimary,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        IconButton(onClick = { onToggleBookmark(note.id) }) {
                            Icon(
                                imageVector = if (note.isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                contentDescription = "Bookmark",
                                tint = if (note.isBookmarked) CosmicPurplePrimary else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }

    if (showUploadDialog) {
        AlertDialog(
            onDismissRequest = { showUploadDialog = false },
            title = { Text("Upload Student Note / PDF", fontWeight = FontWeight.Bold) },
            text = {
                Text("Select any handwritten note, PDF summary, or lecture handout to save to your personal offline UniVerse repository.")
            },
            confirmButton = {
                Button(
                    onClick = { showUploadDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = CosmicPurplePrimary)
                ) {
                    Text("Select PDF")
                }
            },
            dismissButton = {
                TextButton(onClick = { showUploadDialog = false }) { Text("Cancel") }
            }
        )
    }

    if (selectedNoteForReading != null) {
        AlertDialog(
            onDismissRequest = { selectedNoteForReading = null },
            title = { Text(selectedNoteForReading!!.title, fontWeight = FontWeight.Bold) },
            text = {
                Column {
                    Text(
                        text = "Subject: ${selectedNoteForReading!!.subject} • By: ${selectedNoteForReading!!.author}",
                        fontSize = 12.sp,
                        color = CosmicPurplePrimary,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = selectedNoteForReading!!.contentPreview,
                        fontSize = 13.sp,
                        lineHeight = 18.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "📄 Includes high-yield formula sheets, chapter schematics, and previous-year question highlights.",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = { selectedNoteForReading = null },
                    colors = ButtonDefaults.buttonColors(containerColor = CosmicPurplePrimary)
                ) {
                    Text("Close")
                }
            }
        )
    }
}
