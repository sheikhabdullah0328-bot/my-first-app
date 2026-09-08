package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("V.Ai", appName)
  }

  @Test
  fun `verify trending library contains prompts`() {
    val trending = com.example.data.repository.TrendingLibrary.trendingPrompts
    assert(trending.isNotEmpty())
    assert(trending.first().scenes.size == 4)
  }

  @Test
  fun `verify prompt expander generates viral story`() {
    val story = com.example.ai.PromptExpander.generateViralStoryFromIdea(
        "cat astronaut on mars",
        com.example.data.model.VideoStyle.PIXAR_3D
    )
    assert(story.scenes.size == 4)
    assert(story.hook.isNotBlank())
  }
}
