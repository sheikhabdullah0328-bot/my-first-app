package com.example.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.BackgroundDark
import com.example.ui.theme.NeonAmber
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonGreen
import com.example.ui.theme.NeonPink
import com.example.ui.theme.NeonPurple
import com.example.ui.theme.SurfaceCard
import com.example.ui.theme.SurfaceDark

data class TutorialStep(
    val stepNumber: Int,
    val title: String,
    val subtitle: String,
    val description: String,
    val kidTip: String,
    val mockUiTitle: String,
    val mockUiSnippet: String,
    val highlightTag: String,
    val primaryColor: Color
)

@Composable
fun TutorialScreen(
    onBackClick: () -> Unit,
    onStartCreatingClick: () -> Unit
) {
    val steps = remember {
        listOf(
            TutorialStep(
                stepNumber = 1,
                title = "Idea Batayein (Enter Your Idea)",
                subtitle = "Even children can start with 1 simple sentence",
                description = "Simply type or speak your basic idea in English, Urdu, or Hindi. For example: 'A fluffy cat astronaut on Mars' or 'Ek superhero billi jo chori rokti hai'. Or pick from viral trending templates!",
                kidTip = "Children Tip: Any funny animal or superhero idea makes a great viral video!",
                mockUiTitle = "AI Idea Prompt Box",
                mockUiSnippet = "\"A flying robot delivering pizza in cyber Tokyo...\"\n[ ✨ Generate Viral Story ]",
                highlightTag = "Step 1: Input Idea",
                primaryColor = NeonCyan
            ),
            TutorialStep(
                stepNumber = 2,
                title = "AI Writes Story & Viral Script",
                subtitle = "Automatic 4-scene breakdown with 3-second hook",
                description = "V.Ai's smart AI instantly analyzes trending formats and creates a high-engagement 4-scene video storyboard: The 3-Second Viral Hook, The Build-Up Twist, The Climax, and The Finale Outro.",
                kidTip = "Viral Tip: The first 3 seconds are the most important to stop people from scrolling!",
                mockUiTitle = "Generated Viral Storyboard",
                mockUiSnippet = "Scene 1: Hook - \"You won't believe what happened!\"\nScene 2: Conflict - \"Everything changed in seconds...\"\nScene 3: Climax - \"Mind blowing moment revealed!\"",
                highlightTag = "Step 2: AI Script Engine",
                primaryColor = NeonPurple
            ),
            TutorialStep(
                stepNumber = 3,
                title = "Custom Script & Prompt Editing",
                subtitle = "Full freedom to change anything you want",
                description = "Want to change the words or visual scene? Tap the edit button on any scene to customize narration, change prompts, or edit captions according to your wish!",
                kidTip = "Creator Tip: You can add your friend's name or your own custom dialogue easily.",
                mockUiTitle = "Scene Editor (Full Customization)",
                mockUiSnippet = "Narration: \"One small step for cat, giant leap for kitten-kind!\"\nVisual Prompt: \"Chubby orange cat smiling in galaxy suit\"",
                highlightTag = "Step 3: Easy Editing",
                primaryColor = NeonPink
            ),
            TutorialStep(
                stepNumber = 4,
                title = "Choose Styles, Music & Captions",
                subtitle = "Pixar 3D, Anime, Cyberpunk, and Trending Beats",
                description = "Select your preferred visual style (Pixar 3D animation, Anime, Cinematic 4K, Cyberpunk). Pick your background music from trending beats, and choose animated Hormozi neon captions!",
                kidTip = "Music Tip: Choose 'Whimsical Journey' for cute cartoon videos or 'Viral Phonk' for action!",
                mockUiTitle = "Style & Audio Deck",
                mockUiSnippet = "Style: Pixar 3D Animation (Kid Friendly)\nMusic: Midnight Drift (Viral Phonk, 135 BPM)\nCaptions: Hormozi Neon Yellow / Cyan",
                highlightTag = "Step 4: Style & Audio",
                primaryColor = NeonAmber
            ),
            TutorialStep(
                stepNumber = 5,
                title = "Direct 1-Tap Post & Save",
                subtitle = "Share to TikTok, Reels, Shorts & WhatsApp",
                description = "Preview your video in full motion with animated captions. Tap 'Direct Post' to launch TikTok, Instagram Reels, or YouTube Shorts with viral captions and hashtags automatically copied to your clipboard!",
                kidTip = "Safety Guarantee: All generated music and visuals are 100% copyright-free. No copyright strikes!",
                mockUiTitle = "Direct Social Publisher",
                mockUiSnippet = "[ TikTok ] [ Instagram Reels ] [ YouTube Shorts ]\n✓ Captions & #Hashtags Auto-Copied to Clipboard",
                highlightTag = "Step 5: Post & Go Viral",
                primaryColor = NeonGreen
            )
        )
    }

    var currentStepIndex by remember { mutableIntStateOf(0) }
    val currentStep = steps[currentStepIndex]
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        // Top App Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier
                        .size(40.dp)
                        .background(SurfaceCard, CircleShape)
                        .testTag("tutorial_back_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "How to Use V.Ai",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Beginner & Children Step-by-Step Guide",
                        color = NeonCyan,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Surface(
                shape = RoundedCornerShape(8.dp),
                color = SurfaceCard,
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF4C3C78))
            ) {
                Text(
                    text = "${currentStepIndex + 1} / ${steps.size}",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                )
            }
        }

        // Content Scrollable
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp)
        ) {
            // Hero Illustration banner
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp)),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceCard)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.tutorial_guide_hero),
                        contentDescription = "Tutorial visual workflow graphic",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                    // Gradient overlay
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(Color.Transparent, Color(0xCC090714))
                                )
                            )
                    )

                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = NeonPurple
                        ) {
                            Text(
                                text = "EASY GUIDE",
                                color = Color.White,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Black,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Learn to create trending videos in 5 minutes",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Step Progress Tabs
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                steps.forEachIndexed { index, step ->
                    val isActive = index == currentStepIndex
                    val isPast = index < currentStepIndex
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp))
                            .background(
                                when {
                                    isActive -> step.primaryColor
                                    isPast -> NeonPurple
                                    else -> Color(0xFF2C2250)
                                }
                            )
                            .clickable { currentStepIndex = index }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Animated Step Card
            AnimatedContent(
                targetState = currentStep,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label = "stepContent"
            ) { step ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.5.dp, step.primaryColor.copy(alpha = 0.6f), RoundedCornerShape(20.dp)),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceDark)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        // Step Tag
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = step.primaryColor.copy(alpha = 0.2f),
                                border = androidx.compose.foundation.BorderStroke(1.dp, step.primaryColor)
                            ) {
                                Text(
                                    text = "STEP ${step.stepNumber} OF 5",
                                    color = step.primaryColor,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Black,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }

                            Text(
                                text = step.highlightTag,
                                color = Color(0xFF94A3B8),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = step.title,
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = step.subtitle,
                            color = NeonCyan,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(top = 2.dp)
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = step.description,
                            color = Color(0xFFCBD5E1),
                            fontSize = 14.sp,
                            lineHeight = 20.sp
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Visual Mockup / Screenshot Card
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = Color(0xFF0C091A),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF3B2F63)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Box(
                                            modifier = Modifier
                                                .size(8.dp)
                                                .background(step.primaryColor, CircleShape)
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = step.mockUiTitle,
                                            color = Color.White,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                    Surface(
                                        shape = RoundedCornerShape(4.dp),
                                        color = Color(0xFF1E1738)
                                    ) {
                                        Text(
                                            text = "SCREENSHOT PREVIEW",
                                            color = NeonCyan,
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = step.mockUiSnippet,
                                    color = Color(0xFF94A3B8),
                                    fontSize = 12.sp,
                                    fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                                    lineHeight = 18.sp,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(Color(0xFF161129), RoundedCornerShape(8.dp))
                                        .padding(10.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Kid / Beginner friendly tip box
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = NeonAmber.copy(alpha = 0.15f),
                            border = androidx.compose.foundation.BorderStroke(1.dp, NeonAmber.copy(alpha = 0.5f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Lightbulb,
                                    contentDescription = "Tip",
                                    tint = NeonAmber,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = step.kidTip,
                                    color = Color(0xFFFEF3C7),
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Copyright Free Guarantee Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceCard),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF10B981).copy(alpha = 0.4f))
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Security,
                        contentDescription = "Safe",
                        tint = Color(0xFF10B981),
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "100% No Copyright Strike Guarantee",
                            color = Color.White,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "All music tracks and generated visuals are copyright-free for YouTube Shorts, Reels, and TikTok monetization.",
                            color = Color(0xFF94A3B8),
                            fontSize = 11.sp,
                            lineHeight = 15.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }

        // Bottom Navigation Actions
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = SurfaceDark,
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF2E2452))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (currentStepIndex > 0) {
                    OutlinedButton(
                        onClick = { currentStepIndex-- },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Previous",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Previous")
                    }
                } else {
                    Spacer(modifier = Modifier.width(10.dp))
                }

                if (currentStepIndex < steps.size - 1) {
                    Button(
                        onClick = { currentStepIndex++ },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = NeonPurple),
                        modifier = Modifier.testTag("next_step_button")
                    ) {
                        Text("Next Step", fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Next",
                            modifier = Modifier.size(16.dp)
                        )
                    }
                } else {
                    Button(
                        onClick = onStartCreatingClick,
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = NeonGreen),
                        modifier = Modifier.testTag("start_creating_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.RocketLaunch,
                            contentDescription = "Start",
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Start Creating AI Videos!", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
