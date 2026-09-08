package com.example.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Subtitles
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ai.GeminiStoryService
import com.example.data.model.CaptionStyle
import com.example.data.model.MusicCatalog
import com.example.data.model.TrendingPrompt
import com.example.data.model.VideoScene
import com.example.data.model.VideoStyle
import com.example.ui.components.MusicSelectorBottomSheet
import com.example.ui.theme.BackgroundDark
import com.example.ui.theme.NeonAmber
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonGreen
import com.example.ui.theme.NeonPink
import com.example.ui.theme.NeonPurple
import com.example.ui.theme.SurfaceCard
import com.example.ui.theme.SurfaceDark
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CreateStudioScreen(
    initialIdea: String = "",
    initialTrending: TrendingPrompt? = null,
    onBackClick: () -> Unit,
    onVideoCreated: (
        title: String,
        idea: String,
        hook: String,
        script: String,
        styleId: String,
        aspectRatio: String,
        scenes: List<VideoScene>,
        captionStyle: CaptionStyle,
        musicTrackId: String,
        musicVolume: Float
    ) -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var userIdeaText by remember { mutableStateOf(initialIdea.ifEmpty { initialTrending?.ideaPrompt ?: "" }) }
    var selectedStyle by remember { mutableStateOf(initialTrending?.defaultStyle ?: VideoStyle.PIXAR_3D) }
    var selectedAspectRatio by remember { mutableStateOf("9:16") }
    var selectedCaptionStyle by remember { mutableStateOf(CaptionStyle.HORMOZI_NEON) }
    var selectedMusicTrackId by remember { mutableStateOf(initialTrending?.defaultMusicId ?: "track_phonk") }
    var musicVolume by remember { mutableFloatStateOf(0.8f) }

    var isGeneratingAiStory by remember { mutableStateOf(false) }
    var showMusicSheet by remember { mutableStateOf(false) }

    // Storyboard State
    var videoTitle by remember { mutableStateOf(initialTrending?.title ?: "My AI Viral Video") }
    var viralHook by remember { mutableStateOf(initialTrending?.hook ?: "Wait till the end...") }
    var viralScore by remember { mutableStateOf(initialTrending?.estimatedViralScore ?: "99% Viral Potential") }
    var scenes by remember {
        mutableStateOf<List<VideoScene>>(
            initialTrending?.scenes ?: emptyList()
        )
    }

    // Auto-generate if initial idea passed without scenes
    LaunchedEffect(Unit) {
        if (initialIdea.isNotBlank() && scenes.isEmpty()) {
            isGeneratingAiStory = true
            val generated = GeminiStoryService.generateStoryWithGemini(initialIdea, selectedStyle)
            videoTitle = generated.title
            viralHook = generated.hook
            viralScore = generated.viralScore
            scenes = generated.scenes
            isGeneratingAiStory = false
        }
    }

    fun generateStory() {
        if (userIdeaText.isBlank()) {
            Toast.makeText(context, "Please enter your idea first!", Toast.LENGTH_SHORT).show()
            return
        }
        scope.launch {
            isGeneratingAiStory = true
            val generated = GeminiStoryService.generateStoryWithGemini(userIdeaText, selectedStyle)
            videoTitle = generated.title
            viralHook = generated.hook
            viralScore = generated.viralScore
            scenes = generated.scenes
            isGeneratingAiStory = false
            Toast.makeText(context, "Viral Storyboard Created!", Toast.LENGTH_SHORT).show()
        }
    }

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
                        .testTag("create_back_button")
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
                        text = "V.Ai Studio",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "AI Idea to Viral Video",
                        color = NeonCyan,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Surface(
                shape = RoundedCornerShape(8.dp),
                color = Color(0xFF10B981).copy(alpha = 0.2f),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF10B981))
            ) {
                Text(
                    text = "Beginner Friendly ✓",
                    color = Color(0xFF10B981),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }

        // Main Scrollable Editor
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp)
        ) {
            // Idea Input Box
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceDark),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFF4C3C78))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Lightbulb,
                                contentDescription = "Idea",
                                tint = NeonAmber,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "What is your video idea?",
                                color = Color.White,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Text(
                            text = "English / Roman Urdu",
                            color = Color(0xFF94A3B8),
                            fontSize = 11.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = userIdeaText,
                        onValueChange = { userIdeaText = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("user_idea_input"),
                        placeholder = {
                            Text(
                                "e.g., A cute baby dinosaur learning to fly in cloud city, or ek superhero billi jo chori rokti hai...",
                                color = Color(0xFF64748B),
                                fontSize = 13.sp
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = NeonPurple,
                            unfocusedBorderColor = Color(0xFF33295E),
                            focusedContainerColor = Color(0xFF130E26),
                            unfocusedContainerColor = Color(0xFF130E26)
                        ),
                        shape = RoundedCornerShape(12.dp),
                        maxLines = 4
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Quick prompt suggestions for beginners / children
                    Text(
                        text = "QUICK IDEAS FOR CHILDREN & BEGINNERS:",
                        color = Color(0xFF94A3B8),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        listOf(
                            "🐱 Fluffy Cat Astronaut on Mars",
                            "🍕 Flying Pizza Delivery Robot",
                            "🦸 Cute Superhero Puppy",
                            "🌊 Glowing Underwater Kingdom"
                        ).forEach { quickIdea ->
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = SurfaceCard,
                                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF33295E)),
                                modifier = Modifier.clickable {
                                    userIdeaText = quickIdea.substring(2)
                                }
                            ) {
                                Text(
                                    text = quickIdea,
                                    color = Color.White,
                                    fontSize = 11.sp,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Style selector
                    Text(
                        text = "SELECT AI VISUAL STYLE:",
                        color = Color(0xFF94A3B8),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        VideoStyle.values().forEach { style ->
                            val isSelected = style == selectedStyle
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = if (isSelected) NeonPurple else SurfaceCard,
                                border = androidx.compose.foundation.BorderStroke(
                                    1.dp,
                                    if (isSelected) NeonCyan else Color(0xFF33295E)
                                ),
                                modifier = Modifier
                                    .clickable { selectedStyle = style }
                                    .testTag("style_${style.id}")
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 7.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = style.displayName,
                                        color = Color.White,
                                        fontSize = 12.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Surface(
                                        shape = RoundedCornerShape(4.dp),
                                        color = if (isSelected) Color.Black.copy(alpha = 0.3f) else Color(0xFF1F173B)
                                    ) {
                                        Text(
                                            text = style.badge,
                                            color = if (isSelected) NeonAmber else Color(0xFF94A3B8),
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Generate AI Storyboard Button
                    Button(
                        onClick = { generateStory() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("generate_storyboard_button"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = NeonPurple),
                        enabled = !isGeneratingAiStory
                    ) {
                        if (isGeneratingAiStory) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = Color.White,
                                strokeWidth = 2.dp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Writing Viral Script & Scenes...", color = Color.White)
                        } else {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = "Generate",
                                tint = NeonAmber,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (scenes.isEmpty()) "Generate AI Storyboard" else "Re-Generate Viral Script",
                                color = Color.White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Video Format & Captions Config Deck
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceDark),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF33295E))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "VIDEO FORMAT & SOUND SETTINGS",
                        color = Color(0xFF94A3B8),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Aspect Ratio Selector
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf(
                            "9:16" to "Reels/Shorts/TikTok",
                            "16:9" to "YouTube Wide",
                            "1:1" to "Square Post"
                        ).forEach { (ratio, label) ->
                            val isSelected = ratio == selectedAspectRatio
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = if (isSelected) Color(0xFF261C4C) else SurfaceCard,
                                border = androidx.compose.foundation.BorderStroke(
                                    1.5.dp,
                                    if (isSelected) NeonCyan else Color(0xFF33295E)
                                ),
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { selectedAspectRatio = ratio }
                            ) {
                                Column(
                                    modifier = Modifier.padding(vertical = 10.dp, horizontal = 6.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = ratio,
                                        color = if (isSelected) NeonCyan else Color.White,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = label,
                                        color = Color(0xFF94A3B8),
                                        fontSize = 9.sp
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Caption Style Selector
                    Text(
                        text = "ENGAGING CAPTION ANIMATION:",
                        color = Color(0xFF94A3B8),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        CaptionStyle.values().forEach { style ->
                            val isSelected = style == selectedCaptionStyle
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = if (isSelected) NeonPurple else SurfaceCard,
                                border = androidx.compose.foundation.BorderStroke(
                                    1.dp,
                                    if (isSelected) NeonAmber else Color(0xFF33295E)
                                ),
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { selectedCaptionStyle = style }
                            ) {
                                Column(
                                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = style.displayName,
                                        color = Color.White,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Sound Track Selector Bar
                    val activeTrack = MusicCatalog.tracks.find { it.id == selectedMusicTrackId }
                        ?: MusicCatalog.tracks.first()

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFF1A1333),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF4C3C78)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { showMusicSheet = true }
                            .testTag("select_music_bar")
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.MusicNote,
                                    contentDescription = "Music",
                                    tint = NeonCyan,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = activeTrack.title,
                                        color = Color.White,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "${activeTrack.category} • Copyright-Free ✓",
                                        color = Color(0xFF10B981),
                                        fontSize = 11.sp
                                    )
                                }
                            }

                            Text(
                                text = "Change Sound",
                                color = NeonCyan,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Storyboard & Script Scene Customization Section
            AnimatedVisibility(visible = scenes.isNotEmpty()) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Story & Scenes (Fully Customizable)",
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Tap any scene below to edit prompts or lines",
                                color = Color(0xFF94A3B8),
                                fontSize = 12.sp
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = NeonAmber.copy(alpha = 0.2f),
                            border = androidx.compose.foundation.BorderStroke(1.dp, NeonAmber)
                        ) {
                            Text(
                                text = viralScore,
                                color = NeonAmber,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Title & Hook Fields
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = SurfaceDark),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF33295E))
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(
                                text = "VIDEO TITLE",
                                color = Color(0xFF94A3B8),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            OutlinedTextField(
                                value = videoTitle,
                                onValueChange = { videoTitle = it },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White,
                                    focusedBorderColor = NeonPurple,
                                    unfocusedBorderColor = Color(0xFF33295E)
                                )
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = "3-SECOND VIRAL HOOK",
                                color = Color(0xFF94A3B8),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            OutlinedTextField(
                                value = viralHook,
                                onValueChange = { viralHook = it },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White,
                                    focusedBorderColor = NeonCyan,
                                    unfocusedBorderColor = Color(0xFF33295E)
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Scene list with individual edit fields
                    scenes.forEachIndexed { index, scene ->
                        SceneEditorCard(
                            scene = scene,
                            onUpdateScene = { updatedScene ->
                                val updatedList = scenes.toMutableList()
                                updatedList[index] = updatedScene
                                scenes = updatedList
                            }
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }

        // Bottom Action: Launch Video Player
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = SurfaceDark,
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF2E2452))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Button(
                    onClick = {
                        if (scenes.isEmpty()) {
                            // If user didn't generate storyboard yet, auto generate first
                            generateStory()
                        } else {
                            val script = scenes.joinToString("\n") { "[Scene ${it.sceneNumber}] ${it.narrationText}" }
                            onVideoCreated(
                                videoTitle,
                                userIdeaText,
                                viralHook,
                                script,
                                selectedStyle.id,
                                selectedAspectRatio,
                                scenes,
                                selectedCaptionStyle,
                                selectedMusicTrackId,
                                musicVolume
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("create_video_button"),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = NeonPurple)
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayCircle,
                        contentDescription = "Create",
                        tint = Color.White,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (scenes.isEmpty()) "Generate & Watch Video" else "Render & Play AI Video 🎬",
                        color = Color.White,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }

    // Music Selector Bottom Sheet
    if (showMusicSheet) {
        MusicSelectorBottomSheet(
            currentTrackId = selectedMusicTrackId,
            currentVolume = musicVolume,
            onDismiss = { showMusicSheet = false },
            onSelectTrack = { trackId, vol ->
                selectedMusicTrackId = trackId
                musicVolume = vol
            }
        )
    }
}

@Composable
private fun SceneEditorCard(
    scene: VideoScene,
    onUpdateScene: (VideoScene) -> Unit
) {
    var isEditing by remember { mutableStateOf(false) }
    var narration by remember(scene) { mutableStateOf(scene.narrationText) }
    var visualPrompt by remember(scene) { mutableStateOf(scene.visualPrompt) }
    var captionHighlight by remember(scene) { mutableStateOf(scene.captionHighlight) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceCard),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF3B2F63))
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = NeonPurple
                    ) {
                        Text(
                            text = "SCENE ${scene.sceneNumber}",
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Black,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = scene.title,
                        color = Color.White,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                IconButton(
                    onClick = {
                        if (isEditing) {
                            // Save changes
                            onUpdateScene(
                                scene.copy(
                                    narrationText = narration,
                                    visualPrompt = visualPrompt,
                                    captionHighlight = captionHighlight
                                )
                            )
                        }
                        isEditing = !isEditing
                    },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = if (isEditing) Icons.Default.Check else Icons.Default.Edit,
                        contentDescription = "Edit",
                        tint = if (isEditing) Color(0xFF10B981) else NeonCyan,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (isEditing) {
                Text(
                    text = "Narration / Voiceover Script:",
                    color = Color(0xFF94A3B8),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = narration,
                    onValueChange = { narration = it },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = NeonCyan,
                        unfocusedBorderColor = Color(0xFF33295E)
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Visual Prompt for AI:",
                    color = Color(0xFF94A3B8),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = visualPrompt,
                    onValueChange = { visualPrompt = it },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = NeonPurple,
                        unfocusedBorderColor = Color(0xFF33295E)
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Animated Caption Text:",
                    color = Color(0xFF94A3B8),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = captionHighlight,
                    onValueChange = { captionHighlight = it },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = NeonAmber,
                        unfocusedBorderColor = Color(0xFF33295E)
                    )
                )
            } else {
                Text(
                    text = "Voiceover: \"${scene.narrationText}\"",
                    color = Color.White,
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Prompt: ${scene.visualPrompt.take(90)}...",
                    color = Color(0xFF94A3B8),
                    fontSize = 11.sp,
                    lineHeight = 15.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = Color(0xFF161129)
                ) {
                    Text(
                        text = "CAPTION: ${scene.captionHighlight}",
                        color = NeonAmber,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }
    }
}
