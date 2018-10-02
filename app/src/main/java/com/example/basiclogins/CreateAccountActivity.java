package com.example.basiclogins;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
                if (editTextName.getText().toString().equals("") || editTextName.getText().toString().equals("") || editTextName.getText().toString().equals("") || editTextName.getText().toString().equals("") || editTextName.getText().toString().equals("")) {
                    Toast.makeText(CreateAccountActivity.this, "Please enter all the required information", Toast.LENGTH_SHORT).show();
                } else {
                    sendInfo();
                }
            }
        });

    }

    private void sendInfo() {
        Intent intent = new Intent();
        intent.putExtra("hello", editTextUsername.getText().toString());
        intent.putExtra("hi", editTextPassword.getText().toString());
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
