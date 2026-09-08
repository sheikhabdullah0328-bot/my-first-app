package com.example.data.repository

import com.example.data.local.VideoDao
import com.example.data.local.VideoProjectEntity
import com.example.data.model.VideoScene
import kotlinx.coroutines.flow.Flow
import org.json.JSONArray
import org.json.JSONObject

class VideoRepository(private val dao: VideoDao) {

    val allProjects: Flow<List<VideoProjectEntity>> = dao.getAllProjects()

    suspend fun getProjectById(id: Long): VideoProjectEntity? = dao.getProjectById(id)

    suspend fun saveProject(
        id: Long = 0,
        title: String,
        originalIdea: String,
        viralHook: String,
        fullScript: String,
        styleId: String,
        aspectRatio: String,
        scenes: List<VideoScene>,
        captionStyleId: String,
        musicTrackId: String,
        musicVolume: Float,
        narrationVoice: String
    ): Long {
        val scenesJsonArray = JSONArray()
        scenes.forEach { s ->
            val obj = JSONObject().apply {
                put("sceneNumber", s.sceneNumber)
                put("title", s.title)
                put("visualPrompt", s.visualPrompt)
                put("narrationText", s.narrationText)
                put("captionHighlight", s.captionHighlight)
                put("durationSec", s.durationSec)
                put("styleKeyword", s.styleKeyword)
                put("primaryColorHex", s.primaryColorHex)
                put("secondaryColorHex", s.secondaryColorHex)
            }
            scenesJsonArray.put(obj)
        }

        val entity = VideoProjectEntity(
            id = id,
            title = title,
            originalIdea = originalIdea,
            viralHook = viralHook,
            fullScript = fullScript,
            styleId = styleId,
            aspectRatio = aspectRatio,
            scenesJson = scenesJsonArray.toString(),
            captionStyleId = captionStyleId,
            musicTrackId = musicTrackId,
            musicVolume = musicVolume,
            narrationVoice = narrationVoice
        )

        return dao.insertProject(entity)
    }

    suspend fun deleteProject(id: Long) = dao.deleteProjectById(id)

    suspend fun toggleFavorite(id: Long) = dao.toggleFavorite(id)

    companion object {
        fun parseScenesFromJson(jsonStr: String): List<VideoScene> {
            val list = mutableListOf<VideoScene>()
            try {
                val array = JSONArray(jsonStr)
                for (i in 0 until array.length()) {
                    val obj = array.getJSONObject(i)
                    list.add(
                        VideoScene(
                            sceneNumber = obj.optInt("sceneNumber", i + 1),
                            title = obj.optString("title", "Scene ${i + 1}"),
                            visualPrompt = obj.optString("visualPrompt", ""),
                            narrationText = obj.optString("narrationText", ""),
                            captionHighlight = obj.optString("captionHighlight", ""),
                            durationSec = obj.optInt("durationSec", 4),
                            styleKeyword = obj.optString("styleKeyword", "cinematic"),
                            primaryColorHex = obj.optLong("primaryColorHex", 0xFF6C5CE7),
                            secondaryColorHex = obj.optLong("secondaryColorHex", 0xFF00CEC9)
                        )
                    )
                }
            } catch (e: Exception) {
                // Return empty if parsing error
            }
            return list
        }
    }
}
