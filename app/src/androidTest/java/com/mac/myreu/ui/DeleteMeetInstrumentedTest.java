package com.mac.myreu.ui;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.mac.myreu.R;
import com.mac.myreu.data.Meeting;
import com.mac.myreu.ui.util.MainActivity;
import com.mac.myreu.utils.DeleteAction;
import com.mac.myreu.data.MeetingRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static com.mac.myreu.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(AndroidJUnit4.class)
public class DeleteMeetInstrumentedTest {




    @Rule
    public ActivityTestRule mActivityRule = new ActivityTestRule(MainActivity.class);



    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void MeetingList_shouldNotBeEmpty() {
        onView(ViewMatchers.withId(R.id.list_meet))
                .check(withItemCount(3));
    }

    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void meetingList_deleteAction_shouldRemoveItem() {
        // Given : We remove the element at position selected
        onView(ViewMatchers.withId(R.id.list_meet)).check(withItemCount(4));
        // When perform a click on a delete icon
        onView(ViewMatchers.withId(R.id.list_meet))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, new DeleteAction()));
        // Then : the number of element have one less.
        onView(ViewMatchers.withId(R.id.list_meet)).check(withItemCount(4 - 1));
    }
}
