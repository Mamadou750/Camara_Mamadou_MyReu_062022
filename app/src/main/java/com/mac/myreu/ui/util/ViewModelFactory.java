package com.mac.myreu.ui.util;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.mac.myreu.data.MeetingRepository;
import com.mac.myreu.ui.add.AddMeetingViewModel;
import com.mac.myreu.ui.list.MeetingViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {

        private static ViewModelFactory factory;

        public static ViewModelFactory getInstance() {
            if (factory == null) {
                synchronized (ViewModelFactory.class) {
                    if (factory == null) {
                        factory = new ViewModelFactory(
                                new MeetingRepository(

                                )
                        );
                    }
                }
            }

            return factory;
        }

        // This field inherit the singleton property from the ViewModelFactory : it is scoped to the ViewModelFactory
        // Ask your mentor about DI scopes (Singleton, ViewModelScope, ActivityScope)
        @NonNull
        private final MeetingRepository meetingRepository;

        private ViewModelFactory(@NonNull MeetingRepository meetingRepository) {
            this.meetingRepository = meetingRepository;
        }

        @SuppressWarnings("unchecked")
        @NonNull
        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            if (modelClass.isAssignableFrom(MeetingViewModel.class)) {
                return (T) new MeetingViewModel(
                        meetingRepository
                );
            } else if (modelClass.isAssignableFrom(AddMeetingViewModel.class)) {
                return (T) new AddMeetingViewModel(
                        meetingRepository
                );
            }
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }

