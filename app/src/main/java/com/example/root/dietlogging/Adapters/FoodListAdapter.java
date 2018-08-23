package com.example.root.dietlogging.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.root.dietlogging.Entities.Food;
import com.example.root.dietlogging.R;

import java.util.ArrayList;
import java.util.List;

public class FoodListAdapter extends ArrayAdapter<Food> {

    private Context mContext;
    private List<Food> foodList;

    public FoodListAdapter(Context context, ArrayList<Food> list) {
        super(context, 0 , list);

        mContext = context;
        foodList = list;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.search_results,parent,false);

        Food currentFood = foodList.get(position);

        TextView foodName = (TextView) listItem.findViewById(R.id.food_name);
        foodName.setText(currentFood.getFoodName());

        return listItem;
    }


}
