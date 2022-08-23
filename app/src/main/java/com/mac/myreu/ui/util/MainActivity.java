package com.mac.myreu.ui.util;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mac.myreu.R;
import com.mac.myreu.data.Room;
import com.mac.myreu.ui.add.AddMeetingActivity;
import com.mac.myreu.ui.list.MeetingAdapter;
import com.mac.myreu.ui.list.MeetingViewModel;
import com.mac.myreu.ui.list.onMeetingClickListener;
import com.mac.myreu.utulities.DialogDatePickerFragment;
import com.mac.myreu.utulities.DialogPlaceSpinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements DialogDatePickerFragment.DialogDatePickerListener,
        DialogPlaceSpinner.DialogPlaceSpinnerListener{
    private RecyclerView recyclerView;
    private MeetingAdapter adapter;
    private MeetingViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar =  findViewById(R.id.my_toolbar);
        myToolbar.setTitle(R.string.app_name);
        setSupportActionBar(myToolbar);
        viewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(MeetingViewModel.class);
        recyclerView = findViewById(R.id.list_meet);

        viewModel.getMeetingLiveData().observe(this, list ->{
            adapter = new MeetingAdapter(new onMeetingClickListener() {
                @Override
                public void onDeleteMeetingClicked(String meetingName, String meetingHours) {
                    viewModel.onDeleteMeetingClicked(meetingName, meetingHours);
                }
            });  ;
            recyclerView.setAdapter(adapter);

            adapter.submitList(list);
        } );

        FloatingActionButton fab = findViewById(R.id.meeting_add_fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
               navigateToAddActivity(v);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main_activity,menu);
        return true;
    }

    private void navigateToAddActivity(View view) {
        Intent intent = new Intent(MainActivity.this, AddMeetingActivity.class);
        startActivity(intent);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.filter_by_date_item:
                showDatePikerDialog();
                return true;
            case R.id.filter_by_place_item:
                showSpinnerPlaceDialog();
                return true;
            case R.id.remove_filter:
                removeFiltre();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void filterListByDate(String dateString) {
        viewModel.filterByDate(dateString);
    }

    private void filterListByPlace(String place) {
        viewModel.filterByPlace(place);
    }

    private void removeFiltre (){
        viewModel.cleanFilter();
    }

    private void showDatePikerDialog() {
        DialogDatePickerFragment dialog = new DialogDatePickerFragment();
        dialog.show(getSupportFragmentManager(), "DialogDatePickerFragment");
    }

    private void showSpinnerPlaceDialog() {
        DialogPlaceSpinner dialog = new DialogPlaceSpinner();
        dialog.show(getSupportFragmentManager(), "DialogDatePickerFragment");
    }


    @Override
    public void onDialogDatePikerValidateClick(DialogDatePickerFragment dialogDatePickerFragment) {
        DatePicker datePicker = (DatePicker) dialogDatePickerFragment.getDialog().findViewById(R.id.date_dp);
        int mDay = datePicker.getDayOfMonth();
        int mMonth = datePicker.getMonth();
        int mYear = datePicker.getYear();
        Calendar calendar = Calendar.getInstance();
        calendar.set(mYear, mMonth, mDay);
        Date date = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = sdf.format(date);
        filterListByDate(dateString);
    }


    @Override
    public void onDialogPlaceSpinnerValidateClick(DialogFragment dialog) {
        Spinner spinner = (Spinner) dialog.getDialog().findViewById(R.id.dialog_room_spinner_sp);
        Room room = (Room) spinner.getSelectedItem();
        filterListByPlace(room.toString());
    }
}