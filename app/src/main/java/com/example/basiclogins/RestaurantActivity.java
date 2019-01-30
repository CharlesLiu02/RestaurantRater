package com.example.basiclogins;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class RestaurantActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextCuisine;
    private EditText editTextAddress;
    private EditText editTextWebsite;
    private RatingBar ratingBarRating;
    private SeekBar seekBarPrice;
    private Button buttonCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        wireWidgets();
        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewRestaurant();
            }
        });
    }

    private void createNewRestaurant() {
        if(allFieldsVerified()) {
            Restaurant restaurant = new Restaurant();
            String name = editTextName.getText().toString();
            String cuisine = editTextCuisine.getText().toString();
            String address = editTextAddress.getText().toString();
            String website = editTextWebsite.getText().toString();
            int price = seekBarPrice.getProgress() + 1;
            float rating = ratingBarRating.getRating();
            restaurant.setName(name);
            restaurant.setCuisine(cuisine);
            restaurant.setAddress(address);
            restaurant.setWebsitelink(website);
            restaurant.setPrice(price);
            restaurant.setRating(rating);
            Backendless.Persistence.save(restaurant, new AsyncCallback<Restaurant>() {
                public void handleResponse(Restaurant restaurant) {
                    // new restaurant instance has been saved
                    finish();
                }

                public void handleFault(BackendlessFault fault) {
                    Toast.makeText(RestaurantActivity.this, fault.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean allFieldsVerified() {
        if(!editTextAddress.getText().toString().equals("") && !editTextName.getText().toString().equals(null)
                && !editTextCuisine.getText().toString().equals(null) && ratingBarRating.getRating() != 0){
            return true;
        }
        return false;
    }

    private void wireWidgets() {
        editTextName = findViewById(R.id.editText_restaurant_name);
        editTextCuisine = findViewById(R.id.editText_restaurant_cuisine);
        editTextAddress = findViewById(R.id.editText_restaurant_address);
        editTextWebsite = findViewById(R.id.editText_restaurant_website);
        ratingBarRating = findViewById(R.id.ratingBar_restaurant_rating);
        seekBarPrice = findViewById(R.id.seekBar_restaurant_price);
        buttonCreate = findViewById(R.id.button_restaurant_create);
    }
}
