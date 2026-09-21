package com.example

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import com.example.data.model.OtakuArchetype
import com.example.data.model.UserProfileEntity
import com.example.ui.screens.NexusCitySkylineHeroCard
import com.example.ui.theme.MyApplicationTheme
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.github.takahirom.roborazzi.captureRoboImage
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(qualifiers = RobolectricDeviceQualifiers.Pixel8, sdk = [36])
class GreetingScreenshotTest {

  @get:Rule val composeTestRule = createComposeRule()

  @Test
  fun greeting_screenshot() {
    val sampleProfile = UserProfileEntity(
      username = "Kenji Otaku",
      tag = "@kenji_hub",
      rankTitle = "Elite Otaku ⚡",
      level = 7,
      currentXp = 480,
      maxXp = 1000,
      avatarEmoji = "⚡",
      bio = "Passionné d'anime & shonen",
      profileFrame = "CYBER_AURA",
      bannerTheme = "CYBER_BLUE"
    )

    composeTestRule.setContent {
      MyApplicationTheme {
        NexusCitySkylineHeroCard(
          profile = sampleProfile,
          archetype = OtakuArchetype.FIGHTER,
          otakuCoins = 1250,
          onOpenProfile = {},
          onStartBattle = {},
          onNavigateToPortals = {},
          onNavigateToRoom = {},
          onTriggerCinematic = {}
        )
      }
    }

    composeTestRule.onRoot().captureRoboImage(filePath = "src/test/screenshots/greeting.png")
  }
}
