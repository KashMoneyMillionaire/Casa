package com.example.kash.casa.Activities;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.kash.casa.Fragments.CameraPreviewFragment;
import com.example.kash.casa.Fragments.FriendListFragment;
import com.example.kash.casa.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends Activity
{

    @InjectView(R.id.loginPager)
    ViewPager mPager;

    private static int pageCount;
    private int mPosition;

    private Fragment[] fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        fragments = new Fragment[]{new CameraPreviewFragment(), new FriendListFragment()};
        pageCount = fragments.length;
        mPosition = 0;

        // Instantiate a ViewPager and a PagerAdapter.
        final PagerAdapter mPagerAdapter = new ScreenSlidePagerAdapter(getFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setOffscreenPageLimit(1);
        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {
            }

            @Override
            public void onPageSelected(int position)
            {
                mPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state)
            {
                switch (state)
                {
                    case ViewPager.SCROLL_STATE_IDLE:
                        fixStatusBar();
//                        if(mPosition == 1) {
//                            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
//                        } else {
//                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//                            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
//                        }
                }
            }
        });

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        fixStatusBar();
    }

    @Override
    public void onBackPressed()
    {
        //TODO add back logic
//        if (mPager.getCurrentItem() == 0) {
//            // If the user is currently looking at the first step, allow the system to handle the
//            // Back button. This calls finish() on this activity and pops the back stack.
//            super.onBackPressed();
//        } else {
//            // Otherwise, select the previous step.
//            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
//        }
        super.onBackPressed();
    }

    public void fixStatusBar()
    {
        View decorView = getWindow().getDecorView();
        int uiOptions = 0;
        if (fragments[mPosition] instanceof CameraPreviewFragment)
        {
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
            // Hide the status bar.
            uiOptions =
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                    View.SYSTEM_UI_FLAG_FULLSCREEN;
//                View.SYSTEM_UI_FLAG_LOW_PROFILE;
        }
        else
        {
//            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

            // Show the status bar.
            uiOptions =
//                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//                View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        }
        decorView.setSystemUiVisibility(uiOptions);
    }

    private class ScreenSlidePagerAdapter extends FragmentPagerAdapter
    {
        public ScreenSlidePagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            return fragments[position];
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            return "Camera";
        }

        @Override
        public int getCount()
        {
            return pageCount;
        }
    }
}