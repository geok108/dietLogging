package com.example.root.dietlogging;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class FoodListAdapter extends ArrayAdapter<Food> {

    private Context mContext;
    private List<Food> foodList;

    public FoodListAdapter(Context context, ArrayList<Food> list) {
        super(context, 0 , list);

        mContext = context;
        foodList = list;
        Log.d("constructor: ", String.valueOf(foodList));

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.search_results,parent,false);

        Food currentFood = foodList.get(position);
        Log.d("pos: ", String.valueOf(foodList.get(0).getFoodName()));

        TextView foodName = (TextView) listItem.findViewById(R.id.food_name);
        foodName.setText(currentFood.getFoodName());

        return listItem;
    }


}
