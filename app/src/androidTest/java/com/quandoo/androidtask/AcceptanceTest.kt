package com.quandoo.androidtask

import android.content.Intent
import android.os.AsyncTask


import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.quandoo.androidtask.ui.main.MainActivity
import com.quandoo.androidtask.utils.EspressoCustomMarchers.Companion.first
import com.quandoo.androidtask.utils.EspressoCustomMarchers.Companion.withHolderTablesView
import com.quandoo.androidtask.utils.EspressoCustomMarchers.Companion.withRecyclerView
import com.quandoo.androidtask.utils.Constants.CUSTOMERS_FILE_NAME
import com.quandoo.androidtask.utils.Constants.RESERVATIONS_FILE_NAME
import com.quandoo.androidtask.utils.Constants.TABLES_FILE_NAME
import com.quandoo.androidtask.utils.PersistanceUtil
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.hamcrest.CoreMatchers.not
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters


/**
 * Instrumented test, which will execute on an Android device.
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class AcceptanceTest {

    @get:Rule
    var mActivityTestRule: ActivityTestRule<MainActivity> =
            ActivityTestRule(MainActivity::class.java, true,
                    false)

    @Before
    fun setup() {

        //clear cached state
        PersistanceUtil.removeSerializable(TABLES_FILE_NAME)
        PersistanceUtil.removeSerializable(CUSTOMERS_FILE_NAME)
        PersistanceUtil.removeSerializable(RESERVATIONS_FILE_NAME)

        //make espresso wait for RXJava
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.from(AsyncTask.THREAD_POOL_EXECUTOR) }
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.from(AsyncTask.THREAD_POOL_EXECUTOR) }
        RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.from(AsyncTask.THREAD_POOL_EXECUTOR) }


        //launch activity using empty intent (no arguments needed for now ...)
        mActivityTestRule.launchActivity(Intent())
    }

    @After
    fun tearDown() {
    }


    @Test
    fun test_a_reserveTableTest() {

        //GIVEN :

        //App is open
        onView(withText("Tables")).check(matches(isDisplayed()))

        // List of tables visible
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()))

        //There is at least one free table visible
        onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions.scrollToHolder(first(withHolderTablesView("Free"))))


        //Hacky way of getting a position of desired element
        val freeTablePosition = mActivityTestRule.activity.mainViewModel.getTables()?.indexOfFirst { table -> table.reservedBy == null }

        freeTablePosition?.let {
            //WHEN :

        //User clicks on free table
        onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(freeTablePosition, click()))

        //THEN :

        //Screen with customers appear
        onView(withText("Customers")).check(matches(isDisplayed()))

        //WHEN :

        //User clicks on any user
        onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(freeTablePosition, click()))

        //THEN :

        //Screen with users tables appear
        onView(withText("Tables")).check(matches(isDisplayed()))


        //Previously selected table is marked as reserved by a user name
        onView(withRecyclerView(R.id.recycler_view).atPosition(freeTablePosition))
                .check(matches(not(hasDescendant(withText("Free")))))

    }

}

    @Test
    fun test_b_removeReservationTest() {

        //GIVEN :

        //App is open
        onView(withText("Tables")).check(matches(isDisplayed()))

        // List of tables visible
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()))

        //There is at least one reserved table
        onView(withId(R.id.recycler_view))
            .perform(RecyclerViewActions.scrollToHolder(first(not(withHolderTablesView("Free")))))


        val reservedTablePosition = mActivityTestRule.activity.mainViewModel.getTables()?.indexOfFirst { table -> table.reservedBy != null }
        reservedTablePosition?.let {
            //WHEN :
            //User clicks on a reserved table
            onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(reservedTablePosition, click()))

            //THEN :

            //Confirmation dialog appears
            onView(withText("Do you want to free the table?")).check(matches(isDisplayed()))

            //WHEN :

            //User clicks on a accept button
            onView(withText("Yes")).check(matches(isDisplayed())).perform(click())


            //Previously reserved table is marked as free
            onView(withRecyclerView(R.id.recycler_view).atPosition(reservedTablePosition))
                .check(matches(hasDescendant(withText("Free"))))

        }
    }
}