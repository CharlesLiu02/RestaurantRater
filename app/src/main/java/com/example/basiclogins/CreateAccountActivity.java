package com.example.basiclogins;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class CreateAccountActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;
    private EditText editTextEmail;
    private Button buttonCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        wireWidgets();

        Intent intent = getIntent();
        String message = intent.getStringExtra(LoginActivity.EXTRA_MESSAGE);
        editTextUsername.setText(message);

        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerToBackendless();
            }
        });


    }

    private void registerToBackendless() {
        String name = editTextName.getText().toString();
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        String confirmPassword = editTextConfirmPassword.getText().toString();
        String username = editTextUsername.getText().toString();
        if (allFieldsValid(username, confirmPassword, password)) {
            BackendlessUser user = new BackendlessUser();
            user.setProperty("username", username);
            user.setEmail(email);
            user.setProperty("name", name);
            user.setPassword(password);
            Backendless.UserService.register(user, new AsyncCallback<BackendlessUser>() {
                @Override
                public void handleResponse(BackendlessUser response) {
                    Toast.makeText(CreateAccountActivity.this, response.getEmail() + " registered", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    Toast.makeText(CreateAccountActivity.this, fault.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            sendInfo();
        } else {
            Toast.makeText(this, "Your confirm password must match with your password or you must include a username", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean allFieldsValid(String username, String confirmPassword, String password) {
        if (confirmPassword.equals(password) && username.length() > 0) {
            return true;
        }
        return false;
    }

    private void sendInfo() {
        Intent intent = new Intent();
        //put data into intent with key value
        intent.putExtra("username", editTextUsername.getText().toString());
        intent.putExtra("password", editTextPassword.getText().toString());
        //set result so that activity is able to access intent
        setResult(RESULT_OK, intent);
        finish();
    }


    private void wireWidgets() {
        editTextName = findViewById(R.id.editText_create_name);
        editTextUsername = findViewById(R.id.editText_create_username);
        editTextPassword = findViewById(R.id.editText_create_password);
        editTextConfirmPassword = findViewById(R.id.editText_create_confirmPassword);
        editTextEmail = findViewById(R.id.editText_create_email);
        buttonCreate = findViewById(R.id.button_create_create);
    }
}
