package ui

import Section
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.theme.Shapes

@OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
@Composable
fun SectionCard(section: Section, uriHandler: UriHandler) {
    val uri = "https://pisa.ucsc.edu/class_search/"+section.url
    Card(onClick = { uriHandler.openUri(uri) },modifier = Modifier.padding(6.dp).widthIn(max=800.dp), shape = Shapes.small) {
        Column(modifier = Modifier.fillMaxSize().padding(8.dp)) {
            SectionTitle(section.name)
            SectionSubtitle(Icons.Default.Person, section.instructor)
            if (section.location.contains("Online") || section.location.contains("Remote Instruction")) {
                SectionSubtitle(painterResource("videocam.xml"), section.location)
            } else {
                SectionSubtitle(painterResource("location_on.xml"), section.location)
            }
            SectionSubtitle(painterResource("clock.xml"), section.time)
            if (section.location2 != "Not Found") {
                if (section.location2.contains("Online") || section.location2.contains("Remote Instruction")) {
                    SectionSubtitle(painterResource("videocam.xml"), section.location2)
                } else {
                    SectionSubtitle(painterResource("location_on.xml"), section.location2)
                }
            }
            if (section.time2 != "Not Found") {
                SectionSubtitle(painterResource("clock.xml"), section.time2)
            }
            SectionSubtitle(painterResource("group.xml"), section.count)
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

val ROW_PADDING = 4.dp

@Composable
fun SectionSubtitle(icon: ImageVector, subtitle: String) {
    Row {
        Icon(icon,null, modifier = Modifier.padding(end = ROW_PADDING))
        Text(
            text = subtitle,
            fontSize = 16.sp
        )
    }
}

@Composable
fun SectionSubtitle(icon: Painter, subtitle: String) {
    Row {
        Icon(icon,null, modifier = Modifier.padding(end = ROW_PADDING))
        Text(
            text = subtitle,
            fontSize = 16.sp
        )
    }
}

