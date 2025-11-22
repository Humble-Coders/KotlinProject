package org.example.project

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinproject.composeapp.generated.resources.*
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.firstworkshop
import kotlinproject.composeapp.generated.resources.secondworkshop
import kotlinproject.composeapp.generated.resources.thirdworkshop
import kotlinproject.composeapp.generated.resources.fourthworkshop
import kotlinproject.composeapp.generated.resources.fifthworkshop
import kotlinproject.composeapp.generated.resources.sixthworkshop
import org.jetbrains.compose.resources.painterResource
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin


data class Workshop(
    val title: String,
    val topic: String,
    val description: String,
    val projects: List<ProjectItem>,
    val imageResource: String
)

data class ProjectItem(
    val name: String,
    val description: String,
    val icon: ImageVector
)

data class FeedbackData(
    val title: String,
    val segments: List<FeedbackSegment>
)

data class FeedbackSegment(
    val label: String,
    val percentage: Float,
    val color: Color
)

@Composable
fun PreviousWorkshopsPage() {
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
                                .verticalScroll(scrollState),
                            verticalArrangement = Arrangement.spacedBy(0.dp)
                        ) {
                            // Back button
                            val routing = LocalWebRouting.current
                            IconButton(
                                onClick = { routing.navigateTo("") },
                                modifier = Modifier.padding(if (responsiveConfig.isMobile) 16.dp else 24.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Back to home",
                                    tint = TextWhite,
                                    modifier = Modifier.size(28.dp)
                                )
                            }

                            WorkshopsHeader()

                            val workshops = getWorkshopsList()
                            workshops.forEachIndexed { index, workshop ->
                                WorkshopSection(
                                    workshop = workshop,
                                    imageOnLeft = index % 2 == 0,
                                    isFirst = index == 0
                                )
                            }

                            StudentFeedbackSection()

                            Footer()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun WorkshopsHeader() {
    val responsive = LocalResponsiveConfig.current
    val isMobile = responsive.isMobile

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = if (isMobile) 24.dp else 32.dp,
                bottom = 0.dp,
                start = responsive.horizontalPadding,
                end = responsive.horizontalPadding
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Previous Workshops",
            style = MaterialTheme.typography.displayLarge,
            fontWeight = FontWeight.Bold,
            color = TextWhite,
            fontSize = if (isMobile) 36.sp else 56.sp,
            lineHeight = if (isMobile) 42.sp else 64.sp,
            textAlign = TextAlign.Center
        )

        Text(
            text = "Transforming students into confident developers through hands-on training at leading institutions",
            color = TextLightGray,
            fontSize = responsive.bodyTextSize,
            textAlign = TextAlign.Center,
            lineHeight = 26.sp,
            modifier = Modifier.padding(
                top = 20.dp,
                bottom = 0.dp,
                start = if (isMobile) 0.dp else 100.dp,
                end = if (isMobile) 0.dp else 40.dp
            )
        )
        if (isMobile) {
        Spacer(modifier = Modifier.height(20.dp))
    }
    else {
        Spacer(modifier = Modifier.height(50.dp))
    }
}
}

