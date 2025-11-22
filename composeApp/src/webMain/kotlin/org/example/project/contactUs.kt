package org.example.project

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.humble
import kotlinproject.composeapp.generated.resources.instagram
import kotlinproject.composeapp.generated.resources.whatsapp
import kotlinproject.composeapp.generated.resources.linkedin
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

// Firebase Firestore configuration - Replace these with your actual Firebase project details
private const val FIREBASE_PROJECT_ID = "humblecoders-abeb4" // Replace with your Firebase project ID
private const val FIREBASE_API_KEY = "AIzaSyAmpz3j-QaEM4RhYAefD-_fxe6vxzm0Qz8" // Get this from Firebase Console → Project Settings → General → Web API Key
private const val FIRESTORE_COLLECTION = "contacts" // Collection name in Firestore
private const val FIRESTORE_DATABASE_URL = "https://firestore.googleapis.com/v1/projects/$FIREBASE_PROJECT_ID/databases/(default)/documents/$FIRESTORE_COLLECTION?key=$FIREBASE_API_KEY"
// Note: Make sure to configure Firestore security rules to allow writes to the contacts collection

// Helper function to escape JSON strings
private fun escapeJsonString(str: String): String {
    return str
        .replace("\\", "\\\\")
        .replace("\"", "\\\"")
        .replace("\n", "\\n")
        .replace("\r", "\\r")
        .replace("\t", "\\t")
}

// Expect function for platform-specific Firebase Firestore submission
expect suspend fun submitToFirebase(fields: Map<String, String>, databaseUrl: String)

data class ContactFormData(
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val category: String = "",
    val message: String = ""
)

@Composable
fun ContactUsPage() {
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
                            ContactUsHeader()
                            ContactFormSection()
                            Footer()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ContactUsHeader() {
    val responsive = LocalResponsiveConfig.current
    val isMobile = responsive.isMobile
    val routing = LocalWebRouting.current

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
                .size(400.dp)
                .offset(x = (-150).dp, y = 50.dp)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            BlueAccent.copy(alpha = 0.15f),
                            Color.Transparent
                        )
                    )
                )
        )

        SectionContainer(
            verticalPaddingMultiplier = if (isMobile) 0.8f else 1.0f,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Back button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                IconButton(
                    onClick = { routing.navigateTo("") },
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.1f))
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back to Home",
                            tint = TextWhite,
                            modifier = Modifier.size(20.dp)
                        )

                    }
                }
            }

            Text(
                text = "Ready to Build Real Apps? Let's Talk.",
                style = MaterialTheme.typography.displayLarge,
                fontWeight = FontWeight.Bold,
                color = TextWhite,
                fontSize = if (isMobile) 32.sp else 56.sp,
                lineHeight = if (isMobile) 38.sp else 64.sp,
                textAlign = TextAlign.Center
            )

            Text(
                text = "Whether you are ready to join our next cohort, want to bring Humble Coders to your university, or just have a question about Android—we are here to help. Fill out the form, and our team will get back to you within 24 hours.",
                color = TextLightGray,
                fontSize = responsive.bodyTextSize,
                textAlign = TextAlign.Center,
                lineHeight = 26.sp,
                modifier = Modifier.padding(
                    top = 20.dp,
                    start = if (isMobile) 0.dp else 100.dp,
                    end = if (isMobile) 0.dp else 100.dp
                )
            )
        }
    }
}

