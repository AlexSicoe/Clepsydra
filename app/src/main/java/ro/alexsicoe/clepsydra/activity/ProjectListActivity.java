package ro.alexsicoe.clepsydra.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ro.alexsicoe.clepsydra.R;
import ro.alexsicoe.clepsydra.fragment.ProjectListFragment;

public class ProjectListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(ro.alexsicoe.clepsydra.R.layout.activity_project_list);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.root_layout, ProjectListFragment.newInstance(1), "projectListFragment")
                    .commit();
        }


    }



}
