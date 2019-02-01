package com.example.basiclogins;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;

import java.util.List;

public class RestaurantListActivity extends AppCompatActivity {
    private ListView listViewRestaurants;
    private FloatingActionButton fab;
    public static final String EXTRA_RESTAURANT = "restaurant";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);

        wireWidgets();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RestaurantListActivity.this, RestaurantActivity.class);
                startActivity(intent);
            }
        });
        registerForContextMenu(listViewRestaurants);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_restaurant_list, menu);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_restaurant_list_delete:

                break;
        }
        return true;
    }

    private void populateListview() {
        //refactor to only get the items that belong to the user
        //get the current user's objectId (use Backendless.UserService)
        //make a dataquery and use the advanced object retrieval pattern
        //to find all restaurants whose ownerId matches the user's objectId
        //sample WHERE clause with a string: name = 'Joe'
        String ownerId = Backendless.UserService.CurrentUser().getObjectId();
        String whereClause = "ownerId = '" + ownerId + "'";
        DataQueryBuilder queryBuilder = DataQueryBuilder.create();
        queryBuilder.setWhereClause(whereClause);
        Backendless.Data.of(Restaurant.class).find(queryBuilder, new AsyncCallback<List<Restaurant>>(){
            @Override
            public void handleResponse(final List<Restaurant> restaurantList )
            {
                // all restaurant instances have been found
                RestaurantAdapter adapter = new RestaurantAdapter(RestaurantListActivity.this, R.layout.item_restaurantlist, restaurantList);
                listViewRestaurants.setAdapter(adapter);
                //set the onItemClickListener to open the Restaurant Activity
                //take the clicked object and include it in the intent
                //in the RestaurantActivity's onCreate, check if there is a Parceable extra
                //if there is, then get te Restaurant object and populate the fields
                listViewRestaurants.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent restaurantDetailIntent = new Intent(RestaurantListActivity.this, RestaurantActivity.class);
                        restaurantDetailIntent.putExtra(EXTRA_RESTAURANT, restaurantList.get(position));
                        startActivity(restaurantDetailIntent);
                    }
                });
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
        fab = findViewById(R.id.fab_restaurantlist_new);
    }

    @Override
    protected void onStart() {
        super.onStart();
        populateListview();
    }
}
