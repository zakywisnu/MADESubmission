package com.zeroemotion.madejava5.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.zeroemotion.madejava5.R;
import com.zeroemotion.madejava5.view.fragment.FavoriteFragment;
import com.zeroemotion.madejava5.view.fragment.MovieFragment;
import com.zeroemotion.madejava5.view.fragment.TvShowFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.zeroemotion.madejava5.view.activity.SearchActivity.SEARCH_TYPE;

public class MainActivity extends AppCompatActivity {
    private String TITLE_KEY = "title";
    private String FRAGMENT_KEY = "fragment";
    public String title = "MovieEntity";
    private String searchKey;
    private Fragment fragment;

    @BindView(R.id.nav_view)
    BottomNavigationView navView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener =
            menuItem -> {
                switch (menuItem.getItemId()) {
                    case R.id.nav_movie:
                        title = getString(R.string.title_movie);
                        searchKey = "movie";
                        setActionBarTitle(title);
                        Log.d("searchtype", "Search Key : " + searchKey);
                        fragment = new MovieFragment();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frame_container, fragment, fragment.getClass().getSimpleName())
                                .commit();
                        return true;
                    case R.id.nav_tv_show:
                        title = getString(R.string.title_tv_show);
                        setActionBarTitle(title);
                        searchKey = "tv";
                        Log.d("searchtype", "Search Key : " + searchKey);
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
                                .replace(R.id.frame_container, fragment, fragment.getClass().getSimpleName())
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
        initToolbar();
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        if (savedInstanceState == null) {
            navView.setSelectedItemId(R.id.nav_movie);
        } else {
            fragment = getSupportFragmentManager().getFragment(savedInstanceState, FRAGMENT_KEY);
            title = savedInstanceState.getString(TITLE_KEY);
            setActionBarTitle(title);
        }
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setActionBarTitle(String title) {
        if (getSupportActionBar() != null) {
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
        if (item.getItemId() == R.id.setting) {
            Intent nIntent = new Intent(this, SettingActivity.class);
            startActivity(nIntent);
        } else if (item.getItemId() == R.id.search){
            Intent searchIntent = new Intent(this, SearchActivity.class);
            searchIntent.putExtra("search_type", searchKey);
            startActivity(searchIntent);
        }
        return super.onOptionsItemSelected(item);
    }

}
