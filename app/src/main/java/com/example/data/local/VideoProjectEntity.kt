package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "video_projects")
data class VideoProjectEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val originalIdea: String,
    val viralHook: String,
    val fullScript: String,
    val styleId: String,
    val aspectRatio: String = "9:16",
    val scenesJson: String,
    val captionStyleId: String = "hormozi_neon",
    val musicTrackId: String = "track_phonk",
    val musicVolume: Float = 0.8f,
    val narrationVoice: String = "Energetic AI Viral Guy",
    val createdAt: Long = System.currentTimeMillis(),
    val isFavorite: Boolean = false
)
