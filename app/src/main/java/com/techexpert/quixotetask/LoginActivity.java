package com.techexpert.quixotetask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.techexpert.quixotetask.Model.User;

public class LoginActivity extends AppCompatActivity
{

    TextInputLayout editTextEmail, editTextPassword;
    Button buttonLogin;
    TextView textViewRegister;
    UserDao db;
    UserDataBase dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);

        textViewRegister = findViewById(R.id.textViewRegister);

        dataBase = Room.databaseBuilder(this, UserDataBase.class, "mi-database.db")
                .allowMainThreadQueries()
                .build();

        db = dataBase.getUserDao();

        textViewRegister.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));

        buttonLogin.setOnClickListener(v ->
        {
            String email = editTextEmail.getEditText().getText().toString().trim();
            String password = editTextPassword.getEditText().getText().toString().trim();

            User user = db.getUser(email, password);
            if (user != null)
            {
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                i.putExtra("User", user);
                startActivity(i);
                finish();
            }
            else{
                Toast.makeText(LoginActivity.this, "Unregistered user, or incorrect", Toast.LENGTH_SHORT).show();
            }
        });

    }
}