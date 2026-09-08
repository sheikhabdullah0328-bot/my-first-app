package com.example.ai

import android.util.Log
import com.example.BuildConfig
import com.example.data.model.VideoScene
import com.example.data.model.VideoStyle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.util.concurrent.TimeUnit

object GeminiStoryService {
    private const val TAG = "GeminiStoryService"
    private const val MODEL_NAME = "gemini-3.5-flash"

    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    suspend fun generateStoryWithGemini(
        userIdea: String,
        style: VideoStyle
    ): AiGeneratedStory = withContext(Dispatchers.IO) {
        val apiKey = try {
            BuildConfig.GEMINI_API_KEY
        } catch (e: Throwable) {
            ""
        }

        if (apiKey.isBlank() || apiKey == "MY_GEMINI_API_KEY") {
            Log.d(TAG, "No valid Gemini API key configured, using built-in AI story synthesizer")
            return@withContext PromptExpander.generateViralStoryFromIdea(userIdea, style)
        }

        try {
            val systemPrompt = """
                You are V.Ai, a professional AI viral short-form video director.
                Transform the user's idea into a viral 4-scene video script for TikTok, YouTube Shorts, and Instagram Reels.
                Respond strictly in valid JSON format with this exact structure:
                {
                   "title": "Catchy short title",
                   "hook": "Attention grabbing 3-second hook sentence",
                   "viralScore": "99% Viral Hook",
                   "scene1": { "title": "Hook Scene", "prompt": "visual prompt", "narration": "voiceover line", "caption": "SHORT PUNCHY CAPTION" },
                   "scene2": { "title": "Build Scene", "prompt": "visual prompt", "narration": "voiceover line", "caption": "SHORT PUNCHY CAPTION" },
                   "scene3": { "title": "Climax Scene", "prompt": "visual prompt", "narration": "voiceover line", "caption": "SHORT PUNCHY CAPTION" },
                   "scene4": { "title": "Outro Scene", "prompt": "visual prompt", "narration": "voiceover line", "caption": "SHORT PUNCHY CAPTION" },
                   "hashtags": ["#Shorts", "#AIVideo", "#Trending"]
                }
            """.trimIndent()

            val requestJson = JSONObject().apply {
                put("contents", org.json.JSONArray().apply {
                    put(JSONObject().apply {
                        put("parts", org.json.JSONArray().apply {
                            put(JSONObject().apply {
                                put("text", "$systemPrompt\n\nUser Idea: $userIdea\nVisual Style: ${style.displayName}")
                            })
                        })
                    })
                })
            }

            val mediaType = "application/json; charset=utf-8".toMediaType()
            val body = requestJson.toString().toRequestBody(mediaType)
            val url = "https://generativelanguage.googleapis.com/v1beta/models/$MODEL_NAME:generateContent?key=$apiKey"

            val request = Request.Builder()
                .url(url)
                .post(body)
                .build()

            val response = client.newCall(request).execute()
            val responseBodyString = response.body?.string()

            if (response.isSuccessful && !responseBodyString.isNullOrEmpty()) {
                val jsonResponse = JSONObject(responseBodyString)
                val candidates = jsonResponse.optJSONArray("candidates")
                val firstCandidate = candidates?.optJSONObject(0)
                val content = firstCandidate?.optJSONObject("content")
                val parts = content?.optJSONArray("parts")
                val rawText = parts?.optJSONObject(0)?.optString("text") ?: ""

                val cleanJsonString = rawText
                    .replace("```json", "")
                    .replace("```", "")
                    .trim()

                val parsed = JSONObject(cleanJsonString)
                val title = parsed.optString("title", "AI Viral Creation: $userIdea")
                val hook = parsed.optString("hook", "You need to see this...")
                val viralScore = parsed.optString("viralScore", "99.1% Viral Potential")

                val s1 = parsed.optJSONObject("scene1")
                val s2 = parsed.optJSONObject("scene2")
                val s3 = parsed.optJSONObject("scene3")
                val s4 = parsed.optJSONObject("scene4")

                val scenes = listOf(
                    VideoScene(
                        sceneNumber = 1,
                        title = s1?.optString("title") ?: "Scene 1",
                        visualPrompt = "${s1?.optString("prompt") ?: userIdea}, ${style.promptModifier}",
                        narrationText = s1?.optString("narration") ?: hook,
                        captionHighlight = s1?.optString("caption") ?: "WATCH TILL END",
                        durationSec = 4,
                        styleKeyword = style.id,
                        primaryColorHex = 0xFF6C5CE7,
                        secondaryColorHex = 0xFFA29BFE
                    ),
                    VideoScene(
                        sceneNumber = 2,
                        title = s2?.optString("title") ?: "Scene 2",
                        visualPrompt = "${s2?.optString("prompt") ?: userIdea}, ${style.promptModifier}",
                        narrationText = s2?.optString("narration") ?: "The mystery unfolded.",
                        captionHighlight = s2?.optString("caption") ?: "UNBELIEVABLE!",
                        durationSec = 4,
                        styleKeyword = style.id,
                        primaryColorHex = 0xFF00CEC9,
                        secondaryColorHex = 0xFF81ECEC
                    ),
                    VideoScene(
                        sceneNumber = 3,
                        title = s3?.optString("title") ?: "Scene 3",
                        visualPrompt = "${s3?.optString("prompt") ?: userIdea}, ${style.promptModifier}",
                        narrationText = s3?.optString("narration") ?: "Here comes the twist!",
                        captionHighlight = s3?.optString("caption") ?: "CLIMAX REVEALED!",
                        durationSec = 4,
                        styleKeyword = style.id,
                        primaryColorHex = 0xFFFDCB6E,
                        secondaryColorHex = 0xFFFF7675
                    ),
                    VideoScene(
                        sceneNumber = 4,
                        title = s4?.optString("title") ?: "Scene 4",
                        visualPrompt = "${s4?.optString("prompt") ?: userIdea}, ${style.promptModifier}",
                        narrationText = s4?.optString("narration") ?: "Follow for more AI stories!",
                        captionHighlight = s4?.optString("caption") ?: "FOLLOW FOR PART 2",
                        durationSec = 4,
                        styleKeyword = style.id,
                        primaryColorHex = 0xFFE84393,
                        secondaryColorHex = 0xFFFD79A8
                    )
                )

                val script = """
                    [Scene 1] ${scenes[0].narrationText}
                    [Scene 2] ${scenes[1].narrationText}
                    [Scene 3] ${scenes[2].narrationText}
                    [Scene 4] ${scenes[3].narrationText}
                """.trimIndent()

                return@withContext AiGeneratedStory(
                    title = title,
                    hook = hook,
                    viralScore = viralScore,
                    fullScript = script,
                    scenes = scenes,
                    suggestedHashtags = listOf("#VAI", "#Shorts", "#Reels", "#AIVideo", "#Viral")
                )
            } else {
                Log.w(TAG, "Gemini API error ${response.code}: $responseBodyString. Fallback to synthesizer.")
                return@withContext PromptExpander.generateViralStoryFromIdea(userIdea, style)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Gemini API exception", e)
            return@withContext PromptExpander.generateViralStoryFromIdea(userIdea, style)
        }
    }
}
