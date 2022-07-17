package com.mac.myreu.ui.list;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mac.myreu.R;

import com.mac.myreu.data.Meeting;

public class MeetingAdapter extends ListAdapter <Meeting, MeetingAdapter.ViewHolder> {

    private final onMeetingClickListener listener;

    public MeetingAdapter(onMeetingClickListener listener) {
        super(DIFF_CALLBACK);
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.meeting_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.bind(getItem(position), listener );
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView colorImageView;
        private final TextView nameTextView;
        private final TextView mailTextView;
        private final ImageView deleteImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            colorImageView = itemView.findViewById(R.id.list_color);
            nameTextView = itemView.findViewById(R.id.name);
            mailTextView = itemView.findViewById(R.id.list_mail);
            deleteImageView = itemView.findViewById(R.id.item_list_delete_button);
        }

        public void bind(Meeting item, onMeetingClickListener listener) {

            Glide.with(colorImageView)
                    .load(item.getRoomColor())
                    .apply(RequestOptions.circleCropTransform())
                    .into(colorImageView);
            nameTextView.setText(item.getName() +" " + item.getHours() +" " + item.getRoomName());
            mailTextView.setText(item.getMails());
            deleteImageView.setOnClickListener(v -> listener.onDeleteMeetingClicked(item.getId()));

        }
    }

    public static final DiffUtil.ItemCallback<Meeting> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Meeting>() {
                @Override
                public boolean areItemsTheSame(
                        @NonNull Meeting oldMeeting, @NonNull Meeting newMeeting) {
                    // User properties may have changed if reloaded from the DB, but ID is fixed
                    return oldMeeting.getId() == newMeeting.getId();
                }
                @Override
                public boolean areContentsTheSame(
                        @NonNull Meeting oldMeeting, @NonNull Meeting newMeeting) {
                    // NOTE: if you use equals, your object must properly override Object#equals()
                    // Incorrectly returning false here will result in too many animations.
                    return oldMeeting.equals(newMeeting);
                }
            };

}
