package com.example.basiclogins;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class LoginActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "the message";
    public static final int REQUEST_CREATE_ACCOUNT = 1;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button buttonLogin;
    private TextView textViewCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        wireWidgets();

        //initialize Backendless connection
        Backendless.initApp(this, Credentials.APP_ID, Credentials.API_KEY);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginToBackendless();
            }
        });

        textViewCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity();

            }
        });
    }

    private void loginToBackendless() {
        String login = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();
        Backendless.UserService.login(login, password, new AsyncCallback<BackendlessUser>() {
            @Override
            public void handleResponse(BackendlessUser response) {
                //start the new activity here because this method is
                //called when login is complete and successful
                Intent intent = new Intent(LoginActivity.this, RestaurantListActivity.class);
                startActivity(intent);
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(LoginActivity.this, fault.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void wireWidgets() {
        editTextUsername = findViewById(R.id.editText_login_username);
        editTextPassword = findViewById(R.id.editText_login_password);
        buttonLogin = findViewById(R.id.button_login_login);
        textViewCreate = findViewById(R.id.textView_login_create);
    }

    private void startActivity() {
        Intent intent = new Intent(this, CreateAccountActivity.class);
        String message = editTextUsername.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        //send intent over with key value pair
        startActivityForResult(intent, REQUEST_CREATE_ACCOUNT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        //check which request it is that we're responding to
        if(requestCode == REQUEST_CREATE_ACCOUNT){
            // make sure we want to access the intent
            if(resultCode == RESULT_OK){
                String username = data.getStringExtra("username");
                String password = data.getStringExtra("password");
                editTextUsername.setText(username);
                editTextPassword.setText(password);
            }
        }
    }
}
