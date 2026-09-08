package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.data.local.VideoProjectEntity
import com.example.data.model.CaptionStyle
import com.example.data.model.TrendingPrompt
import com.example.data.model.VideoScene
import com.example.data.repository.TrendingLibrary
import com.example.ui.screens.CreateStudioScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.TutorialScreen
import com.example.ui.screens.VideoPlayerScreen
import com.example.ui.theme.BackgroundDark
import com.example.ui.theme.VAiTheme
import com.example.ui.viewmodel.VideoViewModel

sealed class AppScreen {
    object Home : AppScreen()
    data class CreateStudio(
        val initialIdea: String = "",
        val initialTrending: TrendingPrompt? = null
    ) : AppScreen()
    data class Player(
        val projectId: Long = 0,
        val videoTitle: String,
        val viralHook: String,
        val fullScript: String,
        val styleId: String,
        val aspectRatio: String,
        val scenes: List<VideoScene>,
        val captionStyle: CaptionStyle,
        val musicTrackId: String,
        val musicVolume: Float,
        val isFavorite: Boolean = false
    ) : AppScreen()
    object Tutorial : AppScreen()
}

class MainActivity : ComponentActivity() {

    private val viewModel: VideoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            VAiTheme(darkTheme = true) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = BackgroundDark
                ) {
                    val savedProjects by viewModel.allProjects.collectAsState()
                    var currentScreen by remember { mutableStateOf<AppScreen>(AppScreen.Home) }

                    BackHandler(enabled = currentScreen !is AppScreen.Home) {
                        currentScreen = AppScreen.Home
                    }

                    AnimatedContent(
                        targetState = currentScreen,
                        transitionSpec = { fadeIn() togetherWith fadeOut() },
                        label = "screenTransition"
                    ) { screen ->
                        when (screen) {
                            is AppScreen.Home -> {
                                HomeScreen(
                                    savedProjects = savedProjects,
                                    onStartNewVideo = {
                                        currentScreen = AppScreen.CreateStudio()
                                    },
                                    onSelectTrending = { trendingPrompt ->
                                        currentScreen = AppScreen.CreateStudio(
                                            initialIdea = trendingPrompt.ideaPrompt,
                                            initialTrending = trendingPrompt
                                        )
                                    },
                                    onQuickIdeaSubmit = { idea ->
                                        currentScreen = AppScreen.CreateStudio(initialIdea = idea)
                                    },
                                    onOpenProject = { project ->
                                        val parsedScenes = viewModel.parseScenes(project.scenesJson)
                                        val capStyle = CaptionStyle.values().find { it.id == project.captionStyleId }
                                            ?: CaptionStyle.HORMOZI_NEON

                                        currentScreen = AppScreen.Player(
                                            projectId = project.id,
                                            videoTitle = project.title,
                                            viralHook = project.viralHook,
                                            fullScript = project.fullScript,
                                            styleId = project.styleId,
                                            aspectRatio = project.aspectRatio,
                                            scenes = parsedScenes,
                                            captionStyle = capStyle,
                                            musicTrackId = project.musicTrackId,
                                            musicVolume = project.musicVolume,
                                            isFavorite = project.isFavorite
                                        )
                                    },
                                    onDeleteProject = { id ->
                                        viewModel.deleteProject(id)
                                    },
                                    onOpenTutorial = {
                                        currentScreen = AppScreen.Tutorial
                                    }
                                )
                            }

                            is AppScreen.CreateStudio -> {
                                CreateStudioScreen(
                                    initialIdea = screen.initialIdea,
                                    initialTrending = screen.initialTrending,
                                    onBackClick = { currentScreen = AppScreen.Home },
                                    onVideoCreated = { title, idea, hook, script, styleId, ratio, scenes, captionStyle, musicId, musicVol ->
                                        // Save to Room database
                                        viewModel.saveProject(
                                            title = title,
                                            originalIdea = idea,
                                            viralHook = hook,
                                            fullScript = script,
                                            styleId = styleId,
                                            aspectRatio = ratio,
                                            scenes = scenes,
                                            captionStyle = captionStyle,
                                            musicTrackId = musicId,
                                            musicVolume = musicVol
                                        ) { savedId ->
                                            currentScreen = AppScreen.Player(
                                                projectId = savedId,
                                                videoTitle = title,
                                                viralHook = hook,
                                                fullScript = script,
                                                styleId = styleId,
                                                aspectRatio = ratio,
                                                scenes = scenes,
                                                captionStyle = captionStyle,
                                                musicTrackId = musicId,
                                                musicVolume = musicVol
                                            )
                                        }
                                    }
                                )
                            }

                            is AppScreen.Player -> {
                                VideoPlayerScreen(
                                    videoTitle = screen.videoTitle,
                                    viralHook = screen.viralHook,
                                    fullScript = screen.fullScript,
                                    styleId = screen.styleId,
                                    aspectRatio = screen.aspectRatio,
                                    initialScenes = screen.scenes,
                                    initialCaptionStyle = screen.captionStyle,
                                    initialMusicTrackId = screen.musicTrackId,
                                    initialMusicVolume = screen.musicVolume,
                                    isFavorite = screen.isFavorite,
                                    onBackClick = { currentScreen = AppScreen.Home },
                                    onToggleFavorite = {
                                        if (screen.projectId != 0L) {
                                            viewModel.toggleFavorite(screen.projectId)
                                        }
                                    },
                                    onRemixClick = {
                                        currentScreen = AppScreen.CreateStudio(
                                            initialIdea = screen.videoTitle
                                        )
                                    }
                                )
                            }

                            is AppScreen.Tutorial -> {
                                TutorialScreen(
                                    onBackClick = { currentScreen = AppScreen.Home },
                                    onStartCreatingClick = {
                                        currentScreen = AppScreen.CreateStudio()
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
