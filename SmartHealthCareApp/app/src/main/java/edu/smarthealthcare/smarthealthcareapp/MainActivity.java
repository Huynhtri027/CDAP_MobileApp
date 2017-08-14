package edu.smarthealthcare.smarthealthcareapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import edu.smarthealthcare.smarthealthcareapp.Fragments.BlanceFragment;
import edu.smarthealthcare.smarthealthcareapp.Fragments.FragmentFirstAidKit;
import edu.smarthealthcare.smarthealthcareapp.Fragments.FragmentMyAccount;
import edu.smarthealthcare.smarthealthcareapp.Fragments.KitExpiryFragment;
import edu.smarthealthcare.smarthealthcareapp.Fragments.OrdersFragment;
import edu.smarthealthcare.smarthealthcareapp.Utils.NetConnect;
import edu.smarthealthcare.smarthealthcareapp.Utils.SharedPreferenceReader;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView tvHeaderName,tvHeaderEmail;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MapActivity.class);
                startActivity(i);
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View navHeaderView=navigationView.getHeaderView(0);

        tvHeaderName= (TextView) navHeaderView.findViewById(R.id.txtUserNameHead);
        tvHeaderEmail= (TextView) navHeaderView.findViewById(R.id.txtUserEmailHead);

        if (NetConnect.isNetworkConnected(MainActivity.this)){

            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.show();
            progressDialog.setTitle("Please wait!!");
            progressDialog.setMessage("Please wait!!");
            progressDialog.setCancelable(false);

            String user_name = SharedPreferenceReader.getUserName(MainActivity.this);
            String user_email = SharedPreferenceReader.getUserEmail(MainActivity.this);

            tvHeaderName.setText(user_name);
            tvHeaderEmail.setText(user_email);

            FragmentFirstAidKit fragmentFirstAidKit = new FragmentFirstAidKit();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragmentFirstAidKit);
            fragmentTransaction.commit();

            progressDialog.cancel();

        }else {

            new MaterialDialog.Builder(MainActivity.this)
                    .title("No Internet")
                    .content("Please check your WiFi/Mobile Data settings and try again.")
                    .positiveText("OK")
                    .positiveColor(ContextCompat.getColor(MainActivity.this, R.color.material_green))
                    .build().show();
        }
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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {

            new MaterialDialog.Builder(MainActivity.this)
                    .title("Logout")
                    .content("Do you really want to logout now!.")
                    .positiveText("Confirm")
                    .negativeText("Cancel")
                    .positiveColor(ContextCompat.getColor(MainActivity.this, R.color.material_green))
                    .negativeColor(ContextCompat.getColor(MainActivity.this, R.color.material_red))
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            Intent i = new Intent(MainActivity.this, GetStartActivity.class);
                            SharedPreferenceReader.clearPreferences(MainActivity.this);
                            startActivity(i);
                            finish();
                        }
                    })
                    .build().show();



        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (id == R.id.nav_firstaid) {
            if (!(f instanceof FragmentFirstAidKit)){
                showFragment(FragmentFirstAidKit.class);
                setTitle(item.getTitle());
            }
        }else if (id == R.id.nav_orders) {
            if (!(f instanceof OrdersFragment)){
                showFragment(OrdersFragment.class);
                setTitle(item.getTitle());
            }
        }
        else if (id == R.id.nav_balance) {
            if (!(f instanceof BlanceFragment)){
                showFragment(BlanceFragment.class);
                setTitle(item.getTitle());
            }
        } else if (id == R.id.nav_expiry) {
            if (!(f instanceof KitExpiryFragment)){
                showFragment(KitExpiryFragment.class);
                setTitle(item.getTitle());
            }

        } else if (id == R.id.nav_myaccount) {
            if (!(f instanceof FragmentMyAccount)){
                showFragment(FragmentMyAccount.class);
                setTitle(item.getTitle());
            }
        } else if (id == R.id.nav_aboutus) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showFragment(Class fragmentClass) {
        try {
            Fragment fragment = (Fragment)fragmentClass.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
