package com.example.basiclogins;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import java.util.List;

public class RestaurantListActivity extends AppCompatActivity {
    private ListView listViewRestaurants;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);

        wireWidgets();
        populateListview();
    }

    private void populateListview() {
        Backendless.Data.of(Restaurant.class).find(new AsyncCallback<List<Restaurant>>(){
            @Override
            public void handleResponse( List<Restaurant> restaurantList )
            {
                // all restaurant instances have been found
                ArrayAdapter<Restaurant> adapter = new ArrayAdapter<>(
                        RestaurantListActivity.this, android.R.layout.simple_list_item_1, restaurantList
                );
                listViewRestaurants.setAdapter(adapter);
            }
            @Override
            public void handleFault( BackendlessFault fault )
            {
                // an error has occurred, the error code can be retrieved with fault.getCode()
                Toast.makeText(RestaurantListActivity.this, fault.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void wireWidgets() {
        listViewRestaurants = findViewById(R.id.listview_restaurantlistview);
    }
}
