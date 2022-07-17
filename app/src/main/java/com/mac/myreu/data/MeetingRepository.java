package com.mac.myreu.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MeetingRepository {
    private final MutableLiveData<List<Meeting>> meetingsLiveData = new MutableLiveData<>(new ArrayList<>());

    private long maxId = 0;

    public MeetingRepository() {
        // generate meetings
        generateRandomMeetings();

    }

    public void addMeeting(
            @NonNull String name,
            @NonNull String roomColor,
            @Nullable String roomName,
            @Nullable String hours,
            @Nullable String mails
    ) {
        List<Meeting> meetings = meetingsLiveData.getValue();

        if (meetings == null) return;

        meetings.add(
                new Meeting (
                        maxId++,
                        name,
                        roomColor,
                        roomName,
                        hours,
                        mails
                )
        );

        meetingsLiveData.setValue(meetings);
    }

    public void deleteMeeting(long meetingId) {
        List<Meeting> meetings = meetingsLiveData.getValue();

        if (meetings == null) return;

        for (Iterator<Meeting> iterator = meetings.iterator(); iterator.hasNext(); ) {
            Meeting meeting = iterator.next();

            if (meeting.getId() == meetingId) {
                iterator.remove();
                break;
            }
        }

        meetingsLiveData.setValue(meetings);
    }



    public LiveData<List<Meeting>> getMeetingsLiveData() {
        return meetingsLiveData;
    }

    private void generateRandomMeetings() {
        addMeeting(
                "Peach",
                "#40a8db",
                "room 1",
                "14h",
                "maxim@lamzom.com, alex@lamzom.com, luc@lamzom.com"
        );
        addMeeting(
                "Jack",
                "#40a8db",
                "room 2",
                "8h",
                "theo@lamzom.com, sarah@lamzom.com, dan@lamzom.com\""
        );
        addMeeting(
                "Carl",
                "#40a8db",
                "room 3",
                "15h",
                "eric.com, julie@lamzom.com, paul@lamzom.com\""
        );
        addMeeting(
                "Vincent",
                "#40a8db",
                "room 4",
                "18h",
                "kara@lamzom.com,lara@lamzom.com,dexter@lamzom.com\""
        );

    }
}
