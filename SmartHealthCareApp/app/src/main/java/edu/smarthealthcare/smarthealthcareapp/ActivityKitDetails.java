package edu.smarthealthcare.smarthealthcareapp;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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

        viewPager = (ViewPager) findViewById(R.id.main_tabPager);
        mSectionsPagerAdaper = new SectionsPagerAdaper(getSupportFragmentManager());

        viewPager.setAdapter(mSectionsPagerAdaper);

        mTabLayout = (TabLayout) findViewById(R.id.main_tabs);
        mTabLayout.setupWithViewPager(viewPager);


    }
}
