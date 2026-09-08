package com.example.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CaptionStyle
import com.example.data.model.MusicCatalog
import com.example.data.model.VideoScene
import com.example.ui.theme.NeonAmber
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonPink
import com.example.ui.theme.NeonPurple
import kotlinx.coroutines.delay
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun VideoCanvasPlayer(
    scenes: List<VideoScene>,
    captionStyle: CaptionStyle,
    musicTrackId: String,
    aspectRatio: String, // "9:16", "16:9", "1:1"
    modifier: Modifier = Modifier,
    isPlaying: Boolean,
    onTogglePlay: (Boolean) -> Unit,
    onChangeMusicClick: () -> Unit
) {
    if (scenes.isEmpty()) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(300.dp)
                .background(Color(0xFF130F24), RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text("No scenes generated yet", color = Color.White)
        }
        return
    }

    val totalDurationSec = remember(scenes) { scenes.sumOf { it.durationSec }.coerceAtLeast(1) }
    var currentProgressSec by remember { mutableFloatStateOf(0f) }
    var currentSceneIndex by remember { mutableIntStateOf(0) }

    // Playback loop
    LaunchedEffect(isPlaying, scenes) {
        while (isPlaying) {
            delay(100)
            currentProgressSec += 0.1f
            if (currentProgressSec >= totalDurationSec) {
                currentProgressSec = 0f
            }

            // Calculate active scene
            var accumulated = 0f
            var foundIndex = 0
            for (i in scenes.indices) {
                val dur = scenes[i].durationSec.toFloat()
                if (currentProgressSec >= accumulated && currentProgressSec < accumulated + dur) {
                    foundIndex = i
                    break
                }
                accumulated += dur
            }
            currentSceneIndex = foundIndex
        }
    }

    val activeScene = scenes.getOrNull(currentSceneIndex) ?: scenes.first()

    // Smooth Ken Burns animation
    val infiniteTransition = rememberInfiniteTransition(label = "kenBurns")
    val zoomScale by infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 4000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "zoom"
    )
    val panOffset by infiniteTransition.animateFloat(
        initialValue = -15f,
        targetValue = 15f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 6000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pan"
    )

    val aspectMultiplier = when (aspectRatio) {
        "16:9" -> 16f / 9f
        "1:1" -> 1f
        else -> 9f / 16f
    }

    val activeMusic = remember(musicTrackId) {
        MusicCatalog.tracks.find { it.id == musicTrackId } ?: MusicCatalog.tracks.first()
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Video Viewport Box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .aspectRatio(aspectMultiplier)
                .clip(RoundedCornerShape(20.dp))
                .border(1.5.dp, Color(0xFF3B2F63), RoundedCornerShape(20.dp))
                .background(Color.Black)
                .testTag("video_player_container")
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    onTogglePlay(!isPlaying)
                },
            contentAlignment = Alignment.Center
        ) {
            // Animated Canvas Rendering scene dynamics
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        scaleX = zoomScale
                        scaleY = zoomScale
                        translationX = panOffset
                        translationY = panOffset * 0.5f
                    }
            ) {
                val pColor = Color(activeScene.primaryColorHex)
                val sColor = Color(activeScene.secondaryColorHex)

                // Background atmospheric gradient
                drawRect(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            sColor.copy(alpha = 0.5f),
                            pColor.copy(alpha = 0.65f),
                            Color(0xFF07040D)
                        ),
                        center = Offset(size.width * 0.5f, size.height * 0.4f),
                        radius = size.maxDimension * 0.8f
                    )
                )

                // Procedural cinematic visual shapes & light rays
                val sceneNum = activeScene.sceneNumber
                val centerX = size.width / 2f
                val centerY = size.height / 2f

                // Draw cinematic glow orbs
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(Color.White.copy(alpha = 0.4f), Color.Transparent),
                        center = Offset(centerX, centerY * 0.8f),
                        radius = size.width * 0.4f
                    ),
                    radius = size.width * 0.4f,
                    center = Offset(centerX, centerY * 0.8f)
                )

                // Dynamic visual waves / beams
                for (i in 1..4) {
                    val angle = (i * 45f + panOffset * 2f) * (Math.PI / 180f).toFloat()
                    val rayEndX = centerX + cos(angle) * size.width * 0.6f
                    val rayEndY = centerY + sin(angle) * size.height * 0.6f

                    drawLine(
                        brush = Brush.linearGradient(
                            colors = listOf(sColor.copy(alpha = 0.35f), Color.Transparent),
                            start = Offset(centerX, centerY),
                            end = Offset(rayEndX, rayEndY)
                        ),
                        start = Offset(centerX, centerY),
                        end = Offset(rayEndX, rayEndY),
                        strokeWidth = 3.dp.toPx()
                    )
                }

                // Grid floor horizon effect for tech/cinematic look
                val horizonY = size.height * 0.65f
                for (line in 0..6) {
                    val y = horizonY + (line * line * 6f)
                    if (y < size.height) {
                        drawLine(
                            color = sColor.copy(alpha = 0.25f - line * 0.03f),
                            start = Offset(0f, y),
                            end = Offset(size.width, y),
                            strokeWidth = 1.dp.toPx()
                        )
                    }
                }
            }

            // Top Status Bar: V.Ai Logo & Scene indicator
            Row(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // V.Ai Badge
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color.Black.copy(alpha = 0.65f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF5B4D8A))
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(if (isPlaying) Color(0xFF00E676) else NeonAmber, CircleShape)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "V.Ai • SCENE ${currentSceneIndex + 1}/${scenes.size}",
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }

                // Copyright-Safe Certified Badge
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFF10B981).copy(alpha = 0.85f)
                ) {
                    Text(
                        text = "100% Copyright-Free",
                        color = Color.White,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            // Central Scene Title & Visual Prompt Hint
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color.Black.copy(alpha = 0.55f),
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    Text(
                        text = activeScene.title.uppercase(),
                        color = NeonCyan,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                        letterSpacing = 1.sp
                    )
                }

                Text(
                    text = activeScene.narrationText,
                    color = Color.White.copy(alpha = 0.9f),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp,
                    modifier = Modifier
                        .background(Color.Black.copy(alpha = 0.45f), RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }

            // Big Play/Pause icon overlay when paused
            if (!isPlaying) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .background(Color.Black.copy(alpha = 0.65f), CircleShape)
                        .border(2.dp, NeonPurple, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Play",
                        tint = Color.White,
                        modifier = Modifier.size(36.dp)
                    )
                }
            }

            // Bottom Viral Animated Caption Overlay (Hormozi / Kids / Typewriter / Minimal)
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(bottom = 24.dp, start = 16.dp, end = 16.dp)
            ) {
                when (captionStyle) {
                    CaptionStyle.HORMOZI_NEON -> {
                        // Electric Yellow & Cyan Hormozi style
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color.Black.copy(alpha = 0.85f),
                            border = androidx.compose.foundation.BorderStroke(2.dp, Color(0xFFFFEB3B)),
                            modifier = Modifier
                                .align(Alignment.Center)
                                .shadow(12.dp, shape = RoundedCornerShape(8.dp), ambientColor = Color(0xFFFFEB3B))
                        ) {
                            Text(
                                text = activeScene.captionHighlight.uppercase(),
                                color = Color(0xFFFFEB3B),
                                fontSize = 19.sp,
                                fontWeight = FontWeight.Black,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                                letterSpacing = 1.2.sp
                            )
                        }
                    }
                    CaptionStyle.KIDS_BOUNCE -> {
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = NeonPink,
                            border = androidx.compose.foundation.BorderStroke(3.dp, Color.White),
                            modifier = Modifier.align(Alignment.Center)
                        ) {
                            Text(
                                text = "★ ${activeScene.captionHighlight} ★",
                                color = Color.White,
                                fontSize = 17.sp,
                                fontWeight = FontWeight.ExtraBold,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                            )
                        }
                    }
                    CaptionStyle.TYPEWRITER -> {
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = Color(0xFF1E1B4B).copy(alpha = 0.9f),
                            border = androidx.compose.foundation.BorderStroke(1.dp, NeonCyan),
                            modifier = Modifier.align(Alignment.Center)
                        ) {
                            Text(
                                text = "> ${activeScene.captionHighlight}_",
                                color = NeonCyan,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace,
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                            )
                        }
                    }
                    CaptionStyle.MINIMAL_CLEAN -> {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = Color.Black.copy(alpha = 0.75f),
                            modifier = Modifier.align(Alignment.Center)
                        ) {
                            Text(
                                text = activeScene.captionHighlight,
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Progress bar with scrubber info
        LinearProgressIndicator(
            progress = { (currentProgressSec / totalDurationSec).coerceIn(0f, 1f) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp)),
            color = NeonPurple,
            trackColor = Color(0xFF241C42)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Playback Controls & Audio Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Timecode
            val currentSecInt = currentProgressSec.toInt()
            val minutes = currentSecInt / 60
            val seconds = currentSecInt % 60
            val totalSecInt = totalDurationSec
            val totalMin = totalSecInt / 60
            val totalSec = totalSecInt % 60

            Text(
                text = String.format("%02d:%02d / %02d:%02d", minutes, seconds, totalMin, totalSec),
                color = Color(0xFF94A3B8),
                fontSize = 12.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Medium
            )

            // Play / Pause / Replay Buttons
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = {
                        currentProgressSec = 0f
                        currentSceneIndex = 0
                    },
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Replay,
                        contentDescription = "Restart",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Spacer(modifier = Modifier.width(4.dp))

                IconButton(
                    onClick = { onTogglePlay(!isPlaying) },
                    modifier = Modifier
                        .size(42.dp)
                        .background(NeonPurple, CircleShape)
                        .testTag("play_pause_button")
                ) {
                    Icon(
                        imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                        contentDescription = if (isPlaying) "Pause" else "Play",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            // Active Trending Music Pill (Tap to Change Sound)
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color(0xFF1E1738),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF4C3C78)),
                modifier = Modifier
                    .clickable { onChangeMusicClick() }
                    .testTag("change_music_pill")
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.MusicNote,
                        contentDescription = "Music",
                        tint = NeonCyan,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = activeMusic.title.take(18) + if (activeMusic.title.length > 18) "..." else "",
                        color = Color.White,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}
