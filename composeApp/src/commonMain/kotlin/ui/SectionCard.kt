package ui

import Section
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

const val LOCATION_ICON = "location_on.xml"
const val TIME_ICON = "clock.xml"

@OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
@Composable
fun SectionCard(section: Section) {
    Card(onClick = {},modifier = Modifier.padding(6.dp)) {
        Column(modifier = Modifier.fillMaxSize().padding(8.dp)) {
            SectionTitle(section.name)
            SectionSubtitle(Icons.Default.Person, section.instructor)
            if (section.location == "Not Found") {
                SectionSubtitle(painterResource(LOCATION_ICON), section.location2)
            } else {
                SectionSubtitle(painterResource(LOCATION_ICON), section.location)
            }
            if (section.time == "Not Found") {
                SectionSubtitle(painterResource(TIME_ICON), section.time)
            } else {
                SectionSubtitle(painterResource(TIME_ICON), section.time2)
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