@Composable
fun ContactFormSection() {
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
            if (isMobile) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(40.dp)
                ) {
                    ContactForm()
                    ContactInfo()
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(56.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    ContactForm(Modifier.weight(1f))
                    ContactInfo(Modifier.weight(1f))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactForm(modifier: Modifier = Modifier) {
    val responsive = LocalResponsiveConfig.current
    var formData by remember { mutableStateOf(ContactFormData()) }
    var expanded by remember { mutableStateOf(false) }
    var isSubmitting by remember { mutableStateOf(false) }
    var showSuccessMessage by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()

    val categories = listOf(
        "Enquire about Joining a Cohort",
        "Request a College Workshop",
        "Curriculum & Prerequisites",
        "General Inquiry"
    )

    Surface(
        modifier = modifier.fillMaxWidth(),
        color = DarkSurfaceVariant,
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(if (responsive.isMobile) 28.dp else 40.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                text = "Send us a message",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = BlueAccent,
                fontSize = if (responsive.isMobile) 24.sp else 28.sp
            )

            if (showSuccessMessage) {
                Surface(
                    color = Color(0xFF10B981).copy(alpha = 0.2f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Filled.CheckCircle,
                            contentDescription = null,
                            tint = Color(0xFF10B981),
                            modifier = Modifier.size(24.dp)
                        )
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Thanks for reaching out!",
                                color = TextWhite,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier=Modifier.height(16.dp))
                            Text(
                                text = "We have received your query. A member of the Humble Coders team will review it and get back to you shortly.",
                                color = TextLightGray,
                                fontSize = 14.sp,
                                lineHeight = 20.sp
                            )
                        }
                    }
                }
            } else {
                if (errorMessage != null) {
                    Surface(
                        color = Color(0xFFEF4444).copy(alpha = 0.2f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Filled.Error,
                                contentDescription = null,
                                tint = Color(0xFFEF4444),
                                modifier = Modifier.size(24.dp)
                            )
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Error submitting form",
                                    color = TextWhite,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = errorMessage ?: "Please try again later.",
                                    color = TextLightGray,
                                    fontSize = 14.sp,
                                    lineHeight = 20.sp
                                )
                            }
                            IconButton(
                                onClick = { errorMessage = null },
                                modifier = Modifier.size(24.dp)
                            ) {
                                Icon(
                                    Icons.Filled.Close,
                                    contentDescription = "Dismiss",
                                    tint = TextWhite,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
                OutlinedTextField(
                    value = formData.name,
                    onValueChange = { formData = formData.copy(name = it) },
                    label = { Text("Name", color = TextGray) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = BlueAccent,
                        unfocusedBorderColor = Color.White.copy(alpha = 0.3f),
                        cursorColor = BlueAccent,
                        focusedTextColor = TextWhite,
                        unfocusedTextColor = TextWhite
                    ),
                    shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                    value = formData.email,
                    onValueChange = { formData = formData.copy(email = it) },
                    label = { Text("Email Address", color = TextGray) },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = BlueAccent,
                        unfocusedBorderColor = Color.White.copy(alpha = 0.3f),
                        cursorColor = BlueAccent,
                        focusedTextColor = TextWhite,
                        unfocusedTextColor = TextWhite
                    ),
                    shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                    value = formData.phone,
                    onValueChange = { formData = formData.copy(phone = it) },
                    label = { Text("Phone Number (Optional)", color = TextGray) },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = BlueAccent,
                        unfocusedBorderColor = Color.White.copy(alpha = 0.3f),
                        cursorColor = BlueAccent,
                        focusedTextColor = TextWhite,
                        unfocusedTextColor = TextWhite
                    ),
                    shape = RoundedCornerShape(12.dp)
                )

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    OutlinedTextField(
                        value = formData.category,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Select Your Query", color = TextGray) },
                        trailingIcon = {
                            Icon(
                                Icons.Filled.ArrowDropDown,
                                contentDescription = null,
                                tint = TextGray
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = BlueAccent,
                            unfocusedBorderColor = Color.White.copy(alpha = 0.3f),
                            cursorColor = BlueAccent,
                            focusedTextColor = TextWhite,
                            unfocusedTextColor = TextWhite
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.background(DarkSurface)
                    ) {
                        categories.forEach { category ->
                            DropdownMenuItem(
                                text = { Text(category, color = TextWhite) },
                                onClick = {
                                    formData = formData.copy(category = category)
                                    expanded = false
                                },
                                modifier = Modifier.background(DarkSurface)
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = formData.message,
                    onValueChange = { formData = formData.copy(message = it) },
                    label = { Text("Message", color = TextGray) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    placeholder = { Text("Tell us a bit about your background or what you are looking for...", color = TextGray.copy(alpha = 0.5f)) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = BlueAccent,
                        unfocusedBorderColor = Color.White.copy(alpha = 0.3f),
                        cursorColor = BlueAccent,
                        focusedTextColor = TextWhite,
                        unfocusedTextColor = TextWhite
                    ),
                    shape = RoundedCornerShape(12.dp),
                    maxLines = 6
                )

                Button(
                    onClick = {
                        if (formData.name.isNotBlank() && formData.email.isNotBlank() && formData.category.isNotBlank() && formData.message.isNotBlank()) {
                            isSubmitting = true
                            errorMessage = null
                            coroutineScope.launch {
                                try {
                                    submitContactForm(formData)
                                    showSuccessMessage = true
                                    formData = ContactFormData()
                                    errorMessage = null
                                } catch (e: Exception) {
                                    val errorMsg = e.message ?: "Failed to submit form. Please try again later."
                                    errorMessage = if (errorMsg.contains("401") || errorMsg.contains("403")) {
                                        "Authentication error. Please check Firebase configuration or contact support."
                                    } else if (errorMsg.contains("Network") || errorMsg.contains("fetch")) {
                                        "Network error. Please check your internet connection and try again."
                                    } else {
                                        errorMsg
                                    }
                                    showSuccessMessage = false
                                    println("Form submission error: $errorMsg")
                                    e.printStackTrace()
                                } finally {
                                    isSubmitting = false
                                }
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = BlueAccent
                    ),
                    shape = RoundedCornerShape(12.dp),
                    enabled = !isSubmitting && formData.name.isNotBlank() && formData.email.isNotBlank() && formData.category.isNotBlank() && formData.message.isNotBlank()
                ) {
                    if (isSubmitting) {
                        CircularProgressIndicator(
                            color = TextWhite,
                            modifier = Modifier.size(24.dp),
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text(
                            "Send Message",
                            color = TextWhite,
                            fontSize = responsive.bodyTextSize,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ContactInfo(modifier: Modifier = Modifier) {
    val responsive = LocalResponsiveConfig.current
    val uriHandler = LocalUriHandler.current

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        Surface(
            color = DarkSurfaceVariant,
            shape = RoundedCornerShape(24.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(if (responsive.isMobile) 28.dp else 40.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Text(
                    text = "Direct Reach",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = BlueAccent,
                    fontSize = if (responsive.isMobile) 22.sp else 26.sp
                )

                ContactInfoItem(
                    icon = Icons.Filled.Person,
                    label = "Ansh Bajaj",
                    value = "8685988991",
                    onClick = { uriHandler.openUri("tel:8685988991") }
                )

                ContactInfoItem(
                    icon = Icons.Filled.Person,
                    label = "Ishank Goyal",
                    value = "8708667212",
                    onClick = { uriHandler.openUri("tel:8708667212") }
                )

                ContactInfoItem(
                    icon = Icons.Filled.Email,
                    label = "Email",
                    value = "executives@humblecoders.in",
                    onClick = { uriHandler.openUri("mailto:executives@humblecoders.in") }
                )
            }
        }

        Surface(
            color = DarkSurfaceVariant,
            shape = RoundedCornerShape(24.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(if (responsive.isMobile) 28.dp else 40.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Text(
                    text = "Connect with Us",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = BlueAccent,
                    fontSize = if (responsive.isMobile) 22.sp else 26.sp
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    SocialButton(
                        iconResource = Res.drawable.whatsapp,
                        label = "WhatsApp",
                        onClick = { uriHandler.openUri("https://wa.me/918708667212") }
                    )
                    SocialButton(
                        iconResource = Res.drawable.linkedin,
                        label = "LinkedIn",
                        onClick = { uriHandler.openUri("https://www.linkedin.com/company/humble-coders") }
                    )
                    SocialButton(
                        iconResource = Res.drawable.instagram,
                        label = "Instagram",
                        onClick = { uriHandler.openUri("https://www.instagram.com/humble_coders?igsh=d2EyOG52dmpkcmVq") }
                    )
                }

                HorizontalDivider(color = Color(0xFF202A38))

                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Filled.Star,
                            contentDescription = null,
                            tint = Color(0xFFFFD700),
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "Ranked #1 for Android Development on Udemy",
                            color = TextLightGray,
                            fontSize = 14.sp
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Filled.CheckCircle,
                            contentDescription = null,
                            tint = BlueAccent,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "Trusted by students from Thapar University & Punjabi University",
                            color = TextLightGray,
                            fontSize = 14.sp,
                            lineHeight = 20.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ContactInfoItem(
    icon: ImageVector,
    label: String,
    value: String,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        color = Color.Transparent,
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(BlueAccent.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = BlueAccent,
                    modifier = Modifier.size(24.dp)
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = label,
                    color = TextGray,
                    fontSize = 13.sp
                )
                Text(
                    text = value,
                    color = TextWhite,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Icon(
                Icons.Filled.ArrowForward,
                contentDescription = null,
                tint = BlueAccent,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun SocialButton(
    iconResource: DrawableResource,
    label: String,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .size(80.dp)
            .clickable { onClick() },
        color = BlueAccent.copy(alpha = 0.2f),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(iconResource),
                contentDescription = null,
                modifier = Modifier.size(28.dp),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = label,
                color = TextWhite,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
        }
    }
}

suspend fun submitContactForm(formData: ContactFormData) {
    try {
        // Prepare fields for Firestore (Firestore expects string values)
        val fields = mapOf(
            "name" to formData.name,
            "email" to formData.email,
            "phone" to formData.phone,
            "category" to formData.category,
            "message" to formData.message
        )
        
        println("Submitting form to: $FIRESTORE_DATABASE_URL")
        println("Form data: $fields")
        
        // Call platform-specific Firestore submission (timestamp will be added in platform code)
        // NOTE: Firestore REST API requires authentication. For production, use:
        // 1. Firebase Cloud Functions (recommended)
        // 2. Firebase Authentication with proper security rules
        // 3. Or configure Firestore security rules to allow public writes (not recommended)
        submitToFirebase(fields, FIRESTORE_DATABASE_URL)
        println("Form submitted successfully to Firestore")
    } catch (e: Exception) {
        println("Error submitting form: ${e.message}")
        println("Error type: ${e::class.simpleName}")
        e.printStackTrace()
        // Re-throw to allow UI to handle the error
        throw e
    }
}