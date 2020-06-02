package com.school.customizedschoolapp.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.SubMenu;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.school.customizedschoolapp.R;
import com.school.customizedschoolapp.fragments.FragmentAnnouncements;
import com.school.customizedschoolapp.fragments.FragmentAttendances;
import com.school.customizedschoolapp.fragments.FragmentEvents;
import com.school.customizedschoolapp.fragments.FragmentExam;
import com.school.customizedschoolapp.fragments.FragmentNotes;
import com.school.customizedschoolapp.fragments.FragmentReminders;
import com.school.customizedschoolapp.fragments.FragmentResult;
import com.school.customizedschoolapp.fragments.FragmentWeeklySchedule;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private TextView toolbar_text;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar_text = toolbar.findViewById(R.id.toolbar_title);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawer = findViewById(R.id.drawer_layout);
        nvDrawer = findViewById(R.id.nvView);

        drawerToggle = setupDrawerToggle();

        // Setup toggle to display hamburger icon with nice animation
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();

        // Tie DrawerLayout events to the ActionBarToggle
        mDrawer.addDrawerListener(drawerToggle);


        setupDrawerContent(nvDrawer);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open,  R.string.drawer_close);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        Fragment fragment = null;
        Class fragmentClass = null;
        SubMenu subMenu = menuItem.getSubMenu();
        switch(menuItem.getItemId()) {
            case R.id.nav_weekly_schedule:
                fragmentClass = FragmentWeeklySchedule.class;
                break;
            case R.id.nav_exam:
                fragmentClass = FragmentExam.class;
                break;
            case R.id.nav_reminders:
                fragmentClass = FragmentReminders.class;
                break;
            case R.id.nav_notes:
                fragmentClass = FragmentNotes.class;
                break;
            case R.id.nav_attendances:
                fragmentClass = FragmentAttendances.class;
                break;
            case R.id.nav_announcements:
                fragmentClass = FragmentAnnouncements.class;
                break;
            case R.id.nav_events:
                fragmentClass = FragmentEvents.class;
                break;
            case R.id.nav_result:
                fragmentClass = FragmentResult.class;
                break;

            default:
                fragmentClass = FragmentWeeklySchedule.class;
        }

       try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);

        toolbar_text.setText(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }


}
