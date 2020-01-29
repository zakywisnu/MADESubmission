package com.zeroemotion.favoritefilm.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.zeroemotion.favoritefilm.R;
import com.zeroemotion.favoritefilm.adapter.ViewPagerAdapter;
import com.zeroemotion.favoritefilm.view.fragment.MovieFragment;
import com.zeroemotion.favoritefilm.view.fragment.TvShowFragment;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MovieFragment(), "Movie");
        adapter.addFragment(new TvShowFragment(), "TV Show");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        toolbar.setTitle("Favorite");
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
    }
}
