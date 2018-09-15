package ro.alexsicoe.clepsydra.controller.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import ro.alexsicoe.clepsydra.R;
import ro.alexsicoe.clepsydra.controller.fragment.UserTaskListFragment;
import ro.alexsicoe.clepsydra.model.Project;

public class ProjectActivity extends AppCompatActivity {
    private final int PROFILE_PIC_SIZE = 250;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nav_view)
    NavigationView navView;
    private String userEmail;
    private String userName;
    private Uri profilePictureLink;
    private Project project;
    private String projectId;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
        ButterKnife.bind(this);
        getAccountDetails();
        db = FirebaseFirestore.getInstance();


        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        setNav();

        project = (Project) getIntent().getSerializableExtra("Project");
        setTitle(project.getName());
        projectId = project.getId();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.root_layout, UserTaskListFragment.newInstance(projectId), "userTaskListFragment")
                .commit();

        View headerView = navView.getHeaderView(0);
        ImageView ivProfilePicture = headerView.findViewById(R.id.ivProfilePicture);
        TextView tvUserName = headerView.findViewById(R.id.tvUserName);
        TextView tvEmail = headerView.findViewById(R.id.tvEmail);

        Picasso.get().load(profilePictureLink).resize(PROFILE_PIC_SIZE, PROFILE_PIC_SIZE).into(ivProfilePicture);
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
                    Toast.makeText(ProjectActivity.this, "TODO", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.nav_checklist) {
                    Toast.makeText(ProjectActivity.this, "TODO", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.nav_track_progress) {
                    Toast.makeText(ProjectActivity.this, "TODO", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.nav_manage) {
                    Toast.makeText(ProjectActivity.this, "TODO", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.nav_share) {
                    shareProject();
                } else if (id == R.id.nav_add) {
                    Toast.makeText(ProjectActivity.this, "TODO", Toast.LENGTH_SHORT).show();
                }

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    private void shareProject() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.share_project);
        builder.setMessage(R.string.please_insert_friends_email);
        final EditText et = new EditText(this);

        //TODO validate email & existing in db as registered user
        et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        et.setHint(R.string.friends_email_address);
        builder.setView(et);

        builder.setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String friendEmail = et.getText().toString().trim();

                Map<String, Object> map = new HashMap<>();
                map.put("email", friendEmail);
                map.put("projectId", projectId);
                db.collection("UserProjects").add(map);

                Toast.makeText(ProjectActivity.this, getString(R.string.friend_added_successfully), Toast.LENGTH_SHORT).show();
            }
        }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
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
