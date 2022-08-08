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

    public void onDeleteMeetingClicked(long meetingId) {
        meetingRepository.deleteMeeting(meetingId);
    }
}
