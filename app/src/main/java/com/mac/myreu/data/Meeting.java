package com.mac.myreu.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

public class Meeting {
    private static long id;
    @NonNull
    private final String name;
    @NonNull
    private final String roomColor;
    @NonNull
    private final String hours;
    @NonNull
    private final String roomName;
    @NonNull
    private final String mails;


    public Meeting(
            long id,
            @NonNull String name,
            @NonNull String roomColor,
            @NonNull String hours,
            @NonNull String roomName,
            @NonNull String mails
    ) {
        Meeting.id = id;
        this.name = name;
        this.hours = hours;
        this.roomColor= roomColor;
        this.roomName= roomName;
        this.mails = mails;
    }

    public long getId() {
        return id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getHours() {
        return hours;
    }

    @NonNull
    public String getRoomColor() {
        return roomColor;
    }

    @NonNull
    public String getRoomName() {
        return roomName;
    }

    @NonNull
    public String getMails() {
        return mails;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meeting meeting = (Meeting) o;
        return name.equals(meeting.name) && Objects.equals(hours, meeting.hours) && Objects.equals(roomName, meeting.roomName) && Objects.equals(roomColor, meeting.roomColor) && Objects.equals(mails, meeting.mails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, hours, roomName, roomColor, mails);
    }

    @NonNull
    @Override
    public String toString() {
        return "Meeting {" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", hours='" + hours + '\'' +
                ", roomName='" + roomName + '\'' +
                ", roomColor='" + roomColor + '\'' +
                ", mails='" + mails+ '\'';
    }
}
