package com.maurice.cryptothon.app;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.maurice.cryptothon.app.Fragments.MapFragment;
import com.maurice.cryptothon.app.Fragments.Offers.RestaurantsFragment;
import com.maurice.cryptothon.app.Fragments.Profile.ProfileFragment;
import com.maurice.cryptothon.app.Fragments.Users.UsersFragment;
import com.maurice.cryptothon.app.Utils.Settings;

public class MasterActivity extends MasterBluetoothActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(),this);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bluetoothUpstart();
                Snackbar.make(view, "Searching for location offers : ", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_master, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        MasterActivity mActivity;
        RestaurantsFragment restaurantsFragment;
        MapFragment mapFragment;
        ProfileFragment profileFragment;
        UsersFragment userFragment;
        public SectionsPagerAdapter(FragmentManager fm, MasterActivity activity) {
            super(fm);
            mActivity = activity;
            restaurantsFragment = RestaurantsFragment.newInstance(mActivity);
            mapFragment = MapFragment.newInstance(mActivity);
            profileFragment = ProfileFragment.newInstance(mActivity);
            userFragment = UsersFragment.newInstance(mActivity);
        }

        @Override
        public Fragment getItem(int position) {
            if (Settings.isUserSeller()) {
                switch (position) {
                    case 0: return userFragment;
                    case 1: return restaurantsFragment;
                    default: return null;
                }
            }else{
                switch (position) {
                    case 0: return mapFragment;
                    case 1: return restaurantsFragment;
                    case 2: return profileFragment;
                    default: return null;
                }
            }
        }

        @Override
        public int getCount() {
            if (Settings.isUserSeller()) {
                // Show 3 total pages.
                return 2;
            }else{
                return 3;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (Settings.isUserSeller()){
                switch (position) {
                    case 0: return "Users";
                    case 1: return "Profile";
                }
            }else{
                switch (position) {
                    case 0: return "Map";
                    case 1: return "Offers";
                    case 2: return "Profile";
                }
            }
            return null;
        }
    }
}
