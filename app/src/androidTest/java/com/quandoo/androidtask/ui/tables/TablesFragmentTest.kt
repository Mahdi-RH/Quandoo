package com.quandoo.androidtask.ui.tables

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import androidx.test.runner.AndroidJUnit4
import com.quandoo.androidtask.R
import com.quandoo.androidtask.domain.model.Customer
import com.quandoo.androidtask.domain.model.Reservation
import com.quandoo.androidtask.domain.model.Table
import com.quandoo.androidtask.ui.main.MainActivity
import com.quandoo.androidtask.utils.Constants.CUSTOMERS_FILE_NAME
import com.quandoo.androidtask.utils.Constants.RESERVATIONS_FILE_NAME
import com.quandoo.androidtask.utils.Constants.TABLES_FILE_NAME
import com.quandoo.androidtask.utils.PersistanceUtil
import com.quandoo.androidtask.utils.sleep
import org.junit.*
import org.junit.Assert.*
import org.junit.rules.TestName
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters


@RunWith(AndroidJUnit4::class)
@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class TablesFragmentTest {

    @get:Rule
    var mActivityTestRule: ActivityTestRule<MainActivity> =
        ActivityTestRule(MainActivity::class.java, true,
            false)

    @get:Rule
    var name = TestName()

    @get:Rule
    var mGrantPermissionRule: GrantPermissionRule = GrantPermissionRule.grant("android.permission.INTERNET")

    @Before
    fun setup() {

        if (name.methodName.equals("test_a_firstTime_appIsOpened_InternetIsOff")) removeCachedData() else addDataToCache()

        InstrumentationRegistry.getInstrumentation().uiAutomation.executeShellCommand("svc wifi disable")
        InstrumentationRegistry.getInstrumentation().uiAutomation.executeShellCommand("svc data disable")

        //launch activity using empty intent (no arguments needed for now ...)
        mActivityTestRule.launchActivity(Intent())
    }

    @After
    fun tearDown() {
        InstrumentationRegistry.getInstrumentation().uiAutomation.executeShellCommand("svc wifi enable")
        InstrumentationRegistry.getInstrumentation().uiAutomation.executeShellCommand("svc data enable")
    }

    @Test
    fun test_a_firstTime_appIsOpened_InternetIsOff() {
        sleep(5_000) // move to utils
        onView(withText("No internet connection!")).check(matches(isDisplayed()))
    }

    @Test
    fun test_b_appIsOpened_catchMode() {
        sleep(5_000)
        val recyclerView = mActivityTestRule.activity.findViewById<RecyclerView>(R.id.recycler_view)
        assertNotNull(recyclerView)

        if (recyclerView.adapter!!.itemCount == 0) fail("list is empty")
        sleep(1_000)
        val findViewHolderForAdapterPosition = recyclerView.findViewHolderForAdapterPosition(0)
        assertTrue(findViewHolderForAdapterPosition is TablesRvAdapter.TableViewHolder)
    }

    @Test
    fun test_c_check_reservedTable() {
        sleep(5_000)
        val recyclerView = mActivityTestRule.activity.findViewById<RecyclerView>(R.id.recycler_view)
        assertNotNull(recyclerView)
        val adapter = recyclerView.adapter as TablesRvAdapter
        if (adapter.itemCount == 0) fail("list is empty")

        for (i in 0 until adapter.itemCount) {
            val item: Table = adapter.getItem(i)
            if (item.reservedBy.isNullOrEmpty().not()) {
                val findUserImage = adapter.findUserImage(item.reservedBy)
                assertTrue(findUserImage.startsWith("https"))
            }
        }
    }

    private fun addDataToCache() {
        val customer: ArrayList<Customer> = ArrayList()
        customer.add(
            Customer(
                "Marilyn",
                "Monroe",
                "https://s3-eu-west-1.amazonaws.com/quandoo-assessment/images/profile1.png",
                0
            )
        )
        customer.add(
            Customer(
                "Abraham",
                "Lincoln",
                "https://s3-eu-west-1.amazonaws.com/quandoo-assessment/images/profile2.png",
                1
            )
        )
        customer.add(
            Customer(
                "Mother",
                "Teresa",
                "https://s3-eu-west-1.amazonaws.com/quandoo-assessment/images/profile3.png",
                2
            )
        )


        val reservation: ArrayList<Reservation> = ArrayList()
        reservation.add(Reservation(1001, 103, 2))
        reservation.add(Reservation(1002, 104, 2))
        reservation.add(Reservation(1003, 105, 17))

        val tables: ArrayList<Table> = ArrayList()
        tables.add(Table("circle", 101))
        tables.add(Table("square", 102))
        val element = Table("rectangle", 103)
        element.reservedBy = "Mother Teresa"
        tables.add(element)

        PersistanceUtil.writeCustomersToFile(customer)
        PersistanceUtil.writeReservationsToFile(reservation)
        PersistanceUtil.writeTablesToFile(tables)
    }

    private fun removeCachedData() {
        PersistanceUtil.removeSerializable(TABLES_FILE_NAME)
        PersistanceUtil.removeSerializable(CUSTOMERS_FILE_NAME)
        PersistanceUtil.removeSerializable(RESERVATIONS_FILE_NAME)
    }
}
