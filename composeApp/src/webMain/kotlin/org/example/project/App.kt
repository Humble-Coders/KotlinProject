package org.example.project

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Diamond
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.android
import kotlinproject.composeapp.generated.resources.compose_multiplatform
import kotlinproject.composeapp.generated.resources.humble
import kotlinproject.composeapp.generated.resources.ic_android
import kotlinproject.composeapp.generated.resources.logo1
import kotlinproject.composeapp.generated.resources.logo2
import kotlinproject.composeapp.generated.resources.logo3
import kotlinproject.composeapp.generated.resources.logo4
import kotlinproject.composeapp.generated.resources.mentor
import kotlinproject.composeapp.generated.resources.workshop1
import kotlinproject.composeapp.generated.resources.workshop2
import kotlinproject.composeapp.generated.resources.workshop3
import kotlinproject.composeapp.generated.resources.workshop4
import kotlinproject.composeapp.generated.resources.workshop5
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

// Custom Dark Theme Colors - Using shades of #4263a6
val DarkBackground = Color(0xFF0A0A0F)
val DarkSurface = Color(0xFF13131A)
val DarkSurfaceVariant = Color(0xFF1E1E28)
val BlueAccent = Color(0xFF4263A6) // Main blue color
val BlueDark = Color(0xFF2A3F7A) // Darker shade
val BlueLight = Color(0xFF5A7BC0) // Lighter shade
val BlueAccentOld = Color(0xFF3B82F6)
val GreenStars = Color(0xFF10B981)
val TextWhite = Color(0xFFFFFFFF)
val TextGray = Color(0xFF9CA3AF)
val TextLightGray = Color(0xFFD1D5DB)

private const val UDEMY_COURSE_URL = "https://www.udemy.com/course/android-app-development-in-kotlin-jetpack-compose/?referralCode=6728AB4D782F201700BF"

enum class DeviceType { Mobile, Tablet, Desktop }

data class ResponsiveLayoutConfig(
    val deviceType: DeviceType,
    val contentMaxWidth: Dp,
    val horizontalPadding: Dp,
    val verticalSectionPadding: Dp,
    val contentSpacing: Dp,
    val heroTitleSize: TextUnit,
    val heroSubtitleSize: TextUnit,
    val bodyTextSize: TextUnit,
    val buttonHeight: Dp
) {
    val isMobile get() = deviceType == DeviceType.Mobile
}

val LocalResponsiveConfig = staticCompositionLocalOf {
    ResponsiveLayoutConfig(
        deviceType = DeviceType.Desktop,
        contentMaxWidth = 1200.dp,
        horizontalPadding = 48.dp,
        verticalSectionPadding = 80.dp,
        contentSpacing = 32.dp,
        heroTitleSize = 64.sp,
        heroSubtitleSize = 26.sp,
        bodyTextSize = 17.sp,
        buttonHeight = 56.dp
    )
}

@Composable
fun rememberResponsiveConfig(maxWidth: Dp): ResponsiveLayoutConfig {
    return remember(maxWidth) {
        when {
            maxWidth < 640.dp -> ResponsiveLayoutConfig(
                deviceType = DeviceType.Mobile,
                contentMaxWidth = 520.dp,
                horizontalPadding = 16.dp,
                verticalSectionPadding = 40.dp,
                contentSpacing = 20.dp,
                heroTitleSize = 36.sp,
                heroSubtitleSize = 20.sp,
                bodyTextSize = 15.sp,
                buttonHeight = 48.dp
            )

            maxWidth < 1024.dp -> ResponsiveLayoutConfig(
                deviceType = DeviceType.Tablet,
                contentMaxWidth = 900.dp,
                horizontalPadding = 32.dp,
                verticalSectionPadding = 64.dp,
                contentSpacing = 28.dp,
                heroTitleSize = 48.sp,
                heroSubtitleSize = 22.sp,
                bodyTextSize = 16.sp,
                buttonHeight = 52.dp
            )

            else -> ResponsiveLayoutConfig(
                deviceType = DeviceType.Desktop,
                contentMaxWidth = 1200.dp,
                horizontalPadding = 48.dp,
                verticalSectionPadding = 80.dp,
                contentSpacing = 32.dp,
                heroTitleSize = 64.sp,
                heroSubtitleSize = 26.sp,
                bodyTextSize = 17.sp,
                buttonHeight = 56.dp
            )
        }
    }
}

@Composable
fun SectionContainer(
    modifier: Modifier = Modifier,
    verticalPaddingMultiplier: Float = 1f,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    verticalArrangement: Arrangement.Vertical? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    val responsive = LocalResponsiveConfig.current
    val resolvedArrangement = verticalArrangement ?: Arrangement.spacedBy(responsive.contentSpacing)

    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = responsive.contentMaxWidth)
                .padding(
                    horizontal = responsive.horizontalPadding,
                    vertical = responsive.verticalSectionPadding * verticalPaddingMultiplier
                ),
            horizontalAlignment = horizontalAlignment,
            verticalArrangement = resolvedArrangement,
            content = content
        )
    }
}

// Scroll Animation Composable
@Composable
fun ScrollAnimatedContent(
    scrollState: ScrollState,
    modifier: Modifier = Modifier,
    animationDelay: Int = 0,
    content: @Composable () -> Unit
) {
    var isVisible by remember { mutableStateOf(false) }
    var hasBeenVisible by remember { mutableStateOf(false) }
    var contentHeight by remember { mutableStateOf(0) }
    var contentY by remember { mutableStateOf(0) }

    val alpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(
            durationMillis = 800,
            delayMillis = animationDelay,
            easing = FastOutSlowInEasing
        ),
        label = "alpha"
    )

    val translateY by animateFloatAsState(
        targetValue = if (isVisible) 0f else 50f,
        animationSpec = tween(
            durationMillis = 800,
            delayMillis = animationDelay,
            easing = FastOutSlowInEasing
        ),
        label = "translateY"
    )

    val scale by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0.9f,
        animationSpec = tween(
            durationMillis = 800,
            delayMillis = animationDelay,
            easing = FastOutSlowInEasing
        ),
        label = "scale"
    )

    // Track scroll position and element position to determine visibility
    LaunchedEffect(scrollState.value, contentY, contentHeight) {
        if (contentHeight == 0) return@LaunchedEffect

        // Use a reasonable viewport height estimate (will work for most screens)
        val viewportHeight = 900f
        val scrollPosition = scrollState.value.toFloat()
        val elementTop = contentY.toFloat()
        val elementBottom = elementTop + contentHeight.toFloat()

        // Element is visible if it's within the viewport with a threshold
        // Trigger animation when element is 30% into viewport
        val threshold = viewportHeight * 0.3f
        val visibleTop = scrollPosition + threshold
        val visibleBottom = scrollPosition + viewportHeight

        val shouldBeVisible = elementBottom > visibleTop && elementTop < visibleBottom

        if (shouldBeVisible && !hasBeenVisible) {
            isVisible = true
            hasBeenVisible = true
        } else if (!shouldBeVisible && !hasBeenVisible) {
            isVisible = false
        }
    }

    Box(
        modifier = modifier
            .onGloballyPositioned { coordinates ->
                val position = coordinates.positionInParent()
                contentY = position.y.toInt()
                contentHeight = coordinates.size.height
            }
            .alpha(alpha)
            .graphicsLayer {
                translationY = translateY
                scaleX = scale
                scaleY = scale
            }
    ) {
        content()
    }
}

