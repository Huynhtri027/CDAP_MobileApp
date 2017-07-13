package edu.smarthealthcare.smarthealthcareapp.Classes;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import edu.smarthealthcare.smarthealthcareapp.Fragments.OrderProductFragment;
import edu.smarthealthcare.smarthealthcareapp.Fragments.ProductDetailFragment;

/**
 * Created by RG User on 07/13/17.
 */

public class SectionsPagerAdaper extends FragmentPagerAdapter {

    public SectionsPagerAdaper(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                ProductDetailFragment productDetailFragment = new ProductDetailFragment();
                return productDetailFragment;

            case 1:
                OrderProductFragment orderProductFragment = new OrderProductFragment();
                return orderProductFragment;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    public CharSequence getPageTitle(int position){
        switch (position) {
            case 0:
                return "Product Details";
            case 1:

                return "Order Product";
            default:
                return null;
        }
    }
}
