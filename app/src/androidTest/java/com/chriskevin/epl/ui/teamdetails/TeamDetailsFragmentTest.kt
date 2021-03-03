package com.chriskevin.epl.ui.teamdetails

import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.internal.runner.junit4.statement.UiThreadStatement
import com.chriskevin.epl.R
import com.chriskevin.epl.core.domain.usecase.AppUseCase
import com.chriskevin.epl.core.util.EspressoIdlingResource
import com.chriskevin.epl.launchFragmentInHiltContainer
import com.chriskevin.epl.util.Const.TEAM_ID
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
class TeamDetailsFragmentTest {
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
    fun testNavigationSquad() {
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )
        UiThreadStatement.runOnUiThread {
            navController.setGraph(R.navigation.team_details)
            navController.setCurrentDestination(R.id.teamDetailsFragment, Bundle().apply {
                putInt(TEAM_ID, 65)
            })
        }

        launchFragmentInHiltContainer<TeamDetailsFragment> {
            this.viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                if (viewLifecycleOwner != null) {
                    Navigation.setViewNavController(this.requireView(), navController)
                }
            }
        }

        // check arguments
        val backStack = navController.backStack
        val currentDestination = backStack.last()
        assertThat(currentDestination.arguments!![TEAM_ID]).isEqualTo(65)
    }
}