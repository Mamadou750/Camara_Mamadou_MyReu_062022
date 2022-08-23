package com.mac.myreu.ui;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.mac.myreu.R;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import static com.mac.myreu.utils.RecyclerViewItemCountAssertion.withItemCount;

import com.mac.myreu.data.Meeting;
import com.mac.myreu.data.MeetingRepository;
import com.mac.myreu.ui.util.MainActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class FilterByDateInstrumentedTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);




    @Test
    public void filterByDateInstrumentedTest() {
        //check if the meeting is add in recycler view
        onView(ViewMatchers.withId(R.id.list_meet)).check(withItemCount(4));

        //click on button to add meeting
        onView(ViewMatchers.withId(R.id.meeting_add_fab)).perform(click());

        //add text in the editText for the meeting name
        onView(withId(R.id.sujet_reu)).perform(scrollTo(), replaceText("Test"), closeSoftKeyboard());

        //select the date of meeting in dialog
        onView(withId(R.id.date_selector_btn)).perform(click());

        //validate the date selected
        onView(withId(android.R.id.button1)).perform(scrollTo(), click());

        //select time of meeting
        onView(withId(R.id.time_selector_btn)).perform(click());

        //validate the time
        onView(withId(android.R.id.button1)).perform(scrollTo(), click());

        //create the meeting on click on create button
        onView(withId(R.id.add_save)).perform(scrollTo(), click());

        onView(withId(R.id.menu_activity_item_sort_by)).perform(click());
        onView(withText("Filtrer par date")).perform(click());
        onView(allOf(withId(android.R.id.button1))).perform(click());
        onView(ViewMatchers.withId(R.id.list_meet)).check(withItemCount(1));


    }
}
