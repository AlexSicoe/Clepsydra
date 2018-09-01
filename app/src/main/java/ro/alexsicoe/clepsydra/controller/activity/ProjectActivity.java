package ro.alexsicoe.clepsydra.controller.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import ro.alexsicoe.clepsydra.R;
import ro.alexsicoe.clepsydra.async.DownloadImageTask;
import ro.alexsicoe.clepsydra.controller.fragment.UserTaskListFragment;

public class ProjectActivity extends AppCompatActivity {
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nav_view)
    NavigationView navView;
    private String userEmail;
    private String userName;
    private Uri profilePictureLink;
    private final int PROFILE_PIC_SIZE = 250;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
        ButterKnife.bind(this);
        getAccountDetails();

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        setNav();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.root_layout, UserTaskListFragment.newInstance(), "userTaskListFragment")
                .commit();

        View headerView = navView.getHeaderView(0);
        ImageView ivProfilePicture = headerView.findViewById(R.id.ivProfilePicture);
        TextView tvUserName = headerView.findViewById(R.id.tvUserName);
        TextView tvEmail = headerView.findViewById(R.id.tvEmail);

        Picasso.get().load(profilePictureLink).resize(PROFILE_PIC_SIZE,PROFILE_PIC_SIZE).into(ivProfilePicture);
        tvUserName.setText(userName);
        tvEmail.setText(userEmail);
    }

    private void getAccountDetails() {
        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (googleSignInAccount != null) {
            userEmail = googleSignInAccount.getEmail();
            userName = googleSignInAccount.getDisplayName();
            profilePictureLink = googleSignInAccount.getPhotoUrl();
        }
    }


    private void setNav() {
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();


                if (id == R.id.nav_resources) {
                    //TODO
                } else if (id == R.id.nav_checklist) {

                } else if (id == R.id.nav_track_progress) {

                } else if (id == R.id.nav_manage) {
                    Toast.makeText(ProjectActivity.this, "TODO", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.nav_share) {

                } else if (id == R.id.nav_add) {

                }

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_project, menu);
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
}
