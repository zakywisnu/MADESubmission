package com.zeroemotion.madejava5.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.google.android.material.appbar.AppBarLayout;
import com.zeroemotion.madejava5.R;
import com.zeroemotion.madejava5.view.fragment.SettingFragment;

import java.util.Objects;

public class SettingActivity extends AppCompatActivity {

    @BindView(R.id.setting_appbar_layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.setting_toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.holo_orange_dark));
        Objects.requireNonNull(getSupportActionBar()).setTitle("Setting");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportFragmentManager().beginTransaction().replace(R.id.setting_container,new SettingFragment()).commit();
    }
}
