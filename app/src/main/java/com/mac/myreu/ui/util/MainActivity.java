package com.mac.myreu.ui.util;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mac.myreu.R;
import com.mac.myreu.ui.list.MeetingAdapter;
import com.mac.myreu.ui.list.MeetingViewModel;
import com.mac.myreu.ui.list.onMeetingClickListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar myToolbar =  findViewById(R.id.my_toolbar);
        myToolbar.setTitle(R.string.app_name);
        setSupportActionBar(myToolbar);
        MeetingViewModel viewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(MeetingViewModel.class);
        RecyclerView recyclerView = findViewById(R.id.list_meet);
        MeetingAdapter adapter = new MeetingAdapter(new onMeetingClickListener() {
            @Override
            public void onDeleteMeetingClicked(long meetingId) {
                viewModel.onDeleteMeetingClicked(meetingId);
            }
        });  ;
        recyclerView.setAdapter(adapter);

        viewModel.getMeetingLiveData().observe(this, list ->{
            adapter.submitList(list);
        } );


        FloatingActionButton fab = findViewById(R.id.meeting_add_fab);
        //fab.setOnClickListener(v -> startActivity(AddMeetingActivity.navigate(this)));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main_activity,menu);
        return true;
    }
}