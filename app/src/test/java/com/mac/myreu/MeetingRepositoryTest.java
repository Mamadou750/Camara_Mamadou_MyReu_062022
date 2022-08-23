package com.mac.myreu;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.mac.myreu.data.Meeting;
import com.mac.myreu.data.MeetingRepository;
import com.mac.myreu.data.Room;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(MockitoJUnitRunner.class)
public class MeetingRepositoryTest {
    private static  Room room = new Room("salle1", "red");


    private static final String DEFAULT_NAME = "DEFAULT_NAME";
    private static final String DEFAULT_DATE = "DEFAULT_DATE";
    private static final String DEFAULT_HOURS= "DEFAULT_HOURS";
    private static final String DEFAULT_MAILS = "DEFAULT_MAILS";

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private MeetingRepository meetingRepository;

    @Before
    public void setUp() {

        meetingRepository = new MeetingRepository();

    }

    @Test
    public void nominal_case_list_is_empty_for_prod() {
        // When
        LiveDataTestUtils.observeForTesting(meetingRepository.getMeetingsLiveData(), value -> {
            // Then
            assertEquals(4, value.size());
        });
    }

    @Test
    public void add_meeting_should_change_list() {
        // When
        addDefaultMeeting();
        LiveDataTestUtils.observeForTesting(meetingRepository.getMeetingsLiveData(), value -> {
            // Then
            assertEquals(getDefaultMeetingList(5), value);
        });
    }

    @Test
    public void add_meeting_twice_should_change_list() {
        // Given
        addDefaultMeeting();

        // When
        addDefaultMeeting();
        LiveDataTestUtils.observeForTesting(meetingRepository.getMeetingsLiveData(), value -> {
            // Then
            assertEquals(getDefaultMeetingList(6), value);
        });
    }

    @Test
    public void delete_meeting() {

        MeetingRepository meetingRepositoryLocal = new MeetingRepository();

        // When
        meetingRepositoryLocal.deleteMeeting("room 1", "15h");

        LiveDataTestUtils.observeForTesting(meetingRepositoryLocal.getMeetingsLiveData(), value -> {
            // Then
            assertEquals(3, value.size());
        });
    }


    @Test
    public void filterByPlaceMeeting() {

        addDefaultMeeting();
        String placeFilter = "Salle 1";//ROOM_LIST id = 0.
        meetingRepository.filterByPlace(placeFilter);
        LiveDataTestUtils.observeForTesting(meetingRepository.getMeetingsLiveData(), value -> {
            // Then
            for (Meeting m : Objects.requireNonNull(meetingRepository.getMeetingsLiveData().getValue())) {
                assertEquals(m.getRoom().toString(), placeFilter);
            }
        });
    }

    /**
     * Filter the list by Date, and return array of meetings.
     * then check if size list is equals and the meeting have correct date.
     */
    @Test
    public void filterByDateMeeting() {
        String dateFilter = "10/07/2021";
        meetingRepository.filterByDate(dateFilter);
        LiveDataTestUtils.observeForTesting(meetingRepository.getMeetingsLiveData(), value -> {
            // Then
            for (Meeting m : Objects.requireNonNull(meetingRepository.getMeetingsLiveData().getValue())) {
                assertEquals(m.getDateFormated(), dateFilter);
            }
        });
    }










    // region DEFAULTS
    private void addDefaultMeeting() {
        meetingRepository.addMeeting(DEFAULT_NAME, room, DEFAULT_DATE, DEFAULT_HOURS, DEFAULT_MAILS);
    }

    private List<Meeting> getDefaultMeetingList(int count) {
        List<Meeting> meetings = meetingRepository.getMeetingsLiveData().getValue();

        for (int i = 0; i < count; i++) {
            assert meetings != null;
            meetings.add(getDefaultMeeting(i));
        }

        return meetings;
    }



    private Meeting getDefaultMeeting(int index) {
        return new Meeting(
                index,
                DEFAULT_NAME,
                room,
                DEFAULT_DATE,
                DEFAULT_HOURS,
                DEFAULT_MAILS

        );
    }
}