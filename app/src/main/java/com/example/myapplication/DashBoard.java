package com.example.myapplication;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;


import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

public class DashBoard extends AppCompatActivity {

    LinearLayout dwr_doctor, dwr_chemist, dwr_atdnc, dwr_logout, dwr_mrs, dwr_sr;
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawerlayout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.toolbar_color, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.toolbar_color));
        }

        drawer = findViewById(R.id.drawer_layout);
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(DashBoard.this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        inti();
    }

    void inti() {
        dwr_sr = findViewById(R.id.dwr_sr);
        dwr_mrs = findViewById(R.id.dwr_mrs);
        dwr_doctor = findViewById(R.id.dwr_doctor);
        dwr_chemist = findViewById(R.id.dwr_chemist);
        dwr_atdnc = findViewById(R.id.dwr_atdnc);
        dwr_logout = findViewById(R.id.dwr_logout);


        dwr_sr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.close();
                startActivity(new Intent(DashBoard.this, Senior.class));
            }
        });


        dwr_doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.close();
                startActivity(new Intent(DashBoard.this, Doctors.class));
            }
        });


        dwr_chemist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.close();
                startActivity(new Intent(DashBoard.this, Chemist.class));
            }
        });

        dwr_mrs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.close();
                startActivity(new Intent(DashBoard.this, MR.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}