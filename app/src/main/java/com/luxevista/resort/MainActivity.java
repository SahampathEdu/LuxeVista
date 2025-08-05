package com.luxevista.resort;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button loginButton;
    private Button signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize buttons
        loginButton = findViewById(R.id.loginButton);
        signupButton = findViewById(R.id.signupButton);

        // Set OnClickListener for Login Button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open Login Activity
                Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                // Optionally, you can also add finish() to remove the welcome screen from the back stack.
//                finish();
            }
        });

        // Set OnClickListener for Sign Up Button
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open Sign Up Activity
                Intent signupIntent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(signupIntent);
                finish();
            }
        });
    }
}
