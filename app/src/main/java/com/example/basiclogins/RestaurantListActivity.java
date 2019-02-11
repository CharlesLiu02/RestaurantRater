package com.example.basiclogins;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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
    private RestaurantAdapter adapter;
    private Toolbar toolbar;
    public static final String EXTRA_RESTAURANT = "restaurant";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);

        wireWidgets();
        setSupportActionBar(toolbar);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RestaurantListActivity.this, RestaurantActivity.class);
                startActivity(intent);
            }
        });
        registerForContextMenu(listViewRestaurants);
    }

    private void logoutUser() {
        Backendless.UserService.logout( new AsyncCallback<Void>()
        {
            public void handleResponse( Void response )
            {
                // user has been logged out.
                Intent intent = new Intent(RestaurantListActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }

            public void handleFault( BackendlessFault fault )
            {
                // something went wrong and logout failed, to get the error code call fault.getCode()
                Toast.makeText(RestaurantListActivity.this, fault.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_restaurant_list, menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.menu_restaurant_list_delete:
                Restaurant restaurant = (Restaurant) listViewRestaurants.getItemAtPosition(info.position);
                deleteRestaurant(restaurant);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_toolbar_logout:
                logoutUser();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void deleteRestaurant(Restaurant restaurant) {
        Backendless.Persistence.of(Restaurant.class).remove(restaurant,
                new AsyncCallback<Long>() {
                    public void handleResponse(Long response) {
                        // Contact has been deleted. The response is the
                        // time in milliseconds when the object was deleted
                        adapter.notifyDataSetChanged();
                    }

                    public void handleFault(BackendlessFault fault) {
                        // an error has occurred, the error code can be
                        // retrieved with fault.getCode()
                        Toast.makeText(RestaurantListActivity.this, fault.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
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
        Backendless.Data.of(Restaurant.class).find(queryBuilder, new AsyncCallback<List<Restaurant>>() {
            @Override
            public void handleResponse(final List<Restaurant> restaurantList) {
                // all restaurant instances have been found
                adapter = new RestaurantAdapter(RestaurantListActivity.this, R.layout.item_restaurantlist, restaurantList);
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
            public void handleFault(BackendlessFault fault) {
                // an error has occurred, the error code can be retrieved with fault.getCode()
                Toast.makeText(RestaurantListActivity.this, fault.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void wireWidgets() {
        listViewRestaurants = findViewById(R.id.listview_restaurantlistview);
        fab = findViewById(R.id.fab_restaurantlist_new);
        toolbar = findViewById(R.id.toolbar);
    }

    @Override
    protected void onStart() {
        super.onStart();
        populateListview();
    }
}
