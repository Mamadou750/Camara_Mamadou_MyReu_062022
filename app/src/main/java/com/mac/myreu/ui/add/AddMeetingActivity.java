package com.mac.myreu.ui.add;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.textfield.TextInputEditText;
import com.mac.myreu.R;
import com.mac.myreu.data.Meeting;
import com.mac.myreu.data.MeetingRepository;
import com.mac.myreu.data.Room;
import com.mac.myreu.ui.util.ViewModelFactory;
import com.mac.myreu.utulities.DialogDatePickerFragment;
import com.mac.myreu.utulities.DialogTimePickerFragment;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;


public class AddMeetingActivity extends AppCompatActivity
        implements View.OnClickListener, DialogTimePickerFragment.DialogTimePickerListener,
        DialogDatePickerFragment.DialogDatePickerListener{

    private Calendar calendar = Calendar.getInstance();
    private Date mDate;
    private Room Room;
    private int mDay;
    private int mMonth;
    private int mYear;
    private int mHours;
    private int mMinutes;
    public MeetingRepository mMeetingRepository;
    private String timeFormated;
    private String dateFormated;
    private final int TAG_BUTTON_TIME = 1;
    private final int TAG_BUTTON_DATE = 4;

    TextInputEditText subjectReuEditText ;
    Button mMeetingSelectTime ;
    Button mDateSelectorBtn ;
    Spinner mMeetingRoom;
    Room selectedRoom;
    TextInputEditText mailsEditText ;
    Button addMeetingButton ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting);

        subjectReuEditText = findViewById(R.id.sujet_reu);
        mMeetingSelectTime = findViewById(R.id.time_selector_btn);
        mDateSelectorBtn = findViewById(R.id.date_selector_btn);
        mMeetingRoom = findViewById((R.id.roomSpinner_sp));
        mailsEditText = findViewById(R.id.contributor);
        addMeetingButton = findViewById(R.id.add_save);
        configureClickListener();
        configureRoomSpinner();
        AddMeetingViewModel viewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(AddMeetingViewModel.class);

        bindName(viewModel, subjectReuEditText);
        bindAddButton(viewModel, subjectReuEditText, mDateSelectorBtn, mMeetingSelectTime, mailsEditText, addMeetingButton);

        viewModel.getCloseActivitySingleLiveEvent().observe(this, aVoid -> finish());
        //add_return.setOnClickListener(v -> this.finish());
    }

    private void configureClickListener() {
        mMeetingSelectTime.setOnClickListener(this);
        mMeetingSelectTime.setTag(TAG_BUTTON_TIME);
        mDateSelectorBtn.setOnClickListener(this);
        mDateSelectorBtn.setTag(TAG_BUTTON_DATE);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void bindName(AddMeetingViewModel viewModel, TextInputEditText nameEditText) {
        nameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                viewModel.onNameChanged(s.toString());
            }
        });
    }

    private void bindAddButton(AddMeetingViewModel viewModel, TextInputEditText subjectReuEditText, Button mDateSelectorBtn , Button mMeetingSelectTime, TextInputEditText mailsEditText, Button addMeetingButton) {
        addMeetingButton.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                viewModel.onAddButtonClicked(
                        Objects.requireNonNull(subjectReuEditText.getText()).toString(),
                        selectedRoom,
                        dateFormated,
                        timeFormated,
                        Objects.requireNonNull(mailsEditText.getText()).toString());
            }
        });

        viewModel.getIsSaveButtonEnabledLiveData().observe(this, addMeetingButton::setEnabled);
    }

    @Override
    public void onClick(View v) {
        switch ((int) v.getTag()) {
            case TAG_BUTTON_TIME:
                showTimePikerDialog();
                break;
            case TAG_BUTTON_DATE:
                showDatePikerDialog();
                break;
            default:
        }
    }

    private void configureRoomSpinner() {
        //get List Room
        List<Room> rooms = MeetingRepository.getMeetingsRoomsList();
        //add values in room arrayList
        mMeetingRoom.setAdapter(new ArrayAdapter<Room>(getApplicationContext()
                , android.R.layout.simple_spinner_dropdown_item, rooms));
        mMeetingRoom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedRoom = (Room) mMeetingRoom.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private boolean checkIfRoomAvailable(Date date, Room selectedRoom) {
        Boolean available = true;
        List<Meeting> meetingList = (List<Meeting>) mMeetingRepository.getMeetingsLiveData();

        for (Meeting meeting : meetingList) {
            if (meeting.getRoom() == selectedRoom) {
                calendar.setTime(meeting.getDate());
                calendar.add(Calendar.MINUTE, -45);
                Date timeBefore = calendar.getTime();
                calendar.add(Calendar.MINUTE, 90);
                Date timeFinish = calendar.getTime();
                if (date.compareTo(timeBefore) == 1 && date.compareTo(timeFinish) == -1) {
                    available = false;
                }
            }
        }
        return available;
    }


    private void showDatePikerDialog() {
        DialogDatePickerFragment dialog = new DialogDatePickerFragment();
        dialog.show(getSupportFragmentManager(), "DialogDatePickerFragment");
    }

    private void showTimePikerDialog() {
        DialogTimePickerFragment dialog = new DialogTimePickerFragment();
        dialog.show(getSupportFragmentManager(), "DialogTimePikerFragment");
    }

    @Override
    public void onDialogTimePikerValidateClick(DialogFragment dialog) {
        TimePicker time = (TimePicker) Objects.requireNonNull(dialog.getDialog()).findViewById(R.id.time_dp);
        mHours = time.getCurrentHour();
        mMinutes = time.getCurrentMinute();
        timeFormated = String.format("%02dh%02d", mHours, mMinutes);
        mMeetingSelectTime.setText(timeFormated);
        Toast.makeText(this.getApplicationContext(), R.string.choiceConfirmed, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDialogDatePikerValidateClick(DialogDatePickerFragment dialog) {
        DatePicker datePicker = (DatePicker) Objects.requireNonNull(dialog.getDialog()).findViewById(R.id.date_dp);
        mDay = datePicker.getDayOfMonth();
        mMonth = datePicker.getMonth();
        mYear = datePicker.getYear();
        dateFormated = String.format("%02d/%02d/%d", mDay, mMonth + 1, mYear);
        mDateSelectorBtn.setText(dateFormated);
    }
}