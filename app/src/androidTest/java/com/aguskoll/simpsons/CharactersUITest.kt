package com.aguskoll.simpsons

import AuthRepository

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.aguskoll.domain.models.SimpsonCharacter
import com.aguskoll.domain.repositories.CharactersRepository
import com.aguskoll.simpsons.ui.AppActivity
import com.aguskoll.usecases.GetCharactersUseCase
import junit.framework.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

@RunWith(AndroidJUnit4::class)
class CharactersUITest {
    @Rule
    @JvmField
    val composeTestRule = createAndroidComposeRule<AppActivity>()

    private lateinit var testModule: Module

    @Before
    fun setup() {
       /* val fakeRepo = object : CharactersRepository {
            override suspend fun getCharacters(): Result<List<SimpsonCharacter>> {
                return Result.success(
                    listOf(
                        SimpsonCharacter(id = 1, name = "Homer Simpson", portraitPath = null),
                        SimpsonCharacter(id = 2, name = "Marge Simpson", portraitPath = null)
                    )
                )
            }
        }
        testModule = module{ single<GetCharactersUseCase> { GetCharactersUseCase(fakeRepo) } }
        loadKoinModules(testModule)*/
    }



    @After
    fun tearDown() {
       // unloadKoinModules(testModule)
    }

    @Test
    fun testCharactersList() {
        // List container should exist
        composeTestRule.onNodeWithTag("CHARACTERS_LIST").assertExists()
        composeTestRule.onNodeWithTag("CHARACTER_IMAGE").assertExists()
        // Characters should be visible
        composeTestRule.onNodeWithText("Homer Simpson").assertIsDisplayed()
        composeTestRule.onNodeWithText("Marge Simpson").assertIsDisplayed()
    }
}