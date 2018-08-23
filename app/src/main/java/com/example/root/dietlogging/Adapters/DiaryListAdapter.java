package com.example.root.dietlogging.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.root.dietlogging.Entities.Diary;
import com.example.root.dietlogging.R;

import java.util.List;

public class DiaryListAdapter extends RecyclerView.Adapter<DiaryListAdapter.DiaryViewHolder> {

    class DiaryViewHolder extends RecyclerView.ViewHolder {
        private final TextView timeItemView;
        private final TextView foodNameItemView;
        private final TextView gramsItemView;

        private DiaryViewHolder(View itemView) {
            super(itemView);
            timeItemView = itemView.findViewById(R.id.time);
            foodNameItemView = itemView.findViewById(R.id.f_name);
            gramsItemView = itemView.findViewById(R.id.f_grams);

        }

    }


    private final LayoutInflater mInflater;
    private List<Diary> mDiary; // Cached copy of diaries




    DiaryListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }


    @Override
    public DiaryListAdapter.DiaryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_diary, parent, false);

        return new DiaryListAdapter.DiaryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DiaryListAdapter.DiaryViewHolder holder, int position) {
        if (mDiary != null) {
            Diary current = mDiary.get(position);
            holder.timeItemView.setText(String.valueOf(current.getTime()));
            holder.foodNameItemView.setText(current.getFoodName());
            holder.gramsItemView.setText(String.valueOf(current.getGrams()));
        } else {
            // Covers the case of data not being ready yet.
            holder.foodNameItemView.setText("No User");
        }
    }

    void setDiary(List<Diary> diaries){
        mDiary = diaries;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mDiary != null)
            return mDiary.size();
        else return 0;
    }
}