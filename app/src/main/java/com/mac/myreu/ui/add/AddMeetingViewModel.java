package com.mac.myreu.ui.add;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mac.myreu.data.Room;
import com.mac.myreu.ui.util.SingleLiveEvent;


import com.mac.myreu.data.MeetingRepository;

import java.util.List;

public class AddMeetingViewModel extends ViewModel {
    // Injected thanks to ViewModelFactory
    @NonNull
    private final MeetingRepository meetingRepository;

    // Default value is false : button should not be enabled at start
    private final MutableLiveData<Boolean> isSaveButtonEnabledMutableLiveData = new MutableLiveData<>(false);

    // Default value is a generated random profile image
    private final String roomColor = generateRoomColor();
    private final MutableLiveData<String> roomColorMutableLiveData = new MutableLiveData<>(roomColor);

    // Check https://medium.com/androiddevelopers/livedata-with-snackbar-navigation-and-other-events-the-singleliveevent-case-ac2622673150
    private final SingleLiveEvent<Void> closeActivitySingleLiveEvent = new SingleLiveEvent<>();

    public AddMeetingViewModel(@NonNull MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    // Returns a LiveData, not a MutableLiveData. This is an extra security (ask about "immutability" your mentor)
    public LiveData<Boolean> getIsSaveButtonEnabledLiveData() {
        return isSaveButtonEnabledMutableLiveData;
    }

    public LiveData<String> getRoomColorLiveData() {
        return roomColorMutableLiveData;
    }

    public SingleLiveEvent<Void> getCloseActivitySingleLiveEvent() {
        return closeActivitySingleLiveEvent;
    }

    public void onNameChanged(String name) {
        isSaveButtonEnabledMutableLiveData.setValue(!name.isEmpty());
    }

    public void onAddButtonClicked(
            @NonNull String name,
            @NonNull Room room,
            @Nullable String date,
            @Nullable String hours,
            @Nullable String mails
    ) {
        // Add neighbour to the repository...
        meetingRepository.addMeeting(name,room,date,hours, mails);
        // ... and close the Activity !
        closeActivitySingleLiveEvent.call();
    }

    @NonNull
    private String generateRoomColor() {
        return "https://i.pravatar.cc/150?u=" + System.currentTimeMillis();
    }
}
