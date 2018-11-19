package com.academy.fundamentals.details;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.academy.fundamentals.R;
import com.academy.fundamentals.model.MoviesContent;

public class DetailsActivity extends AppCompatActivity {

    public static final String EXTRA_ITEM_POSITION = "init-position-data";
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        viewPager = findViewById(R.id.details_vp_container);
        viewPager.setAdapter(mSectionsPagerAdapter);

        int startPosition = getIntent().getIntExtra(EXTRA_ITEM_POSITION, 0);
        viewPager.setCurrentItem(startPosition, false);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return MovieDetailsFragment.newInstance(MoviesContent.MOVIES.get(position));
        }

        @Override
        public int getCount() {
            return MoviesContent.MOVIES.size();
        }
    }
}
