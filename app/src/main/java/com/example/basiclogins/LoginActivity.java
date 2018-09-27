package com.example.basiclogins;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "the message";
    public static final int REQUEST = 1;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button buttonLogin;
    private TextView textViewCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        wireWidgets();

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "Coming Soon!", Toast.LENGTH_SHORT).show();
            }
        });

        textViewCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity();

            }
        });
    }

    private void wireWidgets() {
        editTextUsername = findViewById(R.id.editText_login_username);
        editTextPassword = findViewById(R.id.editText_login_password);
        buttonLogin = findViewById(R.id.button_login_login);
        textViewCreate = findViewById(R.id.textView_login_create);
    }

    private void startActivity(){
        Intent intent = new Intent(this, CreateAccountActivity.class);
        String message = editTextUsername.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivityForResult(intent, REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == REQUEST){
            if(resultCode == RESULT_OK){
                data = getIntent();
                String username = data.getStringExtra(CreateAccountActivity.editTextUsername);
                String password = data.getStringExtra(CreateAccountActivity.EXTRA_MESSAGE1);
                editTextUsername.setText(username);
                editTextPassword.setText(password);
            }
        }
    }
}
