package com.example.sumanth.warest;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentAbout.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentAbout#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAbout extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FragmentAbout() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentAbout.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentAbout newInstance(String param1, String param2) {
        FragmentAbout fragment = new FragmentAbout();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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

        View view = inflater.inflate(R.layout.fragment_about, container, false);
        TextView about = view.findViewById(R.id.about);
        about.setText("An NGO aimed to connect students, faculty, institutes and corporate");
        TextView about_warest = view.findViewById(R.id.about_warest);
        about_warest.setText("" +
                "1. To inspire students, faculty, institute and corporate about the principles that leads to global responsibility for education, skill set and ethical environment al change.\n\n" +
                "2. To implement innovative livelihood programmes, workshops, conference in various discipline with these communities in different part of Karnataka and india.\n\n" +
                "3. To provide the platform in growth of quality education in engineering, science and technology through innovative ideas and creative deployment.\n\n" +
                "4. To help the various sector of societies by providing proper education and make them future endeavors.\n\n" +
                "5. Investment to raise stable and sustainable development in research through long term link with govt. and other research institution to make globally developed nation.\n\n");
        TextView mission = view.findViewById(R.id.mission);
        mission.setText("1. To identify, verify, define and detail the need of the society in the field of education.\n\n" +
                "2. Uplifting the society for the need of education and helps them to farm a better future.\n\n");
        TextView values = view.findViewById(R.id.values);
        values.setText("Power to make engineering science and technological communities to lead the world as responsible global citizens.\n\n");
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    @Override
    public void onResume(){
        super.onResume();
        ((HomeActivity)getActivity()).setActionBarTitle("About");
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