@Composable
fun WorkshopSection(workshop: Workshop, imageOnLeft: Boolean, isFirst: Boolean = false) {
    val responsive = LocalResponsiveConfig.current
    val isMobile = responsive.isMobile

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = if (isFirst) if (isMobile) 32.dp else 48.dp else if (isMobile) 64.dp else 96.dp,
                bottom = 0.dp,
                start = responsive.horizontalPadding,
                end = responsive.horizontalPadding
            )
    ) {
        if (isMobile) {
            Column(
                verticalArrangement = Arrangement.spacedBy(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                WorkshopImage(workshop)
                WorkshopContent(workshop)
            }
        } else {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(72.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (imageOnLeft) {
                    WorkshopImage(workshop, Modifier.weight(1f))
                    WorkshopContent(workshop, Modifier.weight(1f))
                } else {
                    WorkshopContent(workshop, Modifier.weight(1f))
                    WorkshopImage(workshop, Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun WorkshopImage(workshop: Workshop, modifier: Modifier = Modifier) {
    val responsive = LocalResponsiveConfig.current

    val imageResource = when (workshop.imageResource) {
        "firstworkshop" -> Res.drawable.firstworkshop
        "secondworkshop" -> Res.drawable.secondworkshop
        "thirdworkshop" -> Res.drawable.thirdworkshop
        "fourthworkshop" -> Res.drawable.fourthworkshop
        "fifthworkshop" -> Res.drawable.fifthworkshop
        "sixthworkshop" -> Res.drawable.sixthworkshop
        else -> Res.drawable.firstworkshop // fallback
    }

    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = DarkSurfaceVariant
            ),
            shape = RoundedCornerShape(20.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(if (responsive.isMobile) 280.dp else 400.dp)
            ) {
                Image(
                    painter = painterResource(imageResource),
                    contentDescription = workshop.title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@Composable
fun WorkshopContent(workshop: Workshop, modifier: Modifier = Modifier) {
    val responsive = LocalResponsiveConfig.current

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Surface(
            color = BlueAccent.copy(alpha = 0.15f),
            shape = RoundedCornerShape(24.dp)
        ) {
            Text(
                text = workshop.topic,
                modifier = Modifier.padding(horizontal = 18.dp, vertical = 10.dp),
                color = BlueLight,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Text(
            text = workshop.title,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = TextWhite,
            fontSize = if (responsive.isMobile) 24.sp else 32.sp,
            lineHeight = if (responsive.isMobile) 30.sp else 40.sp
        )

        Text(
            text = workshop.description,
            color = TextLightGray,
            fontSize = responsive.bodyTextSize,
            lineHeight = 26.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Surface(
            color = DarkSurfaceVariant,
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "What We Built:",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = BlueAccent,
                    fontSize = 18.sp
                )

                workshop.projects.forEach { project ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(BlueAccent.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = project.icon,
                                contentDescription = null,
                                tint = BlueAccent,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = project.name,
                                color = TextWhite,
                                fontSize = responsive.bodyTextSize,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = project.description,
                                color = TextGray,
                                fontSize = 14.sp,
                                lineHeight = 20.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StudentFeedbackSection() {
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
        SectionContainer(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalPaddingMultiplier = 1.5f
        ) {
            Text(
                text = "Overwhelming Positive Feedback",
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Bold,
                color = TextWhite,
                fontSize = if (isMobile) 32.sp else 48.sp,
                textAlign = TextAlign.Center
            )

            Text(
                text = "Real results from students who transformed their skills",
                color = TextLightGray,
                fontSize = responsive.bodyTextSize,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 16.dp, bottom = 56.dp)
            )

            val feedbackData = getFeedbackData()

            if (isMobile) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(40.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    feedbackData.forEach { data ->
                        FeedbackChart(data)
                    }
                }
            } else {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(48.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(32.dp, Alignment.CenterHorizontally)
                    ) {
                        feedbackData.take(3).forEach { data ->
                            FeedbackChart(data, Modifier.weight(1f, fill = false))
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(32.dp, Alignment.CenterHorizontally)
                    ) {
                        feedbackData.drop(3).forEach { data ->
                            FeedbackChart(data, Modifier.weight(1f, fill = false))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FeedbackChart(data: FeedbackData, modifier: Modifier = Modifier) {
    val responsive = LocalResponsiveConfig.current

    Card(
        modifier = modifier.widthIn(max = 380.dp),
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
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Box(
                modifier = Modifier.size(if (responsive.isMobile) 200.dp else 240.dp),
                contentAlignment = Alignment.Center
            ) {
                PieChart(segments = data.segments)
            }

            Text(
                text = data.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = TextWhite,
                fontSize = if (responsive.isMobile) 16.sp else 18.sp,
                textAlign = TextAlign.Center,
                lineHeight = 24.sp
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                data.segments.forEach { segment ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(16.dp)
                                .clip(CircleShape)
                                .background(segment.color)
                        )
                        Text(
                            text = "${segment.label}: ${(segment.percentage * 10).toInt() / 10.0}%",
                            color = if (segment.percentage > 0) TextWhite else TextGray.copy(alpha = 0.5f),
                            fontSize = 14.sp,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PieChart(segments: List<FeedbackSegment>) {
    Canvas(
        modifier = Modifier.fillMaxSize()
    ) {
        val radius = size.minDimension / 2
        val center = Offset(size.width / 2, size.height / 2)

        var startAngle = -90f

        segments.forEach { segment ->
            val sweepAngle = (segment.percentage / 100f) * 360f

            drawArc(
                color = segment.color,
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = true,
                topLeft = Offset(center.x - radius, center.y - radius),
                size = androidx.compose.ui.geometry.Size(radius * 2, radius * 2)
            )

            startAngle += sweepAngle
        }
    }
}

fun getWorkshopsList(): List<Workshop> {
    return listOf(
        Workshop(
            title = "Electronics & Communication Engineering Dept, TIET",
            topic = "The Essentials of Android",
            description = "We partnered with the ECE Department at Thapar Institute to deliver a high-impact session focused on efficiency. In just five hours, we took students from zero knowledge to writing their first lines of Kotlin code. The goal was to demystify mobile development and prove that with the right guidance, complex logic can be implemented quickly.",
            projects = listOf(
                ProjectItem(
                    "CGPA Calculator",
                    "A fully functional calculator app built from scratch to handle custom logic and user inputs.",
                    Icons.Filled.Calculate
                )
            ),
            imageResource = "firstworkshop"
        ),
        Workshop(
            title = "MLSC Society Bootcamp",
            topic = "5-Day Intensive App Development",
            description = "In collaboration with the Microsoft Learn Student Chapter (MLSC) at Thapar, we conducted a comprehensive 5-day bootcamp. We dedicated 2 hours each day to hands-on coding, guiding students through a progressive curriculum. We started with basic layouts and moved rapidly toward complex cloud-based architecture, ensuring students understood the full lifecycle of an app.",
            projects = listOf(
                ProjectItem(
                    "Encoder-Decoder",
                    "An encryption tool to understand string manipulation.",
                    Icons.Filled.Lock
                ),
                ProjectItem(
                    "Domino Meal Planner",
                    "A utility app focusing on UI/UX logic.",
                    Icons.Filled.Restaurant
                ),
                ProjectItem(
                    "Twitter Clone",
                    "A real-time social media feed integrated with Firebase.",
                    Icons.Filled.Forum
                )
            ),
            imageResource = "secondworkshop"
        ),
        Workshop(
            title = "Thapar Summer School (Dr. Prashant Singh Rana)",
            topic = "Bridging Machine Learning & Mobile",
            description = "For this rigorous two-month program under the guidance of Dr. Prashant Singh Rana, Humble Coders served as the specialized Android partners. While the summer school focused on Machine Learning theory, our role was to turn that theory into a tangible product. We taught students how to bridge the gap between Python models and mobile devices using TFLite, enabling them to deploy AI on the edge.",
            projects = listOf(
                ProjectItem(
                    "AI Object Detection App",
                    "A live camera application capable of identifying objects in real-time by integrating custom ML models.",
                    Icons.Filled.CameraAlt
                )
            ),
            imageResource = "thirdworkshop"
        ),
        Workshop(
            title = "Punjabi University (MCA Dept)",
            topic = "Cloud Integration & Career Readiness",
            description = "We designed this session specifically for Master of Computer Applications (MCA) students to boost their placement and internship prospects. The focus was on speed and modern backend integration. In a focused 4-hour sprint, we taught students how to bypass complex backend code by utilizing Firebase, giving them a powerful skill set for hackathons and rapid prototyping.",
            projects = listOf(
                ProjectItem(
                    "Real-Time Social App",
                    "A functional Twitter clone built from scratch using Firebase for authentication and database management.",
                    Icons.Filled.Cloud
                )
            ),
            imageResource = "fourthworkshop"
        ),
        Workshop(
            title = "Lovely Professional University (LPU)",
            topic = "Advanced Networking & Hackathon",
            description = "This workshop was tailored for 4th-year students preparing for the corporate world. We skipped the basics and dove into advanced Android concepts required for professional development. We covered difficult topics like API consumption and networking using Retrofit. To validate their learning, we concluded the event with a competitive Hackathon on the second day to test their ability to code under pressure.",
            projects = listOf(
                ProjectItem(
                    "Networking Architecture",
                    "Implementation of REST APIs using Retrofit in Kotlin.",
                    Icons.Filled.Language
                ),
                ProjectItem(
                    "Live Hackathon Projects",
                    "Students built various data-driven applications to test their new skills.",
                    Icons.Filled.EmojiEvents
                )
            ),
            imageResource = "fifthworkshop"
        ),
        Workshop(
            title = "Venture Lab, Thapar",
            topic = "Rapid Prototyping for First-Years",
            description = "We believe in starting early. In collaboration with Venture Lab, we introduced 1st-year students to the world of software development. Over the course of three days, we took fresh students with little coding experience and turned them into Android developers. The workshop focused on logic building and understanding how software comes together.",
            projects = listOf(
                ProjectItem(
                    "Secret Message App",
                    "An Encoder-Decoder tool.",
                    Icons.Filled.VpnKey
                ),
                ProjectItem(
                    "Social Feed",
                    "A foundational Twitter-style application to learn list handling and data flow.",
                    Icons.Filled.DynamicFeed
                )
            ),
            imageResource = "sixthworkshop"
        )
    )
}

fun getFeedbackData(): List<FeedbackData> {
    return listOf(
        FeedbackData(
            title = "Content relevance",
            segments = listOf(
                FeedbackSegment("Very Relevant", 48.3f, Color(0xFF4299E1)),
                FeedbackSegment("Relevant", 53.7f, Color(0xFF48BB78)),
                FeedbackSegment("Neutral", 0f, Color(0xFFECC94B)),
                FeedbackSegment("Irrelevant", 0f, Color(0xFFED8936)),
                FeedbackSegment("Poor", 0f, Color(0xFFF56565))
            )
        ),
        FeedbackData(
            title = "Knowledge and expertise of instructors",
            segments = listOf(
                FeedbackSegment("Excellent", 36.6f, Color(0xFF4299E1)),
                FeedbackSegment("Good", 63.4f, Color(0xFF48BB78)),
                FeedbackSegment("Average", 0f, Color(0xFFECC94B)),
                FeedbackSegment("Fair", 0f, Color(0xFFED8936)),
                FeedbackSegment("Poor", 0f, Color(0xFFF56565))
            )
        ),
        FeedbackData(
            title = "Overall experience",
            segments = listOf(
                FeedbackSegment("Excellent", 34.1f, Color(0xFF4299E1)),
                FeedbackSegment("Good", 65.9f, Color(0xFF48BB78)),
                FeedbackSegment("Average", 0f, Color(0xFFECC94B)),
                FeedbackSegment("Fair", 0f, Color(0xFFED8936)),
                FeedbackSegment("Poor", 0f, Color(0xFFF56565))
            )
        ),
        FeedbackData(
            title = "Hands-on activities/coding sessions",
            segments = listOf(
                FeedbackSegment("Excellent", 65.9f, Color(0xFF4299E1)),
                FeedbackSegment("Good", 29.3f, Color(0xFF48BB78)),
                FeedbackSegment("Average", 4.9f, Color(0xFFECC94B)),
                FeedbackSegment("Fair", 0f, Color(0xFFED8936)),
                FeedbackSegment("Poor", 0f, Color(0xFFF56565))
            )
        ),
        FeedbackData(
            title = "Was the workshop environment conducive to learning",
            segments = listOf(
                FeedbackSegment("Yes", 95.1f, Color(0xFF4299E1)),
                FeedbackSegment("Somewhat", 4.9f, Color(0xFF48BB78)),
                FeedbackSegment("No", 0f, Color(0xFFECC94B))
            )
        ),
        FeedbackData(
            title = "Confidence Level: Applying Workshop Skills to Real-World App Development Projects",
            segments = listOf(
                FeedbackSegment("Very Confident", 41.5f, Color(0xFF4299E1)),
                FeedbackSegment("Confident", 41.5f, Color(0xFF48BB78)),
                FeedbackSegment("Somewhat Confident", 17.1f, Color(0xFFECC94B)),
                FeedbackSegment("Not Confident", 0f, Color(0xFFF56565))
            )
        )
    )
}