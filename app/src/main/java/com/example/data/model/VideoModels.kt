package com.example.data.model

data class VideoScene(
    val sceneNumber: Int,
    val title: String,
    val visualPrompt: String,
    val narrationText: String,
    val captionHighlight: String,
    val durationSec: Int = 4,
    val styleKeyword: String = "cinematic",
    val primaryColorHex: Long = 0xFF6C5CE7,
    val secondaryColorHex: Long = 0xFF00CEC9
)

enum class VideoStyle(
    val id: String,
    val displayName: String,
    val description: String,
    val badge: String,
    val promptModifier: String
) {
    PIXAR_3D(
        "pixar_3d",
        "3D Animation",
        "Pixar & Disney cute stylized 3D characters, vibrant lighting",
        "Kid Friendly",
        "high quality 3D Disney Pixar animation style, soft studio lighting, vibrant colors, expressive adorable character design, octane render, 8k"
    ),
    CINEMATIC_4K(
        "cinematic_4k",
        "Cinematic 4K",
        "Hollywood blockbuster, depth of field, anamorphic lens",
        "Trending",
        "hyperrealistic 4k movie still, ARRI Alexa LF, masterclass cinematography, dramatic lighting, volumetric fog, color graded"
    ),
    ANIME_STUDIO(
        "anime_studio",
        "Anime Magic",
        "Makoto Shinkai & Ghibli aesthetic, glowing skies",
        "Viral",
        "breathtaking Japanese anime aesthetic, vibrant fantasy skies, detailed hand-drawn illustration style, Makoto Shinkai lighting, ultra detailed"
    ),
    CYBERPUNK_NEON(
        "cyberpunk_neon",
        "Cyberpunk Neon",
        "Futuristic neon glow, rain reflections, hologram city",
        "Tech",
        "cyberpunk 2099 aesthetic, glowing neon signage, wet streets with vibrant reflections, futuristic holographic HUDs, high tech atmosphere"
    ),
    HYPERREALISTIC(
        "hyperrealistic",
        "Hyper Realistic",
        "True-to-life documentary quality, natural sunlight",
        "Pro",
        "ultra-realistic 8k photography, National Geographic documentary quality, photorealistic textures, natural sunlight, depth of field"
    ),
    CLAYMATION(
        "claymation",
        "Claymation",
        "Handcrafted clay stop-motion, tactile textures",
        "Fun & Quirky",
        "charming stop-motion claymation style, Aardman animation look, fingerprint clay textures, miniature set lighting, whimsical and warm"
    ),
    RETRO_VHS(
        "retro_vhs",
        "80s Retro VHS",
        "Synthwave sunset, analog tape artifacts, glitch vibe",
        "Nostalgic",
        "1980s retro synthwave aesthetic, CRT television glitch scanlines, chromatic aberration, vibrant magenta and cyan glow, analog VHS tape warmth"
    ),
    COMIC_BOOK(
        "comic_book",
        "Comic Pop Art",
        "Bold ink lines, halftone dots, heroic action pose",
        "Action",
        "dynamic modern comic book illustration, bold ink outlines, Marvel style cel-shading, vibrant pop colors, halftone screen tones"
    )
}

enum class CaptionStyle(
    val id: String,
    val displayName: String,
    val description: String
) {
    HORMOZI_NEON("hormozi_neon", "Viral Neon Bold", "Word-by-word electric neon yellow & cyan bounce"),
    KIDS_BOUNCE("kids_bounce", "Playful Bubble", "Friendly round bouncing colorful font with shadows"),
    TYPEWRITER("typewriter", "Cinematic Typewriter", "Minimal modern typewriter with glowing cursor"),
    MINIMAL_CLEAN("minimal_clean", "Modern Clean", "Sleek subtitle bar with high contrast typography")
}

data class MusicTrack(
    val id: String,
    val title: String,
    val category: String,
    val tempoBpm: Int,
    val isCopyrightFree: Boolean = true,
    val vibeDescription: String,
    val waveHeights: List<Float>
)

object MusicCatalog {
    val tracks = listOf(
        MusicTrack(
            "track_phonk",
            "Midnight Drift (Viral Phonk)",
            "Trending Beats",
            135,
            true,
            "Aggressive high-energy bass, perfect for trending shorts and action",
            listOf(0.4f, 0.8f, 0.95f, 0.6f, 0.9f, 1.0f, 0.7f, 0.85f, 0.5f, 0.9f, 0.6f, 0.75f)
        ),
        MusicTrack(
            "track_lofi",
            "Sunset Cocoa (Lo-Fi Chill)",
            "Relaxing / Aesthetic",
            82,
            true,
            "Warm vintage piano with rain vinyl crackle for cozy stories",
            listOf(0.3f, 0.5f, 0.4f, 0.6f, 0.55f, 0.45f, 0.65f, 0.5f, 0.4f, 0.6f, 0.45f, 0.35f)
        ),
        MusicTrack(
            "track_pixar",
            "Whimsical Journey (Kids Animation)",
            "Kids & Family",
            110,
            true,
            "Playful marimba, bells and pizzicato strings for cute characters",
            listOf(0.5f, 0.7f, 0.85f, 0.6f, 0.9f, 0.75f, 0.8f, 0.65f, 0.95f, 0.7f, 0.6f, 0.8f)
        ),
        MusicTrack(
            "track_epic",
            "Interstellar Horizon (Cinematic)",
            "Epic / Sci-Fi",
            96,
            true,
            "Grand Hans Zimmer style orchestral brass and deep synth pulse",
            listOf(0.2f, 0.4f, 0.6f, 0.75f, 0.85f, 0.95f, 1.0f, 0.9f, 0.85f, 0.7f, 0.5f, 0.3f)
        ),
        MusicTrack(
            "track_cyber",
            "Neon Highway (Cyber Synthwave)",
            "Futuristic / Tech",
            124,
            true,
            "Driving 80s analog arpeggiators and pulsing electronic drums",
            listOf(0.6f, 0.8f, 0.7f, 0.9f, 0.85f, 0.65f, 0.95f, 0.8f, 0.7f, 0.85f, 0.75f, 0.9f)
        ),
        MusicTrack(
            "track_mystery",
            "Enigma Unlocked (Viral Curiosity)",
            "Documentary / Hooks",
            105,
            true,
            "Tense ticking clock and atmospheric bass drops for shocking facts",
            listOf(0.35f, 0.5f, 0.75f, 0.4f, 0.85f, 0.9f, 0.45f, 0.8f, 0.65f, 0.9f, 0.55f, 0.7f)
        )
    )
}

data class TrendingPrompt(
    val id: String,
    val title: String,
    val subtitle: String,
    val tag: String,
    val defaultStyle: VideoStyle,
    val ideaPrompt: String,
    val hook: String,
    val estimatedViralScore: String = "99% Viral Potential",
    val defaultMusicId: String = "track_phonk",
    val scenes: List<VideoScene>
)
