package com.mac.myreu.data;

import androidx.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Meeting {
    private static int id;
    @NonNull
    private final String name;
    @NonNull
    private Room room;
    @NonNull
    private final String date;
    @NonNull
    private final String hours;
    @NonNull
    private final String mails;


    public Meeting(
            int id,
            @NonNull String name,
            @NonNull Room room,
            @NonNull String date,
            @NonNull String hours,
            @NonNull String mails
    ) {
        Meeting.id = id;
        this.name = name;
        this.room = room;
        this.date = date;
        this.hours = hours;
        this.mails = mails;
    }

    public int getId() {
        return id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public Room getRoom() {
        return room;
    }

    @NonNull
    public Date getDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy");
        try {
            return formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @NonNull
    public String getHours() {
        return hours;
    }


    @NonNull
    public String getMails() {
        return mails;
    }

    public String getDateFormated() {
        //SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        //String dateString = sdf.format(date);
        return date;
    }

    public StringBuilder getTimeFormated() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String timeString = sdf.format(this.getDate());
        StringBuilder time = new StringBuilder(timeString);
        time.setCharAt(2, 'H');
        return time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meeting meeting = (Meeting) o;
        return name.equals(meeting.name) && Objects.equals(hours, meeting.hours)  && Objects.equals(mails, meeting.mails) && Objects.equals(room, meeting.room);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, hours, mails);
    }

    @NonNull
    @Override
    public String toString() {
        return "Meeting {" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", room='" + room+ '\'' +
                ", date='" + date + '\'' +
                ", hours='" + hours + '\'' +
                ", mails='" + mails+ '\'';
    }
}
