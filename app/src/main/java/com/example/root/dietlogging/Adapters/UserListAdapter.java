package com.example.root.dietlogging.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.root.dietlogging.Entities.User;
import com.example.root.dietlogging.R;

import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserViewHolder> {

    class UserViewHolder extends RecyclerView.ViewHolder {
        private final TextView userNameItemView;
        private final TextView participantNumberItemView;
        private final TextView dietChoiceItemView;

        private UserViewHolder(View itemView) {
            super(itemView);
            userNameItemView = itemView.findViewById(R.id.user_name_tv);
            participantNumberItemView = itemView.findViewById(R.id.participant_number_tv);
            dietChoiceItemView = itemView.findViewById(R.id.diet_choice_tv);

        }
    }

    private final LayoutInflater mInflater;
    private List<User> mUser; // Cached copy of user

    public UserListAdapter(Context context) { mInflater = LayoutInflater.from(context); }


    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        if (mUser != null) {
            User current = mUser.get(position);
            holder.participantNumberItemView.setText(String.valueOf(current.getParticipant_number()));
            holder.userNameItemView.setText(current.getFull_name());
            holder.dietChoiceItemView.setText(String.valueOf(current.getDiet_choice()));
        } else {
            // Covers the case of data not being ready yet.
            holder.userNameItemView.setText("No User");
        }
    }

    public void setUser(List<User> users){
        mUser = users;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mUser != null)
            return mUser.size();
        else return 0;
    }
}


