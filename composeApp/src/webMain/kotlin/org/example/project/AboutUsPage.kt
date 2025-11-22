package org.example.project

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.founder1
import kotlinproject.composeapp.generated.resources.founder2
import kotlinproject.composeapp.generated.resources.founder3
import kotlinproject.composeapp.generated.resources.founder4
import kotlinproject.composeapp.generated.resources.founder5
import kotlinproject.composeapp.generated.resources.founder6
import kotlinproject.composeapp.generated.resources.humble
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

data class TeamMember(
    val name: String,
    val designation: String,
    val imageResource: DrawableResource
)

@Composable
fun AboutUsPage() {
    MaterialTheme(
        colorScheme = darkColorScheme(
            primary = BlueAccent,
            onPrimary = TextWhite,
            primaryContainer = DarkSurface,
            onPrimaryContainer = TextWhite,
            surface = DarkBackground,
            onSurface = TextWhite,
            surfaceVariant = DarkSurfaceVariant,
            onSurfaceVariant = TextGray
        )
    ) {
        CompositionLocalProvider(
            LocalTextStyle provides TextStyle(
                fontFamily = FontFamily.Default
            )
        ) {
            val scrollState = rememberScrollState()

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF0A0A0F),
                                Color(0xFF0D0F18),
                                Color(0xFF1A1F2F),
                                Color(0xFF0D0F18),
                                Color(0xFF0A0A0F)
                            )
                        )
                    )
            ) {
                BoxWithConstraints(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val config = rememberResponsiveConfig(maxWidth)

                    CompositionLocalProvider(LocalResponsiveConfig provides config) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(scrollState)
                        ) {
                            // Back button
                            val routing = LocalWebRouting.current
                            IconButton(
                                onClick = { routing.navigateTo("") },
                                modifier = Modifier.padding(if (config.isMobile) 16.dp else 24.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Back to home",
                                    tint = TextWhite,
                                    modifier = Modifier.size(28.dp)
                                )
                            }

                            // About Us heading
                            Text(
                                text = "About Us",
                                style = MaterialTheme.typography.displayMedium,
                                fontWeight = FontWeight.Bold,
                                color = BlueAccent,
                                fontSize = if (config.isMobile) 32.sp else 48.sp,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        horizontal = if (config.isMobile) 16.dp else 48.dp,
                                        vertical = if (config.isMobile) 8.dp else 12.dp
                                    ),
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(if (config.isMobile) 16.dp else 32.dp))

                            // Content
                            AboutUsContent()

                            // Meet The Team
                            MeetTheTeamSection()

                            Footer()
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun AboutUsContent() {
    val responsive = LocalResponsiveConfig.current
    val isMobile = responsive.isMobile

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = if (isMobile) 16.dp else 48.dp,
                vertical = if (isMobile) 16.dp else 24.dp
            ),
        verticalArrangement = Arrangement.spacedBy(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Most coding courses leave you with a certificate but no real confidence. At Humble Coders, we are changing that. We are a team of passionate engineers and educators dedicated to mastering one craft: Native Android Development.",
            color = TextLightGray,
            fontSize = responsive.bodyTextSize,
            textAlign = TextAlign.Center,
            lineHeight = 28.sp,
            modifier = Modifier.padding(horizontal = if (isMobile) 0.dp else 80.dp)
        )

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = if (isMobile) 600.dp else 900.dp),
            color = DarkSurfaceVariant,
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(
                modifier = Modifier.padding(if (isMobile) 28.dp else 48.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Text(
                    text = "Why We Are Different",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = BlueAccent,
                    fontSize = if (isMobile) 24.sp else 32.sp
                )

                Text(
                    text = "We made a conscious choice not to be generalists. You won't find us teaching a little bit of web or a little bit of IoT. We focus 100% of our energy on Kotlin and Jetpack Compose because that is what top tech companies demand.",
                    color = TextWhite,
                    fontSize = responsive.bodyTextSize,
                    lineHeight = 26.sp
                )
            }
        }

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = if (isMobile) 600.dp else 900.dp),
            color = Color(0xFF0B111B),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(
                modifier = Modifier.padding(if (isMobile) 28.dp else 48.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Text(
                    text = "Our Promise",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = BlueLight,
                    fontSize = if (isMobile) 24.sp else 32.sp
                )

                Text(
                    text = "We teach by building. When you join us, you don't just listen to lectures. You build military-grade dashboards, retail apps, and complex systems. By the time you leave, you won't just know the syntax. You will know how to think, architect, and deliver professional software.",
                    color = TextWhite,
                    fontSize = responsive.bodyTextSize,
                    lineHeight = 26.sp
                )
            }
        }
    }
}

@Composable
fun MeetTheTeamSection() {
    val responsive = LocalResponsiveConfig.current
    val isMobile = responsive.isMobile

    val teamMembers = listOf(
        TeamMember("Ansh Bajaj", "Course Instructor", Res.drawable.founder1),
        TeamMember("Ishank Goyal", "Course Instructor", Res.drawable.founder2),
        TeamMember("Aaryan Kaushal", "Course Coordinator", Res.drawable.founder3),
        TeamMember("Ritvik Singla", "Course Coordinator", Res.drawable.founder4),
        TeamMember("Shreya Baranwal", "Designer", Res.drawable.founder5),
        TeamMember("Sharanya Goel", "Content Writer", Res.drawable.founder6)
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = if (isMobile) 16.dp else 48.dp,
                vertical = if (isMobile) 32.dp else 48.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(if (isMobile) 24.dp else 32.dp)
    ) {
        Text(
            text = "Meet The Team",
            style = MaterialTheme.typography.displayMedium,
            fontWeight = FontWeight.Bold,
            color = TextWhite,
            fontSize = if (isMobile) 36.sp else 48.sp,
            textAlign = TextAlign.Center
        )

        Text(
            text = "The passionate minds behind Humble Coders",
            color = TextLightGray,
            fontSize = responsive.bodyTextSize,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = if (isMobile) 16.dp else 24.dp)
        )

        if (isMobile) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                teamMembers.forEach { member ->
                    TeamMemberCard(member)
                }
            }
        } else {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(28.dp, Alignment.CenterHorizontally)
                ) {
                    teamMembers.take(3).forEach { member ->
                        TeamMemberCard(
                            member = member,
                            modifier = Modifier.weight(1f, fill = false)
                        )
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(28.dp, Alignment.CenterHorizontally)
                ) {
                    teamMembers.drop(3).forEach { member ->
                        TeamMemberCard(
                            member = member,
                            modifier = Modifier.weight(1f, fill = false)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TeamMemberCard(member: TeamMember, modifier: Modifier = Modifier) {
    val responsive = LocalResponsiveConfig.current

    Card(
        modifier = modifier
            .widthIn(max = 320.dp),
        colors = CardDefaults.cardColors(
            containerColor = DarkSurfaceVariant
        ),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(if (responsive.isMobile) 24.dp else 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(if (responsive.isMobile) 140.dp else 180.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                BlueAccent.copy(alpha = 0.3f),
                                DarkSurface
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(member.imageResource),
                    contentDescription = "${member.name} photo",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = member.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = TextWhite,
                    fontSize = if (responsive.isMobile) 20.sp else 24.sp,
                    textAlign = TextAlign.Center
                )

                Surface(
                    color = BlueAccent.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(
                        text = member.designation,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        color = BlueLight,
                        fontSize = if (responsive.isMobile) 14.sp else 15.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}