package com.mac.myreu.ui;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.matcher.ViewMatchers.withId;


import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.test.espresso.base.MainThread;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.*;

import com.mac.myreu.R;
import static com.mac.myreu.utils.RecyclerViewItemCountAssertion.withItemCount;

import com.mac.myreu.data.Meeting;
import com.mac.myreu.data.MeetingRepository;
import com.mac.myreu.ui.list.MeetingViewModel;
import com.mac.myreu.ui.util.MainActivity;

import java.util.List;

import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.GlobalScope;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class AddMeetInstrumentedTest {



    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);



    @Before
    public void setup() {

    }

    @Test
    public void addMeetingInstrumentedTest() {

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

        //check if the meeting is add in recycler view
        onView(ViewMatchers.withId(R.id.list_meet)).check(withItemCount(5));
    }
}