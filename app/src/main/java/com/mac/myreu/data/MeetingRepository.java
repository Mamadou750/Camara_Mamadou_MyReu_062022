package com.mac.myreu.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class MeetingRepository {
    private final MutableLiveData<List<Meeting>> meetingsLiveData = new MutableLiveData<>(new ArrayList<>());
    private List<Meeting> defaultMeetingList = new ArrayList<>();

    private int maxId = 0;

    public MeetingRepository() {
        // generate meetings
        generateRooms();
        generateRandomMeetings();


    }

    public void addMeeting(
            @NonNull String name,
            @Nullable Room room,
            @Nullable String date,
            @Nullable String hours,
            @Nullable String mails
    ) {
        List<Meeting> meetings = meetingsLiveData.getValue();

        if (meetings == null) return;
        int currentId = maxId;
        assert room != null;
        assert date != null;
        assert hours != null;
        assert mails != null;
        meetings.add(
                new Meeting (
                        currentId,
                        name,
                        room,
                        date,
                        hours,
                        mails
                )
        );
        maxId++;
        meetingsLiveData.setValue(meetings);
        meetingsLiveData.postValue(meetings);
        defaultMeetingList = meetings;
    }

    public void deleteMeeting(String meetingName, String meetingHours) {
        List<Meeting> meetings = meetingsLiveData.getValue();

        if (meetings == null) return;

        for (Iterator<Meeting> iterator = meetings.iterator(); iterator.hasNext(); ) {
            Meeting meeting = iterator.next();

            if (meeting.getName().equals(meetingName) && meeting.getHours().equals(meetingHours)) {
                iterator.remove();
                defaultMeetingList.remove(meeting);
                break;
            }
        }

        meetingsLiveData.setValue(meetings);
    }

    public static List<Room> ROOM_LIST = Arrays.asList(
            new Room("Salle 1", "#40a8db"),
            new Room("Salle 2", "#97ebdb"),
            new Room("Salle 3", "#40a8db"),
            new Room("Salle 4", "#97ebdb"),
            new Room("Salle 5", "#40a8db"),
            new Room("Salle 6", "#97ebdb"),
            new Room("Salle 7", "#40a8db"),
            new Room("Salle 8", "#97ebdb"),
            new Room("Salle 9", "#40a8db"),
            new Room("Salle 10", "#97ebdb")
    );

    static List<Room> generateRooms() {
        return new ArrayList<>(ROOM_LIST);
    }
    public static List<Room> getMeetingsRoomsList() {
        List<Room> roomList = ROOM_LIST ;
        return roomList;
    }

    public void cleanFilter (){
        meetingsLiveData.setValue(defaultMeetingList);
    }


    public LiveData<List<Meeting>> getMeetingsLiveData() {
        return meetingsLiveData;
    }

    public void filterByDate (String date) {
        List<Meeting> meetingListByDate = new ArrayList<>();
        for (Meeting meeting : Objects.requireNonNull(meetingsLiveData.getValue())) {
            if (meeting.getDateFormated().equals(date)) {
                meetingListByDate.add(meeting);
            }
        }
        meetingsLiveData.setValue(meetingListByDate);
    }

    public void  filterByPlace (String place) {
        List<Meeting> meetingListByPlace = new ArrayList<>();
        for (Meeting meeting : Objects.requireNonNull(meetingsLiveData.getValue())) {
            if (meeting.getRoom().toString().equals(place)) {
                meetingListByPlace.add(meeting);
            }
        }
        meetingsLiveData.setValue(meetingListByPlace);
    }

    public  void generateRandomMeetings() {
        addMeeting(

                "room 1",
                ROOM_LIST.get(0),
                "10/07/2021",
                "15h",
                "maxim@lamzom.com, alex@lamzom.com, luc@lamzom.com"
        );
        addMeeting(

                "room 2",
                ROOM_LIST.get(1),
                "10/07/2021",
                "16h",
                "theo@lamzom.com, sarah@lamzom.com, dan@lamzom.com\""
        );
        addMeeting(

                "room 3",
                ROOM_LIST.get(2),
                "12/07/2021",
                "17h",
                "eric.com, julie@lamzom.com, paul@lamzom.com\""
        );
        addMeeting(

                "room 4",
                ROOM_LIST.get(3),
                "20/08/2022",
                "18h",
                "kara@lamzom.com,lara@lamzom.com,dexter@lamzom.com\""
        );

    }
}
