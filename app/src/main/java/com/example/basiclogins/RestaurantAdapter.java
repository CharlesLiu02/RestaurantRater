package com.example.basiclogins;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

public class RestaurantAdapter extends ArrayAdapter<Restaurant> {
    private Context context;
    private int resource;
    private List<Restaurant> restaurantList;

    public RestaurantAdapter(@NonNull Context context, int resource, @NonNull List<Restaurant> restaurantList) {
        super(context, resource, restaurantList);
        this.context = context;
        this.resource = resource;
        this.restaurantList = restaurantList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //just like we did with fragments
        //inflate a layout, wire the widgets, insert data, return the layout
        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_restaurantlist, parent, false);
        }
        TextView textViewName = convertView.findViewById(R.id.textView_restaurantitem_name);
        RatingBar ratingBarRating = convertView.findViewById(R.id.ratingBar_restaurantlist_rating);
        TextView textViewPrice = convertView.findViewById(R.id.textView_restaurantitem_price);

        Restaurant currentRestaurant = restaurantList.get(position);
        textViewName.setText(currentRestaurant.getName());

        //get the right number of $ signs
        String price = "";
        for (int i = 0; i < currentRestaurant.getPrice(); i++) {
            price += "$";

        }
        textViewPrice.setText(price);
        ratingBarRating.setRating(currentRestaurant.getRating());
        return convertView;
    }
}
