package edu.smarthealthcare.smarthealthcareapp;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.gigamole.slideimageview.lib.SlideImageView;

import edu.smarthealthcare.smarthealthcareapp.Classes.SectionsPagerAdaper;

public class ActivityKitDetails extends AppCompatActivity {

    private ViewPager viewPager;
    private SectionsPagerAdaper mSectionsPagerAdaper;

    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kit_details);
        //Tabs
//

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar_new);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }


    }
}
