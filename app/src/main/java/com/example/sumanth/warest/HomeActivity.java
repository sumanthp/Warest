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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import helper.SQLiteHandler;
import helper.SessionManager;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener ,
        FragmentAdmin.OnFragmentInteractionListener,
        FragmentAddEvent.OnFragmentInteractionListener,
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
        FragmentAddWorkshop.OnFragmentInteractionListener,
        FragmentAddWebinars.OnFragmentInteractionListener,
        FragmentAddConference.OnFragmentInteractionListener,
        FragmentAddOnlineCourses.OnFragmentInteractionListener,
        FragmentViewUsers.OnFragmentInteractionListener,
        FragmentStudentUsers.OnFragmentInteractionListener,
        FragmentFacultyUsers.OnFragmentInteractionListener,
        FragmentCorporateUsers.OnFragmentInteractionListener,
        FragmentInstituteUsers.OnFragmentInteractionListener
        {
    private String user;
    private SQLiteHandler db;
    private SessionManager session;
    private ShareActionProvider mShareActionProvider;
    private static final String TAG = HomeActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        db = new SQLiteHandler(getApplicationContext());
        session = new SessionManager(getApplicationContext());
        user = db.getUser();
        Fragment fragment = null;
        Class fragmentClass = null;
        if (savedInstanceState == null && user.equals("aks"))
        {
            fragmentClass = FragmentAdmin.class;
            getStudentDetails();
            getFacultyDetails();
            getCorporateDetails();
            getInstitueDetails();
        }

        else if(savedInstanceState == null)
        {

            fragmentClass = FragmentHomeActivity.class;
        }
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.home_frame, fragment).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View hView =  navigationView.getHeaderView(0);
        TextView user_id =(TextView)hView.findViewById(R.id.emailid);
        user_id.setText(user);

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

    private void logoutUser() {
        session.setLogin(false);
        db.deleteUsers();
        db.deleteUpcomingEvents();
        // Launching the login activity
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }


    public void getStudentDetails()
    {
        String tag_string_req = "req_student_details";

        StringRequest strReq = new StringRequest(Request.Method.GET,
                AppConfig.URL_STUDENT_DETAILS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    // boolean error = jObj.getBoolean("error");
                    // Check for error node in json
                    //if (!error) {

                    JSONArray user = jObj.getJSONArray("student_details");
                    for(int i=0;i<user.length();i++) {
                        JSONObject student_details = user.getJSONObject(i);
                        String name = student_details.getString("name");
                        String email = student_details.getString("email");
                        db.addStudent(name,email);
                    }

                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Retrieving student Details Error: " + error.getMessage());
            }
        });
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq);
    }
    public void getFacultyDetails()
    {
        String tag_string_req = "req_faculty_details";

        StringRequest strReq = new StringRequest(Request.Method.GET,
                AppConfig.URL_FACULTY_DETAILS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    JSONArray user = jObj.getJSONArray("faculty_details");
                    for(int i=0;i<user.length();i++) {
                        JSONObject faculty_details = user.getJSONObject(i);
                        String name = faculty_details.getString("name");
                        String email = faculty_details.getString("email");
                        db.addFaculty(name,email);
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Retrieving faculty Details Error: " + error.getMessage());
            }
        });
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq);
    }
    public void getCorporateDetails()
    {
        String tag_string_req = "req_corporate_details";

        StringRequest strReq = new StringRequest(Request.Method.GET,
                AppConfig.URL_CORPORATE_DETAILS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    JSONArray user = jObj.getJSONArray("corporate_details");
                    for(int i=0;i<user.length();i++) {
                        JSONObject corporate_details = user.getJSONObject(i);
                        String name = corporate_details.getString("name");
                        String email = corporate_details.getString("email");
                        db.addCorporate(name,email);
                    }

                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Retrieving corporate Details Error: " + error.getMessage());
            }
        });
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq);
    }
    public void getInstitueDetails()
    {
        String tag_string_req = "req_institute_details";

        StringRequest strReq = new StringRequest(Request.Method.GET,
                AppConfig.URL_INSTITUE_DETAILS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    JSONArray user = jObj.getJSONArray("institute_details");
                    for(int i=0;i<user.length();i++) {
                        JSONObject institute_details = user.getJSONObject(i);
                        String name = institute_details.getString("name");
                        String email = institute_details.getString("email");
                        db.addInstitute(name,email);
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Retrieving institute Details Error: " + error.getMessage());
            }
        });
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.


        int id = item.getItemId();
        Fragment fragment = null;
        Class fragmentClass = null;
        String User = db.getUser();
         if(id == R.id.home && User.equals("aks"))
         {
             fragmentClass = FragmentAdmin.class;
             try{
                 fragment = (Fragment)fragmentClass.newInstance();
                 FragmentManager fragmentManager = getSupportFragmentManager();
                 fragmentManager.beginTransaction().replace(R.id.home_frame,fragment).commit();
             }catch(Exception e)
             {
                 e.printStackTrace();
             }
         }
         if(id == R.id.home)
         {
             fragmentClass = FragmentHomeActivity.class;
             try{
                 fragment = (Fragment)fragmentClass.newInstance();
                 FragmentManager fragmentManager = getSupportFragmentManager();
                 fragmentManager.beginTransaction().replace(R.id.home_frame,fragment).commit();
             }catch(Exception e)
             {
                 e.printStackTrace();
             }

         }
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
