package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.local.VideoProjectEntity
import com.example.data.model.CaptionStyle
import com.example.data.model.VideoScene
import com.example.data.repository.VideoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class VideoViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: VideoRepository

    val allProjects: StateFlow<List<VideoProjectEntity>>

    init {
        val db = AppDatabase.getDatabase(application)
        repository = VideoRepository(db.videoDao())
        allProjects = repository.allProjects.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    }

    fun saveProject(
        id: Long = 0,
        title: String,
        originalIdea: String,
        viralHook: String,
        fullScript: String,
        styleId: String,
        aspectRatio: String,
        scenes: List<VideoScene>,
        captionStyle: CaptionStyle,
        musicTrackId: String,
        musicVolume: Float,
        narrationVoice: String = "Energetic AI Viral Guy",
        onSaved: (Long) -> Unit
    ) {
        viewModelScope.launch {
            val savedId = repository.saveProject(
                id = id,
                title = title,
                originalIdea = originalIdea,
                viralHook = viralHook,
                fullScript = fullScript,
                styleId = styleId,
                aspectRatio = aspectRatio,
                scenes = scenes,
                captionStyleId = captionStyle.id,
                musicTrackId = musicTrackId,
                musicVolume = musicVolume,
                narrationVoice = narrationVoice
            )
            onSaved(savedId)
        }
    }

    fun deleteProject(id: Long) {
        viewModelScope.launch {
            repository.deleteProject(id)
        }
    }

    fun toggleFavorite(id: Long) {
        viewModelScope.launch {
            repository.toggleFavorite(id)
        }
    }

    fun parseScenes(json: String): List<VideoScene> {
        return VideoRepository.parseScenesFromJson(json)
    }
}
