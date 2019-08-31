package com.example.squareprojects

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.uiautomator.UiDevice
import com.example.squareprojects.api.model.License
import com.example.squareprojects.api.model.Owner
import com.example.squareprojects.api.model.Repository
import com.example.squareprojects.ui.RepositoryListActivity
import org.hamcrest.CoreMatchers.allOf
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class InstrumentedTest {

    @get:Rule
    var activityRule: ActivityTestRule<RepositoryListActivity> = ActivityTestRule(
        RepositoryListActivity::class.java,
        true, false
    )

    private val gitHubService = GitHubService()

    @Before
    fun before() {
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        gitHubService.startService()
    }

    @After
    fun after() {
        gitHubService.stopService()
    }

    @Test
    fun networkDisconnected_openRepositoryListAndLoad_failedWithErrorMessage() {

        gitHubService.failRepositoryListFetch = true

        activityRule.launchActivity(null)

        Assert.assertTrue(
            "Error activity wasn't shown",
            waitForText(R.id.errorTextView, R.string.error_activity_text)
        )
    }

    @Test
    fun networkConnectedAndOneRepositoryExist_openRepositoryListAndLoad_repositoryIsShown() {

        val repository = Repository(
            1,
            "SuperProject1",
            "SuperCoder/SuperProject1",
            "Description of SuperProject1",
            100,
            "Java",
            License("Apache 2.0"),
            Owner("http://localhost/avatar.png") // TODO: extend GitHubService to provide static content for avatars
        )

        gitHubService.addRepository(repository)

        activityRule.launchActivity(null)

        Assert.assertTrue(waitForTextInRecyclerView(repository.name))

        // Open repository details
        onView(
            withId(R.id.listView)
        ).perform(
            RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                hasDescendant(
                    allOf(withId(R.id.name), withText(repository.name))),
                click()
            )
        )

        // Check repository description
        onView(
            allOf(withId(R.id.description), withText(repository.description))
        ).check(
            matches(isDisplayed())
        )

    }
}
