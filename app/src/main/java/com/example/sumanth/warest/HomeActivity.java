package com.example.sumanth.warest;


import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ShareActionProvider;
import android.widget.TextView;

import java.io.File;

import helper.SQLiteHandler;
import helper.SessionManager;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener ,
        FragmentHomeActivity.OnFragmentInteractionListener,
        FragmentFeedback.OnFragmentInteractionListener,
        FragmentAbout.OnFragmentInteractionListener,
        FragmentConference.OnFragmentInteractionListener,
        FragmentEvents.OnFragmentInteractionListener,
        FragmentFacultyTraining.OnFragmentInteractionListener,
        FragmentStudentTraining.OnFragmentInteractionListener,
        FragmentContactUs.OnFragmentInteractionListener,
        FragmentOnlineCourses.OnFragmentInteractionListener,
        FragmentWebinar.OnFragmentInteractionListener,
        FragmentWorkshop.OnFragmentInteractionListener
        {
    private SQLiteHandler db;
    private SessionManager session;
    private ShareActionProvider mShareActionProvider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (savedInstanceState == null) {
            Fragment fragment = null;
            Class fragmentClass = null;
            fragmentClass = FragmentHomeActivity.class;
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.home_frame, fragment).commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        db = new SQLiteHandler(getApplicationContext());
        session = new SessionManager(getApplicationContext());
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        String email = db.getUser();
        View hView =  navigationView.getHeaderView(0);
        TextView email_id =(TextView)hView.findViewById(R.id.emailid);
        email_id.setText(email);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        // Locate MenuItem with ShareActionProvider
        /*MenuItem item = menu.findItem(R.id.nav_share);

        // Fetch and store ShareActionProvider
        mShareActionProvider = (ShareActionProvider) item.getActionProvider();*/

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id==R.id.action_signout)
            logoutUser();

        return super.onOptionsItemSelected(item);
    }
   /* private void setShareIntent(Intent shareIntent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }*/

    private void logoutUser() {
        session.setLogin(false);
        db.deleteUsers();
        db.deleteUpcomingEvents();
        // Launching the login activity
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        Class fragmentClass = null;
         if (id == R.id.nav_upcoming_events) {
             fragmentClass = FragmentEvents.class;
             try{
                 fragment = (Fragment)fragmentClass.newInstance();
                 FragmentManager fragmentManager = getSupportFragmentManager();
                 fragmentManager.beginTransaction().replace(R.id.home_frame,fragment).commit();
             }catch(Exception e)
             {
                 e.printStackTrace();
             }

        } else if (id == R.id.nav_contact_us) {
             fragmentClass = FragmentContactUs.class;
             try{
                 fragment = (Fragment)fragmentClass.newInstance();
                 FragmentManager fragmentManager = getSupportFragmentManager();
                 fragmentManager.beginTransaction().replace(R.id.home_frame,fragment).commit();
             }catch(Exception e)
             {
                 e.printStackTrace();
             }
        }
        else if(id == R.id.nav_about){
             fragmentClass = FragmentAbout.class;
             try{
                 fragment = (Fragment)fragmentClass.newInstance();
                 FragmentManager fragmentManager = getSupportFragmentManager();
                 fragmentManager.beginTransaction().replace(R.id.home_frame,fragment).commit();
             }catch(Exception e)
             {
                 e.printStackTrace();
             }
         }
         else if(id == R.id.workshops){
             fragmentClass = FragmentWorkshop.class;
             try{
                 fragment = (Fragment)fragmentClass.newInstance();
                 FragmentManager fragmentManager = getSupportFragmentManager();
                 fragmentManager.beginTransaction().replace(R.id.home_frame,fragment).commit();
             }catch(Exception e)
             {
                 e.printStackTrace();
             }
         }
         else if(id == R.id.webinar){
             fragmentClass = FragmentWebinar.class;
             try{
                 fragment = (Fragment)fragmentClass.newInstance();
                 FragmentManager fragmentManager = getSupportFragmentManager();
                 fragmentManager.beginTransaction().replace(R.id.home_frame,fragment).commit();
             }catch(Exception e)
             {
                 e.printStackTrace();
             }
         }
         else if(id == R.id.online_courses){
             fragmentClass = FragmentOnlineCourses.class;
             try{
                 fragment = (Fragment)fragmentClass.newInstance();
                 FragmentManager fragmentManager = getSupportFragmentManager();
                 fragmentManager.beginTransaction().replace(R.id.home_frame,fragment).commit();
             }catch(Exception e)
             {
                 e.printStackTrace();
             }
         }
         else if(id == R.id.conference){
             fragmentClass = FragmentConference.class;
             try{
                 fragment = (Fragment)fragmentClass.newInstance();
                 FragmentManager fragmentManager = getSupportFragmentManager();
                 fragmentManager.beginTransaction().replace(R.id.home_frame,fragment).commit();
             }catch(Exception e)
             {
                 e.printStackTrace();
             }
         }
         else if(id == R.id.student_training){
             fragmentClass = FragmentStudentTraining.class;
             try{
                 fragment = (Fragment)fragmentClass.newInstance();
                 FragmentManager fragmentManager = getSupportFragmentManager();
                 fragmentManager.beginTransaction().replace(R.id.home_frame,fragment).commit();
             }catch(Exception e)
             {
                 e.printStackTrace();
             }
         }
         else if(id == R.id.faculty_training){
             fragmentClass = FragmentFacultyTraining.class;
             try{
                 fragment = (Fragment)fragmentClass.newInstance();
                 FragmentManager fragmentManager = getSupportFragmentManager();
                 fragmentManager.beginTransaction().replace(R.id.home_frame,fragment).commit();
             }catch(Exception e)
             {
                 e.printStackTrace();
             }
         }

         else if(id == R.id.nav_share){
             shareApplication();
         }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

            private void shareApplication() {
                ApplicationInfo app = getApplicationContext().getApplicationInfo();
                String filePath = app.sourceDir;

                Intent intent = new Intent(Intent.ACTION_SEND);

                // MIME of .apk is "application/vnd.android.package-archive".
                // but Bluetooth does not accept this. Let's use "*/*" instead.
                intent.setType("*/*");


                // Append file and send Intent
                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(filePath)));
                startActivity(Intent.createChooser(intent, "Share app via"));
            }

            public void setActionBarTitle(String title)
    {
        getSupportActionBar().setTitle(title);
    }
    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
