package com.example.sumanth.warest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import helper.SQLiteHandler;
import helper.SessionManager;

public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = SignUpActivity.class.getSimpleName();
    Button register;
    private SessionManager session;
    private SQLiteHandler db;
    private ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Session manager
        session = new SessionManager(getApplicationContext());
        register = (Button)findViewById(R.id.sign_up);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = ((TextView)findViewById(R.id.email)).getText().toString().trim();
                Long contact = Long.parseLong(((TextView)findViewById(R.id.contact)).getText().toString().trim());
                String password = ((TextView)findViewById(R.id.password)).getText().toString().trim();
                String confirm_password = ((TextView)findViewById(R.id.confirm_password)).getText().toString().trim();
                String user = ((Spinner)findViewById(R.id.dropdown)).getSelectedItem().toString().trim();
                String name = ((TextView)findViewById(R.id.name)).getText().toString().trim();
                String type="";

                switch(user) {
                    case "Student":
                        type = "student_login";
                        break;
                    case "Institute":
                        type = "institute_login";
                        break;
                    case "Admin":
                        type = "admin_login";
                        break;
                    case "Corporate":
                        type = "corporate_login";
                        break;
                    case "Faculty":
                        type = "faculty";
                        break;
                }

                if(!password.equals(confirm_password))
                {
                    Toast.makeText(getApplicationContext(),
                            "Both passwords do not match.Enter correct password!", Toast.LENGTH_LONG)
                            .show();
                }
                else if(!username.isEmpty()||!password.isEmpty()||!confirm_password.isEmpty()||!type.isEmpty()||contact!=0)
                {
                    registerUser(name,username,password,contact,type);
                }
                else{
                    Toast.makeText(getApplicationContext(),
                            "Required credentials are wrong", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });
    }

    private void registerUser(final String name,final String username, final String password,final long contact,final String type) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pDialog.setMessage("Registering user ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_SIGNUP, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        // user successfully logged in
                        // Create login session
                        // Now store the user in SQLite
                        JSONObject user = jObj.getJSONObject("user");
                        String name = user.getString("username");
                        String password=user.getString("password");
                        // Inserting row in users table
                        db.addUser(name,password);
                        session.setLogin(true);
                        getUpcomingEvents();
                        // Launch main activity
                        Intent intent = new Intent(SignUpActivity.this,
                                HomeActivity.class);
                        startActivity(intent);
                        finish();
                        //}
                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
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
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("contact",String.valueOf(contact));
                params.put("name",name);
                params.put("email",username);
                params.put("password", password);
                params.put("type", type);
                return params;
            }

        };

        // Adding request to request queue

        AppController.getInstance().addToRequestQueue(strReq);
    }
    public void getUpcomingEvents(){
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        StringRequest strReq = new StringRequest(Request.Method.GET,
                AppConfig.URL_EVENTS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    // Check for error node in json
                    if (!error) {
                        // user successfully logged in
                        // Create login session
                        // Now store the user in SQLite
                        JSONArray user = jObj.getJSONArray("upcoming_events");
                        for(int i=0;i<user.length();i++) {
                            JSONObject upcoming_events = user.getJSONObject(i);
                            String sno = upcoming_events.getString("s_no");
                            String day = upcoming_events.getString("day");
                            String events = upcoming_events.getString("events");
                            String details = upcoming_events.getString("details");
                            // Inserting row in users table
                            db.addUpcomingEvents(day, events, details);
                        }
                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
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
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

}
