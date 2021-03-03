package com.chriskevin.epl.ui.standings

import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread
import com.chriskevin.epl.R
import com.chriskevin.epl.RecyclerViewItemCountAssertion
import com.chriskevin.epl.core.domain.usecase.AppUseCase
import com.chriskevin.epl.core.util.EspressoIdlingResource
import com.chriskevin.epl.launchFragmentInHiltContainer
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class StandingsFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var appUseCase: AppUseCase

    @Before
    fun init() {
        hiltRule.inject()
        IdlingRegistry.getInstance().register(EspressoIdlingResource.espressoTestIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.espressoTestIdlingResource)
    }

    @Test
    fun testNavigationToTeamDetails() {
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )
        runOnUiThread {
            navController.setGraph(R.navigation.standings)
        }

        launchFragmentInHiltContainer<StandingsFragment> {
            Navigation.setViewNavController(this.requireView(), navController)
        }

        // check recycle
        onView(ViewMatchers.withId(R.id.recycler_view)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        // expected recycle item count = 20(+1 for header)
        onView(ViewMatchers.withId(R.id.recycler_view)).check(RecyclerViewItemCountAssertion(21))
        // click on item in recycler and navigate
        onView(ViewMatchers.withId(R.id.recycler_view)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                1, click()
            )
        )
        // check destination
        assertThat(navController.currentDestination?.id).isEqualTo(R.id.teamDetailsFragment)
    }
}