package com.example.asm2_tkgd;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.asm2_tkgd.fragment.ChiFragment;
import com.example.asm2_tkgd.fragment.GioiThieuFragment;
import com.example.asm2_tkgd.fragment.ThongkeFragment;
import com.example.asm2_tkgd.fragment.ThuFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        navigationView.setItemIconTintList(null);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24);
        actionBar.setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            final FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment fragment;
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_revenue:
                        fragment = new ThuFragment();
                        break;
                    case R.id.item_expenses:
                        fragment = new ChiFragment();
                        break;
                    case R.id.item_introduce:
                        fragment = new GioiThieuFragment();
                        break;
                    case R.id.item_statistical:
                        fragment = new ThongkeFragment();
                        break;
                    case R.id.item_logout:
                        finish();
                        break;
                    default:
                        fragment = new ThuFragment();
                        break;
                }
                fragmentManager.beginTransaction().replace(R.id.linearLayout, fragment).commit();
                drawerLayout.closeDrawers();
                setTitle(item.getTitle());

                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(navigationView);
        }
        return super.onOptionsItemSelected(item);
    }
}