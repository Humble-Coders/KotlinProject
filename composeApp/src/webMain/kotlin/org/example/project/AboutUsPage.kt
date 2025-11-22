package org.example.project

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
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
                    val responsiveConfig = rememberResponsiveConfig(maxWidth)

                    CompositionLocalProvider(LocalResponsiveConfig provides responsiveConfig) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(scrollState)
                        ) {
                            ScrollAnimatedContent(scrollState = scrollState, animationDelay = 0) {
                                AboutUsHeader()
                            }
                            ScrollAnimatedContent(scrollState = scrollState, animationDelay = 100) {
                                AboutUsContent()
                            }
                            ScrollAnimatedContent(scrollState = scrollState, animationDelay = 0) {
                                MeetTheTeamSection()
                            }
                            Footer()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AboutUsHeader() {
    val responsive = LocalResponsiveConfig.current
    val isMobile = responsive.isMobile

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0A0A0F),
                        Color(0xFF0D0F18),
                        Color(0xFF1A1F2F)
                    )
                )
            )
    ) {
        Box(
            modifier = Modifier
                .size(300.dp)
                .offset(x = (-150).dp, y = 100.dp)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            BlueAccent.copy(alpha = 0.1f),
                            Color.Transparent
                        )
                    )
                )
        )
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .size(400.dp)
                .offset(x = 200.dp, y = (-100).dp)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            BlueLight.copy(alpha = 0.08f),
                            Color.Transparent
                        )
                    )
                )
        )

        // Back button
        val routing = remember { createWebRouting() }
        IconButton(
            onClick = {
                routing.navigateTo("")
            },
            modifier = Modifier
                .padding(if (isMobile) 16.dp else 24.dp)
                .align(Alignment.TopStart)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back to home",
                tint = TextWhite,
                modifier = Modifier.size(28.dp)
            )
        }

        SectionContainer(
            verticalPaddingMultiplier = if (isMobile) 0.8f else 1.2f,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(Res.drawable.humble),
                contentDescription = "Humble Coders Logo",
                modifier = Modifier.size(if (isMobile) 90.dp else 120.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "From Student to Creator",
                style = MaterialTheme.typography.displayLarge,
                fontWeight = FontWeight.Bold,
                color = TextWhite,
                fontSize = if (isMobile) 36.sp else 56.sp,
                lineHeight = if (isMobile) 42.sp else 64.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun AboutUsContent() {
    val responsive = LocalResponsiveConfig.current
    val isMobile = responsive.isMobile

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF1A1F2F),
                        Color(0xFF0D0F18),
                        Color(0xFF0A0A0F)
                    )
                )
            )
    ) {
        SectionContainer(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalPaddingMultiplier = 1.2f
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
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

    Box(
        modifier = Modifier
            .fillMaxWidth()
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
        Box(
            modifier = Modifier
                .size(500.dp)
                .offset(x = (-200).dp, y = 100.dp)
                .blur(150.dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            BlueAccent.copy(alpha = 0.2f),
                            Color.Transparent
                        )
                    )
                )
        )

        SectionContainer(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalPaddingMultiplier = 1.5f
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
                modifier = Modifier.padding(top = 16.dp, bottom = 48.dp)
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
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TeamMemberCard(member: TeamMember, modifier: Modifier = Modifier) {
    val responsive = LocalResponsiveConfig.current
    var isHovered by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (isHovered) 1.05f else 1f,
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing),
        label = "teamCardScale"
    )

    val elevation by animateFloatAsState(
        targetValue = if (isHovered) 20f else 8f,
        animationSpec = tween(durationMillis = 300),
        label = "teamCardElevation"
    )

    Card(
        modifier = modifier
            .widthIn(max = 320.dp)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                shadowElevation = elevation
            }
            .onPointerEvent(PointerEventType.Enter) { isHovered = true }
            .onPointerEvent(PointerEventType.Exit) { isHovered = false },
        colors = CardDefaults.cardColors(
            containerColor = DarkSurfaceVariant
        ),
        shape = RoundedCornerShape(20.dp)
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