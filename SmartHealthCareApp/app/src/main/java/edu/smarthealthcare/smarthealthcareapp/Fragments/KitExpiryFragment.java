package edu.smarthealthcare.smarthealthcareapp.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;

import edu.smarthealthcare.smarthealthcareapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class KitExpiryFragment extends Fragment {

    private View view;

    public KitExpiryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_kit_expiry, container, false);

        new MaterialDialog.Builder(getContext())
                .title("Coming soon")
                .content("This feature is not implemented yet.Wait for a while!")
                .positiveText("OK")
                .positiveColor(ContextCompat.getColor(getContext(), R.color.material_green))
                .build().show();

        return view;
    }

}
