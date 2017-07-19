
package edu.smarthealthcare.smarthealthcareapp.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.smarthealthcare.smarthealthcareapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BlanceFragment extends Fragment {

    private View mView;

    public BlanceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_blance, container, false);

        return mView;
    }

}
