package com.ccc.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editText = findViewById(R.id.city);
        Button getWeather = findViewById(R.id.getWeather);
        getWeather.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), ListItem.class);
            String city = editText.getText().toString().trim();
            intent.putExtra("city", city);
            startActivity(intent);
        });
    }
}