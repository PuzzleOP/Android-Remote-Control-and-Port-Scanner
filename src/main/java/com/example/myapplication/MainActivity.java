package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView mytext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnTv = (Button) findViewById(R.id.btnRemote);
        Button btnPortScanner = (Button) findViewById(R.id.btnPortScanner);

        btnTv.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent iTV = new Intent(getApplicationContext(), TVControl.class);
                startActivity(iTV);
            }
        });

        btnPortScanner.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent iPS = new Intent(getApplicationContext(), LanScanner.class);
                startActivity(iPS);
            }
        });

    }
}
