package com.example.data.repository

import com.example.data.model.TrendingPrompt
import com.example.data.model.VideoScene
import com.example.data.model.VideoStyle

object TrendingLibrary {
    val trendingPrompts: List<TrendingPrompt> = listOf(
        TrendingPrompt(
            id = "cat_astronaut",
            title = "Space Cat Astronaut on Mars",
            subtitle = "Cute 3D Pixar style • 14.8M Views Trend",
            tag = "Kids & Family",
            defaultStyle = VideoStyle.PIXAR_3D,
            ideaPrompt = "A brave fluffy orange kitten wearing an astronaut helmet lands on Mars and discovers glowing cheese craters",
            hook = "Wait until you see what this cat found on Mars...",
            estimatedViralScore = "99.4% Viral Hook",
            defaultMusicId = "track_pixar",
            scenes = listOf(
                VideoScene(
                    sceneNumber = 1,
                    title = "The Blastoff",
                    visualPrompt = "Cute chubby orange tabby kitten in a high-tech glowing spacesuit looking through rocket cockpit window at planet Mars, Pixar 3D style",
                    narrationText = "Nobody believed a tiny kitten could pilot a rocket to Mars...",
                    captionHighlight = "TINY KITTEN ON MARS!",
                    durationSec = 4,
                    styleKeyword = "pixar_3d",
                    primaryColorHex = 0xFFFF7675,
                    secondaryColorHex = 0xFFFAB1A0
                ),
                VideoScene(
                    sceneNumber = 2,
                    title = "The Touchdown",
                    visualPrompt = "Orange cat astronaut making soft first pawprints on glowing red Martian dust, starry galaxy sky above, 3D animated octane render",
                    narrationText = "One small step for man, one giant pounce for kitten-kind!",
                    captionHighlight = "GIANT POUNCE!",
                    durationSec = 4,
                    styleKeyword = "pixar_3d",
                    primaryColorHex = 0xFFE17055,
                    secondaryColorHex = 0xFFFFEAA7
                ),
                VideoScene(
                    sceneNumber = 3,
                    title = "The Secret Discovery",
                    visualPrompt = "Close-up of cat astronaut widening sparkling big green eyes discovering glowing golden cheese crater emitting starlight, magical 3D Disney look",
                    narrationText = "And deep inside the Martian crater, the impossible happened...",
                    captionHighlight = "GLOWING CHEESE CRATER?!",
                    durationSec = 4,
                    styleKeyword = "pixar_3d",
                    primaryColorHex = 0xFFFDCB6E,
                    secondaryColorHex = 0xFF00CEC9
                ),
                VideoScene(
                    sceneNumber = 4,
                    title = "Mission Complete",
                    visualPrompt = "Happy cat astronaut high-fiving a friendly miniature alien creature with colorful antennae, smiling together under twin moons",
                    narrationText = "Best friends across the cosmos! Would you travel to Mars?",
                    captionHighlight = "BEST FRIENDS FOREVER!",
                    durationSec = 4,
                    styleKeyword = "pixar_3d",
                    primaryColorHex = 0xFF6C5CE7,
                    secondaryColorHex = 0xFFA29BFE
                )
            )
        ),
        TrendingPrompt(
            id = "cyberpunk_food_truck",
            title = "Tokyo 2099 Flying Ramen Shop",
            subtitle = "Cyberpunk Neon • 22.4M Views Trend",
            tag = "Sci-Fi Viral",
            defaultStyle = VideoStyle.CYBERPUNK_NEON,
            ideaPrompt = "A flying noodle cart hovering among neon skyscrapers in futuristic Tokyo serving steaming holographic ramen",
            hook = "This is street food in the year 2099...",
            estimatedViralScore = "98.9% Viral Hook",
            defaultMusicId = "track_cyber",
            scenes = listOf(
                VideoScene(
                    sceneNumber = 1,
                    title = "Neon Skyline",
                    visualPrompt = "Futuristic Tokyo 2099 night skyline with flying vehicles and glowing holographic billboards through misty rain, ultra cyberpunk style",
                    narrationText = "High above the misty clouds of Neo Tokyo sits the rarest food spot...",
                    captionHighlight = "NEO TOKYO 2099",
                    durationSec = 4,
                    styleKeyword = "cyberpunk_neon",
                    primaryColorHex = 0xFF00CEC9,
                    secondaryColorHex = 0xFF0984E3
                ),
                VideoScene(
                    sceneNumber = 2,
                    title = "The Cyber Chef",
                    visualPrompt = "Friendly cybernetic chef with glowing robotic arm tossing hot noodles into a floating anti-gravity bowl with neon steam",
                    narrationText = "Master Ryu cooks with anti-gravity sparks and secret 100-year spices.",
                    captionHighlight = "ANTI-GRAVITY NOODLES!",
                    durationSec = 4,
                    styleKeyword = "cyberpunk_neon",
                    primaryColorHex = 0xFFE84393,
                    secondaryColorHex = 0xFF6C5CE7
                ),
                VideoScene(
                    sceneNumber = 3,
                    title = "The Sizzle",
                    visualPrompt = "Macro close-up of steaming holographic ramen broth sparkling with edible neon seasoning and glowing soft boiled egg",
                    narrationText = "One sip will recharge your neural implants instantly!",
                    captionHighlight = "NEURAL RECHARGE!",
                    durationSec = 4,
                    styleKeyword = "cyberpunk_neon",
                    primaryColorHex = 0xFFFDCB6E,
                    secondaryColorHex = 0xFFE17055
                ),
                VideoScene(
                    sceneNumber = 4,
                    title = "Customer Arrival",
                    visualPrompt = "Hovering sports car pulls up to the floating stall window, cyberpunk character grabs bowl with a wide grin into the neon night",
                    narrationText = "Would you eat here? Drop a comment below!",
                    captionHighlight = "WOULD YOU TRY THIS?",
                    durationSec = 4,
                    styleKeyword = "cyberpunk_neon",
                    primaryColorHex = 0xFF6C5CE7,
                    secondaryColorHex = 0xFF00CEC9
                )
            )
        ),
        TrendingPrompt(
            id = "animals_with_phones",
            title = "If Animals Had TikTok & Instagram",
            subtitle = "Viral Comedy • 38.1M Views Trend",
            tag = "Humor & Trends",
            defaultStyle = VideoStyle.HYPERREALISTIC,
            ideaPrompt = "Hilarious realistic animals holding modern smartphones filming their daily drama and viral dance challenges",
            hook = "What if animals secretly ran social media?",
            estimatedViralScore = "99.8% Viral Hook",
            defaultMusicId = "track_phonk",
            scenes = listOf(
                VideoScene(
                    sceneNumber = 1,
                    title = "Influencer Squirrel",
                    visualPrompt = "Funny hyperrealistic squirrel wearing tiny wireless earbuds holding gold smartphone checking acorn stock market graphs on a tree branch",
                    narrationText = "First up: The stock trader squirrel checking acorn crypto prices!",
                    captionHighlight = "ACORN CRYPTO BOOM!",
                    durationSec = 4,
                    styleKeyword = "hyperrealistic",
                    primaryColorHex = 0xFF00B894,
                    secondaryColorHex = 0xFF55EFC4
                ),
                VideoScene(
                    sceneNumber = 2,
                    title = "Gym Rat Hamster",
                    visualPrompt = "Chubby hamster wearing a sweatband livestreaming on miniature ring-light while sprinting on running wheel, realistic lighting",
                    narrationText = "He is on a 30-day fitness grind and refuses to skip leg day!",
                    captionHighlight = "NO EXCUSES GYM GRIND!",
                    durationSec = 4,
                    styleKeyword = "hyperrealistic",
                    primaryColorHex = 0xFFFF7675,
                    secondaryColorHex = 0xFFFAB1A0
                ),
                VideoScene(
                    sceneNumber = 3,
                    title = "Drama Queen Cat",
                    visualPrompt = "Fluffy white Persian cat in silk sunglasses holding phone filming a rant video in front of an empty food bowl with dramatic lighting",
                    narrationText = "This cat is exposing her owner for serving lunch 3 minutes late...",
                    captionHighlight = "EXPOSING THE HOOMAN!",
                    durationSec = 4,
                    styleKeyword = "hyperrealistic",
                    primaryColorHex = 0xFFE84393,
                    secondaryColorHex = 0xFFFD79A8
                ),
                VideoScene(
                    sceneNumber = 4,
                    title = "Viral Dance Monkey",
                    visualPrompt = "Cool capuchin monkey wearing backward cap hitting a viral dance trend with friends in lush jungle foliage, camera selfie angle",
                    narrationText = "Hit that share button before the algorithm catches them!",
                    captionHighlight = "SHARE TO SPREAD THE LAUGHS!",
                    durationSec = 4,
                    styleKeyword = "hyperrealistic",
                    primaryColorHex = 0xFF6C5CE7,
                    secondaryColorHex = 0xFF00CEC9
                )
            )
        ),
        TrendingPrompt(
            id = "ghibli_coffee",
            title = "Rainy Café in Studio Ghibli Village",
            subtitle = "Anime Magic • 19.1M Views Trend",
            tag = "Aesthetic Anime",
            defaultStyle = VideoStyle.ANIME_STUDIO,
            ideaPrompt = "Warm cozy coffee shop in a quiet cobblestone alley during gentle spring rain with glowing lanterns and cat barista",
            hook = "Take a breath. You found the calmest place on the internet...",
            estimatedViralScore = "97.5% Viral Hook",
            defaultMusicId = "track_lofi",
            scenes = listOf(
                VideoScene(
                    sceneNumber = 1,
                    title = "Gentle Rain",
                    visualPrompt = "Studio Ghibli style watercolor illustration of raindrops falling into puddles along peaceful cobblestone European village street with glowing orange cafe window",
                    narrationText = "Leave the noise outside. Step into warmth...",
                    captionHighlight = "PEACEFUL RAIN VIBES",
                    durationSec = 4,
                    styleKeyword = "anime_studio",
                    primaryColorHex = 0xFF0984E3,
                    secondaryColorHex = 0xFF74B9FF
                ),
                VideoScene(
                    sceneNumber = 2,
                    title = "Fresh Brew",
                    visualPrompt = "Detailed anime scene of freshly roasted dark coffee beans grinding and steam rising from vintage copper pour-over kettle, golden dust motes",
                    narrationText = "Rich caramel aroma and warm cinnamon gently filling the air.",
                    captionHighlight = "WARM CARAMEL AROMA",
                    durationSec = 4,
                    styleKeyword = "anime_studio",
                    primaryColorHex = 0xFFD63031,
                    secondaryColorHex = 0xFFFAB1A0
                ),
                VideoScene(
                    sceneNumber = 3,
                    title = "Cozy Corner",
                    visualPrompt = "Anime girl reading leather book by steamed window with calico cat curled up sleeping peacefully beside hot ceramic mug with foam art",
                    narrationText = "A moment where time stands still just for you.",
                    captionHighlight = "JUST BREATHE...",
                    durationSec = 4,
                    styleKeyword = "anime_studio",
                    primaryColorHex = 0xFFFDCB6E,
                    secondaryColorHex = 0xFFFFEAA7
                ),
                VideoScene(
                    sceneNumber = 4,
                    title = "Soft Rain Falls",
                    visualPrompt = "Wide panorama of quaint storybook village under soft violet twilight sky, lantern lights twinkling through evening mist",
                    narrationText = "Save this video whenever you need a peaceful break today.",
                    captionHighlight = "SAVE FOR PEACEFUL SLEEP",
                    durationSec = 4,
                    styleKeyword = "anime_studio",
                    primaryColorHex = 0xFF6C5CE7,
                    secondaryColorHex = 0xFFA29BFE
                )
            )
        ),
        TrendingPrompt(
            id = "ancient_underwater_temple",
            title = "10,000 Year Old Sunken Temple",
            subtitle = "Cinematic 4K • 29.5M Views Trend",
            tag = "Epic Mystery",
            defaultStyle = VideoStyle.CINEMATIC_4K,
            ideaPrompt = "Deep ocean expedition discovering an untouched glowing bioluminescent civilization temple hidden inside the Mariana Trench",
            hook = "Scientists hid this discovery for 30 years...",
            estimatedViralScore = "99.2% Viral Hook",
            defaultMusicId = "track_epic",
            scenes = listOf(
                VideoScene(
                    sceneNumber = 1,
                    title = "The Descent",
                    visualPrompt = "High tech deep sea submersible descending through pitch black ocean trench with powerful dual xenon spotlights cutting through particulate water",
                    narrationText = "Seven miles below the surface, sonar picked up something unnatural.",
                    captionHighlight = "7 MILES BELOW THE SEA",
                    durationSec = 4,
                    styleKeyword = "cinematic_4k",
                    primaryColorHex = 0xFF0984E3,
                    secondaryColorHex = 0xFF00CEC9
                ),
                VideoScene(
                    sceneNumber = 2,
                    title = "The Monolith",
                    visualPrompt = "Massive ancient carved obsidian stone gates covered in glowing turquoise bioluminescent coral and unknown crystalline glyphs underwater",
                    narrationText = "Towering gates made of obsidian, built long before human history.",
                    captionHighlight = "IMPOSSIBLE ANCIENT GATES!",
                    durationSec = 4,
                    styleKeyword = "cinematic_4k",
                    primaryColorHex = 0xFF00CEC9,
                    secondaryColorHex = 0xFF55EFC4
                ),
                VideoScene(
                    sceneNumber = 3,
                    title = "Inner Chamber",
                    visualPrompt = "Submarine camera pans into colossal sunken temple rotunda with hovering golden energy orb illuminating carved statues of mythical guardians",
                    narrationText = "At the center... a perpetual light that has burned for 10,000 years.",
                    captionHighlight = "ETERNAL LIGHT ORB!",
                    durationSec = 4,
                    styleKeyword = "cinematic_4k",
                    primaryColorHex = 0xFFFDCB6E,
                    secondaryColorHex = 0xFFE17055
                ),
                VideoScene(
                    sceneNumber = 4,
                    title = "The Awakening",
                    visualPrompt = "Water begins to ripple with mysterious golden energy pulses as giant guardian eye slowly opens, cinematic camera shudder effect",
                    narrationText = "And then... it woke up. Follow for part 2!",
                    captionHighlight = "IT JUST WOKE UP... PART 2?",
                    durationSec = 4,
                    styleKeyword = "cinematic_4k",
                    primaryColorHex = 0xFFE17055,
                    secondaryColorHex = 0xFFD63031
                )
            )
        )
    )
}
