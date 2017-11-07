package com.example.sumanth.warest;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import helper.SQLiteHandler;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentAddEvent.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentAddEvent#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAddEvent extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG =FragmentAddEvent.class.getSimpleName();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private SQLiteHandler db;
    private OnFragmentInteractionListener mListener;

    public FragmentAddEvent() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentAddEvent.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentAddEvent newInstance(String param1, String param2) {
        FragmentAddEvent fragment = new FragmentAddEvent();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onResume(){
        super.onResume();
        ((HomeActivity)getActivity()).setActionBarTitle("ADD Event");
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =  inflater.inflate(R.layout.fragment_add_event, container, false);
        Button submit = (Button)view.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText event_name = (EditText)getActivity().findViewById(R.id.add_event);
                String name = event_name.getText().toString().trim().toUpperCase();
                EditText event_details = (EditText)getActivity().findViewById(R.id.event_details);
                String details = event_details.getText().toString().trim();
                EditText event_date = (EditText)getActivity().findViewById(R.id.event_date);
                String date = event_date.getText().toString().trim();

                if(name.isEmpty()||details.isEmpty()||date.isEmpty())
                {
                    Toast.makeText(getActivity(),"One of the fields is empty.",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    db = new SQLiteHandler(getActivity());
                    addEvent(name,details,date);
                }
            }
        });
        return view;
    }

    public void addEvent(final String name, final String details,final String date)
    {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_EVENTS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                     boolean error = jObj.getBoolean("error");
                    // Check for error node in json
                    if (!error) {

                        String status = jObj.getString("status");
                        if (status.equals("success")) {
                            Toast.makeText(getActivity(), "Event added successfully", Toast.LENGTH_SHORT).show();
                            db.addUpcomingEvents(date, details, name);
                        } else {
                            Toast.makeText(getActivity(), "Event was not added successfully.Try again", Toast.LENGTH_SHORT).show();
                        }
                    }

                    else {
                        // Error in getting data. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getActivity(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getActivity(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }){

                @Override
                protected Map<String, String> getParams() {
                    // Posting parameters to login url
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("name", name);
                    params.put("details", details);
                    params.put("date", date);
                    return params;
                }

            };
            ;
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq);
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