@Composable
fun App() {
    val routing = remember { createWebRouting() }
    
    // Routing state based on URL hash
    var currentRoute by remember { mutableStateOf(routing.getCurrentRoute()) }
    
    // Listen to hash changes
    DisposableEffect(Unit) {
        val cleanup = routing.onRouteChange { route ->
            currentRoute = route
        }
        onDispose {
            cleanup()
        }
    }

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
        // Provide default font family with Unicode/emoji support for all Text components
        CompositionLocalProvider(
            LocalTextStyle provides TextStyle(
                fontFamily = FontFamily.Default
            )
        ) {
            when (currentRoute) {
                "about" -> {
                    AboutUsPage()
                }
                else -> {
                    val scrollState = rememberScrollState()
                    // Store section positions for smooth scrolling
                    val sectionPositions = remember { mutableStateMapOf<String, Int>() }
                    // Track cumulative Y position as we go through sections
                    var currentY by remember { mutableStateOf(0) }

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
                                    ScrollAnimatedContent(scrollState = scrollState, animationDelay = 100) {
                                        HeroSectionWithHeader(scrollState, sectionPositions, currentY) { currentY += it }
                                    }

                                    ScrollAnimatedContent(scrollState = scrollState, animationDelay = 0) {
                                        PartnerLogosSection { currentY += it }
                                    }
                                    ScrollAnimatedContent(scrollState = scrollState, animationDelay = 0) {
                                        ProgramHighlightsSection { currentY += it }
                                    }
                                    ScrollAnimatedContent(scrollState = scrollState, animationDelay = 100) {
                                        CourseVideoSection { currentY += it }
                                    }
                                    ScrollAnimatedContent(scrollState = scrollState, animationDelay = 0) {
                                        WhyChooseSection(scrollState, sectionPositions) { currentY += it }
                                    }
                                    ScrollAnimatedContent(scrollState = scrollState, animationDelay = 100) {
                                        CurriculumSection(sectionPositions)
                                    }
                                    ScrollAnimatedContent(scrollState = scrollState, animationDelay = 0) {
                                        TestimonialsSection(sectionPositions)
                                    }
                                    ScrollAnimatedContent(scrollState = scrollState, animationDelay = 100) {
                                        MeetMentorSection(sectionPositions)
                                    }
                                    ScrollAnimatedContent(scrollState = scrollState, animationDelay = 0) {
                                        PricingSection(sectionPositions)
                                    }
                                    ScrollAnimatedContent(scrollState = scrollState, animationDelay = 100) {
                                        FAQSection(sectionPositions)
                                    }
                                    ScrollAnimatedContent(scrollState = scrollState, animationDelay = 0) {
                                        FinalCTASection()
                                    }
                                    Footer()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HeroSectionWithHeader(
    scrollState: ScrollState,
    sectionPositions: MutableMap<String, Int>,
    startY: Int,
    onHeightMeasured: (Int) -> Unit
) {
    val responsive = LocalResponsiveConfig.current
    val isMobile = responsive.isMobile
    val navigationItems = listOf("Mentors", "Curriculum", "Reviews", "Pricing")
    val coroutineScope = rememberCoroutineScope()

    // Function to scroll to a section
    val scrollToSection: (String) -> Unit = { sectionName ->
        val position = sectionPositions[sectionName]
        if (position != null && position >= 0) {
            coroutineScope.launch {
                // Add a small offset to account for any fixed header spacing
                val scrollPosition = (position - 80).coerceAtLeast(0)
                scrollState.animateScrollTo(
                    value = scrollPosition,
                    animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing)
                )
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .onGloballyPositioned { coordinates ->
                val height = coordinates.size.height
                onHeightMeasured(height)
            }
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
        SectionContainer(
            verticalPaddingMultiplier = if (isMobile) 0.3f else 0.2f,  // Reduced from 1.2f/1.5f to minimize top space
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isMobile) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(Res.drawable.humble),
                        contentDescription = "Humble Coders Logo",
                        modifier = Modifier.size(if (isMobile) 90.dp else 140.dp),
                        contentScale = ContentScale.Fit
                    )
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        navigationItems.forEach { item ->
                            TextButton(
                                onClick = { scrollToSection(item) },
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text(item, color = TextWhite, fontSize = 14.sp)
                            }
                        }
                    }
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(Res.drawable.humble),
                        contentDescription = "Humble Coders Logo",
                        modifier = Modifier.size(140.dp),
                        contentScale = ContentScale.Fit
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(32.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        navigationItems.forEach { item ->
                            TextButton(onClick = { scrollToSection(item) }) {
                                Text(item, color = TextWhite, fontSize = 15.sp)
                            }
                        }
                    }
                }
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy((-12).dp)) {
                        val logos = listOf(
                            Res.drawable.logo1,
                            Res.drawable.logo2,
                            Res.drawable.logo3,
                            Res.drawable.logo4
                        )
                        logos.forEach { logo ->
                            Box(
                                modifier = Modifier
                                    .size(if (isMobile) 34.dp else 40.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF1E1E28))
                            ) {
                                Image(
                                    painter = painterResource(logo),
                                    contentDescription = null,
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Fit
                                )
                            }
                        }
                    }
                    Text(
                        text = "Trusted by 1,500+ students",
                        color = TextGray,
                        fontSize = responsive.bodyTextSize
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Udemy Top Rated", color = TextGray, fontSize = responsive.bodyTextSize)
                    Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                        repeat(4) {
                            Icon(
                                Icons.Filled.Star,
                                contentDescription = null,
                                tint = GreenStars,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Icon(
                            Icons.Filled.Star,
                            contentDescription = null,
                            tint = GreenStars.copy(alpha = 0.5f),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Text("4.5/5", color = TextGray, fontSize = responsive.bodyTextSize, fontWeight = FontWeight.Medium)
                }

                Text(
                    text = "Master Android Development with Kotlin & Compose\nand Live Industry Projects.",
                    style = MaterialTheme.typography.displayLarge,
                    fontWeight = FontWeight.Bold,
                    color = TextWhite,
                    fontSize = responsive.heroTitleSize,
                    lineHeight = responsive.heroTitleSize * 1.2f,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "Bridging the gap between college curriculum and industry expectations with practical-first training.",
                    color = TextLightGray,
                    fontSize = responsive.heroSubtitleSize,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = if (isMobile) 0.dp else 80.dp)
                )

                if (isMobile) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Button(
                            onClick = { scrollToSection("Pricing") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(responsive.buttonHeight),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = BlueAccent
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                "Join the Cohourt",
                                color = TextWhite,
                                fontSize = responsive.bodyTextSize,
                                fontWeight = FontWeight.Medium
                            )
                        }
                        OutlinedButton(
                            onClick = { scrollToSection("Curriculum") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(responsive.buttonHeight),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = TextWhite
                            ),
                            border = BorderStroke(1.2.dp, Color.White.copy(alpha = 0.3f)),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Filled.PlayArrow, contentDescription = null, tint = TextWhite, modifier = Modifier.size(16.dp))
                                Text("View Curriculum", color = TextWhite, fontSize = responsive.bodyTextSize)
                            }
                        }
                    }
                } else {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = { scrollToSection("Pricing") },
                            modifier = Modifier.height(responsive.buttonHeight),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = BlueAccent
                            ),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text(
                                "Join the Workshop",
                                color = TextWhite,
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(horizontal = 12.dp)
                            )
                        }
                        OutlinedButton(
                            onClick = { scrollToSection("Curriculum") },
                            modifier = Modifier.height(responsive.buttonHeight),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = TextWhite
                            ),
                            border = BorderStroke(1.5.dp, Color.White.copy(alpha = 0.3f)),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 12.dp)
                            ) {
                                Icon(Icons.Filled.PlayArrow, contentDescription = null, tint = TextWhite, modifier = Modifier.size(14.dp))
                                Text("View Curriculum", color = TextWhite, fontSize = 17.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PartnerLogosSection(onHeightMeasured: (Int) -> Unit = {}) {
    val responsive = LocalResponsiveConfig.current
    val isMobile = responsive.isMobile
    val universities = listOf(
        "Lovely Professional University",
        "Punjabi University",
        "Thapar University",
        "CT University",
        "GNDU Jalandhar",
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .onGloballyPositioned { coordinates ->
                onHeightMeasured(coordinates.size.height)
            }
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
        SectionContainer(
            horizontalAlignment = if (isMobile) Alignment.CenterHorizontally else Alignment.Start,
            verticalPaddingMultiplier = 0.8f
        ) {
            if (isMobile) {
                // Mobile: Only show the upskilling text, hide universities
                Text(
                    text = "Upskilling students in top colleges",
                    color = TextGray,
                    fontSize = responsive.bodyTextSize,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                // Desktop: Same line using Row with FlowRow for universities
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(24.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Upskilling students in top colleges",
                        color = TextGray,
                        fontSize = responsive.bodyTextSize,
                        fontWeight = FontWeight.SemiBold
                    )
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(24.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        universities.forEach { university ->
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(
                                    Icons.Filled.School,
                                    contentDescription = null,
                                    tint = TextGray,
                                    modifier = Modifier.size(15.dp)
                                )
                                Text(
                                    university,
                                    color = TextGray,
                                    fontSize = responsive.bodyTextSize,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ProgramHighlightsSection(onHeightMeasured: (Int) -> Unit = {}) {
    val responsive = LocalResponsiveConfig.current
    val isMobile = responsive.isMobile
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .onGloballyPositioned { coordinates ->
                onHeightMeasured(coordinates.size.height)
            }
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
        SectionContainer {
            if (isMobile) {
                Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
                    ProgramHighlightCard(
                        icon = Icons.Filled.RocketLaunch,
                        title = "Hybrid Model",
                        description = "Video-based learning on Udemy combined with weekly live interactive classes for real-time doubt clearing and mentorship.",
                        features = listOf(
                            "Pre-recorded HD lectures",
                            "Live coding sessions",
                            "Interactive Q&A",
                            "Personalized feedback"
                        )
                    )
                    ProgramHighlightCard(
                        icon = Icons.Filled.PhoneAndroid,
                        title = "Thinking Developer",
                        description = "Focus on readable code, software architecture, design patterns and scalable solutions beyond just syntax and basics.",
                        features = listOf(
                            "MVVM & Clean Architecture",
                            "Design patterns",
                            "Code optimization",
                            "Industry best practices"
                        )
                    )
                    ProgramHighlightCard(
                        icon = Icons.Filled.Work,
                        title = "Real Projects",
                        description = "Build production-ready apps like the 'Trust' app used by Indian Army, Jewellery Catalogues, and Ed-Tech platforms.",
                        features = listOf(
                            "Defense-grade security",
                            "E-commerce solutions",
                            "Firebase integration",
                            "Play Store deployment"
                        )
                    )
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(28.dp)
                ) {
                    ProgramHighlightCard(
                        icon = Icons.Filled.RocketLaunch,
                        title = "Hybrid Model",
                        description = "Video-based learning on Udemy combined with weekly live interactive classes for real-time doubt clearing and mentorship.",
                        features = listOf(
                            "Pre-recorded HD lectures",
                            "Live coding sessions",
                            "Interactive Q&A",
                            "Personalized feedback"
                        ),
                        modifier = Modifier.weight(1f)
                    )
                    ProgramHighlightCard(
                        icon = Icons.Filled.PhoneAndroid,
                        title = "Thinking Developer",
                        description = "Focus on readable code, software architecture, design patterns and scalable solutions beyond just syntax and basics.",
                        features = listOf(
                            "MVVM & Clean Architecture",
                            "Design patterns",
                            "Code optimization",
                            "Industry best practices"
                        ),
                        modifier = Modifier.weight(1f)
                    )
                    ProgramHighlightCard(
                        icon = Icons.Filled.Work,
                        title = "Real Projects",
                        description = "Build production-ready apps like the 'Trust' app used by Indian Army, Jewellery Catalogues, and Ed-Tech platforms.",
                        features = listOf(
                            "Defense-grade security",
                            "E-commerce solutions",
                            "Firebase integration",
                            "Play Store deployment"
                        ),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ProgramHighlightCard(
    icon: ImageVector,
    title: String,
    description: String,
    features: List<String>,
    modifier: Modifier = Modifier
) {
    val responsive = LocalResponsiveConfig.current
    var isHovered by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isHovered) 1.05f else 1f,
        animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing),
        label = "highlightScale"
    )
    val cardColor by animateColorAsState(
        targetValue = if (isHovered) DarkSurfaceVariant else DarkSurfaceVariant.copy(alpha = 0.6f),
        animationSpec = tween(durationMillis = 250),
        label = "highlightColor"
    )
    val borderColor by animateColorAsState(
        targetValue = if (isHovered) BlueAccent else Color.Transparent,
        animationSpec = tween(durationMillis = 250),
        label = "highlightBorder"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                shadowElevation = if (isHovered) 30f else 8f
            }
            .onPointerEvent(PointerEventType.Enter) { isHovered = true }
            .onPointerEvent(PointerEventType.Exit) { isHovered = false },
        colors = CardDefaults.cardColors(
            containerColor = cardColor
        ),
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(1.5.dp, borderColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(if (responsive.isMobile) 20.dp else 28.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = BlueAccent,
                modifier = Modifier.size(if (responsive.isMobile) 26.dp else 32.dp)
            )
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = TextWhite,
                fontSize = if (responsive.isMobile) 19.sp else 22.sp
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = TextGray,
                fontSize = responsive.bodyTextSize,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Features list
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                features.forEach { feature ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.CheckCircle,
                            contentDescription = null,
                            tint = BlueAccent.copy(alpha = 0.8f),
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = feature,
                            color = TextLightGray,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CourseVideoSection(onHeightMeasured: (Int) -> Unit = {}) {
    // Carousel state
    val images = listOf(
        Res.drawable.workshop1,
        Res.drawable.workshop2,
        Res.drawable.workshop3,
        Res.drawable.workshop4,
        Res.drawable.workshop5
    )
    var currentIndex by remember { mutableStateOf(0) }
    val responsive = LocalResponsiveConfig.current
    val isMobile = responsive.isMobile

    // Auto-scroll effect
    LaunchedEffect(Unit) {
        while (true) {
            delay(3000) // Change image every 3 seconds
            currentIndex = (currentIndex + 1) % images.size
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .onGloballyPositioned { coordinates ->
                onHeightMeasured(coordinates.size.height)
            }
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
        SectionContainer {
            if (isMobile) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CourseVideoTextBlock()
                    CourseVideoCarousel(
                        images = images,
                        currentIndex = currentIndex,
                        isMobile = true
                    )
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(48.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        CourseVideoTextBlock()
                        Spacer(modifier = Modifier.height(12.dp))
                    }

                    CourseVideoCarousel(
                        images = images,
                        currentIndex = currentIndex,
                        modifier = Modifier.weight(1f),
                        isMobile = false
                    )
                }
            }
        }
    }
}

@Composable
private fun CourseVideoTextBlock() {
    val responsive = LocalResponsiveConfig.current
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
            text = "This changes everything for students",
            color = BlueAccent,
            fontSize = responsive.bodyTextSize,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = "Modern Android Development with Kotlin Jetpack Compose",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = TextWhite,
            fontSize = responsive.heroSubtitleSize * 2,
            lineHeight = responsive.heroSubtitleSize * 2.2f
        )
        Text(
            text = "Students move beyond tutorials and into architecting real apps ideal for product roles — fast.",
            color = TextLightGray,
            fontSize = responsive.bodyTextSize,
            lineHeight = 24.sp
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf(
                "Clean Architecture decoded for real products",
                "Weekly live code reviews",
                "Deploy to Play Store in 12 weeks"
            ).forEach { bullet ->
                Text(
                    text = "• $bullet",
                    color = TextGray,
                    fontSize = responsive.bodyTextSize
                )
            }
        }
    }
}

@Composable
private fun CourseVideoCarousel(
    images: List<DrawableResource>,
    currentIndex: Int,
    modifier: Modifier = Modifier,
    isMobile: Boolean
) {
    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(if (isMobile) 240.dp else 450.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFF1E1E28)),
            contentAlignment = Alignment.Center
        ) {
            images.forEachIndexed { index, image ->
                AnimatedVisibility(
                    visible = currentIndex == index,
                    enter = fadeIn(animationSpec = tween(600)),
                    exit = fadeOut(animationSpec = tween(600))
                ) {
                    Image(
                        painter = painterResource(image),
                        contentDescription = "Workshop Preview $index",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.FillBounds
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            images.indices.forEach { index ->
                Box(
                    modifier = Modifier
                        .size(if (currentIndex == index) 24.dp else 8.dp, 8.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(
                            if (currentIndex == index) BlueAccent
                            else TextGray.copy(alpha = 0.5f)
                        )
                )
            }
        }
    }
}

@Composable
fun WhyChooseSection(
    scrollState: ScrollState,
    sectionPositions: MutableMap<String, Int>,
    onHeightMeasured: (Int) -> Unit = {}
) {
    val responsive = LocalResponsiveConfig.current
    val isMobile = responsive.isMobile
    val coroutineScope = rememberCoroutineScope()

    // Function to scroll to a section
    val scrollToSection: (String) -> Unit = { sectionName ->
        val position = sectionPositions[sectionName]
        if (position != null && position >= 0) {
            coroutineScope.launch {
                // Add a small offset to account for any fixed header spacing
                val scrollPosition = (position - 80).coerceAtLeast(0)
                scrollState.animateScrollTo(
                    value = scrollPosition,
                    animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing)
                )
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .onGloballyPositioned { coordinates ->
                onHeightMeasured(coordinates.size.height)
            }
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
        // Decorative background circles
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

        SectionContainer(horizontalAlignment = Alignment.CenterHorizontally) {
            // Enhanced rating badge
            Surface(
                modifier = Modifier.padding(bottom = 16.dp),
                shape = RoundedCornerShape(24.dp),
                color = Color(0xFF1E1E28),
                border = BorderStroke(1.dp, BlueAccent.copy(alpha = 0.3f))
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
                ) {
                    Icon(
                        Icons.Filled.Star,
                        contentDescription = null,
                        tint = Color(0xFFFFD700),
                        modifier = Modifier.size(22.dp)
                    )
                    Text(
                        "Excellent",
                        color = TextWhite,
                        fontSize = responsive.bodyTextSize,
                        fontWeight = FontWeight.SemiBold
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(3.dp)) {
                        repeat(4) {
                            Icon(
                                Icons.Filled.Star,
                                contentDescription = null,
                                tint = Color(0xFFFFD700),
                                modifier = Modifier.size(16.dp)
                            )
                        }
                        Icon(
                            Icons.Filled.Star,
                            contentDescription = null,
                            tint = Color(0xFFFFD700).copy(alpha = 0.5f),
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    Text(
                        "4.5",
                        color = TextWhite,
                        fontSize = responsive.bodyTextSize,
                        fontWeight = FontWeight.Bold
                    )
                    if (!isMobile) {
                        Box(
                            modifier = Modifier
                                .width(1.dp)
                                .height(20.dp)
                                .background(TextGray.copy(alpha = 0.3f))
                        )
                        Text(
                            "1,500+ students",
                            color = TextGray,
                            fontSize = responsive.bodyTextSize
                        )
                    }
                }
            }

            // Enhanced title with gradient
            Text(
                text = "Why choose",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = TextWhite,
                fontSize = if (isMobile) responsive.heroSubtitleSize * 1.6f else 48.sp,
                textAlign = TextAlign.Center
            )
            Text(
                text = "Humble Coders?",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.ExtraBold,
                color = BlueAccent,
                fontSize = if (isMobile) responsive.heroSubtitleSize * 1.6f else 68.sp,
                textAlign = TextAlign.Center
            )

            // Enhanced description tied to pain-point stories
            Text(
                text = "Colleges often get stuck on theory, but at Humble Coders, we believe the only way to learn is by doing. We flip the script by having you build the exact real-world applications highlighted below. Every architecture decision, clean-code pattern, and deployment strategy is mastered by actually solving industry problems rather than just reading about them.",
                color = TextLightGray,
                fontSize = responsive.bodyTextSize,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(
                    top = 20.dp,
                    start = if (isMobile) 0.dp else 100.dp,
                    end = if (isMobile) 0.dp else 100.dp
                ),
                lineHeight = 26.sp
            )

            Spacer(modifier = Modifier.height(28.dp))

            if (isMobile) {
                Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
                    PainPointCard(
                        icon = Icons.Filled.Security,
                        title = "Defense & Security",
                        description = "Mission-critical mobile apps for the Indian Army's Kharga Corps.",
                        stats = listOf(
                            "Offline-first field ops dashboards",
                            "End-to-end encryption & device attestation"
                        ),
                        impact = "Reduced incident reporting time by 43% with hardened Android builds."
                    )
                    PainPointCard(
                        icon = Icons.Filled.ShoppingBag,
                        title = "Retail Solutions",
                        description = "Immersive product experiences for premium jewellery brands.",
                        stats = listOf(
                            "360° catalogue with live inventory sync",
                            "CRM-ready wishlists & assisted selling flows"
                        ),
                        impact = "Lifted assisted conversion by 28% in pilot stores within six weeks."
                    )
                    PainPointCard(
                        icon = Icons.Filled.School,
                        title = "Ed-Tech",
                        description = "Attendance & mentoring platforms that fight proxy culture.",
                        stats = listOf(
                            "Geo-fenced check-ins + face match",
                            "Mentor-led nudges with action plans"
                        ),
                        impact = "Brought absenteeism below 5% while surfacing at-risk cohorts early."
                    )
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(28.dp)
                ) {
                    PainPointCard(
                        icon = Icons.Filled.Security,
                        title = "Defense & Security",
                        description = "Mission-critical mobile apps for the Indian Army's Kharga Corps.",
                        stats = listOf(
                            "Offline-first field ops dashboards",
                            "End-to-end encryption & device attestation"
                        ),
                        impact = "Reduced incident reporting time by 43% with hardened Android builds.",
                        modifier = Modifier.weight(1f)
                    )
                    PainPointCard(
                        icon = Icons.Filled.ShoppingBag,
                        title = "Retail Solutions",
                        description = "Immersive product experiences for premium jewellery brands.",
                        stats = listOf(
                            "360° catalogue with live inventory sync",
                            "CRM-ready wishlists & assisted selling flows"
                        ),
                        impact = "Lifted assisted conversion by 28% in pilot stores within six weeks.",
                        modifier = Modifier.weight(1f)
                    )
                    PainPointCard(
                        icon = Icons.Filled.School,
                        title = "Ed-Tech",
                        description = "Attendance & mentoring platforms that fight proxy culture.",
                        stats = listOf(
                            "Geo-fenced check-ins + face match",
                            "Mentor-led nudges with action plans"
                        ),
                        impact = "Brought absenteeism below 5% while surfacing at-risk cohorts early.",
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PainPointCard(
    icon: ImageVector,
    title: String,
    description: String,
    stats: List<String>,
    impact: String,
    modifier: Modifier = Modifier
) {
    val responsive = LocalResponsiveConfig.current
    var isHovered by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isHovered) 1.04f else 1f,
        animationSpec = tween(durationMillis = 220, easing = FastOutSlowInEasing),
        label = "painPointScale"
    )
    val cardColor by animateColorAsState(
        targetValue = if (isHovered) Color(0xFF0E1724) else Color(0xFF0B111B),
        animationSpec = tween(durationMillis = 220),
        label = "painPointColor"
    )
    val borderColor by animateColorAsState(
        targetValue = if (isHovered) BlueAccent.copy(alpha = 0.85f) else Color(0xFF1F2A3B),
        animationSpec = tween(durationMillis = 220),
        label = "painPointBorder"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                shadowElevation = if (isHovered) 40f else 8f
            }
            .onPointerEvent(PointerEventType.Enter) { isHovered = true }
            .onPointerEvent(PointerEventType.Exit) { isHovered = false },
        colors = CardDefaults.cardColors(
            containerColor = cardColor
        ),
        shape = RoundedCornerShape(18.dp),
        border = BorderStroke(1.dp, borderColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(if (responsive.isMobile) 22.dp else 32.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(if (responsive.isMobile) 48.dp else 60.dp)
                    .clip(CircleShape)
                    .background(BlueAccent.copy(alpha = 0.18f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = BlueAccent,
                    modifier = Modifier.size(if (responsive.isMobile) 22.dp else 28.dp)
                )
            }
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = TextWhite,
                    fontSize = if (responsive.isMobile) 18.sp else 20.sp,
                    lineHeight = 26.sp
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextLightGray,
                    fontSize = responsive.bodyTextSize,
                    lineHeight = 24.sp
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                stats.forEach { bullet ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.CheckCircle,
                            contentDescription = null,
                            tint = BlueAccent.copy(alpha = 0.9f),
                            modifier = Modifier.size(if (responsive.isMobile) 14.dp else 18.dp)
                        )
                        Text(
                            text = bullet,
                            color = TextWhite,
                            fontSize = responsive.bodyTextSize,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            HorizontalDivider( color = Color(0xFF202A38))

            Text(
                text = impact,
                style = MaterialTheme.typography.bodyLarge,
                color = TextLightGray,
                fontSize = responsive.bodyTextSize,
                lineHeight = 25.sp
            )
        }
    }
}

@Composable
fun CurriculumSection(sectionPositions: MutableMap<String, Int> = mutableMapOf()) {
    val responsive = LocalResponsiveConfig.current
    val uriHandler = LocalUriHandler.current
    val isMobile = responsive.isMobile
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .onGloballyPositioned { coordinates ->
                // Use positionInRoot() to get absolute position from top of scrollable content
                sectionPositions["Curriculum"] = coordinates.positionInRoot().y.toInt()
            }
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
        SectionContainer {
            if (isMobile) {
                Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        ModuleCard("Kotlin Basics & Logic Building", 1)
                        ModuleCard("Jetpack Compose UI Framework", 2)
                        ModuleCard("MVVM Architecture & State", 3)
                        ModuleCard("Networking & APIs", 4)
                        ModuleCard("Firebase & Cloud Integration", 5)
                        ModuleCard("Capstone: Deploying to Play Store", 6)
                    }
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(320.dp)
                                .clip(RoundedCornerShape(16.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(Res.drawable.android),
                                contentDescription = "Android Curriculum",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Fit
                            )
                        }
                        Button(
                            onClick = { uriHandler.openUri(UDEMY_COURSE_URL) },
                            modifier = Modifier
                                .width(256.dp)
                                .height(responsive.buttonHeight).align(Alignment.CenterHorizontally),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = BlueAccent
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = "View Course on Udemy",
                                color = TextWhite,
                                fontSize = responsive.bodyTextSize,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(48.dp)
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        ModuleCard("Kotlin Basics & Logic Building", 1)
                        ModuleCard("Jetpack Compose UI Framework", 2)
                        ModuleCard("MVVM Architecture & State", 3)
                        ModuleCard("Networking & APIs", 4)
                        ModuleCard("Firebase & Cloud Integration", 5)
                        ModuleCard("Capstone: Deploying to Play Store", 6)
                    }

                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(550.dp)
                                .clip(RoundedCornerShape(16.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(Res.drawable.android),
                                contentDescription = "Android Curriculum",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Fit
                            )
                        }
                        Button(
                            onClick = { uriHandler.openUri(UDEMY_COURSE_URL) },
                            modifier = Modifier
                                .width(256.dp)
                                .height(responsive.buttonHeight),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = BlueAccent
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = "View Course on Udemy",
                                color = TextWhite,
                                fontSize = responsive.bodyTextSize,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.Top
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(BlueAccent.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Filled.Diamond, contentDescription = null, tint = BlueAccent, modifier = Modifier.size(24.dp))
                }
                Text(
                    text = "We believe in specialized mastery rather than general knowledge. Our curriculum is laser-focused on Native Android Development using Kotlin and modern Jetpack Compose. We guide you through the complete mobile development lifecycle, ensuring you graduate with deep expertise and a professional Capstone app to show for it.",
                    color = TextLightGray,
                    fontSize = responsive.bodyTextSize,
                    lineHeight = 26.sp
                )
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ModuleCard(title: String, number: Int) {
    val responsive = LocalResponsiveConfig.current
    var isHovered by remember { mutableStateOf(false) }
    val backgroundColor by animateColorAsState(
        targetValue = if (isHovered) Color(0xFF1A1F2F) else Color(0xFF13131A),
        animationSpec = tween(durationMillis = 200),
        label = "moduleCardBg"
    )
    val borderColor by animateColorAsState(
        targetValue = if (isHovered) BlueAccent else Color.Transparent,
        animationSpec = tween(durationMillis = 200),
        label = "moduleCardBorder"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .onPointerEvent(PointerEventType.Enter) { isHovered = true }
            .onPointerEvent(PointerEventType.Exit) { isHovered = false },
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.dp, borderColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(if (responsive.isMobile) 16.dp else 24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Filled.PlayArrow, contentDescription = null, tint = if (isHovered) BlueAccent else TextGray, modifier = Modifier.size(16.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = TextWhite,
                    fontSize = responsive.bodyTextSize,
                    fontWeight = FontWeight.Medium
                )
            }
            Surface(
                color = Color(0xFF1E1E28),
                shape = RoundedCornerShape(20.dp)
            ) {
                Text(
                    text = "Module $number",
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                    color = TextGray,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun TestimonialsSection(sectionPositions: MutableMap<String, Int> = mutableMapOf()) {
    val responsive = LocalResponsiveConfig.current
    val isMobile = responsive.isMobile
    val testimonials = listOf(
        TestimonialData(
            "The team is highly dependable for sensitive, high-stakes software development. The 'Trust' application was delivered with precision.",
            "Col. Vikash Parashar",
            "Indian Army"
        ),
        TestimonialData(
            "Their technical expertise and structured approach to the joint certification program were commendable. Students found it highly practical.",
            "Dr. Prashant Singh Rana",
            "Thapar Institute"
        ),
        TestimonialData(
            "Humble Coders delivers the kind of practical training that students genuinely need. Their ability to simplify advanced Android topics through active coding is exceptional..",
            "Dr. Vishal Gupta",
            "Punjabi University"
        ),
        TestimonialData(
            "The workshop was an eye-opener. I finally understood how to connect APIs and manage state in a real app.",
            "Harsh Vardhan",
            "LPU"
        ),
        TestimonialData(
            "From zero coding knowledge to building my own app on Play Store. Humble Coders made it possible in just 3 months!",
            "Priya Kaur",
            "Udemy"
        )
    )

    val carouselItems = remember {
        List(20) { testimonials }.flatten()
    }
    val carouselState = rememberLazyListState(initialFirstVisibleItemIndex = carouselItems.size / 2)
    var currentIndex by remember { mutableStateOf(carouselItems.size / 2) }

    LaunchedEffect(carouselState, carouselItems) {
        if (carouselItems.isEmpty()) return@LaunchedEffect
        while (true) {
            delay(4000)
            currentIndex = (currentIndex + 1) % carouselItems.size
            carouselState.animateScrollToItem(currentIndex)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .onGloballyPositioned { coordinates ->
                // Use positionInRoot() to get absolute position from top of scrollable content
                sectionPositions["Reviews"] = coordinates.positionInRoot().y.toInt()
            }
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
        SectionContainer(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Endorsed by",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = TextWhite,
                fontSize = if (isMobile) responsive.heroSubtitleSize * 1.6f else 48.sp,
                textAlign = TextAlign.Center
            )
            Text(
                text = "Industry Leaders & Academia",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = TextWhite,
                fontSize = if (isMobile) responsive.heroSubtitleSize * 1.6f else 48.sp,
                textAlign = TextAlign.Center
            )
            Text(
                text = "See what professors and our students have to say about our technical expertise and teaching style.",
                color = TextLightGray,
                fontSize = responsive.bodyTextSize,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(
                    top = 20.dp,
                    start = if (isMobile) 0.dp else 120.dp,
                    end = if (isMobile) 0.dp else 120.dp
                ),
                lineHeight = 26.sp
            )

            Spacer(modifier = Modifier.height(56.dp))

            if (isMobile) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    testimonials.take(3).forEach { testimonial ->
                        TestimonialCard(
                            text = testimonial.text,
                            name = testimonial.name,
                            location = testimonial.location,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            } else {
                LazyRow(
                    state = carouselState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(320.dp)
                        .clipToBounds(),
                    horizontalArrangement = Arrangement.spacedBy(28.dp),
                    contentPadding = PaddingValues(horizontal = 80.dp)
                ) {
                    itemsIndexed(carouselItems) { index, testimonial ->
                        val layoutInfo = carouselState.layoutInfo
                        val visibleInfo = layoutInfo.visibleItemsInfo.firstOrNull { it.index == index }
                        val viewportCenter = (layoutInfo.viewportStartOffset + layoutInfo.viewportEndOffset) / 2f
                        val itemCenter = visibleInfo?.let { it.offset + it.size / 2f }
                        val distanceFromCenter = itemCenter?.let { kotlin.math.abs(it - viewportCenter) } ?: viewportCenter
                        val normalizedDistance = if (layoutInfo.viewportEndOffset != layoutInfo.viewportStartOffset) {
                            (distanceFromCenter / (layoutInfo.viewportEndOffset - layoutInfo.viewportStartOffset)).coerceIn(0f, 1f)
                        } else {
                            0f
                        }
                        val scale = 1.1f - (0.25f * normalizedDistance)
                        val alpha = 0.5f + (0.5f * (1f - normalizedDistance))

                        TestimonialCard(
                            text = testimonial.text,
                            name = testimonial.name,
                            location = testimonial.location,
                            modifier = Modifier
                                .width(380.dp)
                                .graphicsLayer {
                                    scaleX = scale
                                    scaleY = scale
                                    this.alpha = alpha
                                }
                        )
                    }
                }
            }
        }
    }
}

data class TestimonialData(
    val text: String,
    val name: String,
    val location: String
)

@Composable
fun TestimonialCard(text: String, name: String, location: String, modifier: Modifier = Modifier) {
    val responsive = LocalResponsiveConfig.current
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF13131A)
        ),
        shape = RoundedCornerShape(14.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(if (responsive.isMobile) 20.dp else 28.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(3.dp)) {
                repeat(5) {
                    Icon(Icons.Filled.Star, contentDescription = null, tint = GreenStars, modifier = Modifier.size(22.dp))
                }
            }
            Text(
                text = "\"$text\"",
                style = MaterialTheme.typography.bodyLarge,
                color = TextWhite,
                fontSize = responsive.bodyTextSize,
                lineHeight = 26.sp
            )
            Text(
                text = "$name, $location",
                style = MaterialTheme.typography.bodyMedium,
                color = TextGray,
                fontSize = responsive.bodyTextSize
            )
        }
    }
}

@Composable
fun MeetMentorSection(sectionPositions: MutableMap<String, Int> = mutableMapOf()) {
    val responsive = LocalResponsiveConfig.current
    val isMobile = responsive.isMobile
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .onGloballyPositioned { coordinates ->
                // Use positionInRoot() to get absolute position from top of scrollable content
                sectionPositions["Mentors"] = coordinates.positionInRoot().y.toInt()
            }
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
        SectionContainer(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Meet Your Mentors",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = TextWhite,
                fontSize = if (isMobile) responsive.heroSubtitleSize * 1.6f else 48.sp,
                textAlign = TextAlign.Center
            )

            if (isMobile) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(340.dp)
                            .clip(RoundedCornerShape(16.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(Res.drawable.mentor),
                            contentDescription = "Mentors",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Fit
                        )
                    }
                    MentorDetails()
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(56.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(600.dp)
                            .clip(RoundedCornerShape(16.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(Res.drawable.mentor),
                            contentDescription = "Mentors",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Fit
                        )
                    }
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        MentorDetails()
                    }
                }
            }
        }
    }
}

@Composable
private fun MentorDetails() {
    val responsive = LocalResponsiveConfig.current
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .widthIn(max = if (responsive.isMobile) Dp.Unspecified else 520.dp),
        color = Color(0xFF13131A),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, Color(0xFF1F2937))
    ) {
        Column(
            modifier = Modifier.padding(if (responsive.isMobile) 20.dp else 32.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Co-Founders & Instructors",
                color = BlueAccent,
                fontSize = responsive.bodyTextSize,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = "Ansh Bajaj & Ishank Goyal",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = TextWhite,
                fontSize = if (responsive.isMobile) 28.sp else 38.sp
            )
            Text(
                text = "Ansh (Founder & CEO) and Ishank (Founder & CFO) lead every cohort together, blending product vision with deep engineering execution.",
                color = TextLightGray,
                fontSize = responsive.bodyTextSize,
                lineHeight = 24.sp
            )
            Text(
                text = "Ranked #1 for Android App Development (Kotlin) on Udemy, they have mentored 1,500+ builders through live cohorts and accelerator-style mentorship.",
                color = TextLightGray,
                fontSize = responsive.bodyTextSize,
                lineHeight = 24.sp
            )
            Text(
                text = "They delivered the Trust Android application for the Indian Army’s Kharga Corps—mission-critical software delivered on time with military-grade reliability.",
                color = TextLightGray,
                fontSize = responsive.bodyTextSize,
                lineHeight = 24.sp
            )
            Text(
                text = "Their academic collaborations include a joint certification program with Thapar Institute (200+ students) and a hands-on workshop at Punjabi University that helped MCA students ship complete apps.",
                color = TextLightGray,
                fontSize = responsive.bodyTextSize,
                lineHeight = 24.sp
            )
            val highlights = listOf(
                "Indian Army letter of recommendation for precision delivery.",
                "Thapar Summer School: Android + ML integration bootcamp.",
                "Punjabi University workshop featured in Royal Patiala."
            )
            highlights.forEach { highlight ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.CheckCircle,
                        contentDescription = null,
                        tint = BlueAccent,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = highlight,
                        color = TextLightGray,
                        fontSize = responsive.bodyTextSize,
                        lineHeight = 22.sp
                    )
                }
            }
            Text(
                text = "Both founders obsess over helping you think like a real developer—shipping production-ready apps with scalable architecture, clean code, and confident releases.",
                color = TextLightGray,
                fontSize = responsive.bodyTextSize,
                lineHeight = 24.sp
            )
        }
    }
}

@Composable
fun PricingSection(sectionPositions: MutableMap<String, Int> = mutableMapOf()) {
    val responsive = LocalResponsiveConfig.current
    val isMobile = responsive.isMobile
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .onGloballyPositioned { coordinates ->
                // Use positionInRoot() to get absolute position from top of scrollable content
                sectionPositions["Pricing"] = coordinates.positionInRoot().y.toInt()
            }
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
        SectionContainer(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Ready to Code?",
                color = BlueAccent,
                fontSize = responsive.bodyTextSize,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Invest in Your Skills",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = TextWhite,
                fontSize = if (isMobile) responsive.heroSubtitleSize * 1.6f else 48.sp
            )
            Text(
                text = "Choose between our self-paced video courses or our immersive hybrid live cohort.",
                color = TextLightGray,
                fontSize = responsive.bodyTextSize,
                textAlign = TextAlign.Center,
                lineHeight = 26.sp,
                modifier = Modifier.padding(
                    top = 16.dp,
                    start = if (isMobile) 0.dp else 100.dp,
                    end = if (isMobile) 0.dp else 100.dp
                )
            )

            Spacer(modifier = Modifier.height(56.dp))

            if (isMobile) {
                Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
                    PricingCard(
                        title = "Self-Paced Udemy Course",
                        description = "Top rated course to start your Android journey.",
                        price = "₹499",
                        features = listOf(
                            "Full HD Video Lectures",
                            "Lifetime Access",
                            "Basic Q&A Support",
                            "Certificate of Completion"
                        ),
                        isPopular = false
                    )
                    PricingCard(
                        title = "Hybrid Live Bootcamp",
                        description = "The complete mentorship program with live classes.",
                        price = "₹4,999",
                        features = listOf(
                            "Everything in Basic, PLUS:",
                            "Weekly Live Interactive Classes",
                            "Code Reviews by Mentors",
                            "Capstone Project Guidance",
                            "Internship Assistance"
                        ),
                        isPopular = true
                    )
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(32.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    PricingCard(
                        title = "Self-Paced Udemy Course",
                        description = "Top rated course to start your Android journey.",
                        price = "₹499",
                        features = listOf(
                            "Full HD Video Lectures",
                            "Lifetime Access",
                            "Basic Q&A Support",
                            "Certificate of Completion"
                        ),
                        isPopular = false,
                        modifier = Modifier.weight(1f)
                    )
                    PricingCard(
                        title = "Hybrid Live Bootcamp",
                        description = "The complete mentorship program with live classes.",
                        price = "₹4,999",
                        features = listOf(
                            "Everything in Basic, PLUS:",
                            "Weekly Live Interactive Classes",
                            "Code Reviews by Mentors",
                            "Capstone Project Guidance",
                            "Internship Assistance"
                        ),
                        isPopular = true,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
fun PricingCard(
    title: String,
    description: String,
    price: String,
    features: List<String>,
    isPopular: Boolean,
    modifier: Modifier = Modifier
) {
    val responsive = LocalResponsiveConfig.current
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (isPopular) BlueAccent else Color(0xFF13131A)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(if (responsive.isMobile) 24.dp else 36.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                if (isPopular) {
                    Surface(
                        color = Color(0xFFFFD700),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Text(
                            text = "Recommended",
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                            color = Color(0xFF0A0A0F),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = TextWhite,
                    fontSize = if (responsive.isMobile) 22.sp else 26.sp,
                    lineHeight = 34.sp
                )
                Text(
                    text = description,
                    color = if (isPopular) TextWhite.copy(alpha = 0.9f) else TextGray,
                    fontSize = responsive.bodyTextSize,
                    lineHeight = 24.sp
                )
                Text(
                    text = price,
                    style = MaterialTheme.typography.displayMedium,
                    fontWeight = FontWeight.Bold,
                    color = TextWhite,
                    fontSize = if (responsive.isMobile) 34.sp else 42.sp
                )

                Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    features.forEach { feature ->
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Icon(Icons.Filled.Check, contentDescription = null, tint = TextWhite, modifier = Modifier.size(20.dp))
                            Text(
                                text = feature,
                                color = if (isPopular) TextWhite.copy(alpha = 0.95f) else TextWhite,
                                fontSize = responsive.bodyTextSize,
                                lineHeight = 24.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FAQSection(sectionPositions: MutableMap<String, Int> = mutableMapOf()) {
    var expandedIndex by remember { mutableStateOf(-1) }
    val responsive = LocalResponsiveConfig.current
    val isMobile = responsive.isMobile

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .onGloballyPositioned { coordinates ->
                // Use positionInRoot() to get absolute position from top of scrollable content
                sectionPositions["FAQs"] = coordinates.positionInRoot().y.toInt()
            }
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
        SectionContainer(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Frequently asked questions",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = TextWhite,
                fontSize = if (isMobile) responsive.heroSubtitleSize * 1.6f else 48.sp
            )
            Text(
                text = "Everything you need to know about Humble Coders training.",
                color = TextLightGray,
                fontSize = responsive.bodyTextSize,
                modifier = Modifier.padding(top = 16.dp),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(48.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                FAQItem(
                    question = "Do I need prior experience with Android?",
                    answer = "No prior experience is required. We start from Kotlin basics and logic building. The course is designed for complete beginners as well as those with some programming background.",
                    expanded = expandedIndex == 0,
                    onExpandedChange = { expandedIndex = if (expandedIndex == 0) -1 else 0 }
                )
                FAQItem(
                    question = "Is this course just video lectures?",
                    answer = "Our hybrid model includes video lectures for concepts and Live Classes for doubt clearing and project work. You get the flexibility of self-paced learning combined with the support of live mentorship sessions every week.",
                    expanded = expandedIndex == 1,
                    onExpandedChange = { expandedIndex = if (expandedIndex == 1) -1 else 1 }
                )
                FAQItem(
                    question = "What tech stack do you teach?",
                    answer = "We focus on the latest industry standards: Kotlin, Jetpack Compose, MVVM Architecture, Retrofit for networking, Firebase for backend services, and Room Database for local storage. Everything you need to build production-ready Android apps.",
                    expanded = expandedIndex == 2,
                    onExpandedChange = { expandedIndex = if (expandedIndex == 2) -1 else 2 }
                )
                FAQItem(
                    question = "Will I build real projects?",
                    answer = "Yes, every student must complete a Capstone project to receive certification. You'll work on multiple mini-projects throughout the course and one major project that you'll deploy to the Google Play Store.",
                    expanded = expandedIndex == 3,
                    onExpandedChange = { expandedIndex = if (expandedIndex == 3) -1 else 3 }
                )
                FAQItem(
                    question = "How long does the course take to complete?",
                    answer = "The self-paced Udemy course can be completed in 6-8 weeks if you dedicate 10-12 hours per week. The Hybrid Live Bootcamp is a structured 12-week program with weekly live sessions and milestones.",
                    expanded = expandedIndex == 4,
                    onExpandedChange = { expandedIndex = if (expandedIndex == 4) -1 else 4 }
                )
                FAQItem(
                    question = "Do you provide placement assistance?",
                    answer = "Yes! The Hybrid Live Bootcamp includes internship assistance, resume building guidance, mock interviews, and connections to our hiring partners. We help you prepare for the job market with real-world interview questions and coding challenges.",
                    expanded = expandedIndex == 5,
                    onExpandedChange = { expandedIndex = if (expandedIndex == 5) -1 else 5 }
                )
                FAQItem(
                    question = "What if I get stuck or need help?",
                    answer = "For the self-paced course, you have Q&A support on Udemy. For the Hybrid Bootcamp, you get dedicated mentor support through weekly live sessions, a private Discord community, and direct access to instructors for code reviews and doubt clearing.",
                    expanded = expandedIndex == 6,
                    onExpandedChange = { expandedIndex = if (expandedIndex == 6) -1 else 6 }
                )
                FAQItem(
                    question = "Can I get a refund if I'm not satisfied?",
                    answer = "The Udemy course comes with Udemy's 30-day money-back guarantee. For the Hybrid Live Bootcamp, we offer a 7-day trial period where you can request a full refund if the program doesn't meet your expectations.",
                    expanded = expandedIndex == 7,
                    onExpandedChange = { expandedIndex = if (expandedIndex == 7) -1 else 7 }
                )
            }
        }
    }
}

@Composable
fun FAQItem(question: String, answer: String, expanded: Boolean, onExpandedChange: () -> Unit) {
    val responsive = LocalResponsiveConfig.current
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF13131A)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onExpandedChange() }
                    .padding(if (responsive.isMobile) 20.dp else 28.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = question,
                    style = MaterialTheme.typography.titleMedium,
                    color = TextWhite,
                    fontSize = if (responsive.isMobile) 16.sp else 18.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = { onExpandedChange() }) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF1E1E28)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (expanded) Icons.Filled.ExpandMore else Icons.Filled.PlayArrow,
                            contentDescription = null,
                            tint = TextGray,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }
            }

            AnimatedVisibility(
                visible = expanded,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Text(
                    text = answer,
                    modifier = Modifier
                        .padding(horizontal = if (responsive.isMobile) 20.dp else 28.dp, vertical = 0.dp)
                        .padding(bottom = if (responsive.isMobile) 20.dp else 28.dp),
                    color = TextGray,
                    fontSize = responsive.bodyTextSize,
                    lineHeight = 24.sp
                )
            }
        }
    }
}

@Composable
fun FinalCTASection() {
    val responsive = LocalResponsiveConfig.current
    val isMobile = responsive.isMobile
    val uriHandler = LocalUriHandler.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = responsive.horizontalPadding, vertical = responsive.verticalSectionPadding)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            Text(
                text = "Are you ready to",
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Bold,
                color = TextWhite,
                fontSize = if (isMobile) responsive.heroSubtitleSize * 1.6f else 56.sp,
                textAlign = TextAlign.Center
            )
            Text(
                text = "Become a developer?",
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Bold,
                color = TextWhite,
                fontSize = if (isMobile) responsive.heroSubtitleSize * 1.6f else 56.sp,
                textAlign = TextAlign.Center
            )

            Button(
                onClick = { uriHandler.openUri(UDEMY_COURSE_URL) },
                modifier = Modifier
                    .height(responsive.buttonHeight)
                    .let { if (isMobile) it.fillMaxWidth() else it },
                colors = ButtonDefaults.buttonColors(
                    containerColor = BlueAccent
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    "Enroll Now",
                    color = TextWhite,
                    fontSize = responsive.bodyTextSize,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
            }

            Spacer(modifier = Modifier.height(48.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(32.dp))
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                Color(0xFF1A1A24),
                                Color(0xFF0F0F16),
                                DarkBackground
                            ),
                            center = Offset(200f, 200f),
                            radius = 1200f
                        )
                    )
            ) {
                // Decorative gradient orbs in background
                Box(
                    modifier = Modifier
                        .size(400.dp)
                        .offset(x = (-100).dp, y = (-150).dp)
                        .blur(150.dp)
                        .background(
                            Brush.radialGradient(
                                colors = listOf(
                                    BlueAccent.copy(alpha = 0.3f),
                                    Color.Transparent
                                )
                            )
                        )
                )

                Box(
                    modifier = Modifier
                        .size(500.dp)
                        .align(Alignment.BottomEnd)
                        .offset(x = 150.dp, y = 200.dp)
                        .blur(180.dp)
                        .background(
                            Brush.radialGradient(
                                colors = listOf(
                                    BlueDark.copy(alpha = 0.4f),
                                    Color.Transparent
                                )
                            )
                        )
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(if (isMobile) 40.dp else 80.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    // Decorative icon/element at top
                    val infiniteTransition = rememberInfiniteTransition(label = "spin")
                    val rotationAngle by infiniteTransition.animateFloat(
                        initialValue = 0f,
                        targetValue = 360f,
                        animationSpec = infiniteRepeatable(
                            animation = keyframes {
                                durationMillis = 3000 // 3 seconds total cycle
                                0f at 0 // Start at 0 degrees
                                360f at 1000 // Fast spin completes in 250ms
                                360f at 3000 // Stay at 360 degrees (same as 0 visually) until next cycle
                            }
                        ),
                        label = "rotation"
                    )

                    Icon(
                        painter = painterResource(Res.drawable.ic_android),
                        contentDescription = null,
                        tint = BlueLight,
                        modifier = Modifier
                            .size(60.dp)
                            .rotate(rotationAngle)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Badge/Pill
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(24.dp))
                            .background(DarkSurfaceVariant)
                            .border(
                                width = 1.dp,
                                color = BlueAccent.copy(alpha = 0.3f),
                                shape = RoundedCornerShape(24.dp)
                            )
                            .padding(horizontal = 20.dp, vertical = 10.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .background(BlueLight, CircleShape)
                            )
                            Text(
                                text = "Start Your Journey!",
                                color = TextWhite,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            )
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .background(BlueLight, CircleShape)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Main heading
                    Text(
                        text = "Kick off your Android journey!",
                        style = MaterialTheme.typography.displayMedium,
                        fontWeight = FontWeight.Bold,
                        color = TextWhite,
                        fontSize = if (isMobile) 32.sp else 48.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = if (isMobile) 38.sp else 56.sp
                    )

                    // Subheading
                    Text(
                        text = "Join thousands of students building real-world apps. Master Android development from basics to advanced.",
                        color = TextLightGray,
                        fontSize = if (isMobile) 16.sp else 18.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = if (isMobile) 24.sp else 28.sp,
                        modifier = Modifier.padding(horizontal = if (isMobile) 0.dp else 40.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // CTA Button
                    Button(
                        onClick = { uriHandler.openUri(UDEMY_COURSE_URL) },
                        modifier = Modifier
                            .height(56.dp)
                            .let { if (isMobile) it.fillMaxWidth(0.8f) else it.width(200.dp) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(28.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.horizontalGradient(
                                        colors = listOf(
                                            BlueAccent,
                                            BlueLight
                                        )
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "Explore Now",
                                color = TextWhite,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun Footer() {
    val responsive = LocalResponsiveConfig.current
    val isMobile = responsive.isMobile
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color(0xFF0A0A0F)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = responsive.horizontalPadding, vertical = responsive.verticalSectionPadding / 2),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            if (isMobile) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    FooterBrand()
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth(),
                        maxItemsInEachRow = 2
                    ) {
                        FooterLinks()
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        SocialIcon("IG")
                        SocialIcon("IN")
                    }
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    FooterBrand()
                    Row(horizontalArrangement = Arrangement.spacedBy(32.dp)) {
                        FooterLinks()
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        SocialIcon("IG")
                        SocialIcon("IN")
                    }
                }
            }

            HorizontalDivider(color = Color(0xFF1E1E28))

            if (isMobile) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    FooterBottomRow()
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    FooterBottomRow()
                }
            }
        }
    }
}

@Composable
private fun FooterBrand() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Humble",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = TextWhite,
            fontSize = 24.sp
        )
        Text(
            text = "Coders",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Light,
            color = TextWhite,
            fontSize = 24.sp
        )
    }
}

@Composable
private fun FooterLinks() {
    val routing = remember { createWebRouting() }
    val links = listOf("About","Workshops", "Contact Us")
    links.forEach { label ->
        TextButton(
            onClick = {
                when (label) {
                    "About" -> {
                        routing.navigateTo("about")
                    }
                    else -> {
                        // Handle other links if needed
                    }
                }
            }
        ) {
            Text(label, color = TextGray, fontSize = 15.sp)
        }
    }
}

@Composable
private fun FooterBottomRow() {
    val responsive = LocalResponsiveConfig.current
    if (responsive.isMobile) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "© 2025 Humble Coders. All rights reserved.",
                color = TextGray,
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )
            Text(
                text = "executives@humblecoders.in",
                color = TextGray,
                fontSize = 14.sp
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Developed by",
                    color = TextGray,
                    fontSize = 14.sp
                )
                Box(
                    modifier = Modifier
                        .height(24.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color.White)
                        .padding(horizontal = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Humble Coders",
                        color = Color.Black,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    } else {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "© 2025 Humble Coders. All rights reserved.",
                color = TextGray,
                fontSize = 14.sp
            )
            Text(
                text = "executives@humblecoders.in",
                color = TextGray,
                fontSize = 14.sp
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Developed by",
                    color = TextGray,
                    fontSize = 14.sp
                )
                Box(
                    modifier = Modifier
                        .height(24.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color.White)
                        .padding(horizontal = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Humble Coders",
                        color = Color.Black,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun SocialIcon(icon: String) {
    Box(
        modifier = Modifier
            .size(44.dp)
            .clip(CircleShape)
            .background(Color(0xFF1E1E28))
            .clickable { },
        contentAlignment = Alignment.Center
    ) {
        Text(icon, color = TextWhite, fontSize = 14.sp, fontWeight = FontWeight.Medium)
    }
}