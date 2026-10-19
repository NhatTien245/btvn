package com.example.uicomponentslist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}

// -------------------------------------------------------------
// 1. Quản lý Điều hướng (Navigation Routes)
// -------------------------------------------------------------
sealed class Screen(val route: String) {
    object Onboarding : Screen("onboarding")
    object ComponentList : Screen("component_list")
    object TextDetail : Screen("text_detail")
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Onboarding.route
    ) {
        composable(Screen.Onboarding.route) {
            OnboardingScreen(onNavigateToList = {
                navController.navigate(Screen.ComponentList.route)
            })
        }
        composable(Screen.ComponentList.route) {
            ComponentListScreen(onTextItemClick = {
                navController.navigate(Screen.TextDetail.route)
            })
        }
        composable(Screen.TextDetail.route) {
            TextDetailScreen(onBackClick = {
                navController.popBackStack()
            })
        }
    }
}

// -------------------------------------------------------------
// 2. Màn hình 1: Onboarding / Welcome
// -------------------------------------------------------------
@Composable
fun OnboardingScreen(onNavigateToList: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.ic_compose_logo),
                contentDescription = "Compose Logo",
                modifier = Modifier.size(130.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Jetpack Compose",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Jetpack Compose is a modern UI toolkit for building native Android applications using a declarative programming approach.",
                textAlign = TextAlign.Center,
                color = Color.Gray,
                fontSize = 14.sp,
                lineHeight = 20.sp
            )
        }

        Button(
            onClick = onNavigateToList,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E88E5))
        ) {
            Text(text = "I'm ready", color = Color.White, fontSize = 16.sp)
        }
    }
}

// -------------------------------------------------------------
// 3. Màn hình 2: UI Components List
// -------------------------------------------------------------
@Composable
fun ComponentListScreen(onTextItemClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F7FA))
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "UI Components List",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1E88E5),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Nhóm Display
        SectionHeader(title = "Display")
        ComponentCard(title = "Text", subtitle = "Displays text", onClick = onTextItemClick)
        ComponentCard(title = "Image", subtitle = "Displays an image")

        Spacer(modifier = Modifier.height(12.dp))

        // Nhóm Input
        SectionHeader(title = "Input")
        ComponentCard(title = "TextField", subtitle = "Input field for text")
        ComponentCard(title = "PasswordField", subtitle = "Input field for passwords")

        Spacer(modifier = Modifier.height(12.dp))

        // Nhóm Layout
        SectionHeader(title = "Layout")
        ComponentCard(title = "Column", subtitle = "Arranges elements vertically")
        ComponentCard(title = "Row", subtitle = "Arranges elements horizontally")

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        fontWeight = FontWeight.Bold,
        fontSize = 15.sp,
        modifier = Modifier.padding(vertical = 6.dp)
    )
}

@Composable
fun ComponentCard(title: String, subtitle: String, onClick: () -> Unit = {}) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFD6E8FF))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = subtitle, fontSize = 13.sp, color = Color.Gray)
        }
    }
}

// -------------------------------------------------------------
// 4. Màn hình 3: Text Detail
// -------------------------------------------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextDetailScreen(onBackClick: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Text Detail",
                        color = Color(0xFF1E88E5),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                // Dùng nút trống ở bên phải để tiêu đề nằm cân bằng chính giữa
                actions = { Spacer(modifier = Modifier.width(48.dp)) }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            val annotatedText = buildAnnotatedString {
                append("The ")
                withStyle(style = SpanStyle(textDecoration = TextDecoration.LineThrough)) {
                    append("quick")
                }
                append(" ")
                withStyle(
                    style = SpanStyle(
                        color = Color(0xFFB87333),
                        fontWeight = FontWeight.Bold,
                        fontSize = 26.sp
                    )
                ) {
                    append("Brown")
                }
                append("\nfox ")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("jumps")
                }
                append(" ")
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Italic,
                        fontSize = 22.sp
                    )
                ) {
                    append("over")
                }
                append("\nthe ")
                withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
                    append("lazy")
                }
                append(" dog.")
            }

            Text(
                text = annotatedText,
                fontSize = 20.sp,
                lineHeight = 36.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}