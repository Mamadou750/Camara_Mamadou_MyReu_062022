package com.mac.myreu.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

public class Meeting {
    private final long id;
    @NonNull
    private final String name;
    @NonNull
    private final String roomColor;
    @Nullable
    private final String hours;
    @Nullable
    private final String roomName;
    @Nullable
    private final String mails;


    public Meeting(
            long id,
            @NonNull String name,
            @NonNull String roomColor,
            @Nullable String hours,
            @Nullable String roomName,
            @Nullable String mails
    ) {
        this.id = id;
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

    @Nullable
    public String getHours() {
        return hours;
    }

    @NonNull
    public String getRoomColor() {
        return roomColor;
    }

    @Nullable
    public String getRoomName() {
        return roomName;
    }

    @Nullable
    public String getMails() {
        return mails;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meeting meeting = (Meeting) o;
        return id == Meeting.id && name.equals(meeting.name) && Objects.equals(hours, meeting.hours) && Objects.equals(roomName, meeting.roomName) && Objects.equals(roomColor, meeting.roomColor) && Objects.equals(mails ,meeting.mails);
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
