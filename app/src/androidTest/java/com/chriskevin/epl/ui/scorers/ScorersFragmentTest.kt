package com.chriskevin.epl.ui.scorers

import android.view.View
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread
import com.chriskevin.epl.R
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
class ScorersFragmentTest {
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
            navController.setGraph(R.navigation.stats)
        }

        launchFragmentInHiltContainer<ScorersFragment> {
            Navigation.setViewNavController(this.requireView(), navController)
        }

        // check recycle
        onView(ViewMatchers.withId(R.id.recycler_view)).check(matches(isDisplayed()))

        // click team name on item in recycler and navigate
        onView(ViewMatchers.withId(R.id.recycler_view)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                1, object : ViewAction {
                    override fun getConstraints() = null
                    override fun getDescription() = "Click on an item view with specified id."
                    override fun perform(uiController: UiController?, view: View?) {
                        val itemView = view?.findViewById<View>(R.id.team_name)
                            ?: throw Exception("Item view is not found")
                        itemView.performClick()
                    }
                })
        )
        // check destination
        assertThat(navController.currentDestination?.id).isEqualTo(R.id.teamDetailsFragment)
    }
}