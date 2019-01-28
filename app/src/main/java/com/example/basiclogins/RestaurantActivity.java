package com.example.basiclogins;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;

public class RestaurantActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextCuisine;
    private EditText editTextAddress;
    private EditText editTextWebsite;
    private RatingBar ratingBarRating;
    private SeekBar seekBarPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        wireWidgets();
    }

    private void wireWidgets() {
        editTextName = findViewById(R.id.editText_restaurant_name);
        editTextCuisine = findViewById(R.id.editText_restaurant_cuisine);
        editTextAddress = findViewById(R.id.editText_restaurant_address);
        ratingBarRating = findViewById(R.id.ratingBar_restaurant_rating);
        seekBarPrice = findViewById(R.id.seekBar_restaurant_price);
    }
}
