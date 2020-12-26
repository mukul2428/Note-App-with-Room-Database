package com.techexpert.quixotetask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.techexpert.quixotetask.Model.User;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity
{
    TextInputLayout editTextUsername, editTextEmail, editTextPassword, editTextCnfPassword;
    Button buttonRegister;

    TextView textViewLogin;
    private UserDao userDao;

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextCnfPassword = findViewById(R.id.editTextCnfPassword);
        buttonRegister = findViewById(R.id.buttonRegister);

        textViewLogin = findViewById(R.id.textViewLogin);
        textViewLogin.setOnClickListener(v -> startActivity(new Intent(RegisterActivity.this, MainActivity.class)));

        userDao = Room.databaseBuilder(this, UserDataBase.class, "mi-database.db").allowMainThreadQueries()
                .build().getUserDao();

        buttonRegister.setOnClickListener(v ->
        {
            if (!validateEmail() | !validateUsername() | !validatePassword()) {
                return;
            }
            String userName = editTextUsername.getEditText().getText().toString().trim();
            String email = editTextEmail.getEditText().getText().toString().trim();
            String password = editTextPassword.getEditText().getText().toString().trim();
            String passwordConf = editTextCnfPassword.getEditText().getText().toString().trim();

            if (password.equals(passwordConf))
            {
                User user = new User(userName,password,email);
                userDao.insert(user);
                Intent moveToLogin = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(moveToLogin);

            } else {
                Toast.makeText(RegisterActivity.this, "Password is not matching", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private boolean validatePassword() {
        String passwordInput = editTextPassword.getEditText().getText().toString().trim();
        if (passwordInput.isEmpty()) {
            editTextPassword.setError("Field can't be empty");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            editTextPassword.setError("Password too weak");
            return false;
        } else {
            editTextPassword.setError(null);
            return true;
        }
    }
    private boolean validateEmail() {
        String emailInput = editTextEmail.getEditText().getText().toString().trim();
        if (emailInput.isEmpty()) {
            editTextEmail.setError("Field can't be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            editTextEmail.setError("Please enter a valid email address");
            return false;
        } else {
            editTextEmail.setError(null);
            return true;
        }
    }
    private boolean validateUsername() {
        String usernameInput = editTextUsername.getEditText().getText().toString().trim();
        if (usernameInput.isEmpty()) {
            editTextUsername.setError("Field can't be empty");
            return false;
        } else if (usernameInput.length() > 15) {
            editTextUsername.setError("Username too long");
            return false;
        } else {
            editTextUsername.setError(null);
            return true;
        }
    }
}
