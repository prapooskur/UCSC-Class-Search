package ui

import Section
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SectionCard(section: Section) {
    Card(onClick = {},modifier = Modifier.padding(6.dp)) {
        Column(modifier = Modifier.fillMaxSize().padding(8.dp)) {
            SectionTitle(section.name)
            SectionSubtitle(Icons.Default.AddCircle, section.instructor)
            SectionSubtitle(Icons.Default.AddCircle, section.location)
            SectionSubtitle(Icons.Default.AddCircle, section.time)
            SectionSubtitle(Icons.Default.AddCircle, section.time2)
            SectionSubtitle(Icons.Default.AddCircle, section.count)
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp
    )
}

@Composable
fun SectionSubtitle(icon: ImageVector, subtitle: String) {
    Row {
        Icon(icon,"")
        Text(
            text = subtitle,
            fontSize = 16.sp
        )
    }
}