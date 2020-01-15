package com.example.madejava4.view.fragment;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.madejava4.R;
import com.example.madejava4.adapter.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class FavoriteFragment extends Fragment {


    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    public FavoriteFragment(){

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.favorite_fragment, container, false);
        ButterKnife.bind(this,view);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFragment(new MovieFavoriteFragment(),getResources().getString(R.string.title_movie));
        viewPagerAdapter.addFragment(new TvShowFavoriteFragment(),getResources().getString(R.string.title_tv_show));
        viewPager.setAdapter(viewPagerAdapter);
    }


}
