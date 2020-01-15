package com.example.madejava4.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;

import com.example.madejava4.R;
import com.example.madejava4.view.fragment.FavoriteFragment;
import com.example.madejava4.view.fragment.MovieFragment;
import com.example.madejava4.view.fragment.TvShowFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private String TITLE_KEY = "title";
    private String FRAGMENT_KEY = "fragment";
    private String title = "MovieEntity";
    private Fragment fragment;

    @BindView(R.id.nav_view)
    BottomNavigationView navView;

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener =
            menuItem -> {
                switch (menuItem.getItemId()) {
                    case R.id.nav_movie:
                        title = getString(R.string.title_movie);
                        setActionBarTitle(title);
                        fragment = new MovieFragment();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frame_container, fragment, fragment.getClass().getSimpleName())
                                .commit();
                        return true;
                    case R.id.nav_tv_show:
                        title = getString(R.string.title_tv_show);
                        setActionBarTitle(title);
                        fragment = new TvShowFragment();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frame_container, fragment, fragment.getClass().getSimpleName())
                                .commit();
                        return true;
                    case R.id.nav_fav:
                        title = getString(R.string.favorite);
                        fragment = new FavoriteFragment();
                        setActionBarTitle(title);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frame_container,fragment,fragment.getClass().getSimpleName())
                                .commit();
                        return true;
                }
                return false;
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        if (savedInstanceState == null) {
            navView.setSelectedItemId(R.id.nav_movie);
        } else {
            fragment = getSupportFragmentManager().getFragment(savedInstanceState, FRAGMENT_KEY);
            title = savedInstanceState.getString(TITLE_KEY);
            setActionBarTitle(title);
        }
    }
    private void setActionBarTitle(String title){
        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle(title);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.language){
            Intent nIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(nIntent);
        }
        return super.onOptionsItemSelected(item);
    }

}
