package com.example.app01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    NavigationView navigationView;
    Toolbar toolbar;
    Menu menu;
    DrawerLayout drawerLayout;

    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*---------------------Hooks------------------------*/
        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);
        toolbar=findViewById(R.id.toolbar);


        /*---------------------Toolbar------------------------*/

        setSupportActionBar(toolbar);


        /*---------------------Navigation Drawer Menu------------------------*/


        //Hide or show items
        Menu menu = navigationView.getMenu();
        //menu.findItem(R.id.nav_logout).setVisible(false);
        //menu.findItem(R.id.nav_profile).setVisible(false);


        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

    }
    // to avoid closing the app on Back pressed
    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {super.onBackPressed();
        }
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
           case R.id.nav_home:
               Intent intent10 = new Intent(MainActivity.this, MainActivity.class);
               startActivity(intent10);
              // getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
               break;
           case R.id.nav_settings:
               Intent intent9 = new Intent(MainActivity.this, SettingsMenuActivity.class);
               startActivity(intent9);
               break;
           case  R.id.nav_profile:
               Intent intent2 = new Intent(MainActivity.this, ProfileActivity.class);
               startActivity(intent2);
               //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
               break;
           case R.id.nav_toy:
               Intent intent = new Intent(MainActivity.this, JouetActivity.class);
               startActivity(intent);
               break;
           case R.id.nav_share:
               Intent intent3 = new Intent(Intent.ACTION_SEND);
               intent3.setType("text/plain");
               String Body = "Download this app";
               String Sub = "http://play.google.com";
               intent3.putExtra(Intent.EXTRA_TEXT, Body);
               intent3.putExtra(Intent.EXTRA_TEXT, Sub);
               startActivity(Intent.createChooser(intent3, "Share using"));
               break;
           case R.id.nav_about:
               getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AboutFragment()).commit();
               break;
           case R.id.nav_map:
               Intent intent7 = new Intent(MainActivity.this, MapActivity.class);
               startActivity(intent7);
               break;

           case R.id.nav_logout:
               //  Toast.makeText(this, "Logout!", Toast.LENGTH_SHORT).show();
               Intent intent1 = new Intent(MainActivity.this, LoginActivity.class);
               intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
               startActivity(intent1);
               finish();
               break;

       }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }



}

