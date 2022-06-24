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

    public void toggleFavoriteNeighbour(long id) {
        List<Meeting> meetings = meetingsLiveData.getValue();

        if (meetings == null) return;

        for (int i = 0; i < meetings.size(); i++) {
            Meeting meeting = meetings.get(i);
            if (meeting.getId() == id) {
                meetings.remove(i);
                meetings.add(
                        i,
                        // This is ugly to copy unchanged fields in Java but immutability is very important !
                        // Setters are a terrible practice for models outside MVC world.
                        // Ask about your mentor why immutability of models is so important.
                        new Meeting(
                                meeting.getId(),
                                meeting.getName(),
                                meeting.getHours(),
                                meeting.getRoomName(),
                                meeting.getRoomColor(),
                                meeting.getMails()
                        )
                );
                break;
            }
        }

        meetingsLiveData.setValue(meetings);
    }

    public LiveData<List<Meeting>> getMeetingsLiveData() {
        return meetingsLiveData;
    }

    public LiveData<Meeting> getNeighbourLiveData(long meetingId) {
        // We use a Transformation here so whenever the neighboursLiveData changes, the underlying lambda will be called too, and
        // the Neighbour will be re-emitted (with potentially new information like isFavorite set to true or false)

        // This Transformation transforms a List of Neighbours into a Neighbour (matched by its ID)
        return Transformations.map(meetingsLiveData, meetings -> {
            for (Meeting meeting : meetings) {
                if (meeting.getId() == meetingId) {
                    return meeting;
                }
            }

            return null;
        });
    }

    private void generateRandomMeetings() {
        addMeeting(
                "Peach",
                "rouge",
                "Salle 1",
                "14h",
                "maxim@lamzom.com, alex@lamzom.com, luc@lamzom.com"
        );
        addMeeting(
                "Jack",
                "bleu",
                "Salle 2",
                "8h",
                "theo@lamzom.com, sarah@lamzom.com, dan@lamzom.com\""
        );
        addMeeting(
                "Carl",
                "vert",
                "Salle 3",
                "15h",
                "eric.com, julie@lamzom.com, paul@lamzom.com\""
        );
        addMeeting(
                "Vincent",
                "gris",
                "Salle 4",
                "18h",
                "kara@lamzom.com, lara@lamzom.com, dexter@lamzom.com\""
        );

    }
}
