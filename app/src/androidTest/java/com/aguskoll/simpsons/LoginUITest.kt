package com.aguskoll.simpsons

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.aguskoll.simpsons.ui.AppActivity
import com.aguskoll.usecases.LoginUseCase
import AuthRepository
import androidx.compose.ui.test.onAllNodesWithText
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
class LoginUITest {
    @get:Rule
    @JvmField
    val composeTestRule = createAndroidComposeRule<AppActivity>()

    private lateinit var testModule: Module

    @Before
    fun setup() {
        val fakeRepo = object : AuthRepository {
            override suspend fun login(email: String, password: String): Result<Unit> =
                Result.success(Unit)
        }
        testModule = module { single<LoginUseCase> { LoginUseCase(fakeRepo) } }
        loadKoinModules(testModule)
    }

    @After
    fun tearDown() {
        unloadKoinModules(testModule)
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        TestCase.assertEquals("com.aguskoll", appContext.packageName)
    }

    @Test
    fun login_composable_component_test() {
        composeTestRule.onNodeWithTag("LOGIN_EMAIL_INPUT").assertExists().assertIsEnabled()
            .assertIsDisplayed()
    }

    @Test
    fun login_shows_welcome_and_button() {
        composeTestRule.onNodeWithText("Welcome Back!").assertIsDisplayed()
        composeTestRule.onNodeWithText("LOG IN").assertIsDisplayed()
    }

    @Test
    fun email_shows_error_for_invalid_format() {
        composeTestRule.onNodeWithTag("LOGIN_EMAIL_INPUT").performTextInput("not-an-email")
        composeTestRule.onNodeWithText("Email format not valid").assertIsDisplayed()
    }

    @Test
    fun password_shows_error_for_weak_value() {
        composeTestRule.onNodeWithText("Password").performTextInput("123")
        composeTestRule.onNodeWithText(
            "Password must contain a lower case, upper case, a number and a special character"
        ).assertIsDisplayed()
    }

    @Test
    fun password_toggle_visibility_icon_changes_description() {
        composeTestRule.onNodeWithContentDescription("Hide password").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Hide password").performClick()
        composeTestRule.waitForIdle()
        Thread.sleep(1800)
        composeTestRule.onNodeWithContentDescription("Show password").assertIsDisplayed()
    }

    @Test
    fun navigates_to_home_on_successful_login() {
        // Enter valid credentials
        composeTestRule.onNodeWithTag("LOGIN_EMAIL_INPUT").performTextInput("user@example.com")
        composeTestRule.onNodeWithText("Password").performTextInput("Abcdef1!")
        // Click LOG IN
        composeTestRule.onNodeWithText("LOG IN").performClick()
        // Wait until Main Page is shown
        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule.onAllNodesWithText("Main Page").fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.onNodeWithText("Main Page").assertIsDisplayed()
    }

    @Test
    fun stays_on_login_when_login_fails() {
        // Temporarily override with a failing use case
        val failingModule = module {
            single<LoginUseCase> {
                val failingRepo = object : AuthRepository {
                    override suspend fun login(email: String, password: String): Result<Unit> =
                        Result.failure(IllegalStateException("Invalid creds"))
                }
                LoginUseCase(failingRepo)
            }
        }
        loadKoinModules(failingModule)
        try {
            // Provide valid-looking inputs so validation passes and use case is invoked
            composeTestRule.onNodeWithTag("LOGIN_EMAIL_INPUT").performTextInput("user@example.com")
            composeTestRule.onNodeWithText("Password").performTextInput("Abcdef1!")
            composeTestRule.onNodeWithText("LOG IN").performClick()
            composeTestRule.waitForIdle()
            // Ensure we did NOT navigate to Main Page
            composeTestRule.waitUntil(timeoutMillis = 1_500) { true }
            val mainNodes = composeTestRule.onAllNodesWithText("Main Page").fetchSemanticsNodes()
            TestCase.assertTrue("Should stay on Login when login fails", mainNodes.isEmpty())
            // Login screen text still visible
            composeTestRule.onNodeWithText("Welcome Back!").assertIsDisplayed()
        } finally {
            unloadKoinModules(failingModule)
        }
    }
}