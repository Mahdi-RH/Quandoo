package com.quandoo.androidtask.utils



import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.util.HumanReadables
import androidx.test.espresso.util.TreeIterables
import com.quandoo.androidtask.R
import com.quandoo.androidtask.ui.tables.TablesRvAdapter
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import java.util.concurrent.TimeoutException
import org.hamcrest.BaseMatcher


class EspressoCustomMarchers {
    companion object {


        /** Perform action of waiting for a specific view id.  */
        fun waitForView(viewId: Int, millis: Long): ViewAction {
            return object : ViewAction {
                override fun getConstraints(): Matcher<View> {
                    return isRoot()
                }

                override fun getDescription(): String {
                    return "wait for a specific view with id <$viewId> during $millis millis."
                }

                override fun perform(uiController: UiController, view: View) {
                    uiController.loopMainThreadUntilIdle()
                    val startTime = System.currentTimeMillis()
                    val endTime = startTime + millis
                    val viewMatcher = withId(viewId)

                    do {
                        for (child in TreeIterables.breadthFirstViewTraversal(view)) {
                            // found view with required ID
                            if (viewMatcher.matches(child)) {
                                return
                            }
                        }

                        uiController.loopMainThreadForAtLeast(50)
                    } while (System.currentTimeMillis() < endTime)

                    // timeout happens
                    throw PerformException.Builder()
                            .withActionDescription(this.description)
                            .withViewDescription(HumanReadables.describe(view))
                            .withCause(TimeoutException())
                            .build()
                }
            }
        }


        fun <T> first(matcher: Matcher<T>): Matcher<T> {
            return object : BaseMatcher<T>() {
                var isFirst = true

                override fun matches(item: Any): Boolean {
                    if (isFirst && matcher.matches(item)) {
                        isFirst = false
                        return true
                    }

                    return false
                }

                override fun describeTo(description: Description) {
                    description.appendText("should return first matching item")
                }
            }
        }


        fun withHolderTablesView(text: String): Matcher<RecyclerView.ViewHolder> {
            return object : BoundedMatcher<RecyclerView.ViewHolder, TablesRvAdapter.TableViewHolder>(TablesRvAdapter.TableViewHolder::class.java) {

                override fun describeTo(description: Description) {
                    description.appendText("No ViewHolder found with text: $text")
                }

                override fun matchesSafely(item: TablesRvAdapter.TableViewHolder): Boolean {
                    val reservedByText = item.itemView.findViewById(R.id.reservingCustomerName) as TextView
                    return reservedByText.text.toString().contains(text)
                }
            }
        }


        fun withToolbarTitle(title: CharSequence): Matcher<Any> {
            return withToolbarTitle(`is`(title))
        }

        private fun withToolbarTitle(textMatcher: Matcher<CharSequence>): Matcher<Any> {
            return object : BoundedMatcher<Any, Toolbar>(Toolbar::class.java) {
                public override fun matchesSafely(toolbar: Toolbar): Boolean {
                    return textMatcher.matches(toolbar.title)
                }

                override fun describeTo(description: Description) {
                    description.appendText("with toolbar title: ")
                    textMatcher.describeTo(description)
                }
            }
        }

        fun withRecyclerView(recyclerViewId: Int): RecyclerViewMatcher {
            return RecyclerViewMatcher(recyclerViewId)
        }
    }

    fun withResourceName(resourceName: String): Matcher<View> {
        return withResourceName(`is`(resourceName))
    }

    fun withResourceName(resourceNameMatcher: Matcher<String>): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("with resource name: ")
                resourceNameMatcher.describeTo(description)
            }

            override fun matchesSafely(view: View): Boolean {
                val id = view.id
                return (id != View.NO_ID && id != 0 && view.resources != null
                        && resourceNameMatcher.matches(view.resources.getResourceName(id)))
            }
        }
    }


}