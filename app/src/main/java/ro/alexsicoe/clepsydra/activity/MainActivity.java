package ro.alexsicoe.clepsydra.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import ro.alexsicoe.clepsydra.R;
import ro.alexsicoe.clepsydra.model.DFWrapper;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        DFWrapper.setFormat(this);
    }

    @OnClick(R.id.btnProjects)
    public void onClickProjects() {
        Intent intent = new Intent(this, ProjectListActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btnSettings)
    public void onClickSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btnAbout)
    public void onClickAbout() {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }
}
