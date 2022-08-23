package com.mac.myreu.ui.list;

import androidx.annotation.NonNull;

import androidx.lifecycle.LiveData;

import androidx.lifecycle.ViewModel;

import com.mac.myreu.data.Meeting;
import com.mac.myreu.data.MeetingRepository;


import java.util.List;

public class MeetingViewModel extends ViewModel {

    // Injected thanks to ViewModelFactory
    @NonNull
    private final MeetingRepository meetingRepository;

    public MeetingViewModel(@NonNull MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }


    public LiveData<List<Meeting>> getMeetingLiveData() {
        return meetingRepository.getMeetingsLiveData();
    }

    public void onDeleteMeetingClicked(String meetingName, String meetingHours) {
        meetingRepository.deleteMeeting(meetingName, meetingHours);
    }

    public void filterByDate(String date) {
        meetingRepository.filterByDate(date);
    }

    public void filterByPlace(String place) {
        meetingRepository.filterByPlace(place);
    }
    public void cleanFilter(){
        meetingRepository.cleanFilter();
    }
}
