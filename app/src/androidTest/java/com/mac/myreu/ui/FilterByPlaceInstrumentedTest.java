package com.mac.myreu.ui;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;

import com.mac.myreu.R;
import com.mac.myreu.data.MeetingRepository;
import com.mac.myreu.ui.util.MainActivity;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;
import static com.mac.myreu.utils.RecyclerViewItemCountAssertion.withItemCount;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class FilterByPlaceInstrumentedTest {
    public MeetingRepository mMeetingRepository;
    public int ITEM_COUNT;


    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);



    @Test
    public void filterByPlaceInstrumentedTest() {

        onView(withId(R.id.menu_activity_item_sort_by)).perform(click());
        onView(withText("Filtrer par lieu")).perform(click());
        onView(allOf(withId(android.R.id.button1))).perform(click());
        onView(ViewMatchers.withId(R.id.list_meet)).check(withItemCount(1));

    }
}
