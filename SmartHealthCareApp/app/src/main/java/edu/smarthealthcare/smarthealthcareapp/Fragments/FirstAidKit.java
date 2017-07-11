package edu.smarthealthcare.smarthealthcareapp.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.gigamole.slideimageview.lib.SlideImageView;

import edu.smarthealthcare.smarthealthcareapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FirstAidKit extends Fragment {


    public FirstAidKit() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        SlideImageView slideImageView = (SlideImageView) findViewById(R.id.img_horizontal_slide);
//        slideImageView.setSource(R.drawable.wide_background);
//        slideImageView.setRate(0.3f);
//        slideImageView.setAxis(SlideImageView.Axis.HORIZONTAL);


        return inflater.inflate(R.layout.fragment_first_aid_kit, container, false);
    }

}
