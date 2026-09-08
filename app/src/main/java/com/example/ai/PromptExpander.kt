package com.example.ai

import com.example.data.model.VideoScene
import com.example.data.model.VideoStyle

data class AiGeneratedStory(
    val title: String,
    val hook: String,
    val viralScore: String,
    val fullScript: String,
    val scenes: List<VideoScene>,
    val suggestedHashtags: List<String>
)

object PromptExpander {

    fun generateViralStoryFromIdea(ideaText: String, style: VideoStyle): AiGeneratedStory {
        val cleanIdea = ideaText.trim().ifEmpty { "A magical adventure that surprises the world" }
        val lower = cleanIdea.lowercase()

        val isKidTheme = lower.contains("cat") || lower.contains("dog") || lower.contains("billi") ||
                lower.contains("dinosaur") || lower.contains("kid") || lower.contains("child") ||
                lower.contains("cute") || lower.contains("baby") || lower.contains("toy") || lower.contains("candy")

        val isSciFi = lower.contains("space") || lower.contains("robot") || lower.contains("cyber") ||
                lower.contains("future") || lower.contains("flying") || lower.contains("alien") ||
                lower.contains("moon") || lower.contains("mars") || lower.contains("car")

        val isMystery = lower.contains("ancient") || lower.contains("secret") || lower.contains("gold") ||
                lower.contains("treasure") || lower.contains("ocean") || lower.contains("ghost") || lower.contains("monster")

        val title = when {
            isKidTheme -> "The Great Little Adventure of $cleanIdea"
            isSciFi -> "Beyond Tomorrow: $cleanIdea"
            isMystery -> "The Forgotten Secret: $cleanIdea"
            else -> "The Legend of $cleanIdea"
        }

        val hook = when {
            lower.contains("?") -> cleanIdea
            isKidTheme -> "You will NEVER believe what happened when $cleanIdea!"
            isSciFi -> "In 2099, humanity thought it was impossible. Then came this..."
            isMystery -> "Scientists warned everyone not to dig here. They didn't listen..."
            else -> "Watch closely. This 3-second moment changed everything..."
        }

        val styleModifier = style.promptModifier

        val s1Prompt = "Opening establishing scene of $cleanIdea, dramatic cinematic camera angle, introducing main subject with atmospheric lighting, $styleModifier"
        val s1Narr = "$hook Let's see how it all started."
        val s1Cap = "WAIT FOR THE END..."

        val s2Prompt = "Building suspense and action: key conflict or unexpected turning point with $cleanIdea, dynamic motion blur, high detail, $styleModifier"
        val s2Narr = "At first, everything seemed normal... until an incredible twist took place!"
        val s2Cap = "THEN THIS HAPPENED!"

        val s3Prompt = "Climax scene: thrilling revelation, explosive visual detail and emotional payoff for $cleanIdea, epic lighting, $styleModifier"
        val s3Narr = "Look at the details right here! This has never been captured on video before."
        val s3Cap = "MIND BLOWING MOMENT!"

        val s4Prompt = "Final resolution: breathtaking panoramic outro scene featuring $cleanIdea with awe-inspiring glow, memorable ending frame, $styleModifier"
        val s4Narr = "Would you experience this in real life? Share your thoughts in the comments!"
        val s4Cap = "DROP YOUR THOUGHTS!"

        val scenes = listOf(
            VideoScene(
                sceneNumber = 1,
                title = "The Hook",
                visualPrompt = s1Prompt,
                narrationText = s1Narr,
                captionHighlight = s1Cap,
                durationSec = 4,
                styleKeyword = style.id,
                primaryColorHex = 0xFF6C5CE7,
                secondaryColorHex = 0xFFA29BFE
            ),
            VideoScene(
                sceneNumber = 2,
                title = "The Conflict",
                visualPrompt = s2Prompt,
                narrationText = s2Narr,
                captionHighlight = s2Cap,
                durationSec = 4,
                styleKeyword = style.id,
                primaryColorHex = 0xFF00CEC9,
                secondaryColorHex = 0xFF81ECEC
            ),
            VideoScene(
                sceneNumber = 3,
                title = "The Climax",
                visualPrompt = s3Prompt,
                narrationText = s3Narr,
                captionHighlight = s3Cap,
                durationSec = 4,
                styleKeyword = style.id,
                primaryColorHex = 0xFFFDCB6E,
                secondaryColorHex = 0xFFFF7675
            ),
            VideoScene(
                sceneNumber = 4,
                title = "The Finale & CTA",
                visualPrompt = s4Prompt,
                narrationText = s4Narr,
                captionHighlight = s4Cap,
                durationSec = 4,
                styleKeyword = style.id,
                primaryColorHex = 0xFFE84393,
                secondaryColorHex = 0xFFFD79A8
            )
        )

        val fullScript = """
            [Scene 1: Hook] $s1Narr
            [Scene 2: Twist] $s2Narr
            [Scene 3: Climax] $s3Narr
            [Scene 4: Outro] $s4Narr
        """.trimIndent()

        val hashtags = listOf(
            "#VAI",
            "#AIVideo",
            "#Trending",
            "#Shorts",
            "#Reels",
            "#ViralStory",
            "#TikTokTrend",
            "#NoCopyright"
        )

        return AiGeneratedStory(
            title = title,
            hook = hook,
            viralScore = "98.${(5..9).random()}% Viral Hook",
            fullScript = fullScript,
            scenes = scenes,
            suggestedHashtags = hashtags
        )
    }
}
